package com.slimsimapps.ava.request;

import com.slimsimapps.ava.Greeting;
import com.slimsimapps.ava.participant.Participant;
import com.slimsimapps.ava.participant.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;


@Controller
public class RequestController {

    @Autowired
    ParticipantService participantService;

    @MessageMapping("/request")
    @SendTo("/topic/greetings")
    public Greeting request(Request request) throws Exception {
        System.out.println( "RequestController.request ->");
        System.out.println( "RequestController.request: message = " + request);

	Participant p = participantService.setParticipantRequest( request.getParticipantId(), request.getTypeOfRequest(), request.isActive() );
        System.out.println( "RequestController.request: p = " + p);

        String greeting = "";

        if( request.isActive() ) {
            greeting = p.getName() + " requests " + request.getTypeOfRequest();
        } else {
            greeting = p.getName() + " retracts " + request.getTypeOfRequest();
        }

	
        return new Greeting( greeting );
    }

/*
    @MessageMapping("/request2")
    @SendTo("/topic/greetings")
    public Greeting request2(HelloMessage2 message) throws Exception {
        System.out.println( "RequestController.request2 ->");
        System.out.println( "RequestController.request2: message = " + message);
        //Thread.sleep(1000); // simulated delay
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }
*/
}
