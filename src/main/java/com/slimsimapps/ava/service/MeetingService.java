package com.slimsimapps.ava.service;

import com.slimsimapps.ava.dto.mapper.MeetingMapper;
import com.slimsimapps.ava.dto.model.MeetingDto;
import com.slimsimapps.ava.enums.EntityType;
import com.slimsimapps.ava.enums.ExceptionType;
import com.slimsimapps.ava.model.Meeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;import java.util.List;
import java.util.stream.Collectors;

import static com.slimsimapps.ava.enums.EntityType.MEETING;
import static com.slimsimapps.ava.enums.ExceptionType.ENTITY_NOT_FOUND;
import static com.slimsimapps.ava.enums.ExceptionType.ENTITY_NULL;
import static com.slimsimapps.ava.exception.AvaException.throwException;

@Service
public class MeetingService {

    //@Autowired
    //private MeetingRepository meetingRepository;

    @Autowired
    BadLogService log;


    private ArrayList<Meeting> meetingList = new ArrayList<>();

    public List<MeetingDto> getAllMeetings() {
        log.ao();
        return meetingList.stream().map( MeetingMapper::toMeetingDto ).collect(Collectors.toList());
    }

    public MeetingDto getMeeting(int id) throws RuntimeException {
        log.ao( id );
        return MeetingMapper.toMeetingDto(
                meetingList.stream().filter( m -> m.getId() == id ).findFirst().orElseThrow(
                        () -> throwException(MEETING, ENTITY_NOT_FOUND, id) ));
        //return meetingRepository.findById(id).orElseThrow(
        //        () -> new Exception("No Meeting found with id " + id) );
    }

    public MeetingDto addMeeting(MeetingDto meetingDto) throws RuntimeException {
        log.a( meetingDto );
        if( meetingDto == null ) {
            throw throwException(MEETING, ENTITY_NULL);
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

    public MeetingDto updateMeeting(int id, MeetingDto updatedMeetingData) throws RuntimeException {
        log.a(id, updatedMeetingData);
        //if( !meetingRepository.existsById(id) ) {
        //    throw new Exception("No Meeting found with id " + id);
        //}
        getMeeting( id ); // check if meeting exists, otherwise throw exception
        meetingList.set( id, MeetingMapper.toMeeting( updatedMeetingData ) );
        log.o(updatedMeetingData);
        return updatedMeetingData;
        //return meetingRepository.save(updatedMeetingData);
    }

    public void deleteMeeting(int id) throws RuntimeException {
        log.a(id);
        /*
        if( !meetingRepository.existsById(id) ) {
            throw new Exception("No Meeting found with id " + id);
        }
        meetingRepository.deleteById( id );
        */
        getMeeting( id ); // check if meeting exists, otherwise throw exception
        meetingList.remove( id );
        log.o(id);
    }
}
