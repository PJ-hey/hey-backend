package hey.io.heybackend.report.entities;

import hey.io.heybackend.artist.entities.Artist;
import hey.io.heybackend.common.entities.CommonModel;
import hey.io.heybackend.show.entities.Show;
import hey.io.heybackend.show.entities.ShowArtist;
import hey.io.heybackend.user.entities.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShowReport extends CommonModel {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ElementCollection(fetch = FetchType.LAZY)
    private List<String> type = new ArrayList<>();

    private String content;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "show_id")
    private Show show;

}
