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
