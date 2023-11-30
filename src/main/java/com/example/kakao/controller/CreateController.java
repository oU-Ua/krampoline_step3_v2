package com.example.kakao.controller;

import com.example.kakao.dto.RequestDTO;
import com.example.kakao.dto.ResponseDTO;
import com.example.kakao.service.CreateService;
import com.example.kakao.service.CreateServiceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CreateController {
    private final CreateService creatService;
    private final CreateServiceImpl createService;

    @GetMapping("/create")
    public ResponseDTO createAll(@RequestBody RequestDTO requestDTO){
        String uri = createService.createImage(requestDTO.getKeyword());
        String text_before = createService.createText(requestDTO.getKeyword());
        String text = createService.transfer(text_before, requestDTO.getLanguage());

        return new ResponseDTO(uri,text);
    }
}
