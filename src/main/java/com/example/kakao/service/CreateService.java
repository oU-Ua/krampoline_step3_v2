package com.example.kakao.service;

import com.example.kakao.dto.ChatGPTRequestDTO;
import com.example.kakao.dto.ImageResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.Map;

public interface CreateService {
    public String createImage(String keyword);
    public String[] createText(String keyword) throws JsonProcessingException;
    public String[] transfer(String[] text, String language);
    public String chatGPT(String keyword) throws JsonProcessingException;
    public String midjourey(String keyword);

}
