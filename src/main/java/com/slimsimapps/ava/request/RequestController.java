package com.slimsimapps.ava.request;

import com.slimsimapps.ava.Greeting;
import com.slimsimapps.ava.participant.Participant;
import com.slimsimapps.ava.participant.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;


@Controller
public class RequestController {

    @Autowired
    ParticipantService participantService;

    @MessageMapping("/request")
    public void request(Request request) throws Exception {
	    participantService.setParticipantRequest( request );
    }

}
