package my.music.note.back.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import my.music.note.back.controller.UserController;
import my.music.note.back.data.dto.jwt.response.TokenCreateResponse;
import my.music.note.back.data.dto.user.request.DeleteAccountRequest;
import my.music.note.back.data.dto.user.request.FindUserRequest;
import my.music.note.back.data.dto.user.request.LoginOrRegisterRequest;
import my.music.note.back.data.dto.user.request.ModifyNameRequest;
import my.music.note.back.data.dto.user.response.FindUserResponse;
import my.music.note.back.data.entity.User;
import my.music.note.back.service.TokenService;
import my.music.note.back.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = UserController.class)
@ExtendWith({MockitoExtension.class})
class UserControllerTest {

    @Autowired
    MockMvc mockmvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    UserService userService;

    @MockBean
    TokenService tokenService;

    String token = "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJteS1tdXNpYy1ub3RlIiwic3ViIjoiMiIsImlhdCI6MTczMDY4NzIxNCwiZXhwIjoxNzMwNjg5MDE0LCJhdXRob3JpdHkiOiJST0xFX1VTRVIifQ.0JKeOVEmnlKDiZSKl9OJz6rtjLFnpXrXxCb-3hYuBAt9yFGTZIc8aX-jzmpFDL0e9EzSoOHIVhiDFViNC6T9vw";


    @Test
    @DisplayName("로그인 or 회원가입 성공")
    void givenLoginOrRegisterRequest_whenCallLoginOrRegister_thenReturnTokenCreateResponse(@Mock User user) throws Exception {

        LoginOrRegisterRequest loginOrRegisterRequest = new LoginOrRegisterRequest("test-user", "this-is-eamil@google.com", "google", "google-provider");
        TokenCreateResponse tokenCreateResponse = new TokenCreateResponse("this-is-test-token");
        String content = objectMapper.writeValueAsString(loginOrRegisterRequest);

        when(userService.loginOrRegister(any(LoginOrRegisterRequest.class))).thenReturn(user);
        when(tokenService.createToken(anyLong(), anyBoolean())).thenReturn(tokenCreateResponse);

        mockmvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("this-is-test-token"));

        verify(userService, times(1)).loginOrRegister(any(LoginOrRegisterRequest.class));
        verify(tokenService, times(1)).createToken(anyLong(), anyBoolean());
    }

    @Test
    @DisplayName("회원탈퇴 성공")
    void givenToken_whenCallDeleteAccount_thenReturnOK(@Mock DeleteAccountRequest deleteAccountRequest) throws Exception {

        doNothing().when(userService).deleteUser(deleteAccountRequest);

        mockmvc.perform(delete("/api/users")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(userService, times(1)).deleteUser(any(DeleteAccountRequest.class));
    }

    @Test
    @DisplayName("이름변경 성공")
    void givenTokenAndModifyNameRequest_whenCallModifyName_thenReturnOK(@Mock ModifyNameRequest modifyNameRequest) throws Exception {

        doNothing().when(userService).modifyName(modifyNameRequest, 1L);

        String content = objectMapper.writeValueAsString(modifyNameRequest);

        mockmvc.perform(put("/api/users")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk());

        verify(userService, times(1)).modifyName(any(ModifyNameRequest.class), anyLong());
        verify(tokenService, times(1)).getUserId(anyString());
    }

    @Test
    @DisplayName("회원 조회 성공")
    void givenToken_whenCallFindUserInfo_thenReturnOK() throws Exception {

        FindUserResponse findUserResponse = new FindUserResponse("test-user", "testEmail@google.com", false);
        when(userService.findUser(any(FindUserRequest.class))).thenReturn(findUserResponse);

        mockmvc.perform(get("/api/users")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("test-user"))
                .andExpect(jsonPath("$.email").value("testEmail@google.com"))
                .andExpect(jsonPath("$.isAdmin").value(false));

        verify(userService, times(1)).findUser(any(FindUserRequest.class));
        verify(tokenService, times(1)).getUserId(anyString());

    }
}