package com.example.kakao.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ImageResponseDTO {
    String uri;
    String progress;
}
