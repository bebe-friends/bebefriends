package com.bbf.bebefriends.hotdeal.dto;

import com.bbf.bebefriends.hotdeal.entity.HotDealComment;
import com.bbf.bebefriends.hotdeal.entity.HotDealPost;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class HotDealCommentDto {

    private Long repliedCommentId;          // 핫딜 댓글(대댓글) 식별자

    private Long hotDealPostId;             // 핫딜 게시글 식별자

    private String content;                 // 내용

    private LocalDateTime deleted_at;       // 삭제 여부

    // Entity -> dto
    public static HotDealCommentDto fromEntity(HotDealComment hotDealComment) {
        HotDealCommentDto commentDto = HotDealCommentDto.builder()
                .hotDealPostId(hotDealComment.getHotDealPost().getId())
                .content(hotDealComment.getContent())
                .deleted_at(hotDealComment.getDeleted_at())
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
