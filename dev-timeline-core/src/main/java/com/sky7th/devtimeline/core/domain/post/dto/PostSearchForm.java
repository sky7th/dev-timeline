package com.sky7th.devtimeline.core.domain.post.dto;

import com.sky7th.devtimeline.core.config.PagingConstant;
import com.sky7th.devtimeline.core.domain.company.domain.CompanyType;
import com.sky7th.devtimeline.core.domain.linkpost.domain.LinkType;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
@Getter
@Setter
public class PostSearchForm {

    Long offset = 0L;
    Long limit = PagingConstant.PAGE_SIZE;
    List<String> tags;
    List<String> companies;
    List<String> linkTypes;
    boolean liked;
    String sortOrderType;

    @Builder
    public PostSearchForm(Long offset, Long limit, List<String> tags, List<String> companies, List<String> linkTypes,
                          boolean liked, String sortOrderType) {
        this.offset = offset;
        this.limit = limit;
        this.tags = tags;
        this.companies = companies;
        this.linkTypes = linkTypes;
        this.liked = liked;
        this.sortOrderType = sortOrderType;
    }

    public boolean isFirstLoad() {
        return this.offset == 0L;
    }

    @Override
    public String toString() {
        return "PostSearchForm{" +
                "offset=" + offset +
                ", limit=" + limit +
                ", tags='" + tags + '\'' +
                ", companies=" + companies +
                '}';
    }

    public List<CompanyType> getCompanies() {
        if (StringUtils.isEmpty(companies)) {
            return null;
        }
        return companies.stream()
                .map(CompanyType::valueOf)
                .collect(Collectors.toList());
    }

    public List<LinkType> getLinkTypes() {
        if (StringUtils.isEmpty(linkTypes)) {
            return null;
        }
        return linkTypes.stream()
                .map(LinkType::valueOf)
                .collect(Collectors.toList());
    }

}
