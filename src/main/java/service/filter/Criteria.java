package service.filter;

import entities.Entity;

import java.util.List;

/**
 * Implementors are criteria used for filtering a list of entities
 */
public interface Criteria<T> {

    public List<T> apply(List<T> list);

}
