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

import java.util.ArrayList;
import java.util.Comparator;
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


    public List<ParticipantDto> getAllParticipantDtos(int meetingId ) {
        log.a(meetingId);
        /*
        List<Participant> participants = new ArrayList<>();
        Iterable<Participant> meetingIterable = participantRepository.findByMeetingId( meetingId );
        meetingIterable.forEach(participants::add);
        return participantRepository.findByMeetingId( meetingId );
        */
        log.o();
        return participants.stream()
                       .filter(p -> p.getMeeting().getId() == meetingId)
                       .map( ParticipantMapper::toParticipantDto )
                       .collect(Collectors.toList());
    }

    public ParticipantDto setParticipantRequest( RequestDto requestDto ) throws Exception {
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

    private Participant getParticipantModel(int id) throws Exception {
        log.a(id);
        log.o();
        return participants.stream().filter(participant -> participant.getId() == id ).findFirst().get();
        //return participantRepository.findById(id).orElseThrow(
        //        () -> new Exception("No Participant found with id " + id) );
    }

    public ParticipantDto getParticipant(int id) throws Exception {
        log.a(id);
        return ParticipantMapper.toParticipantDto( getParticipantModel( id ) );
    }

    public ParticipantDto addParticipant(ParticipantDto participantDto, int meetingId) throws Exception {
        log.a(participantDto, meetingId);
        if( participantDto == null ) {
            throw new Exception("No body found, participant is null");
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

    private Participant updateParticipantModel(int participantId, int meetingId, Participant updatedParticipantData) throws Exception {
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

    public ParticipantDto updateParticipant(int participantId, int meetingId, ParticipantDto updatedParticipantDtoData) throws Exception {
        return ParticipantMapper.toParticipantDto( updateParticipantModel(participantId, meetingId, ParticipantMapper.toParticipant( updatedParticipantDtoData )) );

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

    public List<ParticipantDto> getSpeakerQue(int meetingId) {
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

    public MeetingDto getParticipantMeeting(int participantId) throws Exception {
        return MeetingMapper.toMeetingDto( getParticipantModel( participantId ).getMeeting() );
    }
}
