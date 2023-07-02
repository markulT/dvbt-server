package com.osmos.server.banner.dto;

import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BannerDto {

    private String title;
    private String content;
    private String imgName;
    private String detailsLink;
    private String id;

    public BannerDto(String title, String content, String imgName, String detailsLink, UUID id) {
        this.title = title;
        this.content = content;
        this.detailsLink = detailsLink;
        this.id = id.toString();
        this.imgName = imgName;
    }
}
