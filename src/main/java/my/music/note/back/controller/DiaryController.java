package my.music.note.back.controller;

import lombok.RequiredArgsConstructor;
import my.music.note.back.data.dto.diary.request.DiaryCreateRequest;
import my.music.note.back.data.dto.diary.request.DiaryModifyRequest;
import my.music.note.back.data.dto.diary.response.FindDiaryResponse;
import my.music.note.back.service.DiaryService;
import my.music.note.back.service.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/diaries")
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryService diaryService;

    private final TokenService tokenService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void createDiary(@RequestHeader("Authorization") String token, @RequestBody DiaryCreateRequest request) {
        Long userId = tokenService.getUserId(token);
        diaryService.createDiary(request, userId);
    }

    @DeleteMapping("/{diaryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDiary(@RequestHeader("Authorization") String token, @PathVariable Long diaryId) {
        Long userId = tokenService.getUserId(token);
        diaryService.deleteDiary(diaryId, userId);
    }

    @PutMapping("/{diaryId}")
    @ResponseStatus(HttpStatus.OK)
    public void modifyDiary(@RequestHeader("Authorization") String token, @PathVariable Long diaryId, @RequestBody DiaryModifyRequest request) {
        Long userId = tokenService.getUserId(token);
        diaryService.modifyDiary(diaryId, userId, request);
    }

    @GetMapping("/{diaryId}")
    public ResponseEntity<FindDiaryResponse> FindDiaryResponse(@RequestHeader("Authorization") String token, @PathVariable Long diaryId) {
        Long userId = tokenService.getUserId(token);
        FindDiaryResponse response = diaryService.findDiary(diaryId, userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<FindDiaryResponse>> FindDiaryResponse(@RequestHeader("Authorization") String token) {

        Long userId = tokenService.getUserId(token);
        List<FindDiaryResponse> response = diaryService.findDiaries(userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
