package com.sky7th.devtimeline.crawlpost;

import com.sky7th.devtimeline.crawlpost.company.domain.CompanyType;
import com.sky7th.devtimeline.post.config.PagingConstant;
import com.sky7th.devtimeline.post.post.dto.FilterType;
import com.sky7th.devtimeline.post.post.dto.OrderType;
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
public class CrawlPostSearchForm {

    public static long PAGE_SIZE = 30;

    Long offset = 0L;
    Long limit = PagingConstant.POST_PAGE_SIZE;
    List<String> companies;
    List<String> tags;
    boolean liked;
    OrderType orderType;
    FilterType filterType;

    @Builder
    public CrawlPostSearchForm(Long offset, Long limit, List<String> companies, List<String> tags, boolean liked,
        OrderType orderType, FilterType filterType) {
        this.offset = offset;
        this.limit = limit;
        this.companies = companies;
        this.tags = tags;
        this.liked = liked;
        this.orderType = orderType;
        this.filterType = filterType;
    }

    public boolean isFirstLoad() {
        return this.offset == 0L;
    }

    public List<CompanyType> getCompanies() {
        if (StringUtils.isEmpty(companies)) {
            return null;
        }
        return companies.stream()
                .map(CompanyType::valueOf)
                .collect(Collectors.toList());
    }
}
