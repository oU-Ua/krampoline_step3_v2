package com.example.kakao.dto;

import lombok.*;

import java.awt.*;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ChatGPTResponseDTO {
    String id;
    String object;
    LocalDate created;
    String model;
    Usage usage;
    List<Choice> choices;


}
