package com.persons.finder

import com.persons.finder.config.TestContainersConfig
import javax.persistence.EntityManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Import(TestContainersConfig::class)
abstract class ParentIT {

    @Autowired
    protected lateinit var mockMvc: MockMvc

    @Autowired
    protected lateinit var entityManager: EntityManager

    protected val JSON_DATA_PREFIX_PATH = "datasets/json/%s/"

    protected fun getJsonDataPrefixPath(): String {
        return String.format(JSON_DATA_PREFIX_PATH, this.javaClass.simpleName)
    }

    @Throws(IOException::class)
    protected fun readFromFile(path: String): String {
        return Files.readString(Paths.get("src/test/resources/$path"))
    }
}
