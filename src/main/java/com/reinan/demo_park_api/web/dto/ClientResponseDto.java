package com.reinan.demo_park_api.web.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ClientResponseDto {

    private Long id;
    private String name;
    private String cpf;
}
