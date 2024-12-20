package hey.io.heybackend.domain.member.dto;

import hey.io.heybackend.domain.member.enums.InterestCode;
import hey.io.heybackend.domain.performance.enums.PerformanceGenre;
import hey.io.heybackend.domain.performance.enums.PerformanceType;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Getter;

@Getter
@Schema(description = "관심 정보 목록")
public class MemberInterestRequest {

  @Parameter(description = "관심 유형", array = @ArraySchema(schema = @Schema(implementation = PerformanceType.class)))
  private List<InterestCode> type;

  @Parameter(description = "관심 장르", array = @ArraySchema(schema = @Schema(implementation = PerformanceGenre.class)))
  private List<InterestCode> genre;

}
