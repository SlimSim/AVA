package com.slimsimapps.ava.controller.v1.api;

import com.slimsimapps.ava.dto.model.MeetingDto;
import com.slimsimapps.ava.dto.model.ParticipantDto;
import com.slimsimapps.ava.dto.model.RequestDto;
import com.slimsimapps.ava.service.BadLogService;
import com.slimsimapps.ava.service.ParticipantService;
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
    public List<ParticipantDto> request(@RequestBody RequestDto request) {
        log.a( request );
        participantService.setParticipantRequest( request );
        MeetingDto meeting = participantService.getParticipantMeeting( request.getParticipantId() );
        log.o();
        return participantService.getSpeakerQue( meeting.getId() );
    }

    @PostMapping( "/ajax/singleRequest" )
    public ParticipantDto singleRequest(@RequestBody RequestDto request) {
        log.ao(request);
        return participantService.setParticipantRequest( request );
    }

}
