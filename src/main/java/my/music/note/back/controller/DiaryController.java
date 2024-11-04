package my.music.note.back.controller;

import lombok.RequiredArgsConstructor;
import my.music.note.back.data.dto.request.diary.DiaryCreateRequest;
import my.music.note.back.data.dto.request.diary.DiaryDeleteRequest;
import my.music.note.back.data.dto.request.diary.DiaryModifyRequest;
import my.music.note.back.data.dto.response.diary.FindDiaryResponse;
import my.music.note.back.service.DiaryService;
import my.music.note.back.service.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/api/diaries")
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryService diaryService;

    private final TokenService tokenService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void createDiary(@RequestBody DiaryCreateRequest request) {
        diaryService.createDiary(request);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDiary(@RequestBody DiaryDeleteRequest request) {
        diaryService.deleteDiary(request);
    }

    @PutMapping
    public void modifyDiary(@RequestBody DiaryModifyRequest request) {

        diaryService.modifyDiary(request);
    }

    @GetMapping("/{diaryId}")
    public ResponseEntity<FindDiaryResponse> FindDiaryResponse(@PathVariable Long diaryId) {

        FindDiaryResponse response = diaryService.findDiary(diaryId);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<List<FindDiaryResponse>> FindDiaryResponse(@RequestHeader("Authorization") String token) {

        Long userId = tokenService.getUserId(token);
        List<FindDiaryResponse> response = diaryService.findDiaries(userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



}
