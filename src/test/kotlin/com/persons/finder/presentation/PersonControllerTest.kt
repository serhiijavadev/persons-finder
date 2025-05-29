package com.persons.finder.presentation

import com.persons.finder.domain.services.PersonsService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(PersonController::class)
class PersonControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var personsService: PersonsService

    @Test
    fun `should return 400 for invalid coordinates`() {
        mockMvc.perform(
            MockMvcRequestBuilders.put("/api/v1/persons/1/location")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(
                    """
                                {
                                  "latitude": -300.40,
                                  "longitude": 500.18
                                }
                                """.trimIndent()
                )
        )
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.longitude").value("Invalid longitude: must be between -180.0 and 180.0"))
            .andExpect(jsonPath("$.latitude").value("Invalid latitude: must be between -90.0 and 90.0"))
    }
}
