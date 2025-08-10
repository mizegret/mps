package com.mizegret.mps.mps_api.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import com.mizegret.mps.mps_api.controllers.HealthController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HealthController.class)
public class HealthControllerTest{

    @Autowired
    private MockMvc mockMvc;

    @Test
    void healthReturnsOkAndExpectedString() throws Exception{
        mockMvc.perform(get(HealthController.HEALTH_ENDPOINT)).andExpect(status().isOk())
                .andExpect(content().string("Hello, MPS API is running!"));
    }
}
