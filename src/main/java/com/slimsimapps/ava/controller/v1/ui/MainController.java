package com.slimsimapps.ava.controller.v1.ui;


import com.slimsimapps.ava.dto.model.MeetingDto;
import com.slimsimapps.ava.dto.model.ParticipantDto;
import com.slimsimapps.ava.service.BadLogService;
import com.slimsimapps.ava.service.MeetingService;
import com.slimsimapps.ava.service.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
        String url = "/" + m2.getId() + "/admin";
        log.o( url );
        return new ModelAndView( "redirect:" + url);
    }

    @GetMapping("/{meetingId}/admin")
    public ModelAndView meetingAdmin(@PathVariable int meetingId, ModelMap model, HttpServletRequest request ) throws Exception {
        log.a();
        MeetingDto m2 = meetingService.getMeeting( meetingId );
        String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        model.addAttribute("participants", participantService.getAllParticipantDtos( meetingId ));
        model.addAttribute( "meeting", m2 );
        model.addAttribute("joinUrl", baseUrl + "/meeting/" + meetingId + "/join");

        log.o( model );
        return new ModelAndView( "meeting", model );
    }

    @PostMapping("/meeting/{meetingId}/participant")
    public ModelAndView createParticipant(@PathVariable int meetingId, ParticipantDto p, ModelMap model, HttpServletRequest request ) throws Exception {
        log.a();
        log.d( "p", p );
        ParticipantDto p2 = participantService.addParticipant(p, meetingId);

        request.getSession().setAttribute( "participantId", p2.getId() );

        String url = "/" + meetingId;

        log.o( url );
        return new ModelAndView( "redirect:" + url);
    }

    @GetMapping("/{meetingId}")
    public ModelAndView meeting(@PathVariable int meetingId, ModelMap model, HttpServletRequest request ) throws Exception {
        log.a();

        MeetingDto m = meetingService.getMeeting(meetingId);
        model.addAttribute( "meetingName", m.getName() );

        HttpSession session = request.getSession();

        Integer participantId = (Integer) session.getAttribute( "participantId" );

        if( participantId == null ||
                    !participantService.participantExists( participantId ) ||
                    !participantService.isParticipantInMeeting( participantId, meetingId ) ) {

            if( participantId != null && participantService.participantExists( participantId ) ) {
                participantService.deleteParticipant(participantId);
            }

            model.addAttribute( "meetingId", m.getId() );
            log.o();
            return new ModelAndView( "createMe", model );
        }

        ParticipantDto p = participantService.getParticipant(participantId);
        model.addAttribute("participant", p );

        log.o( model );
        return new ModelAndView( "participant", model );
    }

}
