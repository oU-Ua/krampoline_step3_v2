package com.example.kakao.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ChatGPTRequestDTO {
    String model;
    List<Message> Message;
    double temperature;


}
