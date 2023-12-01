package com.example.kakao.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Response2DTO {
    String uri;
    String [] text;
}
