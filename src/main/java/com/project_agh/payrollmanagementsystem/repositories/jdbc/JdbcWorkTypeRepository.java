package com.project_agh.payrollmanagementsystem.repositories.jdbc;

import com.project_agh.payrollmanagementsystem.entities.WorkType;
import com.project_agh.payrollmanagementsystem.repositories.WorkTypeRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcWorkTypeRepository implements WorkTypeRepository {
    private static final String FIND_ALL_FULL_SQL =
            "SELECT * FROM typ_pracy";

    private static final String CREATE_NEW_WORK_TYPE=
            "INSERT INTO typ_pracy (nazwa, opis) VALUES (?, ?)";

    private static final String DELETE_WORK_TYPE =
            "DELETE FROM  typ_pracy \n" +
                    "WHERE id_typ_pracy = ?\n";

    private static final String EDIT_WORK_TYPE=
            "UPDATE typ_pracy SET " +
                    "nazwa = ?, " +
                    "opis = ?" +
                    "WHERE id_typ_pracy = ?";


    private final JdbcTemplate jdbcTemplate;

    public JdbcWorkTypeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<WorkType> workTypeRowMapper = (rs, rowNum) -> {
        WorkType workType = new WorkType();
        workType.setId(rs.getLong("id_typ_pracy"));
        workType.setName(rs.getString("nazwa"));
        workType.setDescription(rs.getString("opis"));
        return workType;
    };

    @Override
    public List<WorkType> findAll() {
        return jdbcTemplate.query(FIND_ALL_FULL_SQL, workTypeRowMapper);
    }

    @Override
    public void createWorkType(String name, String description) {
        int rowsAffected = jdbcTemplate.update(CREATE_NEW_WORK_TYPE, name, description );

        if (rowsAffected != 1) {
            // Optional: handle the error if the row was not inserted
        }
    }

    @Override
    public void deleteWorkType(Long id) {
        int rowsAffected = jdbcTemplate.update(
                DELETE_WORK_TYPE,
                id
        );

        if (rowsAffected != 1) {
        }
    }

    @Override
    public void editWorkType(Long id, String name, String description) {
        int rowsAffected = jdbcTemplate.update(
                EDIT_WORK_TYPE,
                name,
                description,
                id
        );

        if (rowsAffected != 1) {
        }
    }
}
