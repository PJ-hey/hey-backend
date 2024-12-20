package hey.io.heybackend.domain.member.enums;

import hey.io.heybackend.common.mapper.EnumMapperType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum InterestCode implements EnumMapperType {

    CONCERT_IN("국내 콘서트", InterestCategory.TYPE),
    CONCERT_EX("내한 콘서트", InterestCategory.TYPE),
    FESTIVAL_IN("페스티벌", InterestCategory.TYPE),
    FESTIVAL_EX("해외 페스티벌", InterestCategory.TYPE),

    BALLAD("발라드", InterestCategory.GENRE),
    HIPHOP("힙합", InterestCategory.GENRE),
    RNB("R&B", InterestCategory.GENRE),
    EDM("EDM", InterestCategory.GENRE),
    INDIE("인디", InterestCategory.GENRE),
    ROCK("락", InterestCategory.GENRE),
    JAZZ("재즈", InterestCategory.GENRE),
    IDOL("아이돌", InterestCategory.GENRE),
    ETC("기타", InterestCategory.GENRE);

    private final String description; // Enum 설명
    private final InterestCategory interestCategory;

    @Override
    public String getCode() {
        return name();
    }
}
