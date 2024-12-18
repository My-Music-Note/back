package my.music.note.back.data.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import my.music.note.back.data.dto.diary.request.DiaryCreateRequest;
import my.music.note.back.data.dto.diary.request.DiaryModifyRequest;
import my.music.note.back.data.dto.diary.response.FindDiaryResponse;

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

    private Diary(LocalDateTime createdAt, String content, Boolean isLongEntry,User user) {
        this.createdAt = createdAt;
        this.content = content;
        this.isLongEntry = isLongEntry;
        this.isDeleted = false;
        this.user = user;
    }

    public static Diary of(DiaryCreateRequest request,User user) {
        return new Diary(LocalDateTime.now(), request.content(), request.isLongEntry(),user);
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
