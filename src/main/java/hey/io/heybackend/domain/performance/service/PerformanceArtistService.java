package hey.io.heybackend.domain.performance.service;

import hey.io.heybackend.common.jwt.dto.TokenDto;
import hey.io.heybackend.domain.artist.dto.ArtistDto.ArtistListResponse;
import hey.io.heybackend.domain.artist.dto.ArtistDto.ArtistSearchCondition;
import hey.io.heybackend.domain.artist.repository.ArtistRepository;
import hey.io.heybackend.domain.file.dto.FileDto;
import hey.io.heybackend.domain.file.enums.EntityType;
import hey.io.heybackend.domain.file.service.FileService;
import hey.io.heybackend.domain.performance.dto.PerformanceDto.PerformanceListResponse;
import hey.io.heybackend.domain.performance.dto.PerformanceDto.PerformanceSearchCondition;
import hey.io.heybackend.domain.performance.repository.PerformanceRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PerformanceArtistService {

    private final PerformanceRepository performanceRepository;
    private final ArtistRepository artistRepository;
    private final FileService fileService;

    /**
     * <p>공연 목록</p>
     *
     * @param searchCondition 조회 조건
     * @param tokenDto        토큰 정보
     * @return 공연 목록
     */
    public List<PerformanceListResponse> searchPerformanceList(PerformanceSearchCondition searchCondition,
        TokenDto tokenDto) {
        // 1. 공연 목록 조회
        List<PerformanceListResponse> performanceList = performanceRepository.selectPerformanceList(searchCondition,
            tokenDto.getMemberId());

        List<Long> performanceIds = performanceList.stream()
            .map(PerformanceListResponse::getPerformanceId)
            .toList();

        // 2. 공연별 티켓 오픈 시간 조회
        List<PerformanceListResponse> ticketingList = performanceRepository.selectPerformanceOpenDatetimeList(
            performanceIds);

        // 3. 공연 파일 목록 조회
        List<FileDto> fileList = fileService.getThumbnailFileList(EntityType.PERFORMANCE, performanceIds);

        // 4. 목록 데이터 생성
        return PerformanceListResponse.of(performanceList, ticketingList, fileList);
    }

    /**
     * <p>아티스트 목록</p>
     *
     * @param searchCondition 조회 조건
     * @param tokenDto        토큰 정보
     * @return 아티스트 목록
     */
    public List<ArtistListResponse> searchArtistList(ArtistSearchCondition searchCondition, TokenDto tokenDto) {
        // 1. 아티스트 목록 조회
        List<ArtistListResponse> artistList = artistRepository.selectArtistList(searchCondition,
            tokenDto.getMemberId());

        List<Long> artistIds = artistList.stream()
            .map(ArtistListResponse::getArtistId)
            .toList();

        // 2. 아티스트 파일 목록 조회
        List<FileDto> fileList = fileService.getThumbnailFileList(EntityType.ARTIST, artistIds);

        // 3. 목록 데이터 생성
        return ArtistListResponse.of(artistList, fileList);
    }
}