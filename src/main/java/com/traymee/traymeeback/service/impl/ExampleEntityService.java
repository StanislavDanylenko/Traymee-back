package com.traymee.traymeeback.service.impl;

import com.traymee.traymeeback.db.entity.ExampleEntity;
import com.traymee.traymeeback.db.repository.ExampleEntityRepository;
import com.traymee.traymeeback.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExampleEntityService implements GenericService<ExampleEntity> {

    @Autowired
    private ExampleEntityRepository exampleEntityRepository;

    @Override
    public ExampleEntity save(ExampleEntity entity) {
        return exampleEntityRepository.save(entity);
    }

    @Override
    public void delete(ExampleEntity entity) {
        exampleEntityRepository.delete(entity);
    }

    @Override
    public ExampleEntity update(ExampleEntity entity) {
        return exampleEntityRepository.save(entity);
    }

    @Override
    public ExampleEntity find(Long id) {
        return exampleEntityRepository.getOne(id);
    }

    @Override
    public Iterable<ExampleEntity> findAll() {
        return exampleEntityRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        exampleEntityRepository.deleteById(id);
    }
}
