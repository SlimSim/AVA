package com.slimsimapps.ava.request;

public class Request {

    private int participantId;
    private TypeOfRequest typeOfRequest;
    private boolean active;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public enum TypeOfRequest {
        breakingQuestion,
        information,
        comment,
        requestToSpeak,
        handRaised;
    }

    public Request() {}

    public Request(int participantId, TypeOfRequest typeOfRequest, boolean active) {
        super();
        this.participantId = participantId;
        this.typeOfRequest = typeOfRequest;
        this.active = active;
    }
    public int getParticipantId() {
        return participantId;
    }

    public void setParticipantId(int participantId) {
        this.participantId = participantId;
    }

    public TypeOfRequest getTypeOfRequest() {
        return typeOfRequest;
    }

    public void setTypeOfRequest(TypeOfRequest typeOfRequest) {
        this.typeOfRequest = typeOfRequest;
    }

    public String toString(){
        return "participantId " + participantId
                + ", typeOfRequest " + typeOfRequest
                + ", active " + active
                ;
    }

}
