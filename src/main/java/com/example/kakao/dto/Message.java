package com.example.kakao.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Message {
    String role;
    String content;
}
