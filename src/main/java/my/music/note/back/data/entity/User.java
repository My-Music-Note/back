package my.music.note.back.data.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import my.music.note.back.data.dto.user.request.LoginOrRegisterRequest;
import my.music.note.back.data.dto.user.request.ModifyNameRequest;
import my.music.note.back.data.dto.user.response.FindUserResponse;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@NoArgsConstructor
public class User {

    private User(String name, LocalDateTime createdAt, String email, String provider, String providerId, boolean isAdmin, boolean isDeleted) {
        this.name = name;
        this.createdAt = createdAt;
        this.email = email;
        this.provider = provider;
        this.providerId = providerId;
        this.isAdmin = isAdmin;
        this.isDeleted = isDeleted;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private LocalDateTime createdAt;

    private String email;

    private String provider;

    private String providerId;

    private Boolean isAdmin;

    private Boolean isDeleted;

    public Long getUserId() {
        return id;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public static User of(LoginOrRegisterRequest request) {
        return new User(request.name(), LocalDateTime.now(), request.email(), request.provider(), request.providerId(), false, false);
    }

    public FindUserResponse convertToFindUserResponse() {
        return new FindUserResponse(this.name, this.email, this.isAdmin);
    }

    public void deleteAccount() {
        this.isDeleted = true;
    }

    public void modifyName(ModifyNameRequest request) {
        this.name = request.name();
    }


}
