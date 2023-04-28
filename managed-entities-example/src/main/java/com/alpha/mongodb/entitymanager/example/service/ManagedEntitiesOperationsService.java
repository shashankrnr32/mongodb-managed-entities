package com.alpha.mongodb.entitymanager.example.service;

import com.alpha.mongodb.entitymanager.example.api.models.EntityDTO;

import java.util.Optional;

public interface ManagedEntitiesOperationsService {

    EntityDTO insert(EntityDTO entity);

    Optional<EntityDTO> findById(String id);

    Optional<EntityDTO> clone(String id);

    boolean delete(String id);


}
