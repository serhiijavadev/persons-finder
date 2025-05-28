package com.persons.finder.presentation

import com.persons.finder.ParentIT
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson

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
                ))
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
                ))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andReturn()

        val content = mvcResult.getResponse().getContentAsString()
        val expected: String? = readFromFile(getJsonDataPrefixPath() + "response-createPerson-empty-name.json")

        assertThatJson(content).isEqualTo(expected)
    }

}