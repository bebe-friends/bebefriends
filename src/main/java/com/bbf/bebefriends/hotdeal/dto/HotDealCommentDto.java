package com.bbf.bebefriends.hotdeal.dto;

import com.bbf.bebefriends.hotdeal.entity.HotDealComment;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class HotDealCommentDto {

    private Long id;                        // 핫딜 댓글 식별자

    private Long userId;                    // 회원 식별자

    private Long repliedCommentId;          // 핫딜 대댓글 식별자

    private Long hotDealPostId;             // 핫딜 게시글 식별자

    private String content;                 // 내용

    private LocalDateTime deletedAt;        // 삭제일자

    // Entity -> dto
    public static HotDealCommentDto fromEntity(HotDealComment hotDealComment) {
        HotDealCommentDto commentDto = HotDealCommentDto.builder()
                .id(hotDealComment.getId())
                .userId(hotDealComment.getUser().getUid())
                .hotDealPostId(hotDealComment.getHotDealPost().getId())
                .content(hotDealComment.getContent())
                .deletedAt(hotDealComment.getDeletedAt())
                .build();

        // 대댓글이 있는 경우
        if (hotDealComment.getRepliedComment() != null) {
            commentDto = commentDto.toBuilder()
                    .repliedCommentId(hotDealComment.getRepliedComment().getId())
                    .build();
        }

        return commentDto;
    }


}
