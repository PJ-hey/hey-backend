package hey.io.heybackend.show.controller;

import hey.io.heybackend.common.response.ResponseDTO;
import hey.io.heybackend.show.dtos.request.CreateShowRequest;
import hey.io.heybackend.show.dtos.request.UpdateShowRequest;
import hey.io.heybackend.show.dtos.response.ShowArtistResponse;
import hey.io.heybackend.show.dtos.response.ShowResponse;
import hey.io.heybackend.show.service.ShowService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/show")
public class ShowController {

    private final ShowService showService;

    @PostMapping
    public ResponseEntity<ResponseDTO<ShowResponse>> createShow(@RequestBody CreateShowRequest request) {

        ShowResponse showResponse = showService.createShow(request);

        ResponseDTO<ShowResponse> responseDTO = new ResponseDTO<>(true, Optional.of(showResponse));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<ResponseDTO<Page<ShowResponse>>> getShow(Pageable pageable) {

        Page<ShowResponse> showResponse = showService.getShow(pageable);
        ResponseDTO<Page<ShowResponse>> responseDTO = new ResponseDTO<>(true, Optional.of(showResponse));

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<ShowResponse>> getShowInfo(@PathVariable("id") Long showId) {

        ShowResponse showResponse = showService.getShowInfo(showId);
        ResponseDTO<ShowResponse> responseDTO = new ResponseDTO<>(true, Optional.of(showResponse));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);

    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<ShowResponse>> updateShow(@PathVariable("id") Long showId, @RequestBody UpdateShowRequest request) {

        ShowResponse showResponse = showService.updateShow(showId, request);
        ResponseDTO<ShowResponse> responseDTO = new ResponseDTO<>(true, Optional.of(showResponse));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<Void>> deleteShow(@PathVariable("id") Long showId) {

        showService.deleteShow(showId);
        ResponseDTO<Void> responseDTO = new ResponseDTO<>(true, null);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);

    }


}
