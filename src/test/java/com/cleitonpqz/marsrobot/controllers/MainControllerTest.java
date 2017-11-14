package com.cleitonpqz.marsrobot.controllers;

import com.cleitonpqz.marsrobot.Application;
import com.cleitonpqz.marsrobot.entities.Face;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;
import java.util.Arrays;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
class MainControllerTest {


    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);

        assertNotNull(this.mappingJackson2HttpMessageConverter);
    }

    @BeforeEach
    void setUp() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void rightRotationMovements() throws Exception {
        //GIVEN
        String movements = "MMRMMRMM";

        //WHEN
        ResultActions resultActions = mockMvc.perform(post("/rest/mars/"+movements));

        //THEN
        resultActions.andExpect(content().contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.coordinate.x", equalTo(2)))
                .andExpect(jsonPath("$.coordinate.y", equalTo(0)))
                .andExpect(jsonPath("$.face", equalTo(Face.SOUTH.getFace())));
    }

    @Test
    public void leftMovement() throws Exception {
        //GIVEN
        String movements = "MML";

        //WHEN
        ResultActions resultActions = mockMvc.perform(post("/rest/mars/"+movements));

        //THEN
        resultActions.andExpect(content().contentType(contentType))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.coordinate.x", equalTo(0)))
            .andExpect(jsonPath("$.coordinate.y", equalTo(2)))
            .andExpect(jsonPath("$.face", equalTo(Face.WEST.getFace())));
    }

    @Test
    public void leftMovementRepetition() throws Exception {
        //GIVEN
        String movements = "MML";

        //WHEN
        ResultActions resultActions = mockMvc.perform(post("/rest/mars/"+movements));

        //THEN
        resultActions.andExpect(content().contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.coordinate.x", equalTo(0)))
                .andExpect(jsonPath("$.coordinate.y", equalTo(2)))
                .andExpect(jsonPath("$.face", equalTo(Face.WEST.getFace())));
    }

    @Test
    public void invalidCommand() throws Exception {
        //GIVEN
        String movements = "AAA";

        //WHEN
        ResultActions resultActions = mockMvc.perform(post("/rest/mars/"+movements));

        //THEN
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    public void invalidPosition() throws Exception {
        //GIVEN
        String movements = "MMMMMMMMMMMMMMMMMMMMMMMM";

        //WHEN
        ResultActions resultActions = mockMvc.perform(post("/rest/mars/"+movements));

        //THEN
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    public void notSupportedGetMethod() throws Exception {
        //GIVEN
        String movements = "MMMMMMMMMMMMMMMMMMMMMMMM";

        //WHEN
        ResultActions resultActions = mockMvc.perform(get("/rest/mars/"+movements));

        //THEN
        resultActions.andExpect(status().isMethodNotAllowed());
    }

}