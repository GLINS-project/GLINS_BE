package GLINS_BE.GLINS.wishlist.repository;

import GLINS_BE.GLINS.review.domain.Review;
import GLINS_BE.GLINS.wishlist.domain.Wishlist;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class jpaWishlistRepository implements WishlistRepository{
    private final EntityManager em;

    public jpaWishlistRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Wishlist save(Wishlist member) {
        em.persist(member);
        return member;
    }

    @Override
    public Wishlist delete(Wishlist member) {
        em.remove(member);
        return member;
    }

    @Override
    public Wishlist findById(Long id) {
        Wishlist wishlist = em.find(Wishlist.class, id);
        return wishlist;
    }

    public List<Wishlist> findByUserId(Long user_id) {
        List<Wishlist> result = em.createQuery("select m from Wishlist m where m.user_id= :user_id", Wishlist.class)
                .setParameter("user_id", user_id)
                .getResultList();

        return result;
    }
}
