package com.traymee.traymeeback.db.repository;

import com.traymee.traymeeback.db.entity.ExampleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExampleEntityRepository extends JpaRepository<ExampleEntity, Long> {
}
