package com.sky7th.devtimeline.web.presentation;


import com.sky7th.devtimeline.web.service.RecruitPostService;
import com.sky7th.devtimeline.web.service.dto.RecruitPostViewItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

import static com.sky7th.devtimeline.web.config.PagingConstant.PAGE_SIZE;

@Slf4j
@RequiredArgsConstructor
@Controller
public class IndexWebController {

    private final RecruitPostService recruitPostService;

    @GetMapping("/")
    public String index(Model model) {
        int offset = 0;
        List<RecruitPostViewItem> items = recruitPostService.findAllLimitDesc(offset, PAGE_SIZE);
        model.addAttribute("articles", items);
        model.addAttribute("offset", offset+items.size());
        return "index";
    }

}
