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

    HotDealPostDto createHotDealPost(HotDealPostDto hotDealPostDto, User user);

    HotDealPostDto updateHotDealPost(HotDealPostDto hotDealPostDto, User user);

    Long deleteHotDealPost(Long hotDealPostId, User user);

    HotDealPostDto searchHotDealPostDetail(Long hotDealPostId, User user);

    HotDealCommentDto createHotDealComment(HotDealCommentDto hotDealCommentDto, User user);

    HotDealCommentDto updateHotDealComment(HotDealCommentDto hotDealCommentDto, User user);

    Long deleteHotDealComment(Long hotDealCommentId, User user);

    Page<HotDealCommentDto> searchHotDealComment(Long hotDealPostId,Pageable pageable);

    HotDealLikeDto likeHotDealPost(Long hotDealPostId, User user);

    HotDealLikeDto likeHotDealPostChk(Long hotDealPostId, User user);
}
