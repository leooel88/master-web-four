package com.quest.quest_web_java;

import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTests {

    private String token = "";

    @Autowired
    private MockMvc mvc;

    @Test
    public void testAuthenticate() throws Exception {

        //identifiant de l'utilisateur

        Map<String,String> payload = new HashMap<>();
        payload.put("username","wtf7");
        payload.put("password","passwordwtf7");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(payload);

        //enregistrer l'utilisateur

        // mvc.perform( MockMvcRequestBuilders
        // .post("/register")
        // .content(json)
        // .contentType(MediaType.APPLICATION_JSON)
        // .accept(MediaType.APPLICATION_JSON))
        // .andExpect(MockMvcResultMatchers.status().isCreated());

        //vérifier qu'on peut pas enregistrer 2 fois error 409

        mvc.perform( MockMvcRequestBuilders
        .post("/register")
        .content(json)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isConflict());

        //authentifier pour avoir le token jwt

        ResultActions result = mvc.perform( MockMvcRequestBuilders
        .post("/authenticate")
        .content(json)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andDo(MockMvcResultHandlers.print());


        // retourner le token

        String resultString = result.andReturn().getResponse().getContentAsString();

        token = "Bearer " + resultString;

        // tester le chemin /me avec le token

        mvc.perform( MockMvcRequestBuilders
        .get("/me")
        .content(json)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .header("Authorization", token)
        .header("Content-Type", "application/json"))
        .andExpect(MockMvcResultMatchers.status().isOk());

    }

    // @Test
    // public void testUser() throws Exception {
    //     token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ3dGY3IiwiZXhwIjoxNjQ5ODQ2NTQ5LCJpYXQiOjE2NDk4NDQ3NDl9.FSeWcF_qERM3qSWH2AyLoyt3xuuNnsefp8kjL9d1IPLavE8glfd_CandAOoO9IFathv60xUGYjd4sLGTgDcwow";
    //     Map<String,String> payload = new HashMap<>();
    //     payload.put("username","wtf7");
    //     payload.put("password","passwordwtf7");
    //     ObjectMapper objectMapper = new ObjectMapper();
    //     String json = objectMapper.writeValueAsString(payload);
    //     mvc.perform( MockMvcRequestBuilders
    //     .get("/user")
    //     .content(json)
    //     .contentType(MediaType.APPLICATION_JSON)
    //     .accept(MediaType.APPLICATION_JSON)
    //     .header("Authorization", token)
    //     .header("Content-Type", "application/json"))
    //     .andExpect(MockMvcResultMatchers.status().isUnauthorized());

    //     mvc.perform( MockMvcRequestBuilders
    //     .get("/user")
    //     .content(json)
    //     .contentType(MediaType.APPLICATION_JSON)
    //     .accept(MediaType.APPLICATION_JSON)
    //     .header("Authorization", "Bearer " + token)
    //     .header("Content-Type", "application/json"))
    //     .andExpect(MockMvcResultMatchers.status().isOk());
    // }
}
