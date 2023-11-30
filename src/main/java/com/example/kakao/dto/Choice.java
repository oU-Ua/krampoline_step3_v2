package com.example.kakao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Choice {
    Integer index;
    Message message;

    @JsonProperty("finish_reason")
    String finishReason;
}
