package com.slimsimapps.ava.service;

import com.slimsimapps.ava.model.badlog.BadLog;
import com.slimsimapps.ava.controller.v1.ui.MainController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;

@Service
public class BadLogService {

    Logger javaLog = LoggerFactory.getLogger(MainController.class);

    private static ArrayList<BadLog> badLogList = new ArrayList<>();


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
            String o = "";
            for ( StackTraceElement ste : Thread.currentThread().getStackTrace()) {
                if( !ste.toString().startsWith( "com.slimsimapps" )
                            || "BadLogService.java".equals( ste.getFileName())
                            || "AvaException.java".equals( ste.getFileName())
                ) {
                    continue;
                }
                badLogList.add( new BadLog( timestamp, ste.getFileName(), ste.getMethodName(), ste.getLineNumber(), level, log ) );
                timestamp = "";
                log = "at";
            }
        } else {
            badLogList.add( new BadLog( timestamp, fileName, methodName, lineNumber, level, objectString.toString() ) );
        }


        /*
        switch (level) {

            case "dev":     javaLog.debug( fileName + " " + lineNumber + " " + text ); break;
            case "info":    javaLog.info( fileName + " " + lineNumber + " " + text ); break;
            case "warn":    javaLog.warn( fileName + " " + lineNumber + " " + text ); break;
            case "error":   javaLog.error( fileName + " " + lineNumber + " " + text ); break;
            //case "trace":   javaLog.trace( fileName + " " + lineNumber + " " + text ); break;
            default:        javaLog.trace( fileName + " " + lineNumber + " " + text ); break;
        }
         */


    }

    public void a( Object ...o ) { great( "trace", "->", o ); }
    public void o( Object ...o ) { great( "trace", "<-", o ); }
    public void t( String text, Object ...o ) { great( "trace", text, o ); }
    public void d( String text, Object ...o ) { great( "dev", text, o ); }
    public void i( String text, Object ...o ) { great( "info", text, o ); }
    public void w( String text, Object ...o ) { great( "warn", text, o ); }
    public void e( String text, Object ...o ) { great( "error", text, o ); }
    public static void thrownException( String text, Object ...o ) { great( "thrown", text, o ); }

}
