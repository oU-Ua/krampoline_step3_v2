package com.example.kakao.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Mess {
    String location;
    String description;
    String address;
}
