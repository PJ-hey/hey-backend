package hey.io.heybackend.artist.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hey.io.heybackend.artist.dtos.request.UpdateArtistRequest;
import hey.io.heybackend.common.entities.CommonModel;
import hey.io.heybackend.show.entities.ShowArtist;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@Table(name = "\"artist\"")
@NoArgsConstructor
@AllArgsConstructor
public class Artist extends CommonModel {

    private String name;
    private String profileImage;
    private String genre;
    private LocalDateTime debutDate;

    @Builder.Default
    @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Album> albums = new ArrayList<>();



    public void addAlbum(List<Album> albums) {

        this.albums.addAll(albums);
        albums.forEach(album -> album.setArtist(this));

    }

    public void updateArtist(UpdateArtistRequest request) {
        this.name = request.getName();
        this.profileImage = request.getProfileImage();
        this.genre = request.getGenre();
        this.debutDate = request.getDebutDate();

    }

}