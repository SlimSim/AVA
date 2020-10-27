package com.slimsimapps.ava.request;

import com.slimsimapps.ava.badlog.BadLogService;
import com.slimsimapps.ava.participant.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;


@Controller
public class RequestController {

    @Autowired
    BadLogService log;

    @Autowired
    ParticipantService participantService;

    @MessageMapping("/request")
    public void request(Request request) throws Exception {
        log.a(request);
        participantService.setParticipantRequest( request );
    }

}
