package com.project_agh.payrollmanagementsystem.repositories.jdbc;

import com.project_agh.payrollmanagementsystem.entities.Position;
import com.project_agh.payrollmanagementsystem.repositories.PositionRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JDBC implementation of {@link PositionRepository}.
 * <p>
 * Provides methods to retrieve {@link Position} entities
 * using Spring's {@link JdbcTemplate}.
 * </p>
 */
@Repository
public class JdbcPositionRepository implements PositionRepository {

    private static final String FIND_ALL_FULL_SQL =
            "SELECT id_stanowisko, nazwa, opis FROM stanowisko";

    private static final String CREATE_NEW_POSITION =
            "INSERT INTO stanowisko (nazwa, opis) VALUES (?, ?)";


    private static final String DELETE_POSITION =
            "DELETE FROM  stanowisko \n" +
                    "WHERE id_stanowisko = ?\n";

    private static final String EDIT_POSITION=
            "UPDATE stanowisko SET " +
                    "nazwa = ?, " +
                    "opis = ? " +

                    "WHERE id_stanowisko = ?";

    private final JdbcTemplate jdbcTemplate;

    /**
     * Constructs a new {@link JdbcPositionRepository} with the given {@link JdbcTemplate}.
     *
     * @param jdbcTemplate the {@link JdbcTemplate} used for database operations
     */
    public JdbcPositionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Position> positionRowMapper = (rs, rowNum) -> {
        Position position = new Position();
        position.setId(rs.getLong("id_stanowisko"));
        position.setName(rs.getString("nazwa"));
        position.setDescription(rs.getString("opis"));
        return position;
    };

    /**
     * Retrieves all positions from the database.
     *
     * @return a {@link List} of all {@link Position} entities
     */
    @Override
    public List<Position> findAll() {
        return jdbcTemplate.query(FIND_ALL_FULL_SQL, positionRowMapper);
    }

    @Override
    public void createPosition(String position_name, String position_desc) {
        int rowsAffected = jdbcTemplate.update(CREATE_NEW_POSITION, position_name, position_desc);

        if (rowsAffected != 1) {
            // Optional: handle the error if the row was not inserted
        }
    }

    @Override
    public void editPosition(Long id, String name, String description) {
        // ИСПОЛЬЗУЕМ jdbcTemplate.update() и убираем RowMapper
        int rowsAffected = jdbcTemplate.update(
                EDIT_POSITION,
                name,
                description,
                id
        );

        // rowsAffected содержит 1, если вставка прошла успешно
        if (rowsAffected != 1) {
        }
    }

    @Override
    public void deletePosition(Long id) {
        int rowsAffected = jdbcTemplate.update(
                DELETE_POSITION,
                id
        );

        if (rowsAffected != 1) {
        }
    }
}
