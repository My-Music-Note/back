package my.music.note.back.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import my.music.note.back.user.dto.request.DeleteAccountRequest;
import my.music.note.back.user.dto.request.LoginOrRegisterRequest;
import my.music.note.back.user.dto.request.ModifyNameRequest;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor
public class User {

    private User(String userName, LocalDateTime createdAt, String email, String provider, String providerId, boolean isAdmin, boolean isDeleted) {
        this.userName = userName;
        this.createdAt = createdAt;
        this.email = email;
        this.provider = provider;
        this.providerId = providerId;
        this.isAdmin = isAdmin;
        this.isDeleted = isDeleted;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long userId;

    String userName;

    LocalDateTime createdAt;

    String email;

    String provider;

    String providerId;

    Boolean isAdmin;

    Boolean isDeleted;


    public static User of(LoginOrRegisterRequest request) {
        return new User(request.name(), LocalDateTime.now(), request.email(), request.provider(), request.providerId(), false, false);
    }

    public void deleteAccount() {
        this.isDeleted = true;
    }

    public void modifyName(ModifyNameRequest request) {
        this.userName = request.name();
    }


}