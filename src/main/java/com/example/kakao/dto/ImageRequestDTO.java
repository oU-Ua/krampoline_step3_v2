package com.example.kakao.dto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImageRequestDTO {
    String prompt;
}