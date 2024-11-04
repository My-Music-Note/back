package my.music.note.back.gpt.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import my.music.note.back.controller.ChatGPTController;
import my.music.note.back.data.dto.request.gpt.ChatCompletionDto;
import my.music.note.back.data.dto.request.gpt.ChatRequestMsgDto;
import my.music.note.back.service.ChatGPTService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = ChatGPTController.class)
@ExtendWith(MockitoExtension.class)
class ChatGPTControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ChatGPTService chatGPTService;

    @Test
    @DisplayName("ChatGPT 프롬프트 호출 성공")
    void givenChatCompletionDto_whenCallSelectPrompt_thenReturnSuccessResponse() throws Exception {
        // given
        ChatRequestMsgDto chatRequestMsgDto = new ChatRequestMsgDto("user", "Hello!");
        ChatCompletionDto chatCompletionDto = ChatCompletionDto.builder()
                .model("gpt-3.5-turbo")
                .messages(Arrays.asList(chatRequestMsgDto))
                .build();

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("response", "Hello, how can I assist you?");

        when(chatGPTService.prompt(any(ChatCompletionDto.class))).thenReturn(resultMap);

        String content = objectMapper.writeValueAsString(chatCompletionDto);

        mockMvc.perform(post("/api/chatGpt/prompt")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response").value("Hello, how can I assist you?"));

        verify(chatGPTService, times(1)).prompt(any(ChatCompletionDto.class));
    }

}
