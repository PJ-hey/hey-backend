package hey.io.heybackend.artist.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hey.io.heybackend.artist.entities.Artist;
import hey.io.heybackend.artist.entities.QArtist;
import hey.io.heybackend.show.entities.QShow;
import hey.io.heybackend.show.entities.QShowArtist;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class ArtistRepositoryImpl implements ArtistRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Artist> getSearch(String keyword, Pageable pageable) {

        QArtist artist = QArtist.artist;
        QShowArtist showArtist = QShowArtist.showArtist;
        QShow show = QShow.show;

        LocalDateTime now = LocalDateTime.now();


        QueryResults<Artist> resultsAfter = jpaQueryFactory.select(artist)
                .from(artist)
                .join(showArtist).on(artist.eq(showArtist.artist))
                .join(show).on(show.eq(showArtist.show))
                .where(
                        show.date.after(now)
                                .and(show.ticketOpenTime.after(now))
                                .and(showArtist.artist.name.containsIgnoreCase(keyword))
                )
                .orderBy(
                        show.date.asc(),
                        show.ticketOpenTime.asc()
                )
                .fetchResults();

        QueryResults<Artist> resultsBefore = jpaQueryFactory.select(artist)
                .from(artist)
                .join(showArtist).on(artist.eq(showArtist.artist))
                .join(show).on(show.eq(showArtist.show))
                .where(
                        show.date.before(now)
                                .and(showArtist.artist.name.containsIgnoreCase(keyword))
                )
                .orderBy(
                        artist.name.asc()
                        )
                .fetchResults();

        List<Artist> combinedResults = new ArrayList<>(resultsAfter.getResults());
        combinedResults.addAll(resultsBefore.getResults());


        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), combinedResults.size());
        List<Artist> sublist = combinedResults.subList(start, end);

        return new PageImpl<>(combinedResults, pageable, combinedResults.size());

    }

}
