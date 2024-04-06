package com.gagan.basicauth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BasicAuthController {


    @GetMapping("/authenticate")
    public String auhenticate() {
        return "Yippy, Authentication successful.";
    }

    @GetMapping("/authorize")
    public String authorize() {
        return "Authroization successful.";
    }

    @GetMapping("/public")
    public String publicResource() {
        return "Public URL, no authentication is required !!";
    }
}
