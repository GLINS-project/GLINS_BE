package GLINS_BE.GLINS.wishlist.domain;

import GLINS_BE.GLINS.client.domain.Client;

import javax.persistence.*;

@Entity
@Table(name = "Wishlist")
public class Wishlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wishlist_id;
    private Long user_id;
    private Long place_id;
    private String wishlist_kind;

    @ManyToOne
    @JoinColumn (name = "Clinet_id")
    private Client client;

    public Long getWishlist_id() {
        return wishlist_id;
    }

    public void setWishlist_id(Long wishlist_id) {
        this.wishlist_id = wishlist_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getPlace_id() {
        return place_id;
    }

    public void setPlace_id(Long place_id) {
        this.place_id = place_id;
    }

    public String getWishlist_kind() {
        return wishlist_kind;
    }

    public void setWishlist_kind(String wishlist_kind) {
        this.wishlist_kind = wishlist_kind;
    }
}
