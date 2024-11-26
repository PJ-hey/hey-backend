package hey.io.heybackend.domain.performance.service;

import hey.io.heybackend.domain.artist.dto.ArtistDTO.ArtistListResponse;
import hey.io.heybackend.domain.artist.dto.ArtistDTO.ArtistSearchCondition;
import hey.io.heybackend.domain.artist.repository.ArtistRepository;
import hey.io.heybackend.domain.file.dto.FileDTO;
import hey.io.heybackend.domain.file.enums.EntityType;
import hey.io.heybackend.domain.file.service.FileService;
import hey.io.heybackend.domain.performance.dto.PerformanceDTO.PerformanceListResponse;
import hey.io.heybackend.domain.performance.dto.PerformanceDTO.PerformanceSearchCondition;
import hey.io.heybackend.domain.performance.repository.PerformanceRepository;
import hey.io.heybackend.domain.system.dto.TokenDTO;
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
     * @param tokenDTO        JWT 토큰 정보
     * @return 공연 목록
     */
    public List<PerformanceListResponse> searchPerformanceList(PerformanceSearchCondition searchCondition,
        TokenDTO tokenDTO) {
        // 1. 공연 목록 조회
        List<PerformanceListResponse> performanceList = performanceRepository.selectPerformanceList(searchCondition,
            tokenDTO.getMemberId());

        List<Long> performanceIds = performanceList.stream()
            .map(PerformanceListResponse::getPerformanceId)
            .toList();

        // 2. 공연별 티켓 오픈 시간 조회
        List<PerformanceListResponse> ticketingList = performanceRepository.selectPerformanceOpenDatetimeList(performanceIds);

        // 3. 공연 파일 목록 조회
        List<FileDTO> fileList = fileService.getThumbnailFileList(EntityType.PERFORMANCE, performanceIds);

        // 4. DTO 조합
        return PerformanceListResponse.of(performanceList, ticketingList, fileList);
    }

    /**
     * <p>아티스트 목록</p>
     *
     * @param searchCondition 조회 조건
     * @param tokenDTO        JWT 토큰 정보
     * @return 아티스트 목록
     */
    public List<ArtistListResponse> searchArtistList(ArtistSearchCondition searchCondition, TokenDTO tokenDTO) {
        // 1. 아티스트 목록 조회
        List<ArtistListResponse> artistList = artistRepository.selectArtistList(searchCondition, tokenDTO.getMemberId());

        List<Long> artistIds = artistList.stream()
            .map(ArtistListResponse::getArtistId)
            .toList();

        // 2. 아티스트 파일 목록 조회
        List<FileDTO> fileList = fileService.getThumbnailFileList(EntityType.ARTIST, artistIds);

        // 3. DTO 조합
        return ArtistListResponse.of(artistList, fileList);
    }
}