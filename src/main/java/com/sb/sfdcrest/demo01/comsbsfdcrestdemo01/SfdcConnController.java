package com.sb.sfdcrest.demo01.comsbsfdcrestdemo01;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SfdcConnController {
    
    @RequestMapping("/sfdcconn01")
    public String connpage() {
        return "test the sfdc conn here";
    }
    
    
}
