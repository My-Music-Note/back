package my.music.note.back.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import my.music.note.back.data.dto.diary.request.DiaryCreateRequest;
import my.music.note.back.data.dto.diary.request.DiaryModifyRequest;
import my.music.note.back.data.dto.diary.response.FindDiaryResponse;
import my.music.note.back.data.entity.Diary;
import my.music.note.back.data.entity.User;
import my.music.note.back.data.repository.DiaryRepository;
import my.music.note.back.data.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryRepository diaryRepository;

    private final UserRepository userRepository;

    public void createDiary(DiaryCreateRequest diaryCreateRequest, Long userId) {

        User user = userRepository.findById(userId).orElseThrow();

        Diary diary = Diary.of(diaryCreateRequest, user);
        diaryRepository.save(diary);
    }

    public void deleteDiary(Long diaryId, Long userId) {

        if (!diaryRepository.existsByIdAndUserId(diaryId, userId)) {
            throw new IllegalArgumentException();
        }

        diaryRepository.deleteById(diaryId);
    }

    public void modifyDiary(Long diaryId, Long userId, DiaryModifyRequest diaryModifyRequest) {


        if (!diaryRepository.existsByIdAndUserId(userId, diaryId)) {
            throw new IllegalArgumentException();
        }

        Diary diary = diaryRepository.findById(diaryId).get();
        diary.modifyDiaryContent(diaryModifyRequest);
    }

    public List<FindDiaryResponse> findDiaries(Long userId) {
        return diaryRepository.findAllByUserIdAndIsDeletedFalse(userId);
    }

    public FindDiaryResponse findDiary(Long diaryId, Long userId) {

        return diaryRepository.findByUserIdAndIdAndIsDeletedFalse(userId, diaryId).orElseThrow(IllegalArgumentException::new);
    }

}
