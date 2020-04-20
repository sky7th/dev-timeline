package com.sky7th.devtimeline.batch.dto;

import com.sky7th.devtimeline.core.domain.companyUrl.CompanyUrl;
import com.sky7th.devtimeline.core.domain.companyUrl.CompanyUrlType;
import com.sky7th.devtimeline.core.domain.post.recruitpost.RecruitPost;
import com.sky7th.devtimeline.core.domain.post.techpost.TechPost;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrawlingDto {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private CompanyUrl companyUrl;

    private String title;

    private String author;

    private String closingDate;

    private String date;

    private String thumbnailUrl;

    private String contentUrl;

    public String toString() {
        return companyUrl.getCompany().getCompanyType().getName()
                +","+companyUrl.getCompanyUrlType().getName()
                +","+title
                +","+(date==null ? "" : date)+(closingDate==null ? "" : closingDate)
                +","+contentUrl;
    }

    public RecruitPost toRecruitPost() {
        RecruitPost recruitPost = RecruitPost.builder()
                .contentUrl(this.contentUrl)
                .closingDate(this.closingDate)
                .title(this.title)
                .sortDate(parseClosingTime())
                .build();
        recruitPost.setCompanyUrl(this.companyUrl);

        return recruitPost;
    }

    public TechPost toTechPost() {
        TechPost techPost = TechPost.builder()
                .contentUrl(this.contentUrl)
                .author(this.author)
                .title(this.title)
                .date(this.date)
                .thumbnailUrl(this.thumbnailUrl)
                .sortDate(parseDateTime())
                .build();
        techPost.setCompanyUrl(this.companyUrl);

        return techPost;
    }

    public boolean isCompanyUrlType(CompanyUrlType companyUrlType) {
        return this.companyUrl.getCompanyUrlType().equals(companyUrlType);
    }

    private LocalDateTime parseClosingTime() {
        String resultDate = "";

        if (closingDate.equals("") || closingDate.matches("^[가-힣\\s]*$")) {
            return null;

        } else if (closingDate.contains("까지")) {
            String[] dates = closingDate.replaceAll("[년일월]", "").split(" ");
            resultDate = dates[0] + "-" + get2Digit(dates[1]) + "-" + get2Digit(dates[2]) + " 00:00:00";
        } else {
            String startDate = closingDate.split("~")[0];
            String[] dates = startDate.split("[.]");
            resultDate = dates[0] + "-" + get2Digit(dates[1]) + "-" + get2Digit(dates[2]) + " 00:00:00";
        }
        return LocalDateTime.parse(resultDate, FORMATTER);
    }

    private String get2Digit(String date) {
        if (date.length()==1)
            return "0"+date;
        else
            return date;
    }

    private LocalDateTime parseDateTime() {
        String resultDate = "";

        if (date.equals("")) {
            return LocalDateTime.now();

        } else {
            String[] dates = date.split("[.]");
            resultDate = dates[0] + "-" + get2Digit(dates[1]) + "-" + get2Digit(dates[2]) + " 00:00:00";
        }
        return LocalDateTime.parse(resultDate, FORMATTER);
    }

}
