package com.example.kakao.service;

import com.example.kakao.dto.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.net.URL;
import com.google.gson.JsonParser;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateServiceImpl implements CreateService {


    public String[] createText(String keyword) throws JsonProcessingException {
        String  response = chatGPT(keyword);
        String[] textlist = response.split("\n\n");

        return textlist;


    }



    public String[] transfer(String[] text, String lang){
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<text.length;i++){
            sb.append(text[i]);
        }
        String txt = sb.toString();

        String result = papago(txt,lang);
        String [] results = result.split("\n\n");
        System.out.println(Arrays.toString(results));
        return results;
    }

    public String papago(String txt, String lang) {
        Dotenv dotenv = Dotenv.configure().load();

        String clientSecret = dotenv.get("X-NCP-APIGW-API-KEY");
        String clientId = "s1axzu9x6n";
        StringBuffer response;
        try {
            String text = URLEncoder.encode(txt, "UTF-8");
            String apiURL = "https://naveropenapi.apigw.ntruss.com/nmt/v1/translation";
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId);
            con.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret);
            // post request
            String postParams = "source=ko&target="+lang+"&text=" + text;
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(postParams);
            wr.flush();
            wr.close();
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if (responseCode == 200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 오류 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            return response.toString();


        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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


}
