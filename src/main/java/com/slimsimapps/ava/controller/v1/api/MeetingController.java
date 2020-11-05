package com.slimsimapps.ava.controller.v1.api;


import com.slimsimapps.ava.dto.model.MeetingDto;
import com.slimsimapps.ava.dto.response.Response;
import com.slimsimapps.ava.service.BadLogService;
import com.slimsimapps.ava.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MeetingController {


    @Autowired
    BadLogService log;

    @Autowired
    MeetingService meetingService;

    /*
    @GetMapping("/meetings")
    public List<MeetingDto> getAllMeetings(){
        return meetingService.getAllMeetings();
    }

    @GetMapping("/meetings/{id}")
    public Response<Object> getMeeting(@PathVariable int id ) throws Exception {
        return Response.ok().setPayload( meetingService.getMeeting( id ) );
    }

    @PostMapping("/meetings")
    public MeetingDto addMeeting(@RequestBody MeetingDto meetingDto) throws Exception {
        System.out.println("addMeeting ->");
        return meetingService.addMeeting(meetingDto);
    }

    @PutMapping( "/meetings/{id}" )
    public MeetingDto updateMeeting(@PathVariable int id, @RequestBody MeetingDto meetingDto) throws Exception {
        return meetingService.updateMeeting( id, meetingDto);
    }

    @PostMapping( "/meetings/{id}" )
    public MeetingDto setMeeting(@PathVariable int id, @RequestBody MeetingDto meetingDto) throws Exception {
        return meetingService.updateMeeting( id, meetingDto);
    }

    @DeleteMapping( "/meetings/{id}" )
    public void deleteMeeting( @PathVariable int id ) throws Exception {
        meetingService.deleteMeeting( id );
    }
*/

}
