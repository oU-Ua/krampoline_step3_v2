package com.example.kakao.service;

import com.example.kakao.dto.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.JsonParser;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateServiceImpl implements CreateService {

    @Override
    public String createImage(String keyword) {
        String uri= midjourey(keyword);
        return uri;
    }



    public String[] createText(String keyword) throws JsonProcessingException {
        String  response = chatGPT(keyword);
        String[] textlist = response.split("\n\n");

        return textlist;


    }
    public String transfer(String text, String language){
        if(language.equals("kr"))
            return text;
        text = "transfertest";
        return text;
    }

    @Override
    public String chatGPT(String keyword) throws JsonProcessingException {
        Dotenv dotenv = Dotenv.configure().load();

        String apiKey = dotenv.get("OPEN_AI_API_KEY");
        ObjectMapper mapper = new ObjectMapper();
        List<Message> messages = new ArrayList<>();
        StringBuilder prompt = new StringBuilder();

        prompt.append("당신은 제주도 관광가이드입니다. ");
        prompt.append(keyword);
        prompt.append("에 관광하고싶어하는 외국인에게 설명해주세요.");
        prompt.append("장소에 대한 설명, 그 장소에서 할 수 있는 체험이나 활동, 역사에 대해 알려주세요. ");
        messages.add(new Message("user", prompt.toString()));

        ChatGPTRequestDTO chatGptRequest = new ChatGPTRequestDTO("gpt-4", messages, 0.7);
        String input = null;
        input = mapper.writeValueAsString(chatGptRequest);
        System.out.println(input);
        System.out.println("apikey : " + apiKey);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openai.com/v1/chat/completions"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer "+apiKey)
                .POST(HttpRequest.BodyPublishers.ofString(input))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        ObjectMapper mapper1 = new ObjectMapper();
        mapper1.registerModule(new JavaTimeModule());
        ChatGPTResponseDTO dto;
        try {
            dto = mapper1.readValue(response.body(), ChatGPTResponseDTO.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        List<Choice> choices = dto.getChoices();
        String text = choices.get(0).getMessage().getContent();
        return text;
    }

   @Override
   public String midjourey(String keyword) {
       Dotenv dotenv = Dotenv.configure().load();

       String apiKey = dotenv.get("OPEN_AI_API_KEY");
       StringBuilder prompt = new StringBuilder();
       prompt.append("Manjanggul Cave");
       prompt.append("in Jeju");
       String params = "prompt="+ URLEncoder.encode(prompt.toString(), StandardCharsets.UTF_8);


       HttpRequest request = HttpRequest.newBuilder()
               .uri(URI.create("https://mj.medici-mansion.com/image?"))
               .header("auth", apiKey)
               .POST(HttpRequest.BodyPublishers.ofString(params))
               .build();

       HttpClient client = HttpClient.newHttpClient();

       String responseBody = null;
       try {
           responseBody = client.send(request, HttpResponse.BodyHandlers.ofString()).body();
       } catch (IOException | InterruptedException e) {
           throw new RuntimeException(e);
       }
       System.out.println(responseBody);
//       JsonReader jsonReader = Json.createReader(new StringReader(responseBody));
//       JsonArray responses = jsonReader.readArray();
//
//       JsonObject firstResponse = responses.getJsonObject(0);
//       String firstUri = firstResponse.getString("uri");
//       List<String> uri = new ArrayList<>();
//       for (int i = 1; i < responses.size(); i++) {
//           JsonObject response = responses.getJsonObject(i);
//           uri.add(response.getString("uri"));
//       }
       return responseBody;

   }

}
