package football;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.PostgreSQLContainer;

public abstract class DatabaseBaseTest {

//    static final MySQLContainer DATABASE = new MySQLContainer("mysql:latest").
//            withDatabaseName("test").
//            withUsername("hakan").
//            withPassword("123");
    static final PostgreSQLContainer DATABASE = new PostgreSQLContainer ("postgres:15.1-alpine").
            withDatabaseName("test").
            withUsername("hakan").
            withPassword("123");

    static {
        DATABASE.start();
    }

    @DynamicPropertySource
    static void databaseProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", DATABASE::getJdbcUrl);
        registry.add("spring.datasource.username", DATABASE::getUsername);
        registry.add("spring.datasource.password", DATABASE::getPassword);
//        registry.add("spring.datasource.driverClassName", ()-> "com.mysql.cj.jdbc.Driver");
//        registry.add("spring.jpa.database-platform", () -> "org.hibernate.dialect.MySQL8Dialect");
    }
}
