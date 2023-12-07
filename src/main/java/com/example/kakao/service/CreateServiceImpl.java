package com.example.kakao.service;

import com.example.kakao.dto.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javax.json.Json;
import java.net.*;
import javax.json.JsonArray;
import javax.json.JsonReader;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateServiceImpl implements CreateService {

    @Override
    public String createImage(String keyword) {
        String uri= karlo(keyword);
        return uri;
    }



    public String[] createText(String keyword) throws JsonProcessingException {
        String  response = chatGPT(keyword);
        String[] textlist = response.split("\n\n");
        return textlist;

    }




    public String[] transfer(String[] text, String lang){
        String [] trasfers = new String [text.length];
        for(int i=0;i<text.length;i++){
            String result = papago(text[i],lang);
            trasfers[i]=result;
        }

        return trasfers;
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
            String getresult = response.toString();

            String result1 = getresult.split("\"")[15];   //스플릿으로 번역된 결과값만 가져오기
            int i=15;
            while (result1.length() <=10){
                result1 = getresult.split("\"") [15+i];
            }
            return result1;


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

        String apiKey = System.getenv("OPEN_AI_API_KEY");
        ObjectMapper mapper = new ObjectMapper();
        List<Message> messages = new ArrayList<>();
        StringBuilder prompt = new StringBuilder();

        prompt.append("내가 제주도의 관광지에 대해서 질문을 할거야. 너가 그 관광지 1개와 근처에 존재하는 관광지를 소개해주면 좋겠어. 내 질문에 대답 외의 설명은 필요없어.\n" +
                "\n" +
                "\"여행지 이름\"\n" +
                "\"소개\"\n" +
                "\"주소\"\n" +
                "\n" +
                "의 구조이고, 첫줄은 내가 입력한 여행지야.\n" +
                "내가 입력한 여행지 외에 추천해 주는 여행지는 3개를 넘으면 안돼\n" +
                "\n" +
                "입력한 여행지 : ");
        prompt.append(keyword);
        prompt.append("\n사담은 필요없어.");
        messages.add(new Message("user", prompt.toString()));

        ChatGPTRequestDTO chatGptRequest = new ChatGPTRequestDTO("gpt-4", messages, 0.7);
        String input = null;
        input = mapper.writeValueAsString(chatGptRequest);
        System.out.println(input);

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
        System.out.println(text);
        return text;
    }

   @Override
   public String karlo(String keyword) {

       Dotenv dotenv = Dotenv.configure().load();

       String apiKey = dotenv.get("KAKAO_API_KEY");
       StringBuilder prompt = new StringBuilder();
       prompt.append(keyword);
       prompt.append("+in+Jeju");
       ObjectMapper mapper = new ObjectMapper();

       ImageRequestDTO imageRequestDTO = new ImageRequestDTO(prompt.toString());
       String input = null;
       try {
           input = mapper.writeValueAsString(imageRequestDTO);
       } catch (JsonProcessingException e) {
           throw new RuntimeException(e);
       }

       HttpRequest request = HttpRequest.newBuilder()
               .uri(URI.create("https://api.kakaobrain.com/v2/inference/karlo/t2i"))
               .header("Content-Type", "application/json")
               .header("Authorization","KakaoAK "+ apiKey)
               .POST(HttpRequest.BodyPublishers.ofString(input))
               .build();
       System.out.println("request : "+ request);
       HttpClient client = HttpClient.newHttpClient();
       HttpResponse<String> response = null;
       try {
           response = client.send(request, HttpResponse.BodyHandlers.ofString());
           ObjectMapper mapper1 = new ObjectMapper();
           ImageResponseDTO dto;
           dto = mapper1.readValue(response.body(),ImageResponseDTO.class);
           List<Image> result = dto.getImages();
           String url = result.get(0).getImage();
           return url;
       } catch (IOException e) {
           throw new RuntimeException(e);
       } catch (InterruptedException e) {
           throw new RuntimeException(e);
       }


   }

}
