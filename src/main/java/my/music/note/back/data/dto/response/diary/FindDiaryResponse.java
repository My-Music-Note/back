package my.music.note.back.data.dto.response.diary;

import java.time.LocalDateTime;

public record FindDiaryResponse(Long id, String content, Boolean isLongEntry, LocalDateTime createdAt,
                                LocalDateTime currentModifiedAt) {
}

