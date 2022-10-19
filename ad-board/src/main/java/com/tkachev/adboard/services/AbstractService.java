package com.tkachev.adboard.services;

import com.tkachev.adboard.exception_handling.exceptions.NoSuchEntityException;

public abstract class AbstractService {
    public <T> T isNotNull(T entity, String entityName, Long id) {
        if (entity == null) {
            throw new NoSuchEntityException(String.format("No %s with ID = %s exists!", entityName, id));
        }

        return entity;
    }
}
