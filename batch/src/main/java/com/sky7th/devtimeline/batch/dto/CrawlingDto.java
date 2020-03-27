package com.sky7th.devtimeline.batch.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrawlingDto {

    private String name;
    private String title;
    private String url;
    private String date;

}
