package com.sleeptracker.sleeptracker;

import com.sleeptracker.sleeptracker.models.Sleep;
import com.sleeptracker.sleeptracker.repositories.SleepRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class SleepControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SleepRepository sleepRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @WithMockUser(username = "user123")
    @Test
    public void testCreateSleep() throws Exception {
        Sleep sleep = new Sleep();
        sleep.setUserId("user123");
        sleep.setGoToSleep("22:00");
        sleep.setWakeUp("06:45");
        sleep.setCalculatedTime("08:45");
        sleep.setCalculationChoice("goToSleep");

        String sleepJson = objectMapper.writeValueAsString(sleep);

        mockMvc.perform(post("/sleeps-api/sleep")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(sleepJson))
                .andExpect(status().isOk());

        Sleep savedSleep = sleepRepository.findByUserId("user123").get(0);

        assertThat(savedSleep).isNotNull();
        assertThat(savedSleep.getGoToSleep()).isEqualTo("22:00");
        assertThat(savedSleep.getWakeUp()).isEqualTo("06:45");
        assertThat(savedSleep.getCalculatedTime()).isEqualTo("08:45");
        assertThat(savedSleep.getCalculationChoice()).isEqualTo("goToSleep");
    }
}
