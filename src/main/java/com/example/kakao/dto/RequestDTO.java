package com.example.kakao.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RequestDTO {
    String language;
    String keyword;

}
