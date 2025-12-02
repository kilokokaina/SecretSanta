package org.work.secretsantabot.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TelegramUserModel {

    private int id;
    private String first_name;
    private String last_name;
    private String username;
    private String language_code;
    private boolean is_premium;
    private boolean allows_write_to_pm;
    private String photo_url;

}
