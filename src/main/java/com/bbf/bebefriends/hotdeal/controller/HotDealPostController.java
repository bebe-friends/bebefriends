package com.bbf.bebefriends.hotdeal.controller;

import com.bbf.bebefriends.hotdeal.dto.HotDealPostDto;
import com.bbf.bebefriends.hotdeal.service.HotDealPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hot-deal-post")
public class HotDealPostController {

    private final HotDealPostService hotDealPostService;

    @PostMapping
    public HotDealPostDto createHotDealPost(@RequestBody HotDealPostDto hotDealPostDto) {
        return hotDealPostService.createHotDealPost(hotDealPostDto);

    }

}
