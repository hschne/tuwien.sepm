package service;

import org.apache.commons.lang3.Validate;

public class DoubleCriteria {

    double number;

    Operator operator;

    public DoubleCriteria(double number, Operator operator) {
        Validate.notNull(number);
        Validate.notNull(operator);
        this.number = number;
        this.operator = operator;
    }

    public boolean compare(double compareValue) {
        switch (operator) {
            case EQUALS:
                return number == compareValue;
            case GREATER_EQUALS:
                return number <= compareValue;
            case GREATER:
                return number < compareValue;
            case LOWER_EQUALS:
                return number >= compareValue;
            case LOWER:
                return number > compareValue;
        }
        throw new IllegalArgumentException("Can not compare using operator " + operator);

    }
}
