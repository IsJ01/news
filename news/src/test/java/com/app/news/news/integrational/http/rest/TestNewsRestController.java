package com.app.news.news.integrational.http.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.app.news.news.integrational.IntegrationTestBase;
import com.app.news.news.integrational.config.NewsTestConfiguration;
import com.app.news.news.util.JwtTestUtil;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@ContextConfiguration(classes = NewsTestConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestNewsRestController extends IntegrationTestBase {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtTestUtil jwtTestUtil;

    @Test
    public void testFailCreated() throws Exception {
        String token = jwtTestUtil.generateToken();
        mockMvc.perform(
            post("/api/v1/news")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {
                    \"title\": \"News1\",
                    \"description\": \"This is test news2\",
                    \"userId\": 2
                }
            """)
            .header("Authorization", "Bearer " + token)
        ).andExpect(status().is(403));
    }

    @Test
    public void testCreated() throws Exception {
        String token = jwtTestUtil.generateToken();
        mockMvc.perform(
            post("/api/v1/news")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {
                    \"title\": \"News1\",
                    \"description\": \"This is test news1\",
                    \"userId\": 1
                }
            """)
            .header("Authorization", "Bearer " + token)
        ).andExpect(status().isCreated());
    }
    
    @Test
    public void testDelete() throws Exception {
        String token = jwtTestUtil.generateToken();
        JSONParser parser = new JSONParser();

        var result = (JSONObject) parser.parse(
            mockMvc.perform(
                post("/api/v1/news")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        \"title\": \"News2\",
                        \"description\": \"This is test news2\",
                        \"userId\": 1
                    }
                """)
                .header("Authorization", "Bearer " + token)
            ).andExpect(status().is(201))
            .andReturn()
            .getResponse()
            .getContentAsString()
        );

        mockMvc.perform(
            delete(String.format("/api/v1/news/%s", result.get("id")))
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", "Bearer " + token)
        ).andExpect(status().is(204));
    }

    @Test
    public void testPut() throws Exception {
        String token = jwtTestUtil.generateToken();
        JSONParser parser = new JSONParser();

        var result = (JSONObject) parser.parse(
            mockMvc.perform(
                post("/api/v1/news")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        \"title\": \"News3\",
                        \"description\": \"This is test news3\",
                        \"userId\": 1
                    }
                """)
                .header("Authorization", "Bearer " + token)
            ).andExpect(status().is(201))
            .andReturn()
            .getResponse()
            .getContentAsString()
        );

        mockMvc.perform(
            put(String.format("/api/v1/news/%s", result.get("id")))
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {
                    "title": "News3_1",
                    "description": "This is test news3_1"
                }
            """)
            .header("Authorization", "Bearer " + token)
        ).andExpect(status().is(204));
        
        mockMvc.perform(
            get(String.format("/api/v1/public/news/%s", result.get("id")))
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", "Bearer " + token)
        )
        .andExpect(status().is(200))
        .andExpect(jsonPath(".title").value("News3_1"));
    }   

}
