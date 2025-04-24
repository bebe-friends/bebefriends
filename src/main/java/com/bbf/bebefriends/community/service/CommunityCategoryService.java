package com.bbf.bebefriends.community.service;

import com.bbf.bebefriends.community.dto.CommunityCategoryDTO;
import com.bbf.bebefriends.community.entity.CommunityCategory;
import com.bbf.bebefriends.member.entity.User;

import java.util.List;

public interface CommunityCategoryService {
    List<CommunityCategory> getAllCategory();
    CommunityCategoryDTO.CreateCategoryResponse saveCategory(CommunityCategoryDTO.CreateCategoryRequest request, User user);
    CommunityCategoryDTO.UpdateCategoryResponse updateCategory(CommunityCategoryDTO.UpdateCategoryRequest request, User user);
    CommunityCategory getCategoryByName(String name);
}
