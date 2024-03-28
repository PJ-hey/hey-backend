package hey.io.heybackend.user.service;

import hey.io.heybackend.artist.dtos.response.ArtistResponse;
import hey.io.heybackend.artist.entities.Artist;
import hey.io.heybackend.artist.repository.ArtistRepository;
import hey.io.heybackend.common.exceptions.CustomException;
import hey.io.heybackend.common.exceptions.ErrorCode;
import hey.io.heybackend.show.dtos.response.ShowResponse;
import hey.io.heybackend.show.entities.Show;
import hey.io.heybackend.show.repository.ShowRepository;
import hey.io.heybackend.user.dtos.request.FollowArtistListRequest;
import hey.io.heybackend.user.dtos.request.FollowArtistRequest;
import hey.io.heybackend.user.dtos.request.FollowShowListRequest;
import hey.io.heybackend.user.dtos.request.FollowShowRequest;
import hey.io.heybackend.user.dtos.response.FollowArtistResponse;
import hey.io.heybackend.user.dtos.response.FollowShowResponse;
import hey.io.heybackend.user.entities.User;
import hey.io.heybackend.user.entities.UserFollowArtist;
import hey.io.heybackend.user.entities.UserFollowShow;
import hey.io.heybackend.user.repository.UserFollowArtistRepository;
import hey.io.heybackend.user.repository.UserFollowShowRepository;
import hey.io.heybackend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserFollowService {

    private final UserRepository userRepository;
    private final UserFollowShowRepository userFollowShowRepository;
    private final UserFollowArtistRepository userFollowArtistRepository;
    private final ShowRepository showRepository;
    private final ArtistRepository artistRepository;

    @Transactional
    public FollowShowResponse followShow(FollowShowRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        Show show = showRepository.findById(request.getShowId())
                .orElseThrow(() -> new CustomException(ErrorCode.SHOW_NOT_FOUND));

        Optional<UserFollowShow> userFollowShowOptional = userFollowShowRepository.findUserFollowByUserAndShow(user, show);

        if (userFollowShowOptional.isPresent()) {
            UserFollowShow userFollowShow = userFollowShowOptional.get();
            userFollowShowRepository.delete(userFollowShow);
            return new FollowShowResponse(user.getId(), show.getId(), "unfollow");
        } else {
            UserFollowShow userFollowShow = UserFollowShow.of(user, show);
            userFollowShowRepository.save(userFollowShow);
            return new FollowShowResponse(user.getId(), show.getId(), "follow");
        }
    }


    public Page<ShowResponse> getFollowShow(FollowShowListRequest request, Pageable pageable) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Page<UserFollowShow> userFollowShows = userFollowShowRepository.findUserFollowShowByUserId(user.getId(), pageable);

        return userFollowShows.map(userFollowShow -> new ShowResponse(userFollowShow.getShow()));
    }

    @Transactional
    public FollowArtistResponse followArtist(FollowArtistRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        Artist artist = artistRepository.findById(request.getArtistId())
                .orElseThrow(() -> new CustomException(ErrorCode.ARTIST_NOT_FOUND));

        Optional<UserFollowArtist> userFollowArtistOptional = userFollowArtistRepository.findUserFollowByUserAndArtist(user, artist);

        if (userFollowArtistOptional.isPresent()) {
            UserFollowArtist userFollowArtist = userFollowArtistOptional.get();
            userFollowArtistRepository.delete(userFollowArtist);
            return new FollowArtistResponse(user.getId(), artist.getId(), "unfollow");
        } else {
            UserFollowArtist userFollowArtist = UserFollowArtist.of(user, artist);
            userFollowArtistRepository.save(userFollowArtist);
            return new FollowArtistResponse(user.getId(), artist.getId(), "follow");
        }
    }


    public Page<ArtistResponse> getFollowArtist(FollowArtistListRequest request, Pageable pageable) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Page<UserFollowArtist> userFollowArtists = userFollowArtistRepository.findUserFollowArtistByUserId(user.getId(), pageable);

        return userFollowArtists.map(userFollowArtist -> new ArtistResponse(userFollowArtist.getArtist()));
    }

}
