package service.criteria;

import entities.Article;
import service.ServiceException;

import java.util.List;

public class AndCriteria<T> implements Criteria<T> {

    private final Criteria first;
    private final Criteria second;

    public AndCriteria(Criteria<T> first, Criteria<T> second){
        this.first = first;
        this.second = second;
    }

    @Override
    public List apply(List<? extends T> list) throws ServiceException {
        return second.apply(first.apply(list));
    }
}
