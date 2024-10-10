package my.music.note.back.user.service;


import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import my.music.note.back.user.dto.request.DeleteAccountRequest;
import my.music.note.back.user.dto.request.LoginOrRegisterRequest;
import my.music.note.back.user.dto.request.ModifyNameRequest;
import my.music.note.back.user.entity.User;
import my.music.note.back.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@Transactional
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User loginOrRegister(LoginOrRegisterRequest request) {

        if (userRepository.existsByProviderId(request.providerId())) {
            return userRepository.findByProviderId(request.providerId());
        }

        User user = User.of(request);
        return userRepository.save(user);
    }



}
