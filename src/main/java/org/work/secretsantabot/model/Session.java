package org.work.secretsantabot.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;

@Data
@Entity
@NoArgsConstructor
public class Session {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String sessionId;
    private String sessionName;

    private boolean status;

    private Time creationDate;

    private String adminUserId;

}
