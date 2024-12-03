package my.music.note.back.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import my.music.note.back.data.dto.diary.request.DiaryCreateRequest;
import my.music.note.back.data.dto.diary.request.DiaryDeleteRequest;
import my.music.note.back.data.dto.diary.request.DiaryModifyRequest;
import my.music.note.back.data.dto.diary.response.FindDiaryResponse;
import my.music.note.back.data.entity.Diary;
import my.music.note.back.data.repository.DiaryRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryRepository diaryRepository;

    public void createDiary(DiaryCreateRequest diaryCreateRequest) {

        Diary diary = Diary.of(diaryCreateRequest);
        diaryRepository.save(diary);
    }

    public void deleteDiary(DiaryDeleteRequest diaryDeleteRequest) {
        diaryRepository.deleteById(diaryDeleteRequest.diaryId());
    }

    public void modifyDiary(DiaryModifyRequest diaryModifyRequest) {

        Optional<Diary> optionalDiary = diaryRepository.findById(diaryModifyRequest.id());

        Diary diary = optionalDiary.orElseThrow(IllegalArgumentException::new);
        diary.modifyDiaryContent(diaryModifyRequest);
    }

    public List<FindDiaryResponse> findDiaries(Long userId) {
        return diaryRepository.findAllByUserIdAndIsDeletedFalse(userId);
    }

    public FindDiaryResponse findDiary(Long diaryId) {
        Optional<Diary> optionalDiary = diaryRepository.findById(diaryId);
        Diary diary = optionalDiary.orElseThrow(IllegalArgumentException::new);

        if (diary.isDeleted()) {
            throw new IllegalArgumentException();
        }

        return diary.convertToFindDiaryResponse();
    }

}
