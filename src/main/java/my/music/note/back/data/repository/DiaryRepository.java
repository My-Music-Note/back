package my.music.note.back.data.repository;

import my.music.note.back.data.dto.diary.response.FindDiaryResponse;
import my.music.note.back.data.entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DiaryRepository extends JpaRepository<Diary, Long> {

    List<FindDiaryResponse> findAllByUserIdAndIsDeletedFalse(Long userId);

    Boolean existsByIdAndUserId(Long diaryId, Long userId);

    Optional<FindDiaryResponse> findByUserIdAndIdAndIsDeletedFalse(Long userId,Long diaryId);

}
