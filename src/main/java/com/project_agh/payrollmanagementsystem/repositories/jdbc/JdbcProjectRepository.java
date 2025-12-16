package com.project_agh.payrollmanagementsystem.repositories.jdbc;

import com.project_agh.payrollmanagementsystem.entities.Project;
import com.project_agh.payrollmanagementsystem.entities.ProjectMember;
import com.project_agh.payrollmanagementsystem.entities.User;
import com.project_agh.payrollmanagementsystem.repositories.ProjectRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcProjectRepository implements ProjectRepository {

    private static final String FIND_ALL_FULL_SQL = "SELECT * FROM projekt ORDER BY id_projekt";
    private static final String FIND_BY_ID_SQL = "SELECT * FROM projekt WHERE id_projekt = ?";

    private static final String CREATE_NEW_PROJECT = "INSERT INTO projekt (nazwa, opis, data_rozpoczecia, data_zakonczenia) VALUES (?, ?, ?, ?)";
    private static final String DELETE_PROJECT = "DELETE FROM projekt WHERE id_projekt = ?";
    private static final String EDIT_PROJECT = "UPDATE projekt SET nazwa = ?, opis = ?, data_rozpoczecia = ?, data_zakonczenia = ? WHERE id_projekt = ?";

    private static final String ADD_USER_TO_PROJECT =
            "INSERT INTO pracownik_projekt (id_pracownik, id_projekt, rola_w_projekcie, data_przypisania) VALUES (?, ?, ?, ?) " +
                    "ON CONFLICT (id_pracownik, id_projekt) DO UPDATE SET rola_w_projekcie = EXCLUDED.rola_w_projekcie";

    private static final String REMOVE_USER_FROM_PROJECT =
            "DELETE FROM pracownik_projekt WHERE id_projekt = ? AND id_pracownik = ?";

    // --- POPRAWIONE ZAPYTANIE SQL ---
    // Musimy dołączyć tabele stanowisko, rola i dział, bo UserRowMapper tego wymaga!
    private static final String SELECT_USERS_FOR_PROJECT =
            "SELECT " +
                    "    p.id_pracownik, p.imie, p.nazwisko, p.wynagrodzenie_pln_g, p.email, p.telefon, p.haslo_hash, " +
                    "    p.data_zatrudnienia, p.data_zwolnienia, p.aktywny, p.konto_bankowe, " +
                    "    pp.rola_w_projekcie, " + // Rola z tabeli łączącej
                    "    s.id_stanowisko AS stanowisko_id, s.nazwa AS stanowisko_nazwa, s.opis AS stanowisko_opis, " +
                    "    r.id_rola AS rola_id, r.nazwa AS rola_nazwa, " +
                    "    d.id_dzial AS dzial_id, d.nazwa AS dzial_nazwa, d.opis AS dzial_opis " +
                    "FROM pracownik p " +
                    "JOIN pracownik_projekt pp ON p.id_pracownik = pp.id_pracownik " +
                    "JOIN stanowisko s ON s.id_stanowisko = p.id_stanowisko " +
                    "JOIN rola r ON r.id_rola = p.id_rola " +
                    "JOIN dzial d ON d.id_dzial = p.id_dzial " +
                    "WHERE pp.id_projekt = ?";

    private final JdbcTemplate jdbcTemplate;
    private final JdbcUserRepository jdbcUserRepository;

    public JdbcProjectRepository(JdbcTemplate jdbcTemplate, JdbcUserRepository jdbcUserRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcUserRepository = jdbcUserRepository;
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
        List<Project> projects = jdbcTemplate.query(FIND_ALL_FULL_SQL, projectRowMapper);
        for (Project project : projects) {
            loadUsersForProject(project);
        }
        return projects;
    }

    @Override
    public Optional<Project> findById(Long id) {
        try {
            Project project = jdbcTemplate.queryForObject(FIND_BY_ID_SQL, projectRowMapper, id);
            if (project != null) {
                loadUsersForProject(project);
            }
            return Optional.ofNullable(project);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void createProject(String project_name, String project_desc, LocalDate project_start_date, LocalDate project_end_date) {
        jdbcTemplate.update(CREATE_NEW_PROJECT, project_name, project_desc, project_start_date, project_end_date);
    }

    @Override
    public void deleteProject(Long id) {
        jdbcTemplate.update(DELETE_PROJECT, id);
    }

    @Override
    public void editProject(Long id, String name, String description, LocalDate project_start_date, LocalDate project_end_date) {
        jdbcTemplate.update(EDIT_PROJECT, name, description, project_start_date, project_end_date, id);
    }

    @Override
    public void addUserToProject(Long projectId, Long userId, String projectRole, LocalDate userAdded) {
        jdbcTemplate.update(ADD_USER_TO_PROJECT, userId, projectId, projectRole, userAdded);
    }

    @Override
    public void removeUserFromProject(Long projectId, Long userId) {
        jdbcTemplate.update(REMOVE_USER_FROM_PROJECT, projectId, userId);
    }

    // --- METODA POMOCNICZA ---
    private void loadUsersForProject(Project p) {
        List<ProjectMember> team = jdbcTemplate.query(SELECT_USERS_FOR_PROJECT, (rs, rowNum) -> {
            // 1. Mapujemy Usera (teraz zadziała, bo w SQL są kolumny stanowisko_id itd.)
            User user = jdbcUserRepository.getUserRowMapper().mapRow(rs, rowNum);

            // 2. Pobieramy rolę z projektu
            String roleInProject = rs.getString("rola_w_projekcie");

            // 3. Zwracamy obiekt wiążący
            return new ProjectMember(user, roleInProject);
        }, p.getId());

        p.setMembers(team);
    }
}