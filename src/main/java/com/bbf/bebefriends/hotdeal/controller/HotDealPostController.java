package com.bbf.bebefriends.hotdeal.controller;

import com.bbf.bebefriends.hotdeal.dto.HotDealPostDto;
import com.bbf.bebefriends.hotdeal.service.HotDealPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/hot-deal-post")
public class HotDealPostController {

    private final HotDealPostService hotDealPostService;

    @GetMapping
    public Page<HotDealPostDto> searchAllHotDealPost(Pageable pageable) {
        return hotDealPostService.searchAllHotDealPost(pageable);
    }

    @PostMapping
    public HotDealPostDto createHotDealPost(@RequestBody HotDealPostDto hotDealPostDto) {
        return hotDealPostService.createHotDealPost(hotDealPostDto);

    }

}
