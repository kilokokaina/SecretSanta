package org.work.secretsantabot.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TelegramUserResponse {

    private String firstName;
    private String lastName;
    private String username;

}
