package com.example.kakao;

import com.example.kakao.dto.RequestDTO;
import com.example.kakao.service.CreateService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class KakaoApplicationTests {
    @Autowired
    private CreateService createService;

    @Test
    void contextLoads() {
        RequestDTO requestDTO = new RequestDTO("ko","Seongsan Sunrise Peak");
        String result = createService.createImage(requestDTO.getKeyword());
        System.out.println(result);
    }

}