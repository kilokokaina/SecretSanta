package org.work.secretsantabot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.work.secretsantabot.service.SessionService;

@Controller
public class MainController {

    private final SessionService sessionService;

    @Autowired
    public MainController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping
    public String hello() {
        return "index";
    }

    @GetMapping("session/{sessionId}")
    public String session(@PathVariable String sessionId, Model model) {
        return "session";
    }

}
