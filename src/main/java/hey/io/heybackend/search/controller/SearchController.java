package hey.io.heybackend.search.controller;

import hey.io.heybackend.common.response.ResponseDTO;
import hey.io.heybackend.search.dtos.SearchResponse;
import hey.io.heybackend.search.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/search")
    public ResponseEntity<ResponseDTO<Page<SearchResponse>>> getSearch(@RequestParam(value = "keyword", required = false) String keyword,
                                                                        Pageable pageable) {

        Page<SearchResponse> searchResponses = searchService.getSearch(keyword, pageable);
        ResponseDTO<Page<SearchResponse>> responseDTO = new ResponseDTO<>(true, Optional.of(searchResponses));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);

    }
}
