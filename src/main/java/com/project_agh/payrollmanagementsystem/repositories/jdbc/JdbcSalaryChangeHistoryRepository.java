package com.project_agh.payrollmanagementsystem.repositories.jdbc;

import com.project_agh.payrollmanagementsystem.entities.SalaryChangeHistory;
import com.project_agh.payrollmanagementsystem.entities.User;
import com.project_agh.payrollmanagementsystem.repositories.SalaryChangeHistoryRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Repository
public class JdbcSalaryChangeHistoryRepository implements SalaryChangeHistoryRepository {

    private static final String FIND_ALL_WITH_USERS_SQL =
            "SELECT h.*, " +
                    "       p.imie AS user_name, " +
                    "       p.nazwisko AS user_lastname, " +
                    "       p.email AS user_email " +
                    "FROM historia_zmian_wynagrodzen h " +
                    "JOIN pracownik p ON h.id_pracownik = p.id_pracownik " +
                    "ORDER BY h.data DESC";

    private static final String CHANGE_SALARY_SQL =
            "INSERT INTO historia_zmian_wynagrodzen (id_pracownik, stare_wynagr, nowe_wynagr, data, opis) VALUES (?, ?, ?, ?, ?)";

    private final JdbcTemplate jdbcTemplate;

    public JdbcSalaryChangeHistoryRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // --- ROW MAPPER ---
    private final RowMapper<SalaryChangeHistory> salaryChangeHistoryRowMapper = (rs, rowNum) -> {
        SalaryChangeHistory history = new SalaryChangeHistory();

        history.setId(rs.getLong("id_zmiany_wynagrodzenia"));
        history.setOldSalary(rs.getBigDecimal("stare_wynagr"));
        history.setNewSalary(rs.getBigDecimal("nowe_wynagr"));

        Date sqlDate = rs.getDate("data");
        if (sqlDate != null) {
            history.setDate(sqlDate.toLocalDate());
        }

        history.setDescription(rs.getString("opis"));

        User user = new User();
        user.setId(rs.getLong("id_pracownik"));
        user.setName(rs.getString("user_name"));
        user.setLastname(rs.getString("user_lastname"));
        user.setEmail(rs.getString("user_email"));

        history.setUser(user);

        return history;
    };


    @Override
    public void changeSalary(Long userId, BigDecimal oldSalary, BigDecimal newSalary, LocalDate date) {
        jdbcTemplate.update(
                CHANGE_SALARY_SQL,
                userId,
                oldSalary,
                newSalary,
                date,
                null
        );
    }

    @Override
    public List<SalaryChangeHistory> findAll() {
        return jdbcTemplate.query(FIND_ALL_WITH_USERS_SQL, salaryChangeHistoryRowMapper);
    }
}