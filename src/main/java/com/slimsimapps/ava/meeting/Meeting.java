package com.slimsimapps.ava.meeting;

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

}
