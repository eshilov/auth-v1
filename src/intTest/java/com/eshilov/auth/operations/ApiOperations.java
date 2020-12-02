package com.eshilov.auth.operations;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;

@Component
@RequiredArgsConstructor
public class ApiOperations {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    public <T> ResponseEntity<T> postForResponseEntity(
            String path, Object request, Class<T> responseType) {
        var servletResponse = internalPostForServletResponse(path, request);
        var status = servletResponse.getStatus();
        var bodyBytes = servletResponse.getContentAsByteArray();
        var body = objectMapper.readValue(bodyBytes, responseType);
        return ResponseEntity.status(status).body(body);
    }

    public MockHttpServletResponse postForServletResponse(String path, Object request) {
        return internalPostForServletResponse(path, request);
    }

    @SneakyThrows
    private MockHttpServletResponse internalPostForServletResponse(String path, Object request) {
        return mockMvc.perform(
                        post(path)
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(request)))
                .andReturn()
                .getResponse();
    }
}
