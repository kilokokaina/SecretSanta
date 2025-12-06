package org.work.secretsantabot.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.work.secretsantabot.service.AuthService;
import org.work.secretsantabot.service.SessionService;
import org.work.secretsantabot.service.UserService;

@Controller
public class MainController {

    private final SessionService sessionService;
    private final UserService userService;
    private final AuthService authService;

    @Autowired
    public MainController(SessionService sessionService, UserService userService, AuthService authService) {
        this.sessionService = sessionService;
        this.userService = userService;
        this.authService = authService;
    }

    @GetMapping
    public String hello() {
        return "index";
    }

    @GetMapping("session/{sessionId}")
    public String session(@PathVariable String sessionId, HttpServletRequest request, Model model) {
        String authToken = authService.getCookieValue(request.getCookies(), "session_token");
        String userId = userService.findByAuthToken(authToken).getUserId();

        var session = sessionService.findById(sessionId);

        model.addAttribute("sessionName", session.getSessionName());
        model.addAttribute("sessionId", session.getSessionId());
        model.addAttribute("creationDate", session.getCreationDate());
        model.addAttribute("status", session.isStatus());
        model.addAttribute("isAdmin", (session.getAdminUserId().equals(userId)));
        if (session.getAdminUserId().equals(userId)) {
            model.addAttribute("sessionUsers", sessionService.getSessionUserList(sessionId));
        }

        return "session";
    }

}
