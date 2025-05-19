package com.bbf.bebefriends.member.util;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class NicknameGeneratorUtil {

    private static final List<String> ADJECTIVES = Arrays.asList(
            "귀여운", "계획적인", "수줍은", "깜찍한", "느긋한", "장난꾸러기", "엉뚱한", "반짝이는", "웃음많은",
            "탐험가", "새침한", "날쌘", "느린", "멍한", "영리한", "포근한", "호기심많은", "용감한", "얌전한",
            "활발한", "눈큰", "통통한", "반항적인", "지적인", "충직한", "재빠른", "허당", "사랑스런", "센스있는",
            "똑똑한", "자유로운", "상냥한", "깜놀", "당당한", "부끄럼쟁이", "잠꾸러기", "애교쟁이", "까칠한",
            "정리왕", "꾸러기", "예민한", "넉살좋은", "꾀많은", "엉성한", "폭신한", "사려깊은", "조용한",
            "혼자놀기", "미소짓는", "허세있는", "평화주의", "도도한", "몰입하는", "어리둥절한", "깊은생각의",
            "도망가는", "다정한", "깔끔쟁이", "우아한", "눈치빠른", "망설이는", "잔망스런", "넓은마음의",
            "똑부러진", "몽상가", "먹보", "잠이많은", "쫄보", "순둥이", "시크한", "빠른", "겁없는", "소심한",
            "호기로운", "당찬", "다혈질", "외로운", "청결한", "집중하는", "열정적인", "계획충", "청소왕",
            "그루밍천재", "단순한", "아침형", "밤샘용", "엉뚱발랄", "고민많은", "신중한", "뚝심있는",
            "잔잔한", "파티왕", "음악좋아하는", "여행광", "셀카좋아하는", "도서관지박령", "커피중독",
            "말수적은"
    );

    private static final List<String> ANIMALS = Arrays.asList(
            "왈라비", "부엉이", "너구리", "다람쥐", "거북이", "원숭이", "펭귄", "고양이", "햄스터", "코알라",
            "토끼", "치타", "나무늘보", "강아지", "까마귀", "북극곰", "족제비", "수달", "판다", "앵무새",
            "사슴", "기니피그", "양", "고슴도치", "셰퍼드", "돌고래", "고릴라", "삵", "야생마", "흰여우",
            "코끼리", "스컹크", "사막여우", "치와와", "비버", "팬더", "오리", "여우", "캥거루", "표범",
            "대장곰", "사자", "플라밍고", "두루미", "고니", "코뿔소", "까치", "하마", "말", "스라소니",
            "도마뱀", "물개", "미어캣", "수탉", "늑대", "올빼미", "두더지", "기린", "고래", "청설모",
            "멧돼지", "문어", "침팬지", "라쿤"
    );

    private static final Random RANDOM = new Random();

    public static String generateNickname() {
        String adjective = ADJECTIVES.get(RANDOM.nextInt(ADJECTIVES.size()));
        String animal = ANIMALS.get(RANDOM.nextInt(ANIMALS.size()));
        String suffix = UUID.randomUUID().toString().substring(0, 4); // 고유성 보장
        return String.format("%s%s@%s", adjective, animal, suffix);
    }

}
