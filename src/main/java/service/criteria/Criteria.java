package service.criteria;

import service.ServiceException;

import java.util.List;

@FunctionalInterface
public interface Criteria<T> {

    List<T> apply(List<? extends T> list) throws ServiceException;
}
