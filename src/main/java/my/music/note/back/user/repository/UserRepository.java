package my.music.note.back.user.repository;

import my.music.note.back.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {


    boolean existsByProviderId(String providerId);

    User findByProviderId(String providerId);

    User findByUserId(Long userId);

}
