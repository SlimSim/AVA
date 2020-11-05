package com.slimsimapps.ava.model.badlog;

import lombok.Getter;
import lombok.Setter;
/*
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
*/

@Getter
@Setter
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

}
