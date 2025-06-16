package com.bbf.bebefriends.community.entity;

import lombok.Getter;

@Getter
public enum BlockReason {
    SPAM("스팸 홍보/도배글 입니다."),
    PORN("음란물입니다."),
    ILLEGAL_CONTENT("불법정보를 포함하고 있습니다."),
    ABUSE_HATE("욕설/생명경시/혐오/차별적 표현입니다."),
    PERSONAL_INFO("개인정보 노출 게시물입니다."),
    OFFENSIVE("불쾌한 표현이 있습니다."),
    OTHER("기타");

    private final String displayText;

    BlockReason(String displayText) {
        this.displayText = displayText;
    }
}
