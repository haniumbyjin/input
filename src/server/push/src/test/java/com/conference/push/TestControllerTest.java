package com.conference.push;

import com.conference.push.model.request.TestReq;
import com.conference.push.model.stock.StockTransaction;
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
import reactor.core.publisher.Flux;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getTest() throws Exception {
        mockMvc.perform(get("/stock/transaction"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void testSseEmitter() throws Exception {
        mockMvc.perform(get("/sse-events"))
                .andExpect(content().contentType("text/event-stream;charset=UTF-8"))
                .andExpect(header().string("Content-Type", "text/event-stream;charset=UTF-8"))
                .andDo(print());
    }

    @Test
    public void testSseEmitter2() throws Exception {
        mockMvc.perform(get("/sse-events/test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.message").value("SUCCESS"))
                .andExpect(content().contentType("application/stream+json;charset=UTF-8"))
                .andExpect(header().string("Content-Type", "application/stream+json;charset=UTF-8"))
                .andDo(print());
    }

    @Test
    public void getTest2() throws Exception {

        mockMvc.perform(get("/stock/transaction/test?testParam=test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.message").value("SUCCESS"));

    }

    @Test
    public void postTest() throws Exception {

        TestReq request = TestReq.builder()
                .input("input")
                .build();

        String json = asJsonString(request);

        mockMvc.perform(post("/stock/transaction/test")
                .header("testHeader", "testHeader")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.message").value("SUCCESS"));

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
