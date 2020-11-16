package com.slimsimapps.ava.service;

import com.slimsimapps.ava.model.badlog.BadLog;
import com.slimsimapps.ava.controller.v1.ui.MainController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.sql.Timestamp;

@Service
public class BadLogService {

    private static final Logger javaLog = LoggerFactory.getLogger(MainController.class);

    private static final ArrayList<BadLog> badLogList = new ArrayList<>();

    private static final List<String> logLevels = Arrays.asList("trace", "dev", "info", "warn", "error", "thrown");

    private static String badLogLevel;
    private static String consoleLogLevel;
    private static boolean consoleShowThrown;


    @Value("${badLog.badLogLevel}")
    public void setBadLogLevel(String badLogLevel) {
        BadLogService.badLogLevel = badLogLevel;
    }

    @Value("${badLog.consoleLogLevel}")
    public void setConsoleLogLevel(String consoleLogLevel) {
        BadLogService.consoleLogLevel = consoleLogLevel;
    }

    @Value("${badLog.consoleShowThrown}")
    public void setConsoleShowThrown(boolean consoleShowThrown) {
        BadLogService.consoleShowThrown = consoleShowThrown;
    }


    public List<BadLog> getAllBadLogs() {
        return badLogList;
    }
    public BadLog getLatestBadLog() {
        if( badLogList.isEmpty() ) {
            return new BadLog("No BadLog");
        }

        return badLogList.get( badLogList.size() -1 );
    }

    public void clearAllBadLogs() { badLogList.clear();  }


    //------------------------- standard log functions:

    /**
     * NOTE: ALL info- log- debug- error- and so on methods must directly call the "great" method
     * otherwise the stackTraceElement will be off by an amount!
     */
    private static void great( String level, final String text, Object ...objects ) {
        int stackTraceLevel = 3;

        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[ stackTraceLevel ];

        StringBuilder objectString = new StringBuilder();
        objectString.append( text );

        if( objects.length > 0 ){
            if( text.endsWith( "<-" ) || text.endsWith( "->" ) ) {
                objectString.append( " " );
            } else {
                objectString.append( ", " );
            }

            for (Object o : objects) {
                String name = o.getClass().getName();
                objectString
                        .append( name.substring( name.lastIndexOf(".") + 1 ) )
                        .append(": ")
                        .append(o)
                        .append(", ");
            }
            int strLength = objectString.length();
            objectString.delete(strLength - 2, strLength);
        }

        String timestamp = new Timestamp(System.currentTimeMillis()).toLocalDateTime().toString();
        String fileName = stackTraceElement.getFileName();
        String methodName = stackTraceElement.getMethodName();
        int lineNumber = stackTraceElement.getLineNumber();

        if( level.equals( "thrown" ) ) {
            String log = objectString.toString();
            for ( StackTraceElement ste : Thread.currentThread().getStackTrace()) {
                if( !ste.toString().startsWith( "com.slimsimapps" )
                            || "BadLogService.java".equals( ste.getFileName())
                            || "AvaException.java".equals( ste.getFileName())
                ) {
                    continue;
                }
                addBadLog( timestamp, ste.getFileName(), ste.getMethodName(), ste.getLineNumber(), level, log );
                timestamp = "";
                log = "at";
            }
        } else {
            addBadLog( timestamp, fileName, methodName, lineNumber, level, objectString.toString() );
        }


        addConsole( timestamp, fileName, methodName, lineNumber, level, objectString.toString() );
    }

    private static void addConsole(String timestamp, String fileName, String methodName, int lineNumber, String level, String logInfo) {

        if( "none".equals( consoleLogLevel ) || logLevels.indexOf( level ) < logLevels.indexOf( consoleLogLevel )  ) {
            return;
        }

        switch (level) {
            case "trace":
                javaLog.trace( fileName + " " + lineNumber + " " + logInfo );
                break;
            case "dev":
                javaLog.debug(fileName + " " + lineNumber + " " + logInfo);
                break;
            case "info":
                javaLog.info(fileName + " " + lineNumber + " " + logInfo);
                break;
            case "warn":
                javaLog.warn(fileName + " " + lineNumber + " " + logInfo);
                break;
            case "error":
                javaLog.error(fileName + " " + lineNumber + " " + logInfo);
                break;
            case "thrown":
                if( consoleShowThrown ) {
                    javaLog.error(fileName + " " + lineNumber + " " + logInfo);
                }
                break;
            default:
                javaLog.trace(fileName + " " + lineNumber + " " + logInfo);
                break;
        }
    }

    private static void addBadLog(String timestamp, String fileName, String methodName, int lineNumber, String level, String logInfo) {
        if( logLevels.indexOf(level) < logLevels.indexOf( badLogLevel )  ) {
            return;
        }
        badLogList.add(new BadLog(timestamp, fileName, methodName, lineNumber, level, logInfo));

    }

    public void ao( Object ...o ) { great( "trace", "-><", o ); }
    public void a( Object ...o ) { great( "trace", "->", o ); }
    public void o( Object ...o ) { great( "trace", "<-", o ); }
    public void t( String text, Object ...o ) { great( "trace", text, o ); }
    public void d( String text, Object ...o ) { great( "dev", text, o ); }
    public void i( String text, Object ...o ) { great( "info", text, o ); }
    public void w( String text, Object ...o ) { great( "warn", text, o ); }
    public void e( String text, Object ...o ) { great( "error", text, o ); }
    public static void thrownException( String text, Object ...o ) { great( "thrown", text, o ); }
    public static void start_end( Object ...o) { great( "trace", "-><", o ); }
    public static void start( Object ...o) { great( "trace", "->", o ); }
    public static void end( Object ...o) { great( "trace", "<-", o ); }

}
