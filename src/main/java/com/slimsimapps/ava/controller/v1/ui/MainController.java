package com.slimsimapps.ava.controller.v1.ui;


import com.slimsimapps.ava.dto.model.MeetingDto;
import com.slimsimapps.ava.dto.model.ParticipantDto;
import com.slimsimapps.ava.service.BadLogService;
import com.slimsimapps.ava.service.MeetingService;
import com.slimsimapps.ava.service.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    BadLogService log;


    @GetMapping({ "/instructions", "/instructions.html" })
    public ModelAndView instructions( ModelMap model ) {
        log.a();
        log.o();
        return new ModelAndView( "instructions", model );
    }

    @GetMapping({ "/", "/index", "/index/" })
    public ModelAndView index( ModelMap model ) {
        log.a();
        log.o();
        return new ModelAndView( "index", model );
    }

    @PostMapping("/meeting")
    public ModelAndView newMeeting(MeetingDto m, ModelMap model ) throws Exception {
        log.a();
        MeetingDto m2 = meetingService.addMeeting(m);
        String url = "/meeting/" + m2.getId();
        log.o( url );
        return new ModelAndView( "redirect:" + url);
    }

    @GetMapping("meeting/{id}")
    public ModelAndView myMeeting(@PathVariable int id, ModelMap model, HttpServletRequest request ) throws Exception {
        log.a();
        MeetingDto m2 = meetingService.getMeeting( id );
        String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        model.addAttribute("participants", participantService.getAllParticipantDtos( id ));
        model.addAttribute( "meeting", m2 );
        model.addAttribute("joinUrl", baseUrl + "/meeting/" + id + "/join");

        log.o( model );
        return new ModelAndView( "meeting", model );
    }

    @GetMapping("meeting/{id}/join")
    public String joinMeeting(@PathVariable int id, Model model ) throws Exception {
        log.a(id);
        MeetingDto m = meetingService.getMeeting(id);
        model.addAttribute( "meetingId", m.getId() );
        model.addAttribute( "meetingName", m.getName() );
        log.o();
        return "createMe";
    }

    @PostMapping("meeting/{meetingId}/participant")
    public ModelAndView createMeetingParticipant(@PathVariable int meetingId, ParticipantDto p, ModelMap model ) throws Exception {
        log.a();
        ParticipantDto p2 = participantService.addParticipant(p, meetingId);

        String url = "/meeting/" + meetingId + "/participant/" + p2.getId();

        log.o( url );
        return new ModelAndView( "redirect:" + url);
    }

    @GetMapping("meeting/{meetingId}/participant/{participantId}")
    public ModelAndView meetingParticipant(@PathVariable int meetingId, @PathVariable int participantId, ModelMap model ) throws Exception {
        log.a();
        ParticipantDto p = participantService.getParticipant(participantId);
        model.addAttribute("participant", p );

        MeetingDto m = meetingService.getMeeting(meetingId);
        model.addAttribute( "meetingName", m.getName() );

        log.o( model );
        return new ModelAndView( "meetingParticipant", model );
    }


    @PostMapping("meeting/{meetingId}/participant/{participantId}")
    public ModelAndView updateMeetingParticipant(@PathVariable int meetingId, @PathVariable int participantId, ParticipantDto p, ModelMap model ) throws Exception {
        log.a();
        ParticipantDto p2 = participantService.updateParticipant(participantId, meetingId, p);
        model.addAttribute("participant", p2 );

        MeetingDto m = meetingService.getMeeting(meetingId);
        model.addAttribute( "meetingName", m.getName() );

        log.o( model );
        return new ModelAndView( "meetingParticipant", model );
    }

}