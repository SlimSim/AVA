package com.slimsimapps.ava.controller.v1.api;


import com.slimsimapps.ava.dto.response.Response;
import com.slimsimapps.ava.service.BadLogService;
import com.slimsimapps.ava.service.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ParticipantController {


    @Autowired
    BadLogService log;

    @Autowired
    ParticipantService participantService;

    @GetMapping("/meeting/{meetingId}/speakerQue")
    public Response<Object> getSpeakerQue(@PathVariable int meetingId) {
        log.ao( meetingId );
        try {
            return Response.ok().setPayload( participantService.getSpeakerQue( meetingId ) );
        } catch ( RuntimeException re ) {
            return Response.notFound().setPayload( re.getMessage() );
        }
    }

    @GetMapping("/meeting/{meetingId}/participants")
    public Response<Object> getAllParticipants( @PathVariable int meetingId){
        log.ao( meetingId );
        try {
            return Response.ok().setPayload( participantService.getAllParticipantDtos( meetingId ) );
        } catch ( RuntimeException re ) {
            return Response.notFound().setPayload( re.getMessage() );
        }
    }

}
