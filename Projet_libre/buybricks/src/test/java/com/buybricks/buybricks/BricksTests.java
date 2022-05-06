package com.buybricks.buybricks;

import java.util.HashMap;
import java.util.Map;

// import com.buybricks.app.service.BrickService;
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
public class BricksTests {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testCreateGetUpdateDelete() throws Exception {
        // Load JSON
        Map<String,Object> payload = new HashMap<>();
        payload.put("brick_name","bradpitt");
        payload.put("brick_dim_h", 25);
        payload.put("brick_dim_l", 25);
        payload.put("brick_dim_w", 25);
        payload.put("brick_price", 4000);
        payload.put("brick_quantity", 10);
        payload.put("brick_image_url", "http://localhost:8090/images/bradpitt.png");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(payload);

        // Create brick
        MvcResult result = mvc.perform( MockMvcRequestBuilders
        .post("/brick")
        .content(json)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andDo(MockMvcResultHandlers.print())
        .andReturn();

        int brickId = JsonPath.read(result.getResponse().getContentAsString(), "$.brick_created.id");
        
        // Check for that brick by id
        mvc.perform( MockMvcRequestBuilders
        .get("/brick/" + brickId)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andDo(MockMvcResultHandlers.print());

        String brickName = JsonPath.read(result.getResponse().getContentAsString(), "$.brick_created.name");
        int brickPrice = JsonPath.read(result.getResponse().getContentAsString(), "$.brick_created.price");
        int brickDimensionH = JsonPath.read(result.getResponse().getContentAsString(), "$.brick_created.dimH");
        int brickQuantity = JsonPath.read(result.getResponse().getContentAsString(), "$.brick_created.quantity");

        // Check for that brick by name
        payload = new HashMap<>();
        payload.put("brick_name", brickName);
        objectMapper = new ObjectMapper();
        json = objectMapper.writeValueAsString(payload);

        mvc.perform( MockMvcRequestBuilders
        .get("/brick/name")
        .content(json)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andDo(MockMvcResultHandlers.print());

        // Check for that brick by price
        payload = new HashMap<>();
        payload.put("brick_price_min", Long.valueOf(brickPrice - 1));
        payload.put("brick_price_max", Long.valueOf(brickPrice + 1));
        objectMapper = new ObjectMapper();
        json = objectMapper.writeValueAsString(payload);

        mvc.perform( MockMvcRequestBuilders
        .get("/brick/price")
        .content(json)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andDo(MockMvcResultHandlers.print());

        // Check for that brick by dimension
        payload = new HashMap<>();
        payload.put("brick_dimension", brickDimensionH);
        objectMapper = new ObjectMapper();
        json = objectMapper.writeValueAsString(payload);

        mvc.perform( MockMvcRequestBuilders
        .get("/brick/dimension")
        .content(json)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andDo(MockMvcResultHandlers.print());

        // Update that brick
        payload = new HashMap<>();
        payload.put("brick_price", brickPrice + 20);
        payload.put("brick_quantity", brickQuantity + 20);
        objectMapper = new ObjectMapper();
        json = objectMapper.writeValueAsString(payload);

        mvc.perform( MockMvcRequestBuilders
        .put("/brick/" + brickId)
        .content(json)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andDo(MockMvcResultHandlers.print());

        // Delete that brick
        mvc.perform( MockMvcRequestBuilders
        .delete("/brick/" + brickId)
        .content(json)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andDo(MockMvcResultHandlers.print());
    }
    
}
