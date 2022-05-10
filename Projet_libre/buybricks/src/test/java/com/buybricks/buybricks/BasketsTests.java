package com.buybricks.buybricks;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest(classes={com.buybricks.buybricks.BuybricksApplication.class})
@AutoConfigureMockMvc
public class BasketsTests {

    private static String token = "";
    private static int userId = 0;
    private static int brickId_1 = 0;
    private static int brickId_2 = 0;
    private static int basketId = 0;

    @Autowired
    private MockMvc mvc;

    @Test
    public void testCreateGetUpdateDelete() throws Exception {

        initTestEnv();

        // Load JSON
        Map<String,Object> payload = new HashMap<>();
        payload.put("user_id", BasketsTests.userId);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(payload);

        // Create basket
        MvcResult result = mvc.perform( MockMvcRequestBuilders
        .post("/basket")
        .content(json)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andDo(MockMvcResultHandlers.print())
        .andReturn();

        // Get user_id
        BasketsTests.basketId = JsonPath.read(result.getResponse().getContentAsString(), "$.basket_created.id");

        // Get basket by id
        mvc.perform( MockMvcRequestBuilders
        .get("/basket/" + BasketsTests.basketId)
        .content(json)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andDo(MockMvcResultHandlers.print());

        // Update basket brick_1
        payload = new HashMap<>();
        payload.put("brick_id", BasketsTests.brickId_1);
        payload.put("brick_quantity", 5);
        objectMapper = new ObjectMapper();
        json = objectMapper.writeValueAsString(payload);

        mvc.perform( MockMvcRequestBuilders
        .put("/basket/" + BasketsTests.basketId)
        .content(json)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andDo(MockMvcResultHandlers.print());

        // Update basket brick_2
        payload = new HashMap<>();
        payload.put("brick_id", BasketsTests.brickId_2);
        payload.put("brick_quantity", 10);
        objectMapper = new ObjectMapper();
        json = objectMapper.writeValueAsString(payload);

        mvc.perform( MockMvcRequestBuilders
        .put("/basket/" + BasketsTests.basketId)
        .content(json)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andDo(MockMvcResultHandlers.print());

        // Empty basket
        mvc.perform( MockMvcRequestBuilders
        .put("/basket/empty/" + BasketsTests.basketId)
        .content(json)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andDo(MockMvcResultHandlers.print());

        // Delete basket
        mvc.perform( MockMvcRequestBuilders
        .delete("/basket/" + BasketsTests.basketId)
        .content(json)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andDo(MockMvcResultHandlers.print());

    }

    private void initTestEnv() throws Exception {
        // Load /register and /authenticate JSON
        Map<String, String> payload_user = new HashMap<String, String>();
        payload_user.put("username", "usr_admin");
        payload_user.put("password", "usr_admin_pwd");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(payload_user);

        // Register 
        mvc.perform( MockMvcRequestBuilders
        .post("/register")
        .content(json)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isCreated());

        // Authenticate
        MvcResult result = mvc.perform( MockMvcRequestBuilders
        .post("/authenticate")
        .content(json)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andDo(MockMvcResultHandlers.print())
        .andReturn();


        // Get auth token
        String resultString = JsonPath.read(result.getResponse().getContentAsString(), "$.token");

        BasketsTests.token = "Bearer " + resultString;

        // Me (to get user_id)
        result = mvc.perform( MockMvcRequestBuilders
        .get("/me")
        .content(json)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .header("Authorization", BasketsTests.token)
        .header("Content-Type", "application/json"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn();

        // Get user_id
        BasketsTests.userId = JsonPath.read(result.getResponse().getContentAsString(), "$.id");

        // Load /brick JSON
        Map<String, Object> payload_brick = new HashMap<String, Object>();
        payload_brick.put("brick_name","basketTestsBrick_1");
        payload_brick.put("brick_dim_h", 25);
        payload_brick.put("brick_dim_l", 25);
        payload_brick.put("brick_dim_w", 25);
        payload_brick.put("brick_price", 40);
        payload_brick.put("brick_quantity", 10);
        payload_brick.put("brick_image_url", "http://localhost:8090/images/basketTestsBrick_1.png");
        objectMapper = new ObjectMapper();
        json = objectMapper.writeValueAsString(payload_brick);
        objectMapper = new ObjectMapper();
        json = objectMapper.writeValueAsString(payload_brick);

        // Create brick_1
        result = mvc.perform( MockMvcRequestBuilders
        .post("/brick")
        .content(json)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andDo(MockMvcResultHandlers.print())
        .andReturn();
        
        BasketsTests.brickId_1 = JsonPath.read(result.getResponse().getContentAsString(), "$.brick_created.id");

        // Load /brick JSON
        payload_brick = new HashMap<String, Object>();
        payload_brick.put("brick_name","basketTestsBrick_2");
        payload_brick.put("brick_dim_h", 25);
        payload_brick.put("brick_dim_l", 25);
        payload_brick.put("brick_dim_w", 25);
        payload_brick.put("brick_price", 400);
        payload_brick.put("brick_quantity", 1000);
        payload_brick.put("brick_image_url", "http://localhost:8090/images/basketTestsBrick_2.png");
        objectMapper = new ObjectMapper();
        json = objectMapper.writeValueAsString(payload_brick);
        objectMapper = new ObjectMapper();
        json = objectMapper.writeValueAsString(payload_brick);

        // Create brick_2
        result = mvc.perform( MockMvcRequestBuilders
        .post("/brick")
        .content(json)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andDo(MockMvcResultHandlers.print())
        .andReturn();
        
        BasketsTests.brickId_2 = JsonPath.read(result.getResponse().getContentAsString(), "$.brick_created.id");

    }
    
}
