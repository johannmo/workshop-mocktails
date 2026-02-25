package no.digdir.workshop.plain.repository;

import no.digdir.workshop.plain.model.Mocktail;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class MocktailRepository {
    private final JdbcTemplate jdbcTemplate;

    public MocktailRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void createTable() {
        jdbcTemplate.execute("""
                    CREATE TABLE IF NOT EXISTS mocktail (
                        id IDENTITY PRIMARY KEY,
                        name VARCHAR(255),
                        ingredients VARCHAR(1024)
                    )
                """);
    }

    public void save(Mocktail mocktail) {
        jdbcTemplate.update("INSERT INTO mocktail (name, ingredients) VALUES (?, ?)",
                mocktail.getName(), String.join(",", mocktail.getIngredients()));
    }

    public List<Mocktail> findAll() {
        return jdbcTemplate.query("SELECT name, ingredients FROM mocktail",
                (rs, row) -> new Mocktail(
                        rs.getString("name"),
                        List.of(rs.getString("ingredients").split(","))
                ));
    }

}

