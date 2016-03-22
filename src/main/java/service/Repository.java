package service;

import entities.Entity;
import service.criteria.Criteria;

import java.util.List;

public interface Repository<T>  {

    List<T> getAll() throws ServiceException;

    List<T> filter(Criteria<T> criteria) throws ServiceException;
}
