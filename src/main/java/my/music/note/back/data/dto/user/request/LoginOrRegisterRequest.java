package my.music.note.back.data.dto.user.request;

public record LoginOrRegisterRequest(String name, String email, String provider, String providerId) {

}
