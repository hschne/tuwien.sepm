package service.criteria;

import service.ServiceException;

import java.util.List;

public interface Criteria<T>  {

    public List<T> apply(List<T> list) throws ServiceException;
}
