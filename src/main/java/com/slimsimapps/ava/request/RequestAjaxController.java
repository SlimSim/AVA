package com.slimsimapps.ava.request;

import com.slimsimapps.ava.badlog.BadLogService;
import com.slimsimapps.ava.meeting.Meeting;
import com.slimsimapps.ava.participant.Participant;
import com.slimsimapps.ava.participant.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class RequestAjaxController {

    @Autowired
    BadLogService log;

    @Autowired
    ParticipantService participantService;

    @PostMapping( "/ajax/request" )
    public List<Participant> request(@RequestBody Request request) throws Exception {
        log.a( request );
        participantService.setParticipantRequest( request );
        Meeting meeting = participantService.getParticipant( request.getParticipantId() ).getMeeting();
        log.o();
        return participantService.getSpeakerQue( meeting.getId() );
    }

    @PostMapping( "/ajax/singleRequest" )
    public Participant singleRequest(@RequestBody Request request) throws Exception {
        log.a(request);
        participantService.setParticipantRequest( request );
        Participant p = participantService.getParticipant( request.getParticipantId() );
        log.o(p);
        return p;
    }

}
