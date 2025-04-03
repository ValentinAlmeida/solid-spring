package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class DatabaseConnectionTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void testDatabaseConnection() {
        assertNotNull(dataSource, "DataSource não deve ser nulo");
        
        try (Connection connection = dataSource.getConnection()) {
            assertTrue(connection.isValid(2), "Conexão deve ser válida");
            assertFalse(connection.isClosed(), "Conexão não deve estar fechada");
            
            String result = jdbcTemplate.queryForObject("SELECT version()", String.class);
            assertNotNull(result, "Deve retornar a versão do PostgreSQL");
            assertTrue(result.contains("PostgreSQL"), "Deve conter 'PostgreSQL' no resultado");
            
            System.out.println("Conexão bem-sucedida! Versão do PostgreSQL: " + result);
        } catch (SQLException e) {
            fail("Falha ao conectar ao banco de dados: " + e.getMessage());
        }
    }

    @Test
    public void testDatabaseSchema() {
        try {
            jdbcTemplate.execute("SELECT 1 FROM pg_tables WHERE schemaname = 'public'");
            System.out.println("Schema público acessível");
        } catch (Exception e) {
            fail("Falha ao acessar o schema: " + e.getMessage());
        }
    }
}