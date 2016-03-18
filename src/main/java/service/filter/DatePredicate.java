package service.filter;

import org.apache.commons.lang3.Validate;

import java.util.Date;

public class DatePredicate implements Predicate {

    Date date;

    Operator operator;

    public DatePredicate(Date date, Operator operator) {
        Validate.notNull(date);
        Validate.notNull(operator);
        this.date = date;
        this.operator = operator;
    }

    public boolean compare(Date compareValue) {
        switch (operator) {
            case EQUALS:
                return date.equals(compareValue);
            case GREATER_EQUALS:
            case GREATER:
                return date.before(compareValue);
            case LOWER:
            case LOWER_EQUALS:
                return date.after(compareValue);
            default:
                throw new IllegalArgumentException("Can not compare using operator " + operator);
        }
    }
}

