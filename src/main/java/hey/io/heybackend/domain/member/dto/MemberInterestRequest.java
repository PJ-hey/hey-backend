package hey.io.heybackend.domain.member.dto;

import hey.io.heybackend.domain.member.enums.InterestCategory;
import hey.io.heybackend.domain.member.enums.InterestCode;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import java.util.List;
import lombok.Getter;

@Getter
@Schema(description = "관심 정보 목록")
public class MemberInterestRequest {

  @Parameter(description = "관심 유형", array = @ArraySchema(schema = @Schema(implementation = InterestCode.class)))
  private List<InterestCode> type;
  @Parameter(description = "관심 장르", array = @ArraySchema(schema = @Schema(implementation = InterestCode.class)))
  private List<InterestCode> genre;

  @AssertTrue
  public boolean isValidInterestCode() {
    boolean isValidType = type.stream().allMatch(code -> code.getInterestCategory() == InterestCategory.TYPE);
    boolean isValidGenre = genre.stream().allMatch(code -> code.getInterestCategory() == InterestCategory.GENRE);
    return isValidType && isValidGenre;
  }

}
