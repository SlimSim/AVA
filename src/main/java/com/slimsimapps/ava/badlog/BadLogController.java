package com.slimsimapps.ava.badlog;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class BadLogController {


    @Autowired
    BadLogService badLogService;

    @GetMapping({ "/badLog" })
    public ModelAndView badLog(ModelMap model ) {
        return new ModelAndView( "badLog", model );
    }

    @GetMapping("/badLog/api/all")
    public List<BadLog> getAllBadLogs(){
        return badLogService.getAllBadLogs();
    }

    @GetMapping("/badLog/api/latest")
    public BadLog getLatestBadLogs(){
        return badLogService.getLatestBadLog();
    }

    @GetMapping("/badLog/api/clear")
    public BadLog clearAllBadLogs(){
        badLogService.clearAllBadLogs();
        return new BadLog("BadLogs cleared" );
    }

}
