package my.music.note.back.data.dto.diary.response;

import java.time.LocalDateTime;

public record FindDiaryResponse(Long id, String content, Boolean isLongEntry, LocalDateTime createdAt,
                                LocalDateTime currentModifiedAt) {
}

