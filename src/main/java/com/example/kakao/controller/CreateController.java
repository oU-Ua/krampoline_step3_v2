package com.example.kakao.controller;

import com.example.kakao.dto.Mess;
import com.example.kakao.dto.RequestDTO;
import com.example.kakao.dto.Response2DTO;
import com.example.kakao.dto.ResponseDTO;
import com.example.kakao.service.CreateService;
import com.example.kakao.service.CreateServiceImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@CrossOrigin(origins ="*", allowedHeaders = "*")
public class CreateController {
    private final CreateService creatService;
    private final CreateServiceImpl createService;

    @PostMapping("/create")
    public Response2DTO createAll(@RequestBody RequestDTO requestDTO) throws JsonProcessingException {
        String uri = createService.createImage(requestDTO.getKeyword());
//        String uri = "https://cdn.discordapp.com/attachments/1153509131412570112/1179744989236965406/raymond.___Seongsan_Sunrise_Peak_in_Jeju_bdbb42ac-bf87-46ed-b83c-d582bb99c79a.png?ex=657ae624&is=65687124&hm=0d72c7ace0f7a430c17c7a633f2b8a85e9e565b88ec693b02a4a6f7e9baff0f1&";
        String[] result = createService.createText(requestDTO.getKeyword());
        List<Mess> text = new ArrayList<>();
        for(int i=0; i<result.length;i++){
             String [] prompt = result[i].split("\n");

            text.add(new Mess(prompt[0],prompt[1],prompt[2]));
        }

        if(requestDTO.getLang().equals("ko"))
            return new Response2DTO(uri,result);



        String[] text1 = createService.transfer(result, requestDTO.getLang());


        return new Response2DTO(uri,text1);
    }
}
