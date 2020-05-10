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

    public Meeting() {}

    public Meeting(
            int id,
            String name,
            boolean handRaised,
            boolean breakingQuestion,
            boolean requestToSpeak
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

}
