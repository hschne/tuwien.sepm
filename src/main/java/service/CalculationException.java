package service;

import java.sql.SQLException;

public class CalculationException extends Exception {
    public CalculationException(SQLException e) {
        super(e);
    }

}
