package hey.io.heybackend.show.controller;

import hey.io.heybackend.common.response.ResponseDTO;
import hey.io.heybackend.show.dtos.request.CreateShowRequest;
import hey.io.heybackend.show.dtos.request.UpdateShowRequest;
import hey.io.heybackend.show.dtos.response.ShowArtistResponse;
import hey.io.heybackend.show.dtos.response.ShowListResponse;
import hey.io.heybackend.show.dtos.response.ShowResponse;
import hey.io.heybackend.show.service.ShowService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class ShowController {

    private final ShowService showService;

    @PostMapping("/show")
    public ResponseEntity<ResponseDTO<ShowResponse>> createShow(@RequestBody CreateShowRequest request) {

        ShowResponse showResponse = showService.createShow(request);

        ResponseDTO<ShowResponse> responseDTO = new ResponseDTO<>(true, Optional.of(showResponse));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);

    }

    @GetMapping("/show")
    public ResponseEntity<ResponseDTO<Page<ShowListResponse>>> getShow(Pageable pageable,
                                                                       @RequestParam(value = "type", required = false) String type,
                                                                       @RequestParam(value = "genre", required = false) String genre) {

        Page<ShowListResponse> showListResponse = showService.getShow(pageable, type, genre);
        ResponseDTO<Page<ShowListResponse>> responseDTO = new ResponseDTO<>(true, Optional.of(showListResponse));

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);

    }

    @GetMapping("/show/{id}")
    public ResponseEntity<ResponseDTO<ShowResponse>> getShowInfo(@PathVariable("id") Long showId) {

        ShowResponse showResponse = showService.getShowInfo(showId);
        ResponseDTO<ShowResponse> responseDTO = new ResponseDTO<>(true, Optional.of(showResponse));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);

    }

    @PutMapping("/show/{id}")
    public ResponseEntity<ResponseDTO<ShowResponse>> updateShow(@PathVariable("id") Long showId, @RequestBody UpdateShowRequest request) {

        ShowResponse showResponse = showService.updateShow(showId, request);
        ResponseDTO<ShowResponse> responseDTO = new ResponseDTO<>(true, Optional.of(showResponse));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);

    }

    @DeleteMapping("/show/{id}")
    public ResponseEntity<ResponseDTO<Void>> deleteShow(@PathVariable("id") Long showId) {

        showService.deleteShow(showId);
        ResponseDTO<Void> responseDTO = new ResponseDTO<>(true, null);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);

    }

    @GetMapping("/show/{id}/artist")
    public ResponseEntity<ResponseDTO<List<ShowArtistResponse>>> getShowArtist(@PathVariable("id") Long showId) {

        List<ShowArtistResponse> showArtistResponse = showService.getShowArtist(showId);
        ResponseDTO<List<ShowArtistResponse>> responseDTO = new ResponseDTO<>(true, Optional.of(showArtistResponse));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);

    }


}
