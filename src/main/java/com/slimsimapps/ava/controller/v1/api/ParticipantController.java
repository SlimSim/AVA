package com.slimsimapps.ava.controller.v1.api;


import com.slimsimapps.ava.dto.response.Response;
import com.slimsimapps.ava.service.BadLogService;
import com.slimsimapps.ava.service.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
public class ParticipantController {


    @Autowired
    BadLogService log;

    @Autowired
    ParticipantService participantService;

    @Autowired
    private SimpMessagingTemplate template;

    @GetMapping("/meeting/{meetingId}/speakerQue")
    public Response<Object> getSpeakerQue(@PathVariable int meetingId){
        log.a( meetingId );
        return Response.ok().setPayload( participantService.getSpeakerQue( meetingId ) );
    }

    @GetMapping("/meeting/{meetingId}/participants")
    public Response<Object> getAllParticipants( @PathVariable int meetingId){
        log.a( meetingId );
        return Response.ok().setPayload( participantService.getAllParticipantDtos( meetingId ) );
    }

    @GetMapping("/meeting/{meetingId}/participants/{id}")
    public Response<Object> getParticipant( @PathVariable int meetingId, @PathVariable int id ) throws Exception {
        log.a( meetingId, id);
        log.o();
        return Response.ok().setPayload(  participantService.getParticipant( id ) ); //TODO: lägg till meetingId här, för en extra koll :)
    }

    /*
    @MessageMapping("/request")
    @SendTo("/topic/newParticipant")
    public Request request(Request request) throws Exception {
        System.out.println( "RequestController.request ->");
        System.out.println( "RequestController.request: request = " + request);

        Participant p = participantService.setParticipantRequest( request.getParticipantId(), request.getRequestType(), request.isActive() );
        System.out.println( "RequestController.request: p = " + p);

        return request;
    }


    @RequestMapping(value = "/sendMessage")
    public void sendMessage() throws Exception {
        this.template.convertAndSend("/topic/greetings", new HelloMessage(
                (int) Math.random(), "This is Send From Server"));
    }
    */



    /*
    @PostMapping("/meeting/{meetingId}/participants")
    public Participant addParticipant( @PathVariable int meetingId, @RequestBody Participant participant ) throws Exception {
        log.a( meetingId, participant );

        Participant p = participantService.addParticipant(participant, meetingId);
        log.d("addParticipant: p = " + p);
        this.template.convertAndSend("/topic/newParticipant", p);

        log.o(p);
        return p;
    }
    */

    /*
    @PutMapping( "/meeting/{meetingId}/participants/{id}" )
    public Participant updateParticipant( @PathVariable int meetingId, @PathVariable int id, @RequestBody Participant participant ) throws Exception {
        log.a( meetingId, id, participant);
        //participant.setMeeting( new Meeting( meetingId ) );
        log.o();
        return participantService.updateParticipant( id, meetingId, participant );
    }
     */

    /*
    @PostMapping( "/meeting/{meetingId}/participants/{id}" )
    public Participant setParticipant( @PathVariable int meetingId, @PathVariable int id, @RequestBody Participant participant ) throws Exception {
        log.a( meetingId, id, participant);
        log.o();
        return participantService.updateParticipant( id, meetingId, participant );
    }
     */

    /*
    @DeleteMapping( "/meeting/{meetingId}/participants/{id}" )
    public void deleteParticipant( @PathVariable int meetingId, @PathVariable int id ) throws Exception {
        log.a( meetingId, id);
        log.o();
        participantService.deleteParticipant( id ); //TODO: lägg till meetingId här, för en extra koll :)
    }
     */

}
