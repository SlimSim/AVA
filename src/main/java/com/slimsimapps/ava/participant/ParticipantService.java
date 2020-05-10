package com.slimsimapps.ava.participant;

import com.slimsimapps.ava.MainController;
import com.slimsimapps.ava.meeting.Meeting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParticipantService {

    //@Autowired
    //private ParticipantRepository participantRepository;


    Logger log = LoggerFactory.getLogger(MainController.class);

    List<Participant> participants = new ArrayList<>();

    /*
    List<Participant> participants = new ArrayList<>( Arrays. asList(
            new Participant(1, "Adam", false, false, false),
            new Participant(2, "Berit", true, false, false),
            new Participant(3, "Cesar", false, false, false),
            new Participant(4, "Doris", false, false, true),
            new Participant(5, "Erika", false, true, false),
            new Participant(6, "Filippa", false, false, true)
    ) );
    */



    public List<Participant> getAllParticipants( int meetingId ) {
        /*
        List<Participant> participants = new ArrayList<>();
        Iterable<Participant> meetingIterable = participantRepository.findByMeetingId( meetingId );
        meetingIterable.forEach(participants::add);
        return participantRepository.findByMeetingId( meetingId );
        */
        return participants;
    }

    public Participant getParticipant(int id) throws Exception {
        return participants.stream().filter( participant -> participant.getId() == id ).findFirst().get();
        //return participantRepository.findById(id).orElseThrow(
        //        () -> new Exception("No Participant found with id " + id) );
    }

    public Participant addParticipant(Participant participant, int meetingId) throws Exception {
        log.info("createMe: participant = " + participant);
        if( participant == null ) {
            throw new Exception("No body found, participant is null");
        }
        participant.setMeeting(new Meeting( meetingId ) );
        participants.add( participant );
        return participant;
        //return participantRepository.save(participant);
    }

    public Participant updateParticipant(int participantId, int meetingId, Participant updatedParticipantData) throws Exception {
        /*
        if( !participantRepository.existsById(participantId) ) {
            throw new Exception("No Participant found with id " + participantId);
        }
        updatedParticipantData.setMeeting( new Meeting( meetingId ) );
        return participantRepository.save( updatedParticipantData );
        */
        updatedParticipantData.setMeeting( new Meeting( meetingId ) );
        participants.set( participantId, updatedParticipantData );
        return updatedParticipantData;
    }

    public void deleteParticipant(int id) throws Exception {
        /*
        if( !participantRepository.existsById(id) ) {
            throw new Exception("No Participant found with id " + id);
        }
        participantRepository.deleteById( id );
        */
        participants.remove( id );
    }
}
