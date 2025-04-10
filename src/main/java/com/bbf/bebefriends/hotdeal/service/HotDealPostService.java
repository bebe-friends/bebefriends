package com.bbf.bebefriends.hotdeal.service;

import com.bbf.bebefriends.hotdeal.dto.HotDealPostDto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HotDealPostService {

    Page<HotDealPostDto> searchAllHotDealPost(Pageable pageable);

    HotDealPostDto createHotDealPost(HotDealPostDto hotDealPostDto);
}
