package com.bbf.bebefriends.member.service;

import com.bbf.bebefriends.global.exception.ResponseCode;
import com.bbf.bebefriends.member.dto.UserDTO;
import com.bbf.bebefriends.member.entity.User;
import com.bbf.bebefriends.member.entity.UserTermsAgreements;
import com.bbf.bebefriends.member.exception.UserControllerAdvice;
import com.bbf.bebefriends.member.repository.UserRepository;
import com.bbf.bebefriends.member.repository.UserTermsAgreementsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserTermsAgreementsService {

    private final UserTermsAgreementsRepository termsAgreementsRepository;
    private final UserRepository userRepository;

    /**
     * 약관 동의 정보 조회
     *
     * @param uid 사용자 고유 ID
     * @return 약관 동의 정보 (Response DTO)
     */
    public UserDTO.UserTermsAgreementsResponse getTermsAgreements(String uid) {
        UserTermsAgreements entity = termsAgreementsRepository.findById(uid)
                .orElseThrow(() -> new UserControllerAdvice(ResponseCode.MEMBER_NOT_FOUND));

        return new UserDTO.UserTermsAgreementsResponse(
                entity.getUid(),
                entity.getAgreement(),
                entity.getPrivatePolicy(),
                entity.getAge()
        );
    }


    /**
     * 약관 동의 정보 업데이트
     * @param uid 사용자 고유 ID
     * @param agreement 약관 동의 시간
     * @param privatePolicy 개인정보 처리방침 동의 시간
     * @param age 만 나이 동의 여부
     */
    @Transactional
    public void updateTermsAgreements(String uid, LocalDateTime agreement, LocalDateTime privatePolicy, Boolean age) {
        UserTermsAgreements termsAgreements = termsAgreementsRepository.findByUser_Uid(uid)
                .orElseGet(() -> {
                    User user = userRepository.findByUidAndDeletedAtIsNull(uid)
                            .orElseThrow(() -> new UserControllerAdvice(ResponseCode.MEMBER_NOT_FOUND));
                    UserTermsAgreements newAgreements = new UserTermsAgreements();
                    newAgreements.setUid(uid);
                    newAgreements.setUser(user);
                    return newAgreements;
                });

        termsAgreements.setAgreement(agreement);
        termsAgreements.setPrivatePolicy(privatePolicy);
        termsAgreements.setAge(age);

        termsAgreementsRepository.save(termsAgreements);
    }
}