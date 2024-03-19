package com.wp.user.global.file.dto;

import lombok.*;

import java.io.Serializable;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Getter
@Builder
@AllArgsConstructor
public class UploadResultDto implements Serializable {

    private String fileName;
    private String uuid;
    private String folderPath;

    public String getImageURL() {
        return URLEncoder.encode(folderPath+"/"+uuid+"_"+fileName, StandardCharsets.UTF_8);
    }
}