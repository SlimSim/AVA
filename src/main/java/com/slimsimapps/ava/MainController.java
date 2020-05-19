package com.slimsimapps.ava;


import com.slimsimapps.ava.meeting.Meeting;
import com.slimsimapps.ava.meeting.MeetingService;
import com.slimsimapps.ava.participant.Participant;
import com.slimsimapps.ava.participant.ParticipantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MainController {

    @Autowired
    ParticipantService participantService;

    @Autowired
    MeetingService meetingService;

    Logger log = LoggerFactory.getLogger(MainController.class);

    @GetMapping({ "/", "/index", "/index/" })
    public ModelAndView index( ModelMap model ) {
        return new ModelAndView( "index", model );
    }


    @PostMapping("/meeting")
    public ModelAndView newMeeting(Meeting m, ModelMap model ) throws Exception {
        Meeting m2 = meetingService.addMeeting(m);
        String url = "/meeting/" + m2.getId();

        log.info("newMeeting: url = " + url);
        return new ModelAndView( "redirect:" + url);
    }

    @GetMapping("meeting/{id}")
    public ModelAndView myMeeting(@PathVariable int id, ModelMap model, HttpServletRequest request ) throws Exception {
        log.info("myMeeting ->");
        // TODO: fixa pushning av data när det uppdaterats m.a.p websockets, kolla:
        //          https://spring.io/guides/gs/messaging-stomp-websocket/
        Meeting m2 = meetingService.getMeeting( id );
        String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        model.addAttribute("participants", participantService.getAllParticipants( id ));
        model.addAttribute( "meetingId", m2.getId() );
        model.addAttribute( "meetingName", m2.getName() );
        model.addAttribute("joinUrl", baseUrl + "/meeting/" + id + "/join");
        return new ModelAndView( "meeting", model );
    }

    @GetMapping("meeting/{id}/join")
    public String joinMeeting(@PathVariable int id, Model model ) throws Exception {
        log.info("joinMeeting ->");
        Meeting m = meetingService.getMeeting(id);
        model.addAttribute( "meetingId", m.getId() );
        model.addAttribute( "meetingName", m.getName() );
        return "createMe";
    }


    //@Autowired
    //private SimpMessagingTemplate template;
    //this.template.convertAndSend("/topic/newParticipant", p2);

    @PostMapping("meeting/{meetingId}/participant")
    public ModelAndView createMeetingParticipant(@PathVariable int meetingId, Participant p, ModelMap model ) throws Exception {
        log.info("createMeetingParticipant ->");
        Participant p2 = participantService.addParticipant(p, meetingId);

        String url = "/meeting/" + meetingId + "/participant/" + p2.getId();

        System.out.println("addParticipant: p2 = " + p2);
        //this.template.convertAndSend("/topic/newParticipant", p2);



        return new ModelAndView( "redirect:" + url);
    }

    @GetMapping("meeting/{meetingId}/participant/{participantId}")
    public ModelAndView meetingParticipant(@PathVariable int meetingId, @PathVariable int participantId, ModelMap model ) throws Exception {
        log.info("meetingParticipant ->");
        Participant p2 = participantService.getParticipant(participantId);
        model.addAttribute("participant", p2 );


        Meeting m = meetingService.getMeeting(meetingId);
        model.addAttribute( "meetingName", m.getName() );

        return new ModelAndView( "meetingParticipant", model );
    }


    @PostMapping("meeting/{meetingId}/participant/{participantId}")
    public ModelAndView updateMeetingParticipant(@PathVariable int meetingId, @PathVariable int participantId, Participant p, ModelMap model ) throws Exception {
        log.info("updateMeetingParticipant ->");
        Participant p2 = participantService.updateParticipant(participantId, meetingId, p);
        model.addAttribute("participant", p2 );

        Meeting m = meetingService.getMeeting(meetingId);
        model.addAttribute( "meetingName", m.getName() );

        return new ModelAndView( "meetingParticipant", model );
    }

}
