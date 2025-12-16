package com.project_agh.payrollmanagementsystem.repositories.jdbc;

import com.project_agh.payrollmanagementsystem.entities.PaymentType;
import com.project_agh.payrollmanagementsystem.repositories.PaymentTypeRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcPaymentTypeRepository implements PaymentTypeRepository {

    private static final String FIND_ALL_FULL_SQL =
            "SELECT * FROM typ_wyplaty";

    private static final String CREATE_NEW_PAYMENT_TYPE =
            "INSERT INTO typ_wyplaty (nazwa, opis) VALUES (?, ?)";

    private static final String DELETE_PAYMENT_TYPE =
            "DELETE FROM  typ_wyplaty \n" +
                    "WHERE id_typ_wyplaty = ?\n";

    private static final String EDIT_PAYMENT_TYPE=
            "UPDATE typ_wyplaty SET " +
                    "nazwa = ?, " +
                    "opis = ? " +

                    "WHERE id_typ_wyplaty = ?";

    private final JdbcTemplate jdbcTemplate;


    public JdbcPaymentTypeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<PaymentType> paymentTypeRowMapper = (rs, rowNum) -> {
        PaymentType paymentType = new PaymentType();
        paymentType.setId(rs.getLong("id_typ_wyplaty"));
        paymentType.setName(rs.getString("nazwa"));
        paymentType.setDescription(rs.getString("opis"));
        return paymentType;
    };


    @Override
    public List<PaymentType> findAll() {
        return jdbcTemplate.query(FIND_ALL_FULL_SQL, paymentTypeRowMapper);
    }

    @Override
    public void createPaymentType(String name, String description) {
        int rowsAffected = jdbcTemplate.update(CREATE_NEW_PAYMENT_TYPE, name, description);

        if (rowsAffected != 1) {
            // Optional: handle the error if the row was not inserted
        }
    }

    @Override
    public void deletePaymentType(Long id) {
        int rowsAffected = jdbcTemplate.update(
                DELETE_PAYMENT_TYPE,
                id
        );

        if (rowsAffected != 1) {
        }
    }

    @Override
    public void editPaymentType(Long id, String name, String description) {
        int rowsAffected = jdbcTemplate.update(
                EDIT_PAYMENT_TYPE,
                name,
                description,
                id
        );
        if (rowsAffected != 1) {
        }
    }
}
