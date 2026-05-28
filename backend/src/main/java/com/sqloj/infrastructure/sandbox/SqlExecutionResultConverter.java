package com.sqloj.infrastructure.sandbox;

import com.sqloj.domain.judge.SqlExecutionResult;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class SqlExecutionResultConverter {

    public SqlExecutionResult fromResultSet(ResultSet resultSet, long executeTimeMs) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        List<String> columns = new ArrayList<>();
        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
            columns.add(metaData.getColumnLabel(columnIndex));
        }

        List<List<Object>> rows = new ArrayList<>();
        while (resultSet.next()) {
            List<Object> row = new ArrayList<>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                row.add(resultSet.getObject(columnIndex));
            }
            rows.add(row);
        }
        return new SqlExecutionResult(columns, rows, executeTimeMs);
    }
}
