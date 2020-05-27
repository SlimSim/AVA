package com.slimsimapps.ava.participant;


import com.slimsimapps.ava.MainController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RestController
public class ParticipantController {


    Logger log = LoggerFactory.getLogger(MainController.class);

    @Autowired
    ParticipantService participantService;

    @GetMapping("/meeting/{meetingId}/speakerQue")
    public List<Participant> getSpeakerQue( @PathVariable int meetingId){
        List<Participant> x = participantService.getAllParticipants( meetingId );

        List<Participant> VoteYesParticipantList = new ArrayList<>();
        List<Participant> VoteNoParticipantList = new ArrayList<>();
        List<Participant> RequestToSpeakParticipantList = new ArrayList<>();
        List<Participant> CommentParticipantList = new ArrayList<>();
        List<Participant> InformationParticipantList = new ArrayList<>();
        List<Participant> BreakingQuestionParticipantList = new ArrayList<>();

        List<Participant> sortedParticipantList = new ArrayList<>();

        x.forEach( p -> {
            if( p.isBreakingQuestion() ) {
                Participant p2 = new Participant( p.getName(), p.getId() );
                p2.setBreakingQuestion( true );
                p2.setBreakingQuestionTime( p.getBreakingQuestionTime() );
                BreakingQuestionParticipantList.add( p2 );
            }
            if( p.isInformation() ) {
                Participant p2 = new Participant( p.getName(), p.getId() );
                p2.setInformation( true );
                p2.setInformationTime( p.getInformationTime() );
                InformationParticipantList.add( p2 );
            }
            if( p.isComment() ) {
                Participant p2 = new Participant( p.getName(), p.getId() );
                p2.setComment( true );
                p2.setCommentTime( p.getCommentTime() );
                CommentParticipantList.add( p2 );
            }
            if( p.isRequestToSpeak() ) {
                Participant p2 = new Participant( p.getName(), p.getId() );
                p2.setRequestToSpeak( true );
                p2.setRequestToSpeakTime( p.getRequestToSpeakTime() );
                RequestToSpeakParticipantList.add( p2 );
            }
            if( p.isVoteYes() ) {
                Participant p2 = new Participant( p.getName(), p.getId() );
                p2.setVoteYes( true );
                p2.setVoteYesTime( p.getVoteYesTime() );
                VoteYesParticipantList.add( p2 );
            }
            if( p.isVoteNo() ) {
                Participant p2 = new Participant( p.getName(), p.getId() );
                p2.setVoteNo( true );
                p2.setVoteNoTime( p.getVoteNoTime() );
                VoteNoParticipantList.add( p2 );
            }
        });


        VoteYesParticipantList.sort( Comparator.comparing(Participant::getVoteYesTime));
        VoteNoParticipantList.sort( Comparator.comparing(Participant::getVoteNoTime));
        RequestToSpeakParticipantList.sort( Comparator.comparing(Participant::getRequestToSpeakTime));
        CommentParticipantList.sort( Comparator.comparing(Participant::getCommentTime));
        InformationParticipantList.sort( Comparator.comparing(Participant::getInformationTime));
        BreakingQuestionParticipantList.sort( Comparator.comparing(Participant::getBreakingQuestionTime));

        sortedParticipantList.addAll(BreakingQuestionParticipantList);
        sortedParticipantList.addAll(InformationParticipantList);
        sortedParticipantList.addAll(CommentParticipantList);
        sortedParticipantList.addAll(RequestToSpeakParticipantList);
        sortedParticipantList.addAll(VoteYesParticipantList);
        sortedParticipantList.addAll(VoteNoParticipantList);

        return sortedParticipantList;
    }

    @GetMapping("/meeting/{meetingId}/participants")
    public List<Participant> getAllParticipants( @PathVariable int meetingId){
        List<Participant> x = participantService.getAllParticipants( meetingId );

        List<Participant> VoteYesParticipantList = new ArrayList<>();
        List<Participant> VoteNoParticipantList = new ArrayList<>();
        List<Participant> RequestToSpeakParticipantList = new ArrayList<>();
        List<Participant> CommentParticipantList = new ArrayList<>();
        List<Participant> InformationParticipantList = new ArrayList<>();
        List<Participant> BreakingQuestionParticipantList = new ArrayList<>();
        List<Participant> silentParticipantList = new ArrayList<>();

        List<Participant> sortedParticipantList = new ArrayList<>();

        x.forEach( p -> {
            if( p.isBreakingQuestion() ) { BreakingQuestionParticipantList.add( p ); }
            else if( p.isInformation() ) { InformationParticipantList.add( p ); }
            else if( p.isComment() ) { CommentParticipantList.add( p ); }
            else if( p.isRequestToSpeak() ) { RequestToSpeakParticipantList.add( p ); }
            else if( p.isVoteYes() ) { VoteYesParticipantList.add( p ); }
            else if( p.isVoteNo() ) { VoteNoParticipantList.add( p ); }

            else { silentParticipantList.add( p ); }
        });


        VoteYesParticipantList.sort( Comparator.comparing(Participant::getVoteYesTime));
        VoteNoParticipantList.sort( Comparator.comparing(Participant::getVoteNoTime));
        RequestToSpeakParticipantList.sort( Comparator.comparing(Participant::getRequestToSpeakTime));
        CommentParticipantList.sort( Comparator.comparing(Participant::getCommentTime));
        InformationParticipantList.sort( Comparator.comparing(Participant::getInformationTime));
        BreakingQuestionParticipantList.sort( Comparator.comparing(Participant::getBreakingQuestionTime));
        //silentParticipantList.sort( Comparator.comparing( p -> p.getsilentTime()));

        sortedParticipantList.addAll(BreakingQuestionParticipantList);
        sortedParticipantList.addAll(InformationParticipantList);
        sortedParticipantList.addAll(CommentParticipantList);
        sortedParticipantList.addAll(RequestToSpeakParticipantList);
        sortedParticipantList.addAll(VoteYesParticipantList);
        sortedParticipantList.addAll(VoteNoParticipantList);
        sortedParticipantList.addAll(silentParticipantList);

        return sortedParticipantList;
    }

    @GetMapping("/meeting/{meetingId}/participants/{id}")
    public Participant getParticipant( @PathVariable int meetingId, @PathVariable int id ) throws Exception {
        return participantService.getParticipant( id ); //TODO: lägg till meetingId här, för en extra koll :)
    }

    /*
    @MessageMapping("/request")
    @SendTo("/topic/newParticipant")
    public Request request(Request request) throws Exception {
        System.out.println( "RequestController.request ->");
        System.out.println( "RequestController.request: request = " + request);

        Participant p = participantService.setParticipantRequest( request.getParticipantId(), request.getTypeOfRequest(), request.isActive() );
        System.out.println( "RequestController.request: p = " + p);

        return request;
    }


    @RequestMapping(value = "/sendMessage")
    public void sendMessage() throws Exception {
        this.template.convertAndSend("/topic/greetings", new HelloMessage(
                (int) Math.random(), "This is Send From Server"));
    }
    */


    @Autowired
    private SimpMessagingTemplate template;

    @PostMapping("/meeting/{meetingId}/participants")
    public Participant addParticipant( @PathVariable int meetingId, @RequestBody Participant participant ) throws Exception {
        System.out.println("addParticipant ->");

        Participant p = participantService.addParticipant(participant, meetingId);
        System.out.println("addParticipant: p = " + p);
        this.template.convertAndSend("/topic/newParticipant", p);

        return p;
    }






    @PutMapping( "/meeting/{meetingId}/participants/{id}" )
    public Participant updateParticipant( @PathVariable int meetingId, @PathVariable int id, @RequestBody Participant participant ) throws Exception {
        //participant.setMeeting( new Meeting( meetingId ) );
        return participantService.updateParticipant( id, meetingId, participant );
    }

    @PostMapping( "/meeting/{meetingId}/participants/{id}" )
    public Participant setParticipant( @PathVariable int meetingId, @PathVariable int id, @RequestBody Participant participant ) throws Exception {
        return participantService.updateParticipant( id, meetingId, participant );
    }

    @DeleteMapping( "/meeting/{meetingId}/participants/{id}" )
    public void deleteParticipant( @PathVariable int meetingId, @PathVariable int id ) throws Exception {
        participantService.deleteParticipant( id ); //TODO: lägg till meetingId här, för en extra koll :)
    }

}
