package com.socialnetwork.social_network.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/auth")
public class demo {

    @GetMapping("/demo")
    public ResponseEntity<String> saHello() {
        return ResponseEntity.ok("Hello from the other side");
    }
    
}
