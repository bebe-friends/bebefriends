package com.bbf.bebefriends.global.schoulder;

import com.bbf.bebefriends.hotdeal.repository.HotDealCommentRepository;
import com.bbf.bebefriends.hotdeal.repository.HotDealPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DeleteTask {

    private final HotDealPostRepository hotDealPostRepository;
    private final HotDealCommentRepository hotDealCommentRepository;

    // 매일 새벽 1시에 실행
    @Scheduled(cron = "0 0 1 * * *")
    public void delete() {
        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(30);

        // 핫딜 게시글과 댓글은 30일 이후에 실제로 삭제 처리
        hotDealPostRepository.deleteByDeletedAtBefore(cutoffDate);
        hotDealCommentRepository.deleteByDeletedAtBefore(cutoffDate);

    }
}
