package com.slimsimapps.ava.badlog;

import com.slimsimapps.ava.MainController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;

@Service
public class BadLogService {

    Logger javaLog = LoggerFactory.getLogger(MainController.class);

    private ArrayList<BadLog> badLogList = new ArrayList<>();


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
    private void great( String level, final String text, Object ...objects ) {
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];

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

        badLogList.add( new BadLog( timestamp, fileName, methodName, lineNumber, level, objectString.toString() ) );

        switch (level) {

            case "dev":     javaLog.debug( fileName + " " + lineNumber + " " + text ); break;
            case "info":    javaLog.info( fileName + " " + lineNumber + " " + text ); break;
            case "warn":    javaLog.warn( fileName + " " + lineNumber + " " + text ); break;
            case "error":   javaLog.error( fileName + " " + lineNumber + " " + text ); break;
            //case "trace":   javaLog.trace( fileName + " " + lineNumber + " " + text ); break;
            default:        javaLog.trace( fileName + " " + lineNumber + " " + text ); break;
        }


    }

    public void a( Object ...o ) { great( "trace", "->", o ); }
    public void o( Object ...o ) { great( "trace", "<-", o ); }
    public void t( String text, Object ...o ) { great( "trace", text, o ); }
    public void d( String text, Object ...o ) { great( "dev", text, o ); }
    public void i( String text, Object ...o ) { great( "info", text, o ); }
    public void w( String text, Object ...o ) { great( "warn", text, o ); }
    public void e( String text, Object ...o ) { great( "error", text, o ); }

}
