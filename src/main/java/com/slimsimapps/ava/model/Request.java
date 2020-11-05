package com.slimsimapps.ava.model;

import com.slimsimapps.ava.enums.RequestType;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class Request {

    private int participantId;
    private String participantName;
    private RequestType requestType;
    private boolean active;

    public Request() {}

    public Request(int participantId, String name, RequestType requestType, boolean active) {
        super();
        this.participantId = participantId;
        this.participantName = name;
        this.requestType = requestType;
        this.active = active;
    }

    public String toString(){
        return "participantId " + participantId
                + ", participantName " + participantName
                + ", typeOfRequest " + requestType
                + ", active " + active
                ;
    }

}
