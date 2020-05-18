package com.slimsimapps.ava.participant;

import com.slimsimapps.ava.MainController;
import com.slimsimapps.ava.meeting.Meeting;
import com.slimsimapps.ava.request.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Part;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParticipantService {

    //@Autowired
    //private ParticipantRepository participantRepository;


    Logger log = LoggerFactory.getLogger(MainController.class);

    List<Participant> participants = new ArrayList<>();


    public List<Participant> getAllParticipants( int meetingId ) {
        /*
        List<Participant> participants = new ArrayList<>();
        Iterable<Participant> meetingIterable = participantRepository.findByMeetingId( meetingId );
        meetingIterable.forEach(participants::add);
        return participantRepository.findByMeetingId( meetingId );
        */
        return participants.stream().filter(p -> p.getMeeting().getId() == meetingId).collect(Collectors.toList());
    }

    public Participant setParticipantRequest(int participantId, Request.TypeOfRequest typeOfRequest, boolean active ) throws Exception {
        Participant p = getParticipant( participantId );

        if( typeOfRequest == Request.TypeOfRequest.breakingQuestion ) {
            p.setBreakingQuestion( active );
        } else if ( typeOfRequest == Request.TypeOfRequest.information ) {
            p.setInformation( active );
        } else if ( typeOfRequest == Request.TypeOfRequest.comment ) {
            p.setComment( active );
        } else if ( typeOfRequest == Request.TypeOfRequest.requestToSpeak ) {
            p.setRequestToSpeak( active );
        } else if ( typeOfRequest == Request.TypeOfRequest.handRaised ) {
            p.setHandRaised( active );
        }

        return updateParticipant( participantId, p.getMeeting().getId(), p );
    }

    public Participant getParticipant(int id) throws Exception {
        return participants.stream().filter( participant -> participant.getId() == id ).findFirst().get();
        //return participantRepository.findById(id).orElseThrow(
        //        () -> new Exception("No Participant found with id " + id) );
    }

    public Participant addParticipant(Participant participant, int meetingId) throws Exception {
        if( participant == null ) {
            throw new Exception("No body found, participant is null");
        }

        ArrayList<Integer> ids = new ArrayList<>();
        for (Participant p : participants) {
            ids.add(p.getId());
        }
        int id = 1;
        while( ids.contains( id ) ) {
            id++;
        }
        participant.setId( id );

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
        int participantIndex = -1;
        for( int i = 0; i < participants.size(); i++ ) {
            if( participants.get( i ).getId() != participantId ){
                continue;
            }
            participantIndex = i;
            break;
        }

        participants.set( participantIndex, updatedParticipantData );
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
