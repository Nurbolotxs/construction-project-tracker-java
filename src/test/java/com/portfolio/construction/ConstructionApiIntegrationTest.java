
package com.portfolio.construction;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ConstructionApiIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void missingProjectReturns404() throws Exception {
        mvc.perform(get("/api/projects/99999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void emptyProjectNameReturns400() throws Exception {
        mvc.perform(post("/api/projects")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {"name": ""}
                    """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createProjectReturns201() throws Exception {
        String json = mapper.writeValueAsString(Map.of(
                "name", "Test Construction Site",
                "description", "Integration test"
        ));

        mvc.perform(post("/api/projects")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name")
                        .value("Test Construction Site"))
                .andExpect(jsonPath("$.progressPercent").value(0));
    }

    @Test
    void createEmployeeReturns201() throws Exception {
        String json = mapper.writeValueAsString(Map.of(
                "name", "Test Employee",
                "email", "test.employee@example.com",
                "role", "Engineer"
        ));

        mvc.perform(post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Test Employee"));
    }

    @Test
    void deleteProjectReturns204AndThen404() throws Exception {
        String json = """
            {
              "name": "Temporary Test Project",
              "description": "Project for deletion test",
              "status": "PLANNED",
              "startDate": "2026-11-10",
              "deadline": "2027-01-01"
            }
            """;

        String response = mvc.perform(post("/api/projects")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        long id = mapper.readTree(response).get("id").asLong();

        mvc.perform(delete("/api/projects/" + id))
                .andExpect(status().isNoContent());

        mvc.perform(get("/api/projects/" + id))
                .andExpect(status().isNotFound());
    }

    @Test
    void projectProgressUpdatesFrom0To100() throws Exception {

        // Step 1: Create a project
        String projectJson = """
            {
              "name": "Progress Test Project",
              "description": "Testing automatic progress",
              "status": "PLANNED",
              "startDate": "2026-11-10",
              "deadline": "2027-06-30"
            }
            """;

        String projectResponse = mvc.perform(post("/api/projects")
                .contentType(MediaType.APPLICATION_JSON)
                .content(projectJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.progressPercent").value(0))
                .andReturn()
                .getResponse()
                .getContentAsString();

        long projectId = mapper.readTree(projectResponse)
                .get("id").asLong();

        // Step 2: Create a TODO task
        String taskJson = """
            {
              "title": "Prepare construction site",
              "description": "Prepare the land",
              "status": "TODO",
              "priority": "HIGH",
              "dueDate": "2026-12-01"
            }
            """;

        String taskResponse = mvc.perform(
                post("/api/projects/" + projectId + "/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(taskJson))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        long taskId = mapper.readTree(taskResponse)
                .get("id").asLong();

        // Step 3: Verify progress is 0%
        mvc.perform(get("/api/projects/" + projectId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.progressPercent").value(0))
                .andExpect(jsonPath("$.taskCount").value(1))
                .andExpect(jsonPath("$.completedTasks").value(0));

        // Step 4: Mark task as DONE
        String completedTaskJson = """
            {
              "title": "Prepare construction site",
              "description": "Prepare the land",
              "status": "DONE",
              "priority": "HIGH",
              "dueDate": "2026-12-01"
            }
            """;

        mvc.perform(put("/api/projects/" + projectId
                + "/tasks/" + taskId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(completedTaskJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("DONE"));

        // Step 5: Verify progress is 100%
        mvc.perform(get("/api/projects/" + projectId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.progressPercent").value(100))
                .andExpect(jsonPath("$.taskCount").value(1))
                .andExpect(jsonPath("$.completedTasks").value(1));
    }
}
