package my.music.note.back.user.service;

import my.music.note.back.user.dto.request.DeleteAccountRequest;
import my.music.note.back.user.dto.request.FindUserRequest;
import my.music.note.back.user.dto.request.LoginOrRegisterRequest;
import my.music.note.back.user.dto.request.ModifyNameRequest;
import my.music.note.back.user.dto.response.FindUserResponse;
import my.music.note.back.user.entity.User;
import my.music.note.back.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @Test
    @DisplayName("최초 로그인이 아님 -> DB에 정보 남아있어 바로 로그인 처리")
    void givenLoginOrRegisterRequest_whenCallLoginOrRegisterAndLoginBefore_thenReturnUser(@Mock User user) {

        LoginOrRegisterRequest loginOrRegisterRequest = new LoginOrRegisterRequest("user", "testEmail@google.com", "google", "google-provider");
        when(userRepository.existsByProviderId(loginOrRegisterRequest.providerId())).thenReturn(true);
        when(userRepository.findByProviderId(loginOrRegisterRequest.providerId())).thenReturn(user);

        userService.loginOrRegister(loginOrRegisterRequest);

        verify(userRepository, times(1)).existsByProviderId(loginOrRegisterRequest.providerId());
        verify(userRepository, times(1)).findByProviderId(loginOrRegisterRequest.providerId());
        verify(userRepository, times(0)).save(any(User.class));

    }

    @Test
    @DisplayName("최초 로그인임 -> 회원가입처리")
    void givenLoginOrRegisterRequest_whenCallLoginOrRegisterAndNeverLoginBefore_thenReturnUser(@Mock User user) {

        LoginOrRegisterRequest loginOrRegisterRequest = new LoginOrRegisterRequest("user", "testEmail@google.com", "google", "google-provider");
        when(userRepository.existsByProviderId(loginOrRegisterRequest.providerId())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user);

        userService.loginOrRegister(loginOrRegisterRequest);

        verify(userRepository, times(1)).existsByProviderId(loginOrRegisterRequest.providerId());
        verify(userRepository, times(1)).save(any(User.class));
        verify(userRepository, times(0)).findByProviderId(loginOrRegisterRequest.providerId());
    }

    @Test
    @DisplayName("회원탈퇴 성공")
    void givenDeleteAccountRequest_whenCallDeleteUser_thenReturnVoid(@Mock DeleteAccountRequest deleteAccountRequest, @Mock User user) {

        when(deleteAccountRequest.id()).thenReturn(1L);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        doNothing().when(user).deleteAccount();

        userService.deleteUser(deleteAccountRequest);

        verify(userRepository, times(1)).findById(1L);
        verify(user, times(1)).deleteAccount();
        verify(userRepository, times(1)).save(user);
    }

    @Test
    @DisplayName("이름변경 성공")
    void givenModifyNameRequestAndUserId_whenCallModifyName_thenReturnVoid(@Mock ModifyNameRequest modifyNameRequest, @Mock User user) {

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        doNothing().when(user).modifyName(modifyNameRequest);

        userService.modifyName(modifyNameRequest, 1L);

        verify(userRepository, times(1)).findById(1L);
        verify(user, times(1)).modifyName(modifyNameRequest);
        verify(userRepository, times(1)).save(user);

    }

    @Test
    @DisplayName("유저조회 성공")
    void givenFindUserRequest_whenCallFindUser_thenReturnFindUserResponse(@Mock FindUserRequest request, @Mock User user, @Mock FindUserResponse response) {

        when(request.id()).thenReturn(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(user.convertToFindUserResponse()).thenReturn(response);

        userService.findUser(request);

        verify(request, times(1)).id();
        verify(userRepository, times(1)).findById(1L);
        verify(user, times(1)).convertToFindUserResponse();

    }
}