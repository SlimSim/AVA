package com.slimsimapps.ava.badlog;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BadLogController {


    @Autowired
    BadLogService badLogService;

    /*@GetMapping({ "/badLogs/home" })
    public ModelAndView index(ModelMap model ) {
        return new ModelAndView( "badLog", model );
    }*/

    @GetMapping("/badLogs/api/all")
    public List<BadLog> getAllBadLogs(){
        return badLogService.getAllBadLogs();
    }

    @GetMapping("/badLogs/api/latest")
    public BadLog getLatestBadLogs(){
        return badLogService.getLatestBadLog();
    }

    @GetMapping("/badLogs/api/clear")
    public BadLog clearAllBadLogs(){
        badLogService.clearAllBadLogs();
        return new BadLog("BadLogs cleared" );
    }

}
