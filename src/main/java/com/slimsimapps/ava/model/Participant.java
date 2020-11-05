package com.slimsimapps.ava.model;

import com.slimsimapps.ava.model.Meeting;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/*
import javax.persistence.*;

@Entity
*/
@Getter
@Setter
@Accessors(chain = true)
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
    private boolean voteYes;
    private long voteYesTime;
    private boolean voteNo;
    private long voteNoTime;
    private boolean showBreakingQuestion;
    private boolean showInformation;
    private boolean showComment;
    private boolean showRequestToSpeak;
    private boolean showVoteYes;
    private boolean showVoteNo;


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
            boolean voteYes,
            boolean voteNo,
            boolean breakingQuestion,
            boolean requestToSpeak,
            int meetingId
    ) {
        super();
        this.id = id;
        this.name = name;
        this.voteYes = voteYes;
        this.voteNo = voteNo;
        this.breakingQuestion = breakingQuestion;
        this.requestToSpeak = requestToSpeak;
        this.meeting = new Meeting( meetingId );
    }

    public String toString(){
        return "id " + id
                + ", name " + name
                + (meeting==null ? ", null" : ", meeting " + meeting.getId()  + " " + meeting.getName() )
                //+ ", breakingQuestion " + breakingQuestion
                //+ ", breakingQuestionTime " + breakingQuestionTime
                + ", showBreakingQuestion " + showBreakingQuestion
                //+ ", showInformation " + showInformation
                //+ ", showComment " + showComment
                //+ ", showRequestToSpeak " + showRequestToSpeak
                //+ ", showVoteYes " + showVoteYes
                + ", showVoteNo " + showVoteNo
                ;
    }

}
