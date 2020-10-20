package com.slimsimapps.ava;

import com.slimsimapps.ava.meeting.Meeting;
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

    public void clearAllBadLogs() {
        badLogList.clear();
    }


    //------------------------- standard log functions:

    /**
     * NOTE: ALL info- log- debug- error- and so on methods must directly call the "great" method
     * otherwise the stackTraceElement will be off by an amount!
     */
    private void great( String level, String text ) {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];

        String timestamp = new Timestamp(System.currentTimeMillis()).toLocalDateTime().toString();
        String fileName = stackTraceElement.getFileName();
        String methodName = stackTraceElement.getMethodName();
        int lineNumber = stackTraceElement.getLineNumber();

        badLogList.add( new BadLog( timestamp, fileName, methodName, lineNumber, level, text ) );

        switch (level) {
            case "trace": javaLog.trace( fileName + " " + lineNumber + " " + text ); break;
            case "dev": javaLog.debug( fileName + " " + lineNumber + " " + text ); break;
            case "info": javaLog.info( fileName + " " + lineNumber + " " + text ); break;
            case "warn": javaLog.warn( fileName + " " + lineNumber + " " + text ); break;
            case "error": javaLog.error( fileName + " " + lineNumber + " " + text ); break;
        }


    }


    public void t( String text ) { great( "trace", text ); }
    public void d( String text ) { great( "dev", text ); }
    public void i( String text ) { great( "info", text ); }
    public void w( String text ) { great( "warn", text ); }
    public void e( String text ) { great( "error", text ); }



}
