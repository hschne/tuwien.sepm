package service.criteria;

import entities.Receipt;
import service.ServiceException;

import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DateCriteria implements Criteria<Receipt> {

    private Date date;

    private DateOperator operator;

    public DateCriteria(Date date, DateOperator operator) {
        this.date = date;
        this.operator = operator;
    }

    @Override
    public List<Receipt> apply(List<Receipt> list) throws ServiceException {
        return list.stream().filter(getPredicate()).collect(Collectors.toList());
    }

    private Predicate<Receipt> getPredicate() throws ServiceException {
        switch(operator){
            case BEFORE:
                return p -> p.getDate().before(date);
            case AFTER:
                return p -> p.getDate().after(date);
            case ON_DAY:
                return p -> p.getDate().getDay() == date.getDay();
            default:
                throw new ServiceException("Error");
        }
    }

}
