package com.slimsimapps.ava.meeting;

import com.slimsimapps.ava.MainController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;import java.util.List;

@Service
public class MeetingService {

    //@Autowired
    //private MeetingRepository meetingRepository;

    private ArrayList<Meeting> meetingList = new ArrayList<>();

    Logger log = LoggerFactory.getLogger(MainController.class);

    public List<Meeting> getAllMeetings() {
        //List<Meeting> x = meetingRepository.findAll();;
        return meetingList;
    }

    public Meeting getMeeting(int id) throws Exception {
        return meetingList.stream().filter( meeting -> meeting.getId() == id ).findFirst().get();
        //return meetingRepository.findById(id).orElseThrow(
        //        () -> new Exception("No Meeting found with id " + id) );
    }

    public Meeting addMeeting(Meeting meeting) throws Exception {
        if( meeting == null ) {
            throw new Exception("No body found, meeting is null");
        }
        ArrayList<Integer> ids = new ArrayList<>();
        for (Meeting m : meetingList) {
            ids.add(m.getId());
        }
        int id = 1;
        while( ids.contains( id ) ) {
            id++;
        }
        meeting.setId( id );
        meetingList.add( meeting );
        //Meeting m = meetingRepository.save(meeting);
        return meeting;
    }

    public Meeting updateMeeting(int id, Meeting updatedMeetingData) throws Exception {
        //if( !meetingRepository.existsById(id) ) {
        //    throw new Exception("No Meeting found with id " + id);
        //}
        meetingList.set( id, updatedMeetingData );
        return updatedMeetingData;
        //return meetingRepository.save(updatedMeetingData);
    }

    public void deleteMeeting(int id) throws Exception {
        /*
        if( !meetingRepository.existsById(id) ) {
            throw new Exception("No Meeting found with id " + id);
        }
        meetingRepository.deleteById( id );
        */
        meetingList.remove( id );
    }
}
