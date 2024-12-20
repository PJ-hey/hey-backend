package hey.io.heybackend.domain.mypage.dto;

import com.querydsl.core.annotations.QueryProjection;
import hey.io.heybackend.domain.member.enums.InterestCategory;
import hey.io.heybackend.domain.member.enums.InterestCode;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MyPageDto {

    @Getter
    @NoArgsConstructor
    @Schema(description = "회원 정보 수정")
    public static class ModifyMemberRequest {

        @NotNull
        @Schema(description = "닉네임", example = "페스티벌 러버_54321")
        private String nickname;
        @Parameter(description = "관심 유형", array = @ArraySchema(schema = @Schema(implementation = InterestCode.class)))
        private List<InterestCode> type;
        @Parameter(description = "관심 장르", array = @ArraySchema(schema = @Schema(implementation = InterestCode.class)))
        private List<InterestCode> genre;

        @AssertTrue
        public boolean isValidInterestCode() {
            boolean isValidType = type.stream()
                .allMatch(code -> code.getInterestCategory() == InterestCategory.TYPE);
            boolean isValidGenre = genre.stream()
                .allMatch(code -> code.getInterestCategory() == InterestCategory.GENRE);
            return isValidType && isValidGenre;
        }
    }

    @Getter
    @Builder(toBuilder = true)
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "회원 상세")
    public static class MemberDetailResponse {

        @Schema(description = "회원 ID", example = "1")
        private Long memberId;

        @Schema(description = "닉네임", example = "페스티벌 러버_54321")
        private String nickname;

        @Schema(description = "최종 접속 일시", example = "2024.11.29 12:35:00")
        private String accessedAt;

        @Schema(description = "관심 정보", implementation = MemberInterestDto.class)
        private MemberInterestDto interests;

        @Getter
        @Builder(toBuilder = true)
        @AllArgsConstructor
        @Schema(description = "관심 정보")
        public static class MemberInterestDto {

            @Schema(description = "관심 유형", example = "[\"FESTIVAL_EX\"]")
            private List<String> type;

            @Schema(description = "관심 장르", example = "[\"BALLAD\", \"HIPHOP\"]")
            private List<String> genre;

            public static MemberInterestDto of(List<InterestCode> interestCodeList) {
                return MemberInterestDto.builder()
                    .type(setType(interestCodeList))
                    .genre(setGenre(interestCodeList))
                    .build();
            }

            private static List<String> setType(List<InterestCode> interestCodeList) {
                return interestCodeList.stream()
                    .filter(code -> code.getInterestCategory() == InterestCategory.TYPE)
                    .map(InterestCode::getCode)
                    .toList();
            }

            private static List<String> setGenre(List<InterestCode> interestCodeList) {
                return interestCodeList.stream()
                    .filter(code -> code.getInterestCategory() == InterestCategory.GENRE)
                    .map(InterestCode::getCode)
                    .toList();
            }

        }

        @QueryProjection
        public MemberDetailResponse(Long memberId, String nickname, LocalDateTime accessedAt) {
            this.memberId = memberId;
            this.nickname = nickname;
            this.accessedAt = accessedAt.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:dd"));
        }

        public static MemberDetailResponse of(MemberDetailResponse memberDetail,
            MemberInterestDto interests) {
            return memberDetail.toBuilder()
                .interests(interests)
                .build();
        }
    }

}
