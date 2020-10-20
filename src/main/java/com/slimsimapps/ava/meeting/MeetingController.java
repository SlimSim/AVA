package com.slimsimapps.ava.meeting;


import com.slimsimapps.ava.badlog.BadLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MeetingController {


    @Autowired
    BadLogService log;

    @Autowired
    MeetingService meetingService;

    @GetMapping("/meetings")
    public List<Meeting> getAllMeetings(){
        return meetingService.getAllMeetings();
    }

    @GetMapping("/meetings/{id}")
    public Meeting getMeeting(@PathVariable int id ) throws Exception {
        return meetingService.getMeeting( id );
    }

    @PostMapping("/meetings")
    public Meeting addMeeting(@RequestBody Meeting meeting) throws Exception {
        System.out.println("addMeeting ->");
        return meetingService.addMeeting(meeting);
    }

    @PutMapping( "/meetings/{id}" )
    public Meeting updateMeeting(@PathVariable int id, @RequestBody Meeting meeting) throws Exception {
        return meetingService.updateMeeting( id, meeting);
    }

    @PostMapping( "/meetings/{id}" )
    public Meeting setMeeting(@PathVariable int id, @RequestBody Meeting meeting) throws Exception {
        return meetingService.updateMeeting( id, meeting);
    }

    @DeleteMapping( "/meetings/{id}" )
    public void deleteMeeting( @PathVariable int id ) throws Exception {
        meetingService.deleteMeeting( id );
    }

}
