package com.theah64.yts_nl.database;

import com.theah64.webengine.database.BaseTable;
import com.theah64.webengine.database.Connection;
import com.theah64.yts_nl.models.Subscription;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by theapache64 on 1/10/17.
 */
public class Subscriptions extends BaseTable<Subscription> {

    private static final Subscriptions instance = new Subscriptions();
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_VERIFICATION_CODE = "verification_code";

    private Subscriptions() {
        super("subscriptions");
    }

    public static Subscriptions getInstance() {
        return instance;
    }

    @Override
    public boolean add(Subscription subscription) throws SQLException {
        final String query = "INSERT INTO subscriptions (email , verification_code) VALUES (?,?)";
        String error = null;
        final java.sql.Connection con = Connection.getConnection();
        try {
            final PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, subscription.getEmail());
            ps.setString(2, subscription.getVerificationCode());
            if (ps.executeUpdate() != 1) {
                error = "Failed to add new subscription";
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
            if (e.getMessage().equals(String.format("Duplicate entry '%s' for key '%s'", subscription.getEmail(), COLUMN_EMAIL))) {
                error = "Email already exist";
            } else {
                error = e.getMessage();
            }
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        manageError(error);
        return true;
    }

    @Override
    public boolean update(Subscription subscription) throws SQLException {
        boolean isEdited = false;
        final String query = "UPDATE subscriptions SET email = ? , verification_code = ? , is_verified = ? , is_active = ?, updated_at = NOW() WHERE email = ? AND verification_code = ?;";
        final java.sql.Connection con = Connection.getConnection();

        try {
            final PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, subscription.getEmail());
            ps.setString(2, subscription.getVerificationCode());
            ps.setBoolean(3, subscription.isVerified());
            ps.setBoolean(4, subscription.isActive());
            ps.setString(5, subscription.getEmail());
            ps.setString(6, subscription.getVerificationCode());

            isEdited = ps.executeUpdate() == 1;
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (!isEdited) {
            throw new SQLException("Failed to update subscription");
        }
        return isEdited;
    }
}
