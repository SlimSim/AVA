package com.slimsimapps.ava.participant;

import com.slimsimapps.ava.meeting.Meeting;
/*
import javax.persistence.*;

@Entity
*/
public class Participant {

//    @Id
//    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private String name;
    private boolean breakingQuestion;
    private long breakingQuestionTime;
    private boolean information;
    private long informationTime;
    private boolean comment;
    private long commentTime;
    private boolean requestToSpeak;
    private long requestToSpeakTime;
    private boolean handRaised;
    private long handRaisedTime;


//    @ManyToOne
    private Meeting meeting;

    public Participant() {}

    public Participant(String name) {
        super();
        this.name = name;
    }

    public Participant(String name, int id) {
        super();
        this.name = name;
        this.id = id;
    }

    public Participant(
            int id,
            String name,
            boolean handRaised,
            boolean breakingQuestion,
            boolean requestToSpeak,
            int meetingId
    ) {
        super();
        this.id = id;
        this.name = name;
        this.handRaised = handRaised;
        this.breakingQuestion = breakingQuestion;
        this.requestToSpeak = requestToSpeak;
        this.meeting = new Meeting( meetingId );
    }

    public String toString(){
        return "id " + id
                + ", name " + name
                + (meeting==null ? ", null" : ", meeting " + meeting.getId()  + " " + meeting.getName() )
                + ", breakingQuestion " + breakingQuestion
                + ", breakingQuestionTime " + breakingQuestionTime
                ;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isHandRaised() {
        return handRaised;
    }

    public void setHandRaised(boolean handRaised) {
        this.handRaised = handRaised;
    }

    public boolean isBreakingQuestion() {
        return breakingQuestion;
    }

    public void setBreakingQuestion(boolean breakingQuestion) {
        this.breakingQuestion = breakingQuestion;
    }

    public boolean isRequestToSpeak() {
        return requestToSpeak;
    }

    public void setRequestToSpeak(boolean requestToSpeak) {
        this.requestToSpeak = requestToSpeak;
    }


    public Meeting getMeeting() {
        return meeting;
    }

    public void setMeeting(Meeting meeting) {
        this.meeting = meeting;
    }

    public boolean isComment() {
        return comment;
    }

    public void setComment(boolean comment) {
        this.comment = comment;
    }

    public boolean isInformation() {
        return information;
    }

    public void setInformation(boolean information) {
        this.information = information;
    }

    public long getBreakingQuestionTime() {
        return breakingQuestionTime;
    }

    public void setBreakingQuestionTime(long breakingQuestionTime) {
        this.breakingQuestionTime = breakingQuestionTime;
    }

    public long getInformationTime() {
        return informationTime;
    }

    public void setInformationTime(long informationTime) {
        this.informationTime = informationTime;
    }

    public long getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(long commentTime) {
        this.commentTime = commentTime;
    }

    public long getRequestToSpeakTime() {
        return requestToSpeakTime;
    }

    public void setRequestToSpeakTime(long requestToSpeakTime) {
        this.requestToSpeakTime = requestToSpeakTime;
    }

    public long getHandRaisedTime() {
        return handRaisedTime;
    }

    public void setHandRaisedTime(long handRaisedTime) {
        this.handRaisedTime = handRaisedTime;
    }
}
