package com.bbf.bebefriends.hotdeal.service;

import com.bbf.bebefriends.hotdeal.dto.HotDealCommentDto;
import com.bbf.bebefriends.hotdeal.dto.HotDealLikeDto;
import com.bbf.bebefriends.hotdeal.dto.HotDealPostDto;

import com.bbf.bebefriends.member.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HotDealPostService {

    Page<HotDealPostDto> searchAllHotDealPost(Pageable pageable);

    Page<HotDealPostDto> searchCategoryHotDealPost(Long hotDealCategoryId,Pageable pageable);

    HotDealPostDto createHotDealPost(HotDealPostDto hotDealPostDto);

    HotDealCommentDto createHotDealComment(HotDealCommentDto hotDealCommentDto);

    Page<HotDealCommentDto> searchHotDealComment(Long hotDealPostId,Pageable pageable);

    Long likeHotDealPost(Long hotDealPostId, User user);
}
