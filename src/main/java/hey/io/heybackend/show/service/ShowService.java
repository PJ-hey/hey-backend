package hey.io.heybackend.show.service;

import hey.io.heybackend.common.exceptions.CustomException;
import hey.io.heybackend.common.exceptions.ErrorCode;
import hey.io.heybackend.show.dtos.request.CreateShowRequest;
import hey.io.heybackend.show.dtos.request.UpdateShowRequest;
import hey.io.heybackend.show.dtos.response.ShowResponse;
import hey.io.heybackend.show.entities.PriceInfo;
import hey.io.heybackend.show.entities.Show;
import hey.io.heybackend.show.entities.TicketSeller;
import hey.io.heybackend.show.repository.ShowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ShowService {

    private final ShowRepository showRepository;

    @Transactional
    public ShowResponse createShow(CreateShowRequest request) {

        Show show = Show.builder()
                .name(request.getName())
                .priceInfos(new ArrayList<>())
                .ticketSellers(new ArrayList<>())
                .urlId(request.getUrlId())
                .ticketOpenTime(request.getTicketOpenTime())
                .date(request.getDate())
                .strictedAge(request.getStrictedAge())
                .runningTime(request.getRunningTime())
                .place(request.getPlace())
                .type(request.getType())
                .genre(request.getGenre())
                .images(new ArrayList<>())
                .isConfirmed(request.getIsConfirmed())
                .build();

        List<PriceInfo> priceInfos = request.getPriceInfos().stream()
                .map(priceInfo -> PriceInfo.of(priceInfo.getType(), priceInfo.getPrice()))
                .collect(Collectors.toList());

        List<TicketSeller> ticketSellers = request.getTicketSellers().stream()
                .map(ticketSeller -> TicketSeller.of(ticketSeller.getName(), ticketSeller.getBaseUrl(), ticketSeller.getIcon()))
                .collect(Collectors.toList());

        show.addPriceInfo(priceInfos);
        show.addTicketSeller(ticketSellers);

        Show savedShow = showRepository.save(show);

        return new ShowResponse(savedShow);
    }

    public Page<ShowResponse> getShow(Pageable pageable) {

        Page<Show> show = showRepository.findByIsConfirmedTrue(pageable);

        return show.map(ShowResponse::new);

    }

    public ShowResponse getShowInfo(Long showId) {

        Show show = showRepository.findById(showId)
                .orElseThrow(() -> new CustomException(ErrorCode.SHOW_NOT_FOUND));
        return new ShowResponse(show);

    }

    @Transactional
    public ShowResponse updateShow(Long showId, UpdateShowRequest request) {

        Show show = showRepository.findById(showId)
                .orElseThrow(() -> new CustomException(ErrorCode.SHOW_NOT_FOUND));

        return new ShowResponse(show);

    }

    @Transactional
    public void deleteShow(Long showId) {

        Show show = showRepository.findById(showId)
                .orElseThrow(() -> new CustomException(ErrorCode.SHOW_NOT_FOUND));

        showRepository.delete(show);
    }

}
