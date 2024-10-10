package my.music.note.back.user.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import my.music.note.back.jwt.dto.response.TokenCreateResponse;
import my.music.note.back.jwt.service.TokenService;
import my.music.note.back.user.dto.request.DeleteAccountRequest;
import my.music.note.back.user.dto.request.FindUserRequest;
import my.music.note.back.user.dto.request.LoginOrRegisterRequest;
import my.music.note.back.user.dto.request.ModifyNameRequest;
import my.music.note.back.user.entity.User;
import my.music.note.back.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final TokenService tokenService;

    @PostMapping("/login-or-register")
    public ResponseEntity<TokenCreateResponse> loginOrRegister(@RequestBody LoginOrRegisterRequest request) {

        User user = userService.loginOrRegister(request);
        TokenCreateResponse response = tokenService.createToken(user.getUserId(), user.getIsAdmin());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccount(@CookieValue String token) {
        Long userId = tokenService.getUserId(token);
        userService.deleteUser(new DeleteAccountRequest(userId));
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void modifyName(@CookieValue String token, @RequestBody ModifyNameRequest request) {
        Long userId = tokenService.getUserId(token);
        userService.modifyName(request, userId);
    }





}


