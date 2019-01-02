package com.sb.sfdcrest.demo01.comsbsfdcrestdemo01;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("uat/sfdc")
public class SfdcController {
    
    @RequestMapping("apitest")
    public String sfdctest() {
        return "sfdc test hello";
    }
    
}
