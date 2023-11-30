package com.example.kakao.service;

import com.example.kakao.dto.ChatGPTRequestDTO;

public interface CreateService {
    public String createImage(String keyword);
    public String createText(String keyword);
    public String transfer(String text, String language);
    public String chatGPT(String keyword);

}
