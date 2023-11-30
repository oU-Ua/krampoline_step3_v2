package com.example.kakao.controller;

import com.example.kakao.dto.Mess;
import com.example.kakao.dto.RequestDTO;
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
public class CreateController {
    private final CreateService creatService;
    private final CreateServiceImpl createService;

    @PostMapping("/create")
    public ResponseDTO createAll(@RequestParam RequestDTO requestDTO) throws JsonProcessingException {
        String uri = createService.createImage(requestDTO.getKeyword());
//        String uri = "test";
        String[] result = createService.createText(requestDTO.getKeyword());
        List<Mess> text = new ArrayList<>();
        for(int i=0; i<result.length;i++){
            text.add(new Mess(i+1,result[i]));
        }

        if(requestDTO.getLang().equals("ko"))
            return new ResponseDTO(uri,text);

//        String text1 = createService.transfer(text, requestDTO.getLanguage());
        return new ResponseDTO(uri,text);
    }
}
