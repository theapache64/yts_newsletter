package com.theah64.webengine.database.querybuilders;

import com.sun.istack.internal.NotNull;
import com.theah64.webengine.database.BaseTable;
import com.theah64.webengine.database.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by theapache64 on 27/10/17.
 */
public class SelectQueryBuilder<T> {

    private final String tableName;
    private final Callback<T> callback;
    private String[] columns;
    private int limit;
    private String[] whereColumns;
    private String[] whereColumnValues;
    private String orderBy;

    public SelectQueryBuilder(String tableName, Callback<T> callback, String[] columns, String[] whereColumns, String[] whereColumnValues, int limit, String orderBy) {
        this.tableName = tableName;
        this.callback = callback;
        this.columns = columns;
        this.whereColumns = whereColumns;
        this.whereColumnValues = whereColumnValues;
        this.limit = limit;
        this.orderBy = orderBy;
    }


    public static class Builder<T> {

        private final String tableName;
        private final Callback<T> callback;
        private String[] columns;
        private int limit = -1;
        private String[] whereColumns;
        private String[] whereColumnValues;
        private String orderBy;

        public Builder(String tableName, @NotNull Callback<T> callback) {
            this.tableName = tableName;
            this.callback = callback;
        }

        public Builder<T> select(String[] columns) {
            this.columns = columns;
            return this;
        }

        public Builder<T> limit(int limit) {
            this.limit = limit;
            return this;
        }

        public SelectQueryBuilder<T> build() {
            return new SelectQueryBuilder<T>(tableName, callback, columns, whereColumns, whereColumnValues, limit, orderBy);
        }

        public Builder<T> where(String whereColumn, String whereColumnValue) {
            this.whereColumns = new String[]{whereColumn};
            this.whereColumnValues = new String[]{whereColumnValue};
            return this;
        }

        public Builder<T> orderBy(String orderBy) {
            this.orderBy = orderBy;
            return this;
        }
    }

    public T done() throws QueryBuilderException, SQLException {

        StringBuilder queryBuilder = new StringBuilder("SELECT ");
        if (columns == null) {
            throw new QueryBuilderException("No columns selected");
        }

        for (final String column : columns) {
            queryBuilder.append(column).append(",");
        }

        //Removing last comma
        queryBuilder = new StringBuilder(queryBuilder.substring(0, queryBuilder.length() - 1));

        queryBuilder.append(" FROM ").append(tableName);

        if (whereColumns != null && whereColumnValues != null) {

            if (whereColumns.length != whereColumnValues.length) {
                throw new QueryBuilderException("Where section count doesn't match");
            }

            queryBuilder.append(" WHERE ");

            for (final String wColumn : whereColumns) {
                queryBuilder.append(wColumn).append(" = ? AND ");
            }

            //Removing last and
            //4  = AND .length()
            queryBuilder = new StringBuilder(queryBuilder.substring(0, queryBuilder.length() - 4));
        }

        if (orderBy != null) {
            queryBuilder.append("ORDER BY ").append(orderBy);
        }

        if (limit != -1) {
            queryBuilder.append(" LIMIT ").append(limit);
        }

        //Query built
        System.out.println("Final query : " + queryBuilder);


        java.sql.Connection con = Connection.getConnection();
        ResultSet rs = null;
        Statement stmt = null;
        String sqlError = null;
        if (whereColumns != null) {

            System.out.println("Prepared statement");

            try {
                final PreparedStatement ps = con.prepareStatement(queryBuilder.toString());
                for (int i = 1; i <= whereColumnValues.length; i++) {
                    ps.setString(i, whereColumnValues[i - 1]);
                }
                rs = ps.executeQuery();
                stmt = ps;

            } catch (SQLException e) {
                e.printStackTrace();
                sqlError = e.getMessage();
            }

            BaseTable.manageError(sqlError);

        } else {
            System.out.println("Normal statement");
            try {
                stmt = con.createStatement();
                rs = stmt.executeQuery(queryBuilder.toString());
            } catch (SQLException e) {
                e.printStackTrace();
                sqlError = e.getMessage();
            }
            BaseTable.manageError(sqlError);
        }

        T t = null;


        try {

            if (rs != null) {
                rs.first();
            }

            t = callback.getNode(rs);

            if (rs != null) {
                rs.close();
            }

            if (stmt != null) {
                stmt.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            sqlError = e.getMessage();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        BaseTable.manageError(sqlError);

        return t;
    }

    public interface Callback<T> {
        T getNode(ResultSet rs) throws SQLException;
    }
}
