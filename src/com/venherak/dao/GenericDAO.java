package com.venherak.dao;

import com.venherak.domain.AbstractEntity;

import java.util.Collection;

public interface GenericDAO<E extends AbstractEntity> {
    void persist(E object) throws DAOException;

    boolean checkIfExists(long id) throws DAOException;

    Collection<E> getAll() throws DAOException;

    E getById(long id) throws DAOException;

    default void delete(E object) throws DAOException {
        delete(object.getId());
    }

    void delete(long id) throws DAOException;
}
