package my.music.note.back.data.dto.request.user;

public record LoginOrRegisterRequest(String name, String email, String provider, String providerId) {

}
