package com.eshilov.auth;

import static lombok.AccessLevel.PROTECTED;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@Getter(PROTECTED)
@AutoConfigureMockMvc
@Accessors(fluent = true)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class IntTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
}
