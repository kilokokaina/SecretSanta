package org.work.secretsantabot.dto;

public class TelegramUserModel {

    private int id;
    private String first_name;
    private String last_name;
    private String username;
    private String language_code;
    private boolean is_premium;
    private boolean allows_write_to_pm;
    private String photo_url;

    public TelegramUserModel() {}

    public String getFirst_name() {
        return this.first_name;
    }

    public String getLast_name() {
        return this.last_name;
    }

    public String getUsername() {
        return this.username;
    }

}
