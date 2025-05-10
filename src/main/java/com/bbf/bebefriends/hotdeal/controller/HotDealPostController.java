package com.bbf.bebefriends.hotdeal.controller;

import com.bbf.bebefriends.hotdeal.dto.HotDealCommentDto;
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

    @GetMapping("/category")
    public Page<HotDealPostDto> searchCategoryHotDealPost(@RequestParam Long hotDealCategoryId,Pageable pageable) {
        return hotDealPostService.searchCategoryHotDealPost(hotDealCategoryId,pageable);
    }

    @PostMapping
    public HotDealPostDto createHotDealPost(@RequestBody HotDealPostDto hotDealPostDto) {
        return hotDealPostService.createHotDealPost(hotDealPostDto);

    }

    @GetMapping("/comment")
    public Page<HotDealCommentDto> searchHotDealComment(@RequestParam Long hotDealPostId, Pageable pageable) {
        return hotDealPostService.searchHotDealComment(hotDealPostId,pageable);
    }

    @PostMapping("/comment")
    public HotDealCommentDto createHotDealComment(@RequestBody HotDealCommentDto hotDealCommentDto) {
        return hotDealPostService.createHotDealComment(hotDealCommentDto);

    }

}
