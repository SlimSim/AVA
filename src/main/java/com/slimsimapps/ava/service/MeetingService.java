package com.slimsimapps.ava.service;

import com.slimsimapps.ava.dto.mapper.MeetingMapper;
import com.slimsimapps.ava.dto.model.MeetingDto;
import com.slimsimapps.ava.model.Meeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;import java.util.List;
import java.util.stream.Collectors;

@Service
public class MeetingService {

    //@Autowired
    //private MeetingRepository meetingRepository;

    @Autowired
    BadLogService log;


    private ArrayList<Meeting> meetingList = new ArrayList<>();

    public List<MeetingDto> getAllMeetings() {
        log.a();
        //List<Meeting> x = meetingRepository.findAll();;
        log.o();
        return meetingList.stream().map( MeetingMapper::toMeetingDto ).collect(Collectors.toList());
    }

    public MeetingDto getMeeting(int id) throws Exception {
        log.a( id );
        log.o();
        return MeetingMapper.toMeetingDto( meetingList.stream().filter( meeting -> meeting.getId() == id ).findFirst().get() );
        //return meetingRepository.findById(id).orElseThrow(
        //        () -> new Exception("No Meeting found with id " + id) );
    }

    public MeetingDto addMeeting(MeetingDto meetingDto) throws Exception {
        log.a( meetingDto );
        if( meetingDto == null ) {
            throw new Exception("No body found, meetingDto is null");
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
        meetingDto.setId( id );
        meetingList.add( MeetingMapper.toMeeting( meetingDto ) );
        //Meeting m = meetingRepository.save(meetingDto);
        log.o(meetingDto);
        return meetingDto;
    }

    public MeetingDto updateMeeting(int id, MeetingDto updatedMeetingData) throws Exception {
        log.a(id, updatedMeetingData);
        //if( !meetingRepository.existsById(id) ) {
        //    throw new Exception("No Meeting found with id " + id);
        //}
        meetingList.set( id, MeetingMapper.toMeeting( updatedMeetingData ) );
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
