package com.slimsimapps.ava.meeting;

/*
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
*/

public class Meeting {

//    @Id
//    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private String name;

    private boolean showBreakingQuestion;
    private boolean showInformation;
    private boolean showComment;
    private boolean showRequestToSpeak;
    private boolean showVoteYes;
    private boolean showVoteNo;

    public Meeting() {}

    public Meeting(
            int id,
            String name
    ) {
        super();
        this.id = id;
        this.name = name;
    }

    public Meeting(int meetingId) {
        super();
        this.id = meetingId;
    }

    public String toString(){
        return "id " + id
                + ", name " + name
                + ", showBreakingQuestion " + showBreakingQuestion
                //+ ", showInformation " + showInformation
                //+ ", showComment " + showComment
                //+ ", showRequestToSpeak " + showRequestToSpeak
                //+ ", showVoteYes " + showVoteYes
                + ", showVoteNo " + showVoteNo
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

    public boolean isShowBreakingQuestion() {
        return showBreakingQuestion;
    }

    public void setShowBreakingQuestion(boolean showBreakingQuestion) {
        this.showBreakingQuestion = showBreakingQuestion;
    }

    public boolean isShowInformation() {
        return showInformation;
    }

    public void setShowInformation(boolean showInformation) {
        this.showInformation = showInformation;
    }

    public boolean isShowComment() {
        return showComment;
    }

    public void setShowComment(boolean showComment) {
        this.showComment = showComment;
    }

    public boolean isShowRequestToSpeak() {
        return showRequestToSpeak;
    }

    public void setShowRequestToSpeak(boolean showRequestToSpeak) {
        this.showRequestToSpeak = showRequestToSpeak;
    }

    public boolean isShowVoteYes() {
        return showVoteYes;
    }

    public void setShowVoteYes(boolean showVoteYes) {
        this.showVoteYes = showVoteYes;
    }

    public boolean isShowVoteNo() {
        return showVoteNo;
    }

    public void setShowVoteNo(boolean showVoteNo) {
        this.showVoteNo = showVoteNo;
    }
}
