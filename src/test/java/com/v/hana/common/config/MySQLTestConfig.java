package com.v.hana.common.config;

import com.v.hana.common.annotation.MethodInfo;
import com.v.hana.common.annotation.TypeInfo;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@TypeInfo(name = "MySQLTestConfig", description = "MySQL 테스트 설정 클래스")
@Configuration
@Testcontainers // Testcontainers 프레임워크 지원
public class MySQLTestConfig implements BeforeAllCallback {
    @Container
    private static final GenericContainer<?> mySQLContainer =
            new GenericContainer<>(DockerImageName.parse("mysql:8.0"))
                    .withEnv("MYSQL_DATABASE", "test_db")
                    .withEnv("MYSQL_ROOT_USER", "root_user")
                    .withEnv("MYSQL_ROOT_PASSWORD", "root_pass")
                    .withEnv("MYSQL_USER", "test_user")
                    .withEnv("MYSQL_PASSWORD", "test_pass")
                    .withExposedPorts(3306);

    @MethodInfo(name = "beforeAll", description = "테스트 실행 전 MySQL 컨테이너를 실행합니다.")
    @Override
    public void beforeAll(ExtensionContext context) {
        if (!mySQLContainer.isRunning()) {
            mySQLContainer.start();
        }

        configureDataSourceProperties();
        createTestTable();
    }

    @MethodInfo(name = "configureDataSourceProperties", description = "데이터 소스를 설정합니다.")
    private void configureDataSourceProperties() {
        System.setProperty(
                "spring.datasource.url",
                "jdbc:mysql://"
                        + mySQLContainer.getHost()
                        + ":"
                        + mySQLContainer.getMappedPort(3306)
                        + "/test_db");
        System.setProperty("spring.datasource.username", "test_user");
        System.setProperty("spring.datasource.password", "test_pass");
    }

    @MethodInfo(name = "createTestTable", description = "테스트 테이블을 생성합니다.")
    private void createTestTable() {
        List<String> sqlStatements = readSqlFile();

        JdbcTemplate jdbcTemplate = new JdbcTemplate(buildDataSource());
        for (String sqlStatement : sqlStatements) {
            jdbcTemplate.execute(sqlStatement);
        }
    }

    @MethodInfo(name = "MySQLTestConfig.buildDataSource", description = "데이터 소스를 생성합니다.")
    private DataSource buildDataSource() {
        org.springframework.jdbc.datasource.DriverManagerDataSource dataSource =
                new org.springframework.jdbc.datasource.DriverManagerDataSource();
        dataSource.setUrl(System.getProperty("spring.datasource.url"));
        dataSource.setUsername(System.getProperty("spring.datasource.username"));
        dataSource.setPassword(System.getProperty("spring.datasource.password"));
        return dataSource;
    }

    @MethodInfo(
            name = "MySQLTestConfig.readSqlFile",
            description = "SQL 파일을 읽어 이를 여러 개의 SQL 문으로 분리합니다.")
    private List<String> readSqlFile() {
        try {
            ClassPathResource classPathResource = new ClassPathResource("db.sql");
            InputStream inputStream = classPathResource.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            List<String> sqlStatements = new ArrayList<>();
            StringBuilder currentStatement = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.trim().endsWith(";")) {
                    currentStatement.append(line).append("\n");
                    sqlStatements.add(currentStatement.toString().trim());
                    currentStatement.setLength(0);
                } else {
                    currentStatement.append(line).append("\n");
                }
            }

            return sqlStatements;
        } catch (IOException ioException) {
            throw new RuntimeException("Failed to read SQL file: " + "db.sql", ioException);
        }
    }
}
