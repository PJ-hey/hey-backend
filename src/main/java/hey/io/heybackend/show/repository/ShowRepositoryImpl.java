package hey.io.heybackend.show.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hey.io.heybackend.show.entities.QShow;
import hey.io.heybackend.show.entities.QShowArtist;
import hey.io.heybackend.show.entities.Show;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static hey.io.heybackend.show.entities.QShow.show;

@Slf4j
@RequiredArgsConstructor
public class ShowRepositoryImpl implements ShowRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Show> getList(Pageable pageable, String type, String genre) {
        BooleanBuilder condition = new BooleanBuilder(show.isConfirmed.eq(true));

        if(type != null && genre == null) {
            condition.and(show.type.eq(type));
        }

        if(genre != null && type == null) {
            condition.and(show.genre.eq(genre));
        }

        if (genre != null && type != null) {
            condition.and(show.type.eq(type)).and(show.genre.eq(genre));
        }

        QueryResults<Show> results = jpaQueryFactory.selectFrom(show)
                .where(condition)
                .orderBy(show.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }

    @Override
    public Page<Show> getSearch(String keyword, Pageable pageable) {
        BooleanBuilder condition = new BooleanBuilder(show.isConfirmed.eq(true));

        if(keyword != null) {
            condition.and(show.name.contains(keyword));
        }

        QueryResults<Show> results = jpaQueryFactory.selectFrom(show)
                .where(condition)
                .orderBy(show.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }

    @Override
    public Page<Show> findShowsByArtistId(Long artistId, Pageable pageable) {
        QShow show = QShow.show;
        QShowArtist showArtist = QShowArtist.showArtist;

        QueryResults<Show> results = jpaQueryFactory.select(show)
                .from(show)
                .innerJoin(show.artists, showArtist)
                .where(showArtist.artist.id.eq(artistId))
                .fetchResults();

        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }
}
