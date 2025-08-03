package com.bbf.bebefriends.global.scheduler;

import com.bbf.bebefriends.community.entity.CommunityComment;
import com.bbf.bebefriends.community.entity.CommunityPost;
import com.bbf.bebefriends.community.repository.CommunityCommentRepository;
import com.bbf.bebefriends.community.repository.CommunityPostRepository;
import com.bbf.bebefriends.global.utils.file.FireBaseService;
import com.bbf.bebefriends.hotdeal.repository.HotDealCommentRepository;
import com.bbf.bebefriends.hotdeal.repository.HotDealPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeleteTask {
    private final FireBaseService fireBaseService;

    private final HotDealPostRepository hotDealPostRepository;
    private final HotDealCommentRepository hotDealCommentRepository;

    private final CommunityPostRepository communityPostRepository;;
    private final CommunityCommentRepository communityCommentRepository;

    // 매일 새벽 1시에 실행
    @Scheduled(cron = "0 0 1 * * *", zone = "Asia/Seoul")
    @Transactional
    public void delete() {
        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(30);

        // 핫딜 게시글과 댓글은 30일 이후에 실제로 삭제 처리
        hotDealPostRepository.deleteByDeletedAtBefore(cutoffDate);
        hotDealCommentRepository.deleteByDeletedAtBefore(cutoffDate);

        List<CommunityPost> expiredPosts = communityPostRepository.findAllByDeletedAtBefore(cutoffDate);
//        List<CommunityComment> expiredComments = communityCommentRepository.findAllByDeletedAtBefore(cutoffDate);

        for (CommunityPost post : expiredPosts) {
            post.getImages().forEach(img ->
                    fireBaseService.deleteFirebaseFile(img.getImgUrl())
            );
        }

        communityPostRepository.deleteAll(expiredPosts);
//        communityCommentRepository.deleteAll(expiredComments);
    }
}
