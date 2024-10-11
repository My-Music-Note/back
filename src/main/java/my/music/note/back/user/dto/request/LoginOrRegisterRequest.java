package my.music.note.back.user.dto.request;

public record LoginOrRegisterRequest(String name, String email, String provider, String providerId) {

}
