package org.work.secretsantabot.api;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.work.secretsantabot.model.Session;
import org.work.secretsantabot.service.AuthService;
import org.work.secretsantabot.service.SessionService;
import org.work.secretsantabot.service.UserService;

@Slf4j
@RestController
public class SessionApi {

    private final SessionService sessionService;
    private final UserService userService;
    private final AuthService authService;

    @Autowired
    public SessionApi(SessionService sessionService, UserService userService, AuthService authService) {
        this.sessionService = sessionService;
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping("new_session")
    public ResponseEntity<Session> newSession(HttpServletRequest request, @RequestBody String sessionName) {
        String authToken = authService.getCookieValue(request.getCookies(), "session_token");
        if (!authService.checkAuth(authToken)) {
            log.info("Request unauthorized");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        var session = new Session();
        session.setSessionName(sessionName);
        session.setAdminUserId(userService.findByAuthToken(authToken).getUserId());

        return ResponseEntity.ok(sessionService.save(session));
    }

}
