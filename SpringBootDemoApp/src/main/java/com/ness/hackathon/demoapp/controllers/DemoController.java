package com.ness.hackathon.demoapp.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @RequestMapping(method=RequestMethod.GET)
    public String getString(){
	return "Welcome to Hackathon in BITC";
    }
}
