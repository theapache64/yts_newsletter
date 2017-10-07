package com.theah64.yts_nl.database;

import com.theah64.webengine.database.BaseTable;
import com.theah64.webengine.database.Connection;
import com.theah64.yts_nl.models.LetterSent;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by theapache64 on 7/10/17.
 */
public class LettersSent extends BaseTable<LetterSent> {

    private static final LettersSent instance = new LettersSent();

    private LettersSent() {
        super("letters_sent");
    }

    public static LettersSent getInstance() {
        return instance;
    }

    @Override
    public boolean add(LetterSent newInstance) throws SQLException {
        final String query = "INSERT INTO letters_sent (recipients_count,movie_count) VALUES (?,?);";
        final java.sql.Connection con = Connection.getConnection();
        String error = null;
        try {
            final PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, newInstance.getRecipientsCount());
            ps.setInt(2, newInstance.getMovieCount());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
            error = e.getMessage();
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
}
