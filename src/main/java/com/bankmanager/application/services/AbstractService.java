package com.bankmanager.application.services;

import com.bankmanager.application.entities.AbstractEntity;
import com.bankmanager.application.repositories.AbstractRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public abstract class AbstractService<E extends AbstractEntity, T extends AbstractRepository<E>> {

    protected final T repository;

    protected AbstractService(T repository) {
        this.repository = repository;
    }

    public Collection<E> getAll() {
        return new ArrayList<>(this.repository.findAll());
    }

    public void save(E data) {
        this.save(Collections.singletonList(data));
    }

    public void save(Collection<E> data) {
        this.repository.saveAll(data);
    }

    public void remove(Long id) {
        this.repository.deleteById(id);
    }

    public void remove(E data) {
        remove(Collections.singletonList(data));
    }

    public void remove(Collection<E> data) {
        this.repository.deleteAll(data);
    }
}
