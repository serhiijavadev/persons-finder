package com.persons.finder.config

import org.springframework.context.annotation.Configuration
import org.testcontainers.containers.PostgreSQLContainer
import javax.annotation.PreDestroy

@Configuration
class TestContainersConfig {

    companion object {
        private val postgresContainer = PostgreSQLContainer<Nothing>("postgres:15").apply {
            withDatabaseName("testdb")
            withUsername("test")
            withPassword("test")
            start()
        }

        init {
            // Set system properties for Spring to pick up
            System.setProperty("spring.datasource.url", postgresContainer.jdbcUrl)
            System.setProperty("spring.datasource.username", postgresContainer.username)
            System.setProperty("spring.datasource.password", postgresContainer.password)
            System.setProperty("spring.datasource.driver-class-name", "org.postgresql.Driver")
        }
    }

    @PreDestroy
    fun cleanup() {
        postgresContainer.stop()
    }
}
