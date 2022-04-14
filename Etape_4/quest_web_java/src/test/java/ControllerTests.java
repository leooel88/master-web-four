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

import javax.annotation.PostConstruct;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quest.etna.repositories.AddressRepository;
import com.quest.etna.repositories.UserRepository;

@SpringBootTest(classes={com.quest.quest_web_java.QuestWebJavaApplication.class})
@AutoConfigureMockMvc
public class ControllerTests {

    private static String token = "";

    @Autowired
    private MockMvc mvc;

    private static UserRepository userRepositoryTest;
    private static AddressRepository addressRepositoryTest;
    
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private AddressRepository addressRepo;

    @PostConstruct
    private void init() {
        userRepositoryTest = this.userRepo;
        addressRepositoryTest = this.addressRepo;
    }

    @Test
    public void testAuthenticate() throws Exception {

        //identifiant de l'utilisateur

        Map<String,String> payload = new HashMap<>();
        payload.put("username","user1");
        payload.put("password","passworduser1");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(payload);

        //enregistrer l'utilisateur

        mvc.perform( MockMvcRequestBuilders
        .post("/register")
        .content(json)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isCreated());

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

        ControllerTests.token = "Bearer " + resultString;

        // tester le chemin /me avec le token

        mvc.perform( MockMvcRequestBuilders
        .get("/me")
        .content(json)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .header("Authorization", ControllerTests.token)
        .header("Content-Type", "application/json"))
        .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void testUser() throws Exception {
        Map<String,String> payload = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = "";
        String resultString = "";

        // Load user2(ADMIN) credentials
        payload = new HashMap<>();
        payload.put("username","user2");
        payload.put("password","passworduser2");
        objectMapper = new ObjectMapper();
        json = objectMapper.writeValueAsString(payload);

        // Register user2(ADMIN)
        mvc.perform( MockMvcRequestBuilders
        .post("/register")
        .content(json)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON));

        // Authenticate user2(ADMIN)
        ResultActions result = mvc.perform( MockMvcRequestBuilders
        .post("/authenticate")
        .content(json)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk());

        // Retrieve user2(ADMIN)'s token
        resultString = result.andReturn().getResponse().getContentAsString();
        ControllerTests.token = "Bearer " + resultString;

        // Load user2(ADMIN)'s modification
        payload = new HashMap<>();
        payload.put("role","ROLE_ADMIN");
        objectMapper = new ObjectMapper();
        json = objectMapper.writeValueAsString(payload);

        // Get user1(USER) and user2(ADMIN)'s id
        int user1Id = userRepositoryTest.findFirstByUsernameIgnoreCase("user1").getId();
        int user2Id = userRepositoryTest.findFirstByUsernameIgnoreCase("user2").getId();

        // Update user2(ADMIN) to ROLE_ADMIN
        mvc.perform( MockMvcRequestBuilders
        .put("/user/" + user2Id)
        .content(json)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk());

        // 1) (GET)/user without token
        mvc.perform( MockMvcRequestBuilders
        .get("/user")
        .header("Authorization", "Bearer ")
        .header("Content-Type", "application/json"))
        .andExpect(MockMvcResultMatchers.status().isUnauthorized());

        // Load user1(USER) credentials
        payload = new HashMap<>();
        payload.put("username","user1");
        payload.put("password","passworduser1");
        objectMapper = new ObjectMapper();
        json = objectMapper.writeValueAsString(payload);

        // Authenticate user1(USER)
        result = mvc.perform( MockMvcRequestBuilders
        .post("/authenticate")
        .content(json)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON));

        // Retrieve user1(USER)'s token
        resultString = result.andReturn().getResponse().getContentAsString();
        ControllerTests.token = "Bearer " + resultString;

        // 2) (GET)/user with valid token
        mvc.perform( MockMvcRequestBuilders
        .get("/user")
        .header("Authorization", ControllerTests.token)
        .header("Content-Type", "application/json"))
        .andExpect(MockMvcResultMatchers.status().isOk());

        // 3) (DELETE)/user as user1(USER)
        mvc.perform( MockMvcRequestBuilders
        .delete("/user/" + user2Id)
        .header("Authorization", ControllerTests.token)
        .header("Content-Type", "application/json"))
        .andExpect(MockMvcResultMatchers.status().isForbidden());

        // Load user2(ADMIN) credentials
        payload = new HashMap<String, String>();
        payload.put("username","user2");
        payload.put("password","passworduser2");
        objectMapper = new ObjectMapper();
        json = null;
        json = objectMapper.writeValueAsString(payload);

        // Authenticate user2(ADMIN)
        result = mvc.perform( MockMvcRequestBuilders
        .post("/authenticate")
        .content(json)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON));

        // Retrieve user2(ADMIN)'s token
        resultString = result.andReturn().getResponse().getContentAsString();
        ControllerTests.token = "Bearer " + resultString;

        System.out.println(userRepositoryTest.findById(user2Id).get().getRole().toString());

        // 4) (DELETE)/user as user2(ADMIN)
        mvc.perform( MockMvcRequestBuilders
        .delete("/user/" + user1Id)
        .header("Authorization", ControllerTests.token)
        .header("Content-Type", "application/json"))
        .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testAddress() throws Exception {
        Map<String,String> payload = new HashMap<String,String>();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = "";
        String resultString = "";

        // Load address variables
        String street = "street user2";
        String postalCode = "postalCode user2";
        String city = "city user2";
        String country = "country user2";

        // Load user2(ADMIN)'s credentials
        payload = new HashMap<>();
        payload.put("username","user1");
        payload.put("password","passworduser1");
        objectMapper = new ObjectMapper();
        json = objectMapper.writeValueAsString(payload);

        // Authenticate user2(ADMIN)
        ResultActions result = mvc.perform( MockMvcRequestBuilders
        .post("/authenticate")
        .content(json)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk());

        // Retrieve user2(ADMIN)'s token
        resultString = result.andReturn().getResponse().getContentAsString();
        ControllerTests.token = "Bearer " + resultString;

        // Load user2(ADMIN)'s address's variables
        payload = new HashMap<>();
        payload.put("street", street);
        payload.put("postalCode", postalCode);
        payload.put("city", city);
        payload.put("country", country);
        objectMapper = new ObjectMapper();
        json = objectMapper.writeValueAsString(payload);

        // Create user2(ADMIN)'s address variables
        mvc.perform( MockMvcRequestBuilders
        .post("/address")
        .content(json)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .header("Authorization", ControllerTests.token));

        // Get user1(USER) and user2(ADMIN)'s address's id
        int address1Id = 0;
        int address2Id = addressRepositoryTest.findFirstSameAddress(street, postalCode, city, country).getId();

        // Load address variables
        street = "street user1";
        postalCode = "postalCode user1";
        city = "city user1";
        country = "country user1";
    
        // 1) (GET)/address without token
        mvc.perform( MockMvcRequestBuilders
        .get("/address")
        .header("Authorization", "Bearer ")
        .header("Content-Type", "application/json"))
        .andExpect(MockMvcResultMatchers.status().isUnauthorized());

        // Load user1(USER)'s credentials
        payload = new HashMap<>();
        payload.put("username","user1");
        payload.put("password","passworduser1");
        objectMapper = new ObjectMapper();
        json = objectMapper.writeValueAsString(payload);

        // Register user1(USER)
        mvc.perform( MockMvcRequestBuilders
        .post("/register")
        .content(json)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON));

        // Authenticate user1(USER)
        result = mvc.perform( MockMvcRequestBuilders
        .post("/authenticate")
        .content(json)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON));

        // Retrieve user1(USER)'s token
        resultString = result.andReturn().getResponse().getContentAsString();
        ControllerTests.token = "Bearer " + resultString;

        // 2) (GET)/address with valid token
        mvc.perform( MockMvcRequestBuilders
        .get("/address")
        .header("Authorization", ControllerTests.token)
        .header("Content-Type", "application/json"))
        .andExpect(MockMvcResultMatchers.status().isOk());
        
        // Load user1(USER) credentials
        payload = new HashMap<>();
        payload.put("username","user1");
        payload.put("password","passworduser1");
        objectMapper = new ObjectMapper();
        json = objectMapper.writeValueAsString(payload);

        // Authenticate user1(USER)
        result = mvc.perform( MockMvcRequestBuilders
        .post("/authenticate")
        .content(json)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON));

        // Retrieve user1(USER)'s token
        resultString = result.andReturn().getResponse().getContentAsString();
        ControllerTests.token = "Bearer " + resultString;
        
        // Load user1's address
        payload.put("street", street);
        payload.put("postalCode", postalCode);
        payload.put("city", city);
        payload.put("country", country);
        objectMapper = new ObjectMapper();
        json = objectMapper.writeValueAsString(payload);

        // 3) (POST)/address with valid token and valid body
        mvc.perform( MockMvcRequestBuilders
        .post("/address")
        .content(json)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .header("Authorization", ControllerTests.token))
        .andExpect(MockMvcResultMatchers.status().isCreated());

        // Retrieve user1(USER)'s address's id
        address1Id = addressRepositoryTest.findFirstSameAddress(street, postalCode, city, country).getId();

        // 4) (DELETE)/address from user2(ADMIN) with user1(USER)
        mvc.perform(MockMvcRequestBuilders
        .delete("/address/" + address2Id)
        .header("Authorization", ControllerTests.token)
        .header("Content-Type", "application/json"))
        .andExpect(MockMvcResultMatchers.status().isForbidden());

        // Load user2(ADMIN)'s credentials
        payload = new HashMap<String, String>();
        payload.put("username","user2");
        payload.put("password","passworduser2");
        objectMapper = new ObjectMapper();
        json = null;
        json = objectMapper.writeValueAsString(payload);

        // Authenticate user2(ADMIN)
        result = mvc.perform( MockMvcRequestBuilders
        .post("/authenticate")
        .content(json)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON));

        // Returns token
        resultString = result.andReturn().getResponse().getContentAsString();
        ControllerTests.token = "Bearer " + resultString;

        // 5) (DELETE)/user from user1(USER) with user2(ADMIN)
        mvc.perform( MockMvcRequestBuilders
        .delete("/address/" + address1Id)
        .header("Authorization", ControllerTests.token)
        .header("Content-Type", "application/json"))
        .andExpect(MockMvcResultMatchers.status().isOk());

        // Get user1(USER) and user2(ADMIN)'s id
        int user1Id = userRepositoryTest.findFirstByUsernameIgnoreCase("user1").getId();
        int user2Id = userRepositoryTest.findFirstByUsernameIgnoreCase("user2").getId();

        // Reset database
        mvc.perform( MockMvcRequestBuilders
        .delete("/address/" + address1Id)
        .header("Authorization", ControllerTests.token)
        .header("Content-Type", "application/json"));

        mvc.perform( MockMvcRequestBuilders
        .delete("/address/" + address1Id)
        .header("Authorization", ControllerTests.token)
        .header("Content-Type", "application/json"));

        mvc.perform( MockMvcRequestBuilders
        .delete("/user/" + user1Id)
        .header("Authorization", ControllerTests.token)
        .header("Content-Type", "application/json"));

        mvc.perform( MockMvcRequestBuilders
        .delete("/user/" + user2Id)
        .header("Authorization", ControllerTests.token)
        .header("Content-Type", "application/json"));

    }
}
