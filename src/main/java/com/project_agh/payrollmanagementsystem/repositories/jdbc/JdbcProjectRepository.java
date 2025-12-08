package com.project_agh.payrollmanagementsystem.repositories.jdbc;
import com.project_agh.payrollmanagementsystem.entities.Project;
import com.project_agh.payrollmanagementsystem.repositories.ProjectRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Repository
public class JdbcProjectRepository implements ProjectRepository {
    private static final String FIND_ALL_FULL_SQL =
            "SELECT * FROM projekt";

    private static final String CREATE_NEW_PROJECT =
            "INSERT INTO projekt (nazwa, opis, data_rozpoczecia, data_zakonczenia) VALUES (?, ?, ?, ?)";

    private static final String DELETE_PROJECT =
            "DELETE FROM  projekt \n" +
                    "WHERE id_projekt = ?\n";

    private static final String EDIT_PROJECT=
            "UPDATE projekt SET " +
                    "nazwa = ?, " +
                    "opis = ?, " +
                    "data_rozpoczecia = ?, " +
                    "data_zakonczenia = ? " +
                    "WHERE id_projekt = ?";

    private final JdbcTemplate jdbcTemplate;

    public JdbcProjectRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Project> projectRowMapper = (rs, rowNum) -> {
        Project project = new Project();
        project.setId(rs.getLong("id_projekt"));
        project.setName(rs.getString("nazwa"));
        project.setDescription(rs.getString("opis"));

        Date projectSqlDate = rs.getDate("data_rozpoczecia");
        if (projectSqlDate != null) {
            project.setProjectBeginDate(projectSqlDate.toLocalDate());
        } else {
            project.setProjectBeginDate(LocalDate.MIN);
        }

        Date projectEndDate = rs.getDate("data_zakonczenia");
        if (projectEndDate != null) {
            project.setProjectEndDate(projectEndDate.toLocalDate());
        } else {
            project.setProjectEndDate(null);
        }


        return project;
    };

    @Override
    public List<Project> findAll() {
        return jdbcTemplate.query(FIND_ALL_FULL_SQL, projectRowMapper);
    }

    @Override
    public void createProject(String project_name, String project_desc, LocalDate project_start_date, LocalDate project_end_date) {
        int rowsAffected = jdbcTemplate.update(CREATE_NEW_PROJECT, project_name, project_desc, project_start_date, project_end_date );

        if (rowsAffected != 1) {
            // Optional: handle the error if the row was not inserted
        }
    }

    @Override
    public void deleteProject(Long id) {
        int rowsAffected = jdbcTemplate.update(
                DELETE_PROJECT,
                id
        );

        if (rowsAffected != 1) {
        }
    }

    @Override
    public void editProject(Long id, String name, String description, LocalDate project_start_date, LocalDate project_end_date) {
        int rowsAffected = jdbcTemplate.update(
                EDIT_PROJECT,
                name,
                description,
                project_start_date,
                project_end_date,
                id
        );

        if (rowsAffected != 1) {
        }
    }


}
