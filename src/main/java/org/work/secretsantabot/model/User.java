package org.work.secretsantabot.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String userId;

    private String telegramFirstname;
    private String telegramLastname;

    @Column(nullable = false)
    private String telegramUsername;

    @Column(nullable = false)
    private long telegramChatId;

    private String authToken;

    private Date authTokenExpireDate;

}
