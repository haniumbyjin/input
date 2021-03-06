package com.conference.chatting;

import com.conference.chatting.model.request.TestReq;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getTest() throws Exception {

        mockMvc.perform(get("/chat/get/test?testParam=test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.message").value("성공"));

    }

    @Test
    public void postTest() throws Exception {

        TestReq request = TestReq.builder()
                .input("input")
                .build();

        String json = asJsonString(request);

        mockMvc.perform(post("/chat/post/test")
                .header("testHeader", "testHeader")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.message").value("성공"));

    }

    @Test
    public void postTestWithException() throws Exception {

        TestReq request = TestReq.builder()
                .input("Example Exception")
                .build();

        String json = asJsonString(request);

        mockMvc.perform(post("/chat/post/test")
                .header("testHeader", "testHeader")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(999))
                .andExpect(jsonPath("$.message").value("예제 오류 입니다."));

    }

    @Before
    public void initializeTest() {
        // Test가 실행되기 전 코드를 추가할 수 있습니다.
        // Transactional 선언을 통해 DB 작업 이후 롤백이 가능합니다.
    }

    public String asJsonString(Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
