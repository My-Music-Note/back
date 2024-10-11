package my.music.note.back.user.repository;

import my.music.note.back.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {


    boolean existsByProviderId(String providerId);

    User findByProviderId(String providerId);

    Optional<User> findById(Long id);

}
