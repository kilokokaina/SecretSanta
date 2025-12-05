package org.work.secretsantabot.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JoinSessionDto {

    private String sessionId;
    private String userNickname;
    private String wishList;
    private String stopList;

}
