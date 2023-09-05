package GLINS_BE.GLINS.wishlist.service;

import GLINS_BE.GLINS.wishlist.domain.Wishlist;
import GLINS_BE.GLINS.wishlist.repository.WishlistRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class WishlistService {
    private final WishlistRepository wishlistRepository;

    public WishlistService(WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }

    /**
     * 위시리스트 추가
     */

    public Wishlist join(Wishlist wishlist) {

        long start = System.currentTimeMillis();

        try {
            wishlistRepository.save(wishlist);
            return wishlist;
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("join = " + timeMs + "ms");
        }
    }

    /**
     * 위시리스트 삭제
     * @param wishlist
     */

    public Wishlist getout(Wishlist wishlist) {
        long start = System.currentTimeMillis();
        try{
            wishlistRepository.delete(wishlist);
            return wishlist;
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("findMembers " + timeMs + "ms");
        }
    }

    /**
     * 위시리스트 entity에 들어가 있는 요소들 중 하나의 값을 이용해서 해당 값을 찾아내는 Service
     * @return
     */
    public Wishlist findOne(Long wishlistId) {
        return wishlistRepository.findById(wishlistId);
    }

    public List<Wishlist> findbyuser(Long user_id) {
        long start = System.currentTimeMillis();
        try{
            return wishlistRepository.findByUserId(user_id);
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("findreviews " + timeMs + "ms");
        }
    }
}
