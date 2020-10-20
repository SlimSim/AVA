package com.slimsimapps.ava.participant;

import com.slimsimapps.ava.badlog.BadLogService;
import com.slimsimapps.ava.meeting.Meeting;
import com.slimsimapps.ava.meeting.MeetingService;
import com.slimsimapps.ava.request.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParticipantService {

    //@Autowired
    //private ParticipantRepository participantRepository;

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    MeetingService meetingService;

    @Autowired
    BadLogService log;

    List<Participant> participants = new ArrayList<>();


    public List<Participant> getAllParticipants( int meetingId ) {
        log.a(meetingId);
        /*
        List<Participant> participants = new ArrayList<>();
        Iterable<Participant> meetingIterable = participantRepository.findByMeetingId( meetingId );
        meetingIterable.forEach(participants::add);
        return participantRepository.findByMeetingId( meetingId );
        */
        log.o();
        return participants.stream().filter(p -> p.getMeeting().getId() == meetingId).collect(Collectors.toList());
    }

    public Participant setParticipantRequest( Request request ) throws Exception {
        log.a(request);
        int participantId = request.getParticipantId();
        Request.TypeOfRequest typeOfRequest = request.getTypeOfRequest();
        boolean active = request.isActive();

        Participant p = getParticipant( participantId );

        if( typeOfRequest == Request.TypeOfRequest.breakingQuestion ) {
            p.setBreakingQuestion( active );
            p.setBreakingQuestionTime( new Date().getTime() );
        } else if ( typeOfRequest == Request.TypeOfRequest.information ) {
            p.setInformation( active );
            p.setInformationTime( new Date().getTime() );
        } else if ( typeOfRequest == Request.TypeOfRequest.comment ) {
            p.setComment( active );
            p.setCommentTime( new Date().getTime() );
        } else if ( typeOfRequest == Request.TypeOfRequest.requestToSpeak ) {
            p.setRequestToSpeak( active );
            p.setRequestToSpeakTime( new Date().getTime() );
        } else if ( typeOfRequest == Request.TypeOfRequest.voteYes) {
            p.setVoteYes( active );
            p.setVoteYesTime( new Date().getTime() );
        } else if ( typeOfRequest == Request.TypeOfRequest.voteNo) {
            p.setVoteNo( active );
            p.setVoteNoTime( new Date().getTime() );
        }

        template.convertAndSend("/topic/request", request);

        log.o();
        return updateParticipant( participantId, p.getMeeting().getId(), p );
    }

    public Participant getParticipant(int id) throws Exception {
        log.a(id);
        log.o();
        return participants.stream().filter( participant -> participant.getId() == id ).findFirst().get();
        //return participantRepository.findById(id).orElseThrow(
        //        () -> new Exception("No Participant found with id " + id) );
    }

    public Participant addParticipant(Participant participant, int meetingId) throws Exception {
        log.a(participant, meetingId);
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

        Meeting meeting = meetingService.getMeeting( meetingId );
        participant.setShowBreakingQuestion( meeting.isShowBreakingQuestion() );
        participant.setShowInformation( meeting.isShowInformation() );
        participant.setShowComment( meeting.isShowComment() );
        participant.setShowRequestToSpeak( meeting.isShowRequestToSpeak() );
        participant.setShowVoteYes( meeting.isShowVoteYes() );
        participant.setShowVoteNo( meeting.isShowVoteNo() );


        participant.setMeeting(new Meeting( meetingId ) );

        log.d( "participant = " + participant);

        participants.add( participant );

        template.convertAndSend("/topic/newParticipant", participant);

        log.o(participant);
        return participant;
        //return participantRepository.save(participant);
    }

    public Participant updateParticipant(int participantId, int meetingId, Participant updatedParticipantData) throws Exception {
        log.a(participantId, meetingId, updatedParticipantData);
        /*
        if( !participantRepository.existsById(participantId) ) {
            throw new Exception("No Participant found with id " + participantId);
        }
        updatedParticipantData.setMeeting( new Meeting( meetingId ) );
        return participantRepository.save( updatedParticipantData );
        */
        updatedParticipantData.setMeeting( new Meeting( meetingId ) );

        Meeting meeting = meetingService.getMeeting( meetingId );
        updatedParticipantData.setShowBreakingQuestion( meeting.isShowBreakingQuestion() );
        updatedParticipantData.setShowInformation( meeting.isShowInformation() );
        updatedParticipantData.setShowComment( meeting.isShowComment() );
        updatedParticipantData.setShowRequestToSpeak( meeting.isShowRequestToSpeak() );
        updatedParticipantData.setShowVoteYes( meeting.isShowVoteYes() );
        updatedParticipantData.setShowVoteNo( meeting.isShowVoteNo() );

        int participantIndex = -1;
        for( int i = 0; i < participants.size(); i++ ) {
            if( participants.get( i ).getId() != participantId ){
                continue;
            }
            participantIndex = i;
            break;
        }

        participants.set( participantIndex, updatedParticipantData );
        log.o(updatedParticipantData);
        return updatedParticipantData;
    }

    public void deleteParticipant(int id) throws Exception {
        log.a(id);
        /*
        if( !participantRepository.existsById(id) ) {
            throw new Exception("No Participant found with id " + id);
        }
        participantRepository.deleteById( id );
        */

        participants.remove( id );
        log.o();
    }
}
