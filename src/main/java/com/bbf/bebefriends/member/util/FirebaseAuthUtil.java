package com.bbf.bebefriends.member.util;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;

public class FirebaseAuthUtil {

    /**
     * 주어진 ID 토큰을 Firebase 인증을 통해 검증합니다.
     *
     * @param idToken 검증할 ID 토큰
     * @return 유효한 경우 {@link FirebaseToken} 객체를 반환
     * @throws FirebaseAuthException 토큰이 유효하지 않거나 검증에 실패한 경우
     */
    public static FirebaseToken verifyToken(String idToken) throws FirebaseAuthException {
        return FirebaseAuth.getInstance().verifyIdToken(idToken);
    }

    /**
     * 주어진 ID 토큰에서 UID(고유 식별자)를 추출합니다.
     *
     * @param idToken UID를 추출할 ID 토큰
     * @return ID 토큰에서 추출한 UID
     * @throws FirebaseAuthException 토큰이 유효하지 않거나 검증에 실패한 경우
     */
    public static String getUidFromToken(String idToken) throws FirebaseAuthException {
        return verifyToken(idToken).getUid();
    }
}