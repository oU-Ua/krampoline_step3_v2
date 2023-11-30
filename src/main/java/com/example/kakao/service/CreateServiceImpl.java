package com.example.kakao.service;

import com.example.kakao.dto.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateServiceImpl implements CreateService {

    private String apikey= "";
    @Override
    public String createImage(String keyword) {
//        ImageResponseDTO imageResponseDTO = ;
//        String uri = imageResponseDTO.getUri();
        String uri = "test";
        return uri;
    }
    public String createText(String keyword){
//        String  text_before = chatGPT(keyword);
        String text_before = "";
        return text_before;


    }
    public String transfer(String text, String language){
        if(language.equals("kr"))
            return text;
        text = "transfertest";
        return text;
    }

    @Override
    public String chatGPT(String keyword) {

//        ObjectMapper mapper = new ObjectMapper();
//        List<Message> messages = new ArrayList<>();
//        StringBuilder prompt = new StringBuilder();
//
//        prompt.append("당신은 유치원 선생님입니다.");
//        prompt.append(keyword);
//        prompt.append("에 대해 7-8살에게 설명해주세요.");
//        prompt.append("그리고 관광을 어떻게 하면 좋을지 알려주세요");
//        messages.add(new Message("user", "prompt"));
//
//        ChatGPTRequestDTO chatGptRequest = new ChatGPTRequestDTO("gpt-3.5-turbo", messages, 0.7);
//        String input = null;
//        try {
//            input = mapper.writeValueAsString(chatGptRequest);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create("https://api.openai.com/v1/chat/completions"))
//                .header("Content-Type", "application/json")
//                .header("Authorization", apikey)
//                .POST(HttpRequest.BodyPublishers.ofString(input))
//                .build();
//
//        HttpClient client = HttpClient.newHttpClient();
//        HttpResponse<String> response = null;
//        try {
//            response =  client.send(request, HttpResponse.BodyHandlers.ofString());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//        List<Choice> choices = response.getChoices();
//        String text = choices.get(0).getMessage().getContent();
//        return text;
        return keyword;
    }

}
