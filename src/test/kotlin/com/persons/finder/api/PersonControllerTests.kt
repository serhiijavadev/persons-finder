package com.persons.finder.api

import com.persons.finder.ParentIT
import net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
class PersonControllerTests : ParentIT() {

    @Test
    @Sql(scripts = ["classpath:datasets/sql/purge.sql"])
    fun `should create a person`() {
        val mvcResult = mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/persons")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(
                    """
                                {
                                  "name": "John Doe"
                                }
                                """.trimIndent()
                )
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andReturn()

        val content = mvcResult.getResponse().getContentAsString()
        val expected: String? = readFromFile(getJsonDataPrefixPath() + "response-createPerson.json")

        assertThatJson(content).isEqualTo(expected)
    }

    @Test
    @Sql(scripts = ["classpath:datasets/sql/purge.sql"])
    fun `should validate empty person name`() {
        val mvcResult = mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/persons")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(
                    """
                                {
                                  "name": ""
                                }
                                """.trimIndent()
                )
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andReturn()

        val content = mvcResult.getResponse().getContentAsString()
        val expected: String? = readFromFile(getJsonDataPrefixPath() + "response-createPerson-empty-name.json")

        assertThatJson(content).isEqualTo(expected)
    }

    @Test
    @Sql(scripts = ["classpath:datasets/sql/purge.sql", "classpath:datasets/sql/PersonControllerTests/insert-persons.sql"])
    fun `should get persons by ids`() {
        val mvcResult = mockMvc.perform(
            MockMvcRequestBuilders.get("/api/v1/persons?id=1&id=2&id=3")
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andReturn()

        val content = mvcResult.getResponse().getContentAsString()
        val expected: String? = readFromFile(getJsonDataPrefixPath() + "response-getPersonsByIds.json")

        assertThatJson(content).isEqualTo(expected)
    }

    @Test
    @Sql(scripts = ["classpath:datasets/sql/purge.sql", "classpath:datasets/sql/PersonControllerTests/insert-persons.sql"])
    fun `should get a person by id`() {
        val mvcResult = mockMvc.perform(
            MockMvcRequestBuilders.get("/api/v1/persons/1")
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andReturn()

        val content = mvcResult.getResponse().getContentAsString()
        val expected: String? = readFromFile(getJsonDataPrefixPath() + "response-getPersonById.json")

        assertThatJson(content).isEqualTo(expected)
    }

    @Test
    @Sql(scripts = ["classpath:datasets/sql/purge.sql"])
    fun `should return not found when getting non-existent person`() {
        mockMvc.perform(get("/persons/1000"))
            .andExpect(status().isNotFound)
    }

    @Test
    @Sql(scripts = ["classpath:datasets/sql/purge.sql", "classpath:datasets/sql/PersonControllerTests/insert-person-no-location.sql"])
    fun `should create person's location if not exist`() {
        val mvcResult = mockMvc.perform(
            MockMvcRequestBuilders.put("/api/v1/persons/1/location")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(
                    """
                                {
                                  "latitude": -8.4095,
                                  "longitude": 115.1889
                                }
                                """.trimIndent()
                )
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andReturn()

        val content = mvcResult.getResponse().getContentAsString()
        val expected: String? = readFromFile(getJsonDataPrefixPath() + "response-addLocation.json")

        assertThatJson(content).isEqualTo(expected)
    }

    @Test
    @Sql(scripts = ["classpath:datasets/sql/purge.sql", "classpath:datasets/sql/PersonControllerTests/insert-person-with-location.sql"])
    fun `should update an existing person's location`() {
        val mvcResult = mockMvc.perform(
            MockMvcRequestBuilders.put("/api/v1/persons/1/location")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(
                    """
                                {
                                  "latitude": -38.4095,
                                  "longitude": 155.1889
                                }
                                """.trimIndent()
                )
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andReturn()

        val content = mvcResult.getResponse().getContentAsString()
        val expected: String? = readFromFile(getJsonDataPrefixPath() + "response-updateLocation.json")

        assertThatJson(content).isEqualTo(expected)
    }

    @Test
    @Sql(scripts = ["classpath:datasets/sql/purge.sql"])
    fun `should return not found when updating location for non-existent person`() {
        mockMvc.perform(
            MockMvcRequestBuilders.put("/api/v1/persons/1000/location")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(
                    """
                                {
                                  "latitude": -8.4095,
                                  "longitude": 115.1889
                                }
                                """.trimIndent()
                )
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isNotFound)
    }

    @Test
    @Sql(scripts = ["classpath:datasets/sql/purge.sql", "classpath:datasets/sql/PersonControllerTests/insert-nearby.sql"])
    fun `should return only persons within 100km of Singapore`() {
        val mvcResult = mockMvc.perform(
            MockMvcRequestBuilders.get("/api/v1/persons/nearby?lat=1.3521&lon=103.8198&radiusKm=100")
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andReturn()

        val content = mvcResult.getResponse().getContentAsString()
        val expected: String? = readFromFile(getJsonDataPrefixPath() + "response-nearBy.json")

        assertThatJson(content).isEqualTo(expected)
    }

}