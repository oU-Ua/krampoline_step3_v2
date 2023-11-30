package com.example.kakao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Usage {
    @JsonProperty("prompt_tokens")
    String promptTokens;

    @JsonProperty("completion_tokens")
    String completionTokens;

    @JsonProperty("total_tokens")
    String totalTokens;

}
