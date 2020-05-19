package com.slimsimapps.ava.request;

public class Request {

    private int participantId;
    private String participantName;
    private TypeOfRequest typeOfRequest;
    private boolean active;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getParticipantName() {
        return participantName;
    }

    public void setParticipantName(String participantName) {
        this.participantName = participantName;
    }

    public enum TypeOfRequest {
        breakingQuestion,
        information,
        comment,
        requestToSpeak,
        handRaised;
    }

    public Request() {}

    public Request(int participantId, String name, TypeOfRequest typeOfRequest, boolean active) {
        super();
        this.participantId = participantId;
        this.participantName = name;
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
                + ", participantName " + participantName
                + ", typeOfRequest " + typeOfRequest
                + ", active " + active
                ;
    }

}
