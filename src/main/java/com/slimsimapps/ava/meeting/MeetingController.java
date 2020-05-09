package com.slimsimapps.ava.meeting;


import com.slimsimapps.ava.MainController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MeetingController {


    Logger log = LoggerFactory.getLogger(MainController.class);

    @Autowired
    MeetingService meetingService;

    @GetMapping("/meetings")
    public List<Meeting> getAllMeetings(){
        return meetingService.getAllMeetings();
    }

    @GetMapping("/meetings/{id}")
    public Meeting getMeeting(@PathVariable int id ) throws Exception {
        log.info( "getMeeting -> id = " + id);
        return meetingService.getMeeting( id );
    }

    @PostMapping("/meetings")
    public Meeting addMeeting(@RequestBody Meeting meeting) throws Exception {
        System.out.println("addMeeting ->");
        return meetingService.addMeeting(meeting);
    }

    @PutMapping( "/meetings/{id}" )
    public Meeting updateMeeting(@PathVariable int id, @RequestBody Meeting meeting) throws Exception {
        log.info( "updateMeeting -> id = " + id);
        return meetingService.updateMeeting( id, meeting);
    }

    @PostMapping( "/meetings/{id}" )
    public Meeting setMeeting(@PathVariable int id, @RequestBody Meeting meeting) throws Exception {
        log.info( "setMeeting -> id = " + id);
        return meetingService.updateMeeting( id, meeting);
    }

    @DeleteMapping( "/meetings/{id}" )
    public void deleteMeeting( @PathVariable int id ) throws Exception {
        meetingService.deleteMeeting( id );
    }

/*
    @PostMapping("/meetings2")
    public String createMe2(Meeting meeting ) {
        System.out.println("createMe ->");
        log.trace("createMe2 -> trace");
        log.info("createMe2 -> info");
        log.debug("createMe2 -> debug");
        log.warn("createMe2 -> warn");
        System.out.println("createMe2");
        Meeting p2 = new Meeting();
        try {
            p2 = meetingService.addMeeting(meeting);
        } catch ( Exception e ) {
            log.error("createMe could not add meeting: " + e.getMessage() + " " + e.getCause());
        }

        //model.addAttribute("meeting", p2 );

        return "me";
    }
    */

}
