package com.slimsimapps.ava.model;

import com.slimsimapps.ava.enums.TypeOfRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class Request {

    private int participantId;
    private String participantName;
    private TypeOfRequest typeOfRequest;
    private boolean active;

    public Request() {}

    public Request(int participantId, String name, TypeOfRequest typeOfRequest, boolean active) {
        super();
        this.participantId = participantId;
        this.participantName = name;
        this.typeOfRequest = typeOfRequest;
        this.active = active;
    }

    public String toString(){
        return "participantId " + participantId
                + ", participantName " + participantName
                + ", typeOfRequest " + typeOfRequest
                + ", active " + active
                ;
    }

}
