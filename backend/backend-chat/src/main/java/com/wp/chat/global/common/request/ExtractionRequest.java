package com.wp.chat.global.common.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExtractionRequest {
    private String accessToken;
    private List<String> infos;
}
