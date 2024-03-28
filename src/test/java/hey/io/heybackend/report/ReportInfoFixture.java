package hey.io.heybackend.report;

import hey.io.heybackend.report.dtos.request.ReportRequest;

import java.util.Arrays;

public class ReportInfoFixture {

    public static ReportRequest getReportRequestInfo() {
        ReportRequest reportRequest = ReportRequest.builder()
                .userId(1l)
                .type(Arrays.asList("공연 장소", "기타"))
                .content("공연 장소 오류")
                .build();

        return reportRequest;
    }
}
