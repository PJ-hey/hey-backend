package hey.io.heybackend.report.service;

import hey.io.heybackend.artist.entities.Artist;
import hey.io.heybackend.artist.repository.ArtistRepository;
import hey.io.heybackend.common.exceptions.CustomException;
import hey.io.heybackend.common.exceptions.ErrorCode;
import hey.io.heybackend.report.dtos.request.ReportRequest;
import hey.io.heybackend.report.entities.ArtistReport;
import hey.io.heybackend.report.entities.ShowReport;
import hey.io.heybackend.report.repository.ArtistReportRepository;
import hey.io.heybackend.report.repository.ShowReportRepository;
import hey.io.heybackend.show.entities.Show;
import hey.io.heybackend.show.repository.ShowRepository;
import hey.io.heybackend.user.entities.User;
import hey.io.heybackend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReportService {

    private final ShowRepository showRepository;
    private final ArtistRepository artistRepository;
    private final UserRepository userRepository;
    private final ShowReportRepository showReportRepository;
    private final ArtistReportRepository artistReportRepository;

    @Transactional
    public void reportShow(Long showId, ReportRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Show show = showRepository.findById(showId)
                .orElseThrow(() -> new CustomException(ErrorCode.SHOW_NOT_FOUND));

        ShowReport showReport = ShowReport.builder()
                .user(user)
                .type(request.getType())
                .content(request.getContent())
                .show(show)
                .build();

        showReportRepository.save(showReport);
    }

    @Transactional
    public void reportArtist(Long artistId, ReportRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new CustomException(ErrorCode.ARTIST_NOT_FOUND));

        ArtistReport artistReport = ArtistReport.builder()
                .user(user)
                .type(request.getType())
                .content(request.getContent())
                .artist(artist)
                .build();

        artistReportRepository.save(artistReport);

    }

}
