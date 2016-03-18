package service;

import java.sql.SQLException;

public class CalculationException extends Throwable {
    public CalculationException(SQLException e) {
        super(e);
    }

    public CalculationException(String s) {
        super(s);
    }
}
