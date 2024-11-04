package my.music.note.back.controller;

import lombok.RequiredArgsConstructor;
import my.music.note.back.data.dto.response.jwt.TokenCreateResponse;
import my.music.note.back.service.TokenService;
import my.music.note.back.data.dto.request.user.DeleteAccountRequest;
import my.music.note.back.data.dto.request.user.FindUserRequest;
import my.music.note.back.data.dto.request.user.LoginOrRegisterRequest;
import my.music.note.back.data.dto.request.user.ModifyNameRequest;
import my.music.note.back.data.dto.response.user.FindUserResponse;
import my.music.note.back.data.entity.User;
import my.music.note.back.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final TokenService tokenService;

    @PostMapping
    public ResponseEntity<TokenCreateResponse> loginOrRegister(@RequestBody LoginOrRegisterRequest request) {

        User user = userService.loginOrRegister(request);
        TokenCreateResponse response = tokenService.createToken(user.getUserId(), user.getAdmin());
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

    @GetMapping
    public ResponseEntity<FindUserResponse> findUser(@CookieValue String token) {

        Long userId = tokenService.getUserId(token);

        FindUserResponse response = userService.findUser(new FindUserRequest(userId));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}


