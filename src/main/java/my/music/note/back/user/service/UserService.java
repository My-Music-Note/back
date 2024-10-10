package my.music.note.back.user.service;


import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import my.music.note.back.user.dto.request.DeleteAccountRequest;
import my.music.note.back.user.dto.request.FindUserRequest;
import my.music.note.back.user.dto.request.LoginOrRegisterRequest;
import my.music.note.back.user.dto.request.ModifyNameRequest;
import my.music.note.back.user.dto.response.FindUserResponse;
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

    public void deleteUser(DeleteAccountRequest request) {
        User user = userRepository.findByUserId(request.userId());
        user.deleteAccount();
        userRepository.save(user);
    }

    public void modifyName(ModifyNameRequest request, Long userId) {
        User user = userRepository.findByUserId(userId);
        user.modifyName(request);
        userRepository.save(user);
    }

    public FindUserResponse findUser(FindUserRequest request) {
        User user = userRepository.findByUserId(request.userId());
        return user.convertToFindUserResponse();
    }


}
