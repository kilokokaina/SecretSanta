package org.work.secretsantabot.api;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.work.secretsantabot.dto.JoinSessionDto;
import org.work.secretsantabot.model.Session;
import org.work.secretsantabot.model.SessionUserList;
import org.work.secretsantabot.service.AuthService;
import org.work.secretsantabot.service.SessionService;
import org.work.secretsantabot.service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        if (!authService.checkAuth(authToken)) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        var session = new Session();
        session.setSessionName(sessionName);
        session.setAdminUserId(userService.findByAuthToken(authToken).getUserId());

        return ResponseEntity.ok(sessionService.save(session));
    }

    @PostMapping("join_session")
    public ResponseEntity<Session> joinSession(HttpServletRequest request, @RequestBody JoinSessionDto joinSessionDto) {
        String authToken = authService.getCookieValue(request.getCookies(), "session_token");
        if (!authService.checkAuth(authToken)) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        var sessionUserList = new SessionUserList();
        sessionUserList.setUserId(userService.findByAuthToken(authToken).getUserId());
        sessionUserList.setSessionId(sessionService.findById(joinSessionDto.getSessionId()).getSessionId());
        sessionUserList.setUserNickname(joinSessionDto.getUserNickname());
        sessionUserList.setWishList(joinSessionDto.getWishList());
        sessionUserList.setStopList(joinSessionDto.getStopList());

        return ResponseEntity.ok(sessionService.joinSession(sessionUserList));
    }

    @GetMapping("get_sessions")
    public ResponseEntity<Map<String, List<Session>>> getSessions(HttpServletRequest request) {
        String authToken = authService.getCookieValue(request.getCookies(), "session_token");
        if (!authService.checkAuth(authToken)) return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        var result = new HashMap<String, List<Session>>();
        result.put("admin", sessionService.findAllByUsername(userService.findByAuthToken(authToken).getUserId()));
        result.put("user", sessionService.findAllByUserId(userService.findByAuthToken(authToken).getUserId()));

        return ResponseEntity.ok(result);
    }

}
