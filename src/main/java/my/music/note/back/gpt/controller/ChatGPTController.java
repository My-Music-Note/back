package my.music.note.back.gpt.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.music.note.back.gpt.dto.ChatCompletionDto;
import my.music.note.back.gpt.service.ChatGPTService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/chatGpt")
public class ChatGPTController {

    private final ChatGPTService chatGPTService;

    @PostMapping("/prompt")
    public ResponseEntity<Map<String, Object>> selectPrompt(@RequestBody ChatCompletionDto chatCompletionDto) {
        log.debug("param :: " + chatCompletionDto.toString());
        Map<String, Object> result = chatGPTService.prompt(chatCompletionDto);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
