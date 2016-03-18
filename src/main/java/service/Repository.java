package service;

import entities.Entity;

import java.util.List;

public interface Repository<T extends Entity>  {

    List<T> getAll() throws ServiceException;
}
