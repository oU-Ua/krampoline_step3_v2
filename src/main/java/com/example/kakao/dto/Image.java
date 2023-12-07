package com.example.kakao.dto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Image {
    private String id;
    private String image;
    private int seed;
    private Object nsfw_content_detected; // null을 처리하기 위해 Object를 사용
    private Object nsfw_score; // null을 처리하기 위해 Object를 사용
}