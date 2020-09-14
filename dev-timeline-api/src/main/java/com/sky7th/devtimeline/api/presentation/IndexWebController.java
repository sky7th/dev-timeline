package com.sky7th.devtimeline.api.presentation;


import com.sky7th.devtimeline.api.service.RecruitPostService;
import com.sky7th.devtimeline.api.service.dto.PostSearchForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@Controller
public class IndexWebController {

    private final RecruitPostService recruitPostService;

    @GetMapping("/")
    public String index(Model model, @Valid PostSearchForm searchForm) {
//        int offset = 0;
//        List<RecruitPostViewItem> items = recruitPostService.findBySearchForm(searchForm);
//        model.addAttribute("articles", items);
//        model.addAttribute("offset", offset+items.size());
        return "index";
    }

}
