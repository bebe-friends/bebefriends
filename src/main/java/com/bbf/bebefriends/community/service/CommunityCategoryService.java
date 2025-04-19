package com.bbf.bebefriends.community.service;

import com.bbf.bebefriends.community.dto.CommunityCategoryDTO;
import com.bbf.bebefriends.community.entity.CommunityCategory;
import com.bbf.bebefriends.member.entity.Member;

import java.util.List;

public interface CommunityCategoryService {
    List<CommunityCategory> getAllCategory();
    CommunityCategoryDTO.CreateCategoryResponse saveCategory(CommunityCategoryDTO.CreateCategoryRequest request, Member member);
    CommunityCategoryDTO.UpdateCategoryResponse updateCategory(CommunityCategoryDTO.UpdateCategoryRequest request, Member member);
    CommunityCategory getCategoryByName(String name);
}
