package my.music.note.back.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import my.music.note.back.data.dto.request.diary.DiaryCreateRequest;
import my.music.note.back.data.dto.request.diary.DiaryDeleteRequest;
import my.music.note.back.data.dto.request.diary.DiaryModifyRequest;
import my.music.note.back.data.dto.response.diary.FindDiaryResponse;
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
        return diaryRepository.findAllByUserId(userId);
    }

    public FindDiaryResponse findDiary(Long diaryId) {
        Optional<Diary> optionalDiary = diaryRepository.findById(diaryId);
        Diary diary = optionalDiary.orElseThrow(IllegalArgumentException::new);
        return diary.convertToFindDiaryResponse();
    }

}
