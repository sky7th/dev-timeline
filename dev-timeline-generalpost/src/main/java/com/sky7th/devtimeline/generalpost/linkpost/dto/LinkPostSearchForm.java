package com.sky7th.devtimeline.generalpost.linkpost.dto;

import com.sky7th.devtimeline.generalpost.linkpost.domain.LinkType;
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
public class LinkPostSearchForm {

    public static long PAGE_SIZE = 30;

    Long offset = 0L;
    Long limit = PagingConstant.POST_PAGE_SIZE;
    List<String> tags;
    List<String> linkTypes;
    boolean liked;
    OrderType orderType;
    FilterType filterType;

    @Builder
    public LinkPostSearchForm(Long offset, Long limit, List<String> tags, List<String> linkTypes,
                          boolean liked, OrderType orderType, FilterType filterType) {
        this.offset = offset;
        this.limit = limit;
        this.tags = tags;
        this.linkTypes = linkTypes;
        this.liked = liked;
        this.orderType = orderType;
        this.filterType = filterType;
    }

    public boolean isFirstLoad() {
        return this.offset == 0L;
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
