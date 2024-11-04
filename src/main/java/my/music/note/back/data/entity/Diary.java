package my.music.note.back.data.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import my.music.note.back.data.dto.request.diary.DiaryCreateRequest;
import my.music.note.back.data.dto.request.diary.DiaryModifyRequest;
import my.music.note.back.data.dto.response.diary.FindDiaryResponse;

import java.time.LocalDateTime;

@Entity
@Table(name = "diary")
@NoArgsConstructor
public class Diary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime currentModifiedAt;

    private Boolean isLongEntry;

    private Boolean isDeleted;

    private Diary(LocalDateTime createdAt, String content, Boolean isLongEntry) {
        this.createdAt = createdAt;
        this.content = content;
        this.isLongEntry = isLongEntry;
        this.isDeleted = false;
    }

    public static Diary of(DiaryCreateRequest request) {
        return new Diary(LocalDateTime.now(), request.content(), request.isLongEntry());
    }

    public void modifyDiaryContent(DiaryModifyRequest request) {
        this.content = request.content();
        this.currentModifiedAt = LocalDateTime.now();
    }

    public boolean isDeleted() {
        return this.isDeleted;
    }


    public FindDiaryResponse convertToFindDiaryResponse() {
        return new FindDiaryResponse(this.id, this.content, this.isLongEntry, this.createdAt, this.currentModifiedAt);
    }

}
