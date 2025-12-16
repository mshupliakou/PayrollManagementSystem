package com.project_agh.payrollmanagementsystem.repositories.jdbc;

import com.project_agh.payrollmanagementsystem.entities.PaymentStatus;
import com.project_agh.payrollmanagementsystem.repositories.PaymentStatusRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcPaymentStatusRepository implements PaymentStatusRepository {

    private static final String FIND_ALL_FULL_SQL =
            "SELECT * FROM status_wyplaty";

    private static final String CREATE_NEW_PAYMENT_STATUS =
            "INSERT INTO status_wyplaty (nazwa, opis) VALUES (?, ?)";

    private static final String DELETE_PAYMENT_STATUS =
            "DELETE FROM  status_wyplaty \n" +
                    "WHERE id_status_wyplaty = ?\n";

    private static final String EDIT_PAYMENT_STATUS=
            "UPDATE status_wyplaty SET " +
                    "nazwa = ?, " +
                    "opis = ? " +

                    "WHERE id_status_wyplaty = ?";

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<PaymentStatus> paymentStatusRowMapper = (rs, rowNum) -> {
        PaymentStatus paymentStatus = new PaymentStatus();
        paymentStatus.setId(rs.getLong("id_status_wyplaty"));
        paymentStatus.setName(rs.getString("nazwa"));
        paymentStatus.setDescription(rs.getString("opis"));
        return paymentStatus;
    };

    public JdbcPaymentStatusRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<PaymentStatus> findAll() {
        return jdbcTemplate.query(FIND_ALL_FULL_SQL, paymentStatusRowMapper);
    }

    @Override
    public void createPaymentStatus(String name, String description) {
        int rowsAffected = jdbcTemplate.update(CREATE_NEW_PAYMENT_STATUS, name, description);
    }

    @Override
    public void deletePaymentStatus(Long id) {
        int rowsAffected = jdbcTemplate.update(
                DELETE_PAYMENT_STATUS,
                id
        );
    }

    @Override
    public void editPaymentStatus(Long id, String name, String description) {
        int rowsAffected = jdbcTemplate.update(
                EDIT_PAYMENT_STATUS,
                name,
                description,
                id
        );
    }
}
