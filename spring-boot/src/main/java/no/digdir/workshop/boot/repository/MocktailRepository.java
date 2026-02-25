package no.digdir.workshop.boot.repository;

import jakarta.annotation.PostConstruct;
import no.digdir.workshop.boot.model.Mocktail;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class MocktailRepository {
    private final JdbcTemplate jdbc;

    public MocktailRepository(DataSource dataSource) {
        this.jdbc = new JdbcTemplate(dataSource);
    }

    @PostConstruct
    public void createTable() {
        jdbc.execute("""
                    CREATE TABLE IF NOT EXISTS mocktail (
                        id IDENTITY PRIMARY KEY,
                        name VARCHAR(255),
                        ingredients VARCHAR(1024)
                    )
                """);
    }

    public void save(Mocktail mocktail) {
        jdbc.update("INSERT INTO mocktail (name, ingredients) VALUES (?, ?)",
                mocktail.getName(), String.join(",", mocktail.getIngredients()));
    }

    public List<Mocktail> findAll() {
        return jdbc.query("SELECT name, ingredients FROM mocktail",
                (rs, row) -> new Mocktail(
                        rs.getString("name"),
                        List.of(rs.getString("ingredients").split(","))
                ));
    }
}
