package com.slimsimapps.ava.controller.v1.api;

import com.slimsimapps.ava.dto.model.RequestDto;
import com.slimsimapps.ava.service.BadLogService;
import com.slimsimapps.ava.service.ParticipantService;
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
    public void request(RequestDto request) throws Exception {
        log.a(request);
        participantService.setParticipantRequest( request );
    }

}
