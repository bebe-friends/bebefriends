package com.bbf.bebefriends.community.service;

import com.bbf.bebefriends.community.dto.CommunityCommentDTO;
import com.bbf.bebefriends.community.entity.CommunityPost;

import java.util.List;

public interface CommunityCommentService {
    List<CommunityCommentDTO.CommentDetails> getCommentsByPost(CommunityPost post);
}
