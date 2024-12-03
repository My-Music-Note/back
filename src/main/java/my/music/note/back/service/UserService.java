package my.music.note.back.service;


import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import my.music.note.back.data.dto.user.request.DeleteAccountRequest;
import my.music.note.back.data.dto.user.request.FindUserRequest;
import my.music.note.back.data.dto.user.request.LoginOrRegisterRequest;
import my.music.note.back.data.dto.user.request.ModifyNameRequest;
import my.music.note.back.data.dto.user.response.FindUserResponse;
import my.music.note.back.data.entity.User;
import my.music.note.back.data.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
        Optional<User> optionalUser = userRepository.findById(request.id());

        
        User user = optionalUser.orElseThrow(RuntimeException::new);
        user.deleteAccount();;
    }

    public void modifyName(ModifyNameRequest request, Long userId) {

        Optional<User> optionalUser = userRepository.findById(userId);
        User user = optionalUser.orElseThrow(RuntimeException::new);

        user.modifyName(request);
    }

    public FindUserResponse findUser(FindUserRequest request) {
        Optional<User> optionalUser = userRepository.findById(request.id());
        User user = optionalUser.orElseThrow(RuntimeException::new);
        return user.convertToFindUserResponse();
    }


}
