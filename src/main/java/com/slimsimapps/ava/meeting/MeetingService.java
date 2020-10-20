package com.slimsimapps.ava.meeting;

import com.slimsimapps.ava.badlog.BadLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;import java.util.List;

@Service
public class MeetingService {

    //@Autowired
    //private MeetingRepository meetingRepository;

    @Autowired
    BadLogService log;


    private ArrayList<Meeting> meetingList = new ArrayList<>();

    public List<Meeting> getAllMeetings() {
        log.a();
        //List<Meeting> x = meetingRepository.findAll();;
        log.o();
        return meetingList;
    }

    public Meeting getMeeting(int id) throws Exception {
        log.a( id );
        log.o();
        return meetingList.stream().filter( meeting -> meeting.getId() == id ).findFirst().get();
        //return meetingRepository.findById(id).orElseThrow(
        //        () -> new Exception("No Meeting found with id " + id) );
    }

    public Meeting addMeeting(Meeting meeting) throws Exception {
        log.a( meeting );
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
        log.d( "id = " + id );
        meeting.setId( id );
        meetingList.add( meeting );
        //Meeting m = meetingRepository.save(meeting);
        log.o(meeting);
        return meeting;
    }

    public Meeting updateMeeting(int id, Meeting updatedMeetingData) throws Exception {
        log.a(id, updatedMeetingData);
        //if( !meetingRepository.existsById(id) ) {
        //    throw new Exception("No Meeting found with id " + id);
        //}
        meetingList.set( id, updatedMeetingData );
        log.o(updatedMeetingData);
        return updatedMeetingData;
        //return meetingRepository.save(updatedMeetingData);
    }

    public void deleteMeeting(int id) throws Exception {
        log.a(id);
        /*
        if( !meetingRepository.existsById(id) ) {
            throw new Exception("No Meeting found with id " + id);
        }
        meetingRepository.deleteById( id );
        */
        log.o(id);
        meetingList.remove( id );
    }
}
