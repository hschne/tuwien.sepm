package service.filter;

import service.ServiceException;

import java.util.List;

/**
 * Implementors can filter data based on certain criteria
 * @param <T>
 */
@FunctionalInterface
public interface Filter<T extends Criteria> {

    List filter(T criteria) throws ServiceException;
}
