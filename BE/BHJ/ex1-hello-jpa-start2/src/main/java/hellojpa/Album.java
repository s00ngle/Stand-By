package hellojpa;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("bok")
@Table(name = "ALBUM")
public class Album extends Item {

    @Column(name = "ARTIST")
    private String artist;

    @Column(name = "etc")
    private String etc;


    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getEtc() {
        return etc;
    }

    public void setEtc(String etc) {
        this.etc = etc;
    }
}
