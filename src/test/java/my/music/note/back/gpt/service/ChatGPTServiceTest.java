package my.music.note.back.gpt.service;

import my.music.note.back.config.ChatGPTConfig;
import my.music.note.back.data.dto.gpt.request.ChatCompletionDto;
import my.music.note.back.data.dto.gpt.request.ChatRequestMsgDto;
import my.music.note.back.service.ChatGPTService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ChatGPTServiceTest {

    @Mock
    ChatGPTConfig chatGPTConfig;

    @Mock
    RestTemplate restTemplate;

    @InjectMocks
    ChatGPTService chatGPTService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("성공적으로 프롬프트 요청을 처리하고 응답을 반환")
    void givenValidChatCompletionDto_whenPrompt_thenReturnResponseMap() {

        ChatRequestMsgDto chatRequestMsgDto = new ChatRequestMsgDto("user", "Hello!");
        ChatCompletionDto chatCompletionDto = ChatCompletionDto.builder()
                .model("gpt-3.5-turbo")
                .messages(Arrays.asList(chatRequestMsgDto))
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer test-token");
        when(chatGPTConfig.httpHeaders()).thenReturn(headers);

        String responseBody = "{\"response\":\"Hello, how can I assist you?\"}";
        ResponseEntity<String> mockResponse = new ResponseEntity<>(responseBody, HttpStatus.OK);

        when(chatGPTConfig.restTemplate()).thenReturn(restTemplate);
        when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), eq(String.class)))
                .thenReturn(mockResponse);

        chatGPTService.prompt(chatCompletionDto);

        verify(chatGPTConfig, times(1)).httpHeaders();
        verify(chatGPTConfig, times(1)).restTemplate();
    }

}
