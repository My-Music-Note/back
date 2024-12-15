package my.music.note.back.service;

import my.music.note.back.data.dto.diary.request.DiaryCreateRequest;
import my.music.note.back.data.dto.diary.request.DiaryModifyRequest;
import my.music.note.back.data.dto.diary.response.FindDiaryResponse;
import my.music.note.back.data.entity.Diary;
import my.music.note.back.data.entity.User;
import my.music.note.back.data.repository.DiaryRepository;
import my.music.note.back.data.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DiaryServiceTest {

    @Mock
    DiaryRepository diaryRepository;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    DiaryService diaryService;


    @Test
    @DisplayName("일기 생성")
    void createDiaryTestSuccess(@Mock User user) {

        ArgumentCaptor<Diary> diaryCaptor = ArgumentCaptor.forClass(Diary.class);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        DiaryCreateRequest request = new DiaryCreateRequest("test-content", true);
        when(diaryRepository.save(any())).thenReturn(Diary.of(request, user));

        diaryService.createDiary(request, 1L);

        verify(diaryRepository, times(1)).save(diaryCaptor.capture());
        verify(userRepository, times(1)).findById(1L);

        Diary savedDiary = diaryCaptor.getValue();
        FindDiaryResponse findDiaryResponse = savedDiary.convertToFindDiaryResponse();

        assertNotNull(savedDiary);
        assertFalse(savedDiary.isDeleted());
        assertEquals(true, findDiaryResponse.isLongEntry());
        assertEquals("test-content", findDiaryResponse.content());
    }

    @Test
    @DisplayName("일기 생성 실패 - 올바르 유저가 아님")
    void createDiaryTestFailure() {

        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        DiaryCreateRequest request = new DiaryCreateRequest("test-content", true);

        assertThrows(NoSuchElementException.class, () ->
                diaryService.createDiary(request, 1L)
        );

    }


    @Test
    @DisplayName("일기 삭제 성공")
    void deleteDiaryTestSuccess() {

        when(diaryRepository.existsByIdAndUserId(1L, 1L)).thenReturn(true);
        doNothing().when(diaryRepository).deleteById(1L);

        diaryService.deleteDiary(1L, 1L);

        verify(diaryRepository, times(1)).deleteById(1L);
        verify(diaryRepository, times(1)).existsByIdAndUserId(1L, 1L);
    }

    @Test
    @DisplayName("일기 삭제 실패 - 해당 유저 or 일기 존재하지 않음")
    void deleteDiaryTestFailure() {

        when(diaryRepository.existsByIdAndUserId(1L, 1L)).thenReturn(false);
        assertThrows(IllegalArgumentException.class, () -> diaryService.deleteDiary(1L, 1L));
    }

    @Test
    @DisplayName("일기 내용 수정")
    void modifyDiaryTestSuccess(@Mock Diary diary) {

        ArgumentCaptor<DiaryModifyRequest> modifyRequestArgumentCaptor = ArgumentCaptor.forClass(DiaryModifyRequest.class);

        DiaryModifyRequest request = new DiaryModifyRequest("modify-test-content");
        when(diaryRepository.existsByIdAndUserId(1L, 1L)).thenReturn(true);
        when(diaryRepository.queryById(1L)).thenReturn(diary);

        diaryService.modifyDiary(1L, 1L, request);

        verify(diaryRepository, times(1)).existsByIdAndUserId(1L, 1L);
        verify(diaryRepository, times(1)).queryById(1L);
        verify(diary, times(1)).modifyDiaryContent(modifyRequestArgumentCaptor.capture());

        DiaryModifyRequest modifyRequest = modifyRequestArgumentCaptor.getValue();

        assertEquals("modify-test-content", modifyRequest.content());
    }

    @Test
    @DisplayName("일기 내용 수정 실패 - 해당 유저 or 일기 존재하지 않음")
    void modifyDiaryTestFailure() {

        when(diaryRepository.existsByIdAndUserId(1L, 1L)).thenReturn(false);

        DiaryModifyRequest request = new DiaryModifyRequest("modify-test-content");

        assertThrows(IllegalArgumentException.class, () ->
                diaryService.modifyDiary(1L, 1L, request)
        );
    }

    @Test
    @DisplayName("유저 일기 조회")
    void findDiariesTestSuccess() {

        LocalDateTime localDateTime = LocalDateTime.now();

        List<FindDiaryResponse> list = List.of(
                new FindDiaryResponse(1L, "test-content1", true, localDateTime, null)
                , new FindDiaryResponse(2L, "test-content2", false, localDateTime, null));

        when(diaryRepository.findAllByUserIdAndIsDeletedFalse(1L)).thenReturn(list);

        List<FindDiaryResponse> diaryResponseList = diaryService.findDiaries(1L);
        verify(diaryRepository, times(1)).findAllByUserIdAndIsDeletedFalse(1L);

        for (int i = 0; i < list.size(); i++) {
            assertThat(diaryResponseList.get(i))
                    .usingRecursiveComparison()
                    .isEqualTo(list.get(i));
        }

    }


    @Test
    @DisplayName("일기 조회 성공")
    void findDiaryTestSuccess() {

        LocalDateTime localDateTime = LocalDateTime.now();
        FindDiaryResponse findDiaryResponse = new FindDiaryResponse(1L, "test-content1", true, localDateTime, null);
        when(diaryRepository.findByUserIdAndIdAndIsDeletedFalse(1L, 1L)).thenReturn(Optional.of(findDiaryResponse));

        FindDiaryResponse response = diaryService.findDiary(1L, 1L);

        assertEquals(findDiaryResponse.id(),response.id());
        assertEquals(findDiaryResponse.isLongEntry(),response.isLongEntry());
        assertEquals(findDiaryResponse.currentModifiedAt(),response.currentModifiedAt());
        assertEquals(findDiaryResponse.content(),response.content());
        assertEquals(findDiaryResponse.createdAt(),response.createdAt());
    }

    @Test
    @DisplayName("일기 조회 실패 - 유저 or 일기 없음")
    void findDiaryTestFailure() {
        when(diaryRepository.findByUserIdAndIdAndIsDeletedFalse(1L,1L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> diaryService.findDiary(1L, 1L));
    }

}