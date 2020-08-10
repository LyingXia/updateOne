package com.update.controller;

import com.update.service.updateAllService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/testTools")
public class MyController {
    private static updateAllService ua = new updateAllService();
    @RequestMapping("/update")
    public String welcome(){
            return "/update_mechine";
    }

    @RequestMapping("/runSQL")
    public String SQLexecute(){
        return "/run_sql";
    }

}
