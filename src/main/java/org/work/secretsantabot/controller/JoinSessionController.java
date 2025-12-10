package org.work.secretsantabot.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.work.secretsantabot.dto.JoinSessionDto;

@Controller
public class JoinSessionController {

    @MessageMapping("/join_session")
    @SendTo("/topic/new_participant")
    public JoinSessionDto joinSession(JoinSessionDto joinSessionDto) {
        return joinSessionDto;
    }

}
