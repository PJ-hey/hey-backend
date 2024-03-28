package hey.io.heybackend.report.controller;

import hey.io.heybackend.common.response.ResponseDTO;
import hey.io.heybackend.report.dtos.request.ReportRequest;
import hey.io.heybackend.report.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class ReportController {

    private final ReportService reportService;

    @PostMapping("/show/{id}/report")
    public ResponseEntity<ResponseDTO<Void>> reportShow(@PathVariable("id") Long showId, @RequestBody ReportRequest request) {

        reportService.reportShow(showId, request);
        ResponseDTO<Void> responseDTO = new ResponseDTO<>(true, null);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);

    }

    @PostMapping("/artist/{id}/report")
    public ResponseEntity<ResponseDTO<Void>> reportArtist(@PathVariable("id") Long artistId, @RequestBody ReportRequest request) {

        reportService.reportArtist(artistId, request);
        ResponseDTO<Void> responseDTO = new ResponseDTO<>(true, null);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);

    }
}
