package com.slimsimapps.ava.badlog;

/*
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
*/

public class BadLog {

    private String timeStamp;
    private String fileName;
    private String methodName;
    private int lineNumber;
    private String level;
    private String logInfo;

    public BadLog() {}
    public BadLog(String logInfo) {
        super();
        this.logInfo = logInfo;
    }

    public BadLog(
            String timeStamp,
            String fileName,
            String methodName,
            int lineNumber,
            String level,
            String logInfo
    ) {
        super();
        this.timeStamp = timeStamp;
        this.fileName = fileName;
        this.methodName = methodName;
        this.lineNumber = lineNumber;
        this.level = level;
        this.logInfo = logInfo;
    }


    public String toString(){
        return "level " + level
                + ", fileName " + fileName
                //+ ", methodName " + methodName
                + ", lineNumber " + lineNumber
                + ", logInfo " + logInfo
                ;
    }

    public String getLogInfo() {
        return logInfo;
    }

    public void setLogInfo(String logInfo) {
        this.logInfo = logInfo;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
