package com.reinan.demo_park_api.web.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserTokenResponse {
    String accessToken;
    Long ExpiresIn;
}
