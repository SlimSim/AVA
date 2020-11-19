package com.slimsimapps.ava.service;

import com.slimsimapps.ava.dto.mapper.MeetingMapper;
import com.slimsimapps.ava.dto.mapper.ParticipantMapper;
import com.slimsimapps.ava.dto.model.MeetingDto;
import com.slimsimapps.ava.dto.model.ParticipantDto;
import com.slimsimapps.ava.dto.model.RequestDto;
import com.slimsimapps.ava.enums.RequestType;
import com.slimsimapps.ava.model.Meeting;
import com.slimsimapps.ava.model.Participant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;


import com.slimsimapps.ava.exception.AvaException;
import com.slimsimapps.ava.enums.EntityType;
import com.slimsimapps.ava.enums.ExceptionType;

//import static com.starterkit.springboot.brs.exception.ExceptionType.ENTITY_NOT_FOUND;
//import static com.slimsimapps.ava.exception.AvaException.EntityNotFoundException;
//exception.ExceptionType.DUPLICATE_ENTITY;

import static com.slimsimapps.ava.dto.response.Response.exception;
import static com.slimsimapps.ava.enums.EntityType.*;
import static com.slimsimapps.ava.enums.ExceptionType.*;
import static com.slimsimapps.ava.exception.AvaException.throwException;

import java.util.*;
import java.util.function.Supplier;
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


    public List<ParticipantDto> getAllParticipantDtos(int meetingId ) throws RuntimeException {
        log.a(meetingId);
        /*
        List<Participant> participants = new ArrayList<>();
        Iterable<Participant> meetingIterable = participantRepository.findByMeetingId( meetingId );
        meetingIterable.forEach(participants::add);
        return participantRepository.findByMeetingId( meetingId );
        */
        meetingService.getMeeting( meetingId ); //checks if the meeting exists, otherwise throw exception
        log.o();
        return participants.stream()
                       .filter(p -> p.getMeeting().getId() == meetingId)
                       .map( ParticipantMapper::toParticipantDto )
                       .collect(Collectors.toList());
    }

    public ParticipantDto setParticipantRequest( RequestDto requestDto ) throws RuntimeException {
        log.a(requestDto);
        int participantId = requestDto.getParticipantId();
        RequestType requestType = requestDto.getRequestType();
        boolean active = requestDto.isActive();

        Participant p = getParticipantModel( participantId );

        if( requestType == RequestType.breakingQuestion ) {
            p.setBreakingQuestion( active );
            p.setBreakingQuestionTime( new Date().getTime() );
        } else if ( requestType == RequestType.information ) {
            p.setInformation( active );
            p.setInformationTime( new Date().getTime() );
        } else if ( requestType == RequestType.comment ) {
            p.setComment( active );
            p.setCommentTime( new Date().getTime() );
        } else if ( requestType == RequestType.requestToSpeak ) {
            p.setRequestToSpeak( active );
            p.setRequestToSpeakTime( new Date().getTime() );
        } else if ( requestType == RequestType.voteYes) {
            p.setVoteYes( active );
            p.setVoteYesTime( new Date().getTime() );
        } else if ( requestType == RequestType.voteNo) {
            p.setVoteNo( active );
            p.setVoteNoTime( new Date().getTime() );
        }

        template.convertAndSend("/topic/request", requestDto);

        log.o();
        return ParticipantMapper.toParticipantDto( updateParticipantModel( participantId, p.getMeeting().getId(), p ) );
    }

    private Participant getParticipantModel(int id) throws RuntimeException {
        log.a(id);
        return participants.stream().filter(p -> p.getId() == id ).findFirst().orElseThrow(
                () -> throwException(PARTICIPANT, ENTITY_NOT_FOUND, id )
        );
        /*
        Optional<Participant> participant = participants.stream().filter(p -> p.getId() == id ).findFirst();

        //Supplier<RuntimeException> sup;

        //participant.orElseThrow( () -> new Exception("Student not found - " ) );
        return participant.orElseThrow( () -> exception(PARTICIPANT, ENTITY_NOT_FOUND, "userDto.getEmail()") );

        throw exception(PARTICIPANT, ENTITY_NOT_FOUND, "userDto.getEmail()");
        //participants.stream().filter(participant -> participant.getId() == id ).findFirst().orElseThrow( () -> throwException(PARTICIPANT, ENTITY_NOT_FOUND, id ) )
        log.o();
        //return participantRepository.findById(id).orElseThrow(
        //        () -> throwException(PARTICIPANT, ENTITY_NOT_FOUND, id) );
         */
    }

    public ParticipantDto getParticipant(int id) throws RuntimeException {
        log.a(id);
        return ParticipantMapper.toParticipantDto( getParticipantModel( id ) );
    }

    public ParticipantDto addParticipant(ParticipantDto participantDto, int meetingId) throws RuntimeException {
        log.a(participantDto, meetingId);
        if( participantDto == null ) {
            throw throwException(PARTICIPANT, ENTITY_NULL);
        }

        Participant participant = ParticipantMapper.toParticipant( participantDto );

        ArrayList<Integer> ids = new ArrayList<>();
        for (Participant p : participants) {
            ids.add(p.getId());
        }
        int id = 1;
        while( ids.contains( id ) ) {
            id++;
        }
        participant.setId( id );

        MeetingDto meeting = meetingService.getMeeting( meetingId );
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
        return ParticipantMapper.toParticipantDto( participant );
        //return participantRepository.save(participant);
    }

    private int getParticipantIndexFromId(int participantId) throws RuntimeException{
        int participantIndex = -1;
        for( int i = 0; i < participants.size(); i++ ) {
            if( participants.get( i ).getId() != participantId ){
                continue;
            }
            participantIndex = i;
            break;
        }
        if( participantIndex == -1 ) {
            throw throwException(PARTICIPANT, ENTITY_NOT_FOUND, participantId);
        }
        return participantIndex;
    }

    private Participant updateParticipantModel(int participantId, int meetingId, Participant updatedParticipantData) throws RuntimeException {
        log.a(participantId, meetingId, updatedParticipantData);
        /*
        if( !participantRepository.existsById(participantId) ) {
            throw new Exception("No Participant found with id " + participantId);
        }
        updatedParticipantData.setMeeting( new Meeting( meetingId ) );
        return participantRepository.save( updatedParticipantData );
        */
        updatedParticipantData.setMeeting( new Meeting( meetingId ) );

        MeetingDto meeting = meetingService.getMeeting( meetingId );
        updatedParticipantData.setShowBreakingQuestion( meeting.isShowBreakingQuestion() );
        updatedParticipantData.setShowInformation( meeting.isShowInformation() );
        updatedParticipantData.setShowComment( meeting.isShowComment() );
        updatedParticipantData.setShowRequestToSpeak( meeting.isShowRequestToSpeak() );
        updatedParticipantData.setShowVoteYes( meeting.isShowVoteYes() );
        updatedParticipantData.setShowVoteNo( meeting.isShowVoteNo() );

        int participantIndex = getParticipantIndexFromId( participantId );

        participants.set( participantIndex, updatedParticipantData );
        log.o(updatedParticipantData);
        return updatedParticipantData;

    }

    public ParticipantDto updateParticipant(int participantId, int meetingId, ParticipantDto updatedParticipantDtoData) throws Exception {
        return ParticipantMapper.toParticipantDto( updateParticipantModel(participantId, meetingId, ParticipantMapper.toParticipant( updatedParticipantDtoData )) );
    }

    public void deleteParticipant(int id) throws RuntimeException {
        log.a(id);
        /*
        if( !participantRepository.existsById(id) ) {
            throw throwException(PARTICIPANT, ENTITY_NOT_FOUND, ID);
        }
        participantRepository.deleteById( id );
        */

        participants.remove( getParticipantIndexFromId( id ) );
        log.o();
    }

    public List<ParticipantDto> getSpeakerQue(int meetingId) throws RuntimeException {
        log.a();
        List<ParticipantDto> x = getAllParticipantDtos( meetingId );

        List<ParticipantDto> VoteYesParticipantList = new ArrayList<>();
        List<ParticipantDto> VoteNoParticipantList = new ArrayList<>();
        List<ParticipantDto> RequestToSpeakParticipantList = new ArrayList<>();
        List<ParticipantDto> CommentParticipantList = new ArrayList<>();
        List<ParticipantDto> InformationParticipantList = new ArrayList<>();
        List<ParticipantDto> BreakingQuestionParticipantList = new ArrayList<>();

        List<ParticipantDto> sortedParticipantList = new ArrayList<>();

        x.forEach( p -> {
            if( p.isBreakingQuestion() ) {
                ParticipantDto p2 = new ParticipantDto().setName( p.getName() ).setId( p.getId() );
                p2.setBreakingQuestion( true );
                p2.setBreakingQuestionTime( p.getBreakingQuestionTime() );
                BreakingQuestionParticipantList.add( p2 );
            }
            if( p.isInformation() ) {
                ParticipantDto p2 = new ParticipantDto().setName( p.getName() ).setId( p.getId() );
                p2.setInformation( true );
                p2.setInformationTime( p.getInformationTime() );
                InformationParticipantList.add( p2 );
            }
            if( p.isComment() ) {
                ParticipantDto p2 = new ParticipantDto().setName( p.getName() ).setId( p.getId() );
                p2.setComment( true );
                p2.setCommentTime( p.getCommentTime() );
                CommentParticipantList.add( p2 );
            }
            if( p.isRequestToSpeak() ) {
                ParticipantDto p2 = new ParticipantDto().setName( p.getName() ).setId( p.getId() );
                p2.setRequestToSpeak( true );
                p2.setRequestToSpeakTime( p.getRequestToSpeakTime() );
                RequestToSpeakParticipantList.add( p2 );
            }
            if( p.isVoteYes() ) {
                ParticipantDto p2 = new ParticipantDto().setName( p.getName() ).setId( p.getId() );
                p2.setVoteYes( true );
                p2.setVoteYesTime( p.getVoteYesTime() );
                VoteYesParticipantList.add( p2 );
            }
            if( p.isVoteNo() ) {
                ParticipantDto p2 = new ParticipantDto().setName( p.getName() ).setId( p.getId() );
                p2.setVoteNo( true );
                p2.setVoteNoTime( p.getVoteNoTime() );
                VoteNoParticipantList.add( p2 );
            }
        });


        VoteYesParticipantList.sort( Comparator.comparing(ParticipantDto::getVoteYesTime));
        VoteNoParticipantList.sort( Comparator.comparing(ParticipantDto::getVoteNoTime));
        RequestToSpeakParticipantList.sort( Comparator.comparing(ParticipantDto::getRequestToSpeakTime));
        CommentParticipantList.sort( Comparator.comparing(ParticipantDto::getCommentTime));
        InformationParticipantList.sort( Comparator.comparing(ParticipantDto::getInformationTime));
        BreakingQuestionParticipantList.sort( Comparator.comparing(ParticipantDto::getBreakingQuestionTime));

        sortedParticipantList.addAll(BreakingQuestionParticipantList);
        sortedParticipantList.addAll(InformationParticipantList);
        sortedParticipantList.addAll(CommentParticipantList);
        sortedParticipantList.addAll(RequestToSpeakParticipantList);
        sortedParticipantList.addAll(VoteYesParticipantList);
        sortedParticipantList.addAll(VoteNoParticipantList);

        log.o( sortedParticipantList );
        return sortedParticipantList;
    }

    public MeetingDto getParticipantMeeting(int participantId) throws RuntimeException {
        return MeetingMapper.toMeetingDto( getParticipantModel( participantId ).getMeeting() );
    }

    public boolean isParticipantInMeeting(int participantId, int meetingId) {
        return getParticipantModel( participantId ).getMeeting().getId() == meetingId;

    }

    public boolean participantExists(int participantId) {
        return participants.stream().anyMatch(p -> p.getId() == participantId );
    }
}
