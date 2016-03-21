package service.criteria;

import entities.Receipt;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class DateCriteria implements Criteria<Receipt> {

    private Date date;

    public DateCriteria(Date date) {
        this.date = date;
    }

    @Override
    public List<Receipt> apply(List<Receipt> list) {
        return list.stream().filter(p -> p.getDate().after(date)).collect(Collectors.toList());
    }
}
