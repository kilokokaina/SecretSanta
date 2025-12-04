package org.work.secretsantabot.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@NoArgsConstructor
public class Session {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String sessionId;
    private String sessionName;

    private boolean status;

    private Date creationDate;

    private String adminUserId;

}
