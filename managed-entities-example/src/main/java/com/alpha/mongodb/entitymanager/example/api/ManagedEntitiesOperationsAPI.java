package com.alpha.mongodb.entitymanager.example.api;

import com.alpha.mongodb.entitymanager.example.api.enumeration.DataSourceType;
import com.alpha.mongodb.entitymanager.example.api.factory.ManagedEntitiesOperationsServiceFactory;
import com.alpha.mongodb.entitymanager.example.api.models.EntityDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Log4j2
@RestController
@RequestMapping("/v1/ops")
public class ManagedEntitiesOperationsAPI {

    @Autowired
    ManagedEntitiesOperationsServiceFactory serviceFactory;

    @GetMapping(path = "/find/id/{id}")
    public ResponseEntity<?> findById(@PathVariable String id, @RequestParam DataSourceType dataSourceType) {
        Optional<?> entityOptional = serviceFactory.get(dataSourceType).findById(id);
        if (entityOptional.isPresent()) {
            return ResponseEntity.ok(entityOptional);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(path = "/clone/id/{id}")
    public ResponseEntity<?> clone(@PathVariable String id, @RequestParam DataSourceType dataSourceType) {
        Optional<?> entityOptional = serviceFactory.get(dataSourceType).clone(id);
        if (entityOptional.isPresent()) {
            return ResponseEntity.ok(entityOptional);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(path = "/delete/id/{id}")
    public ResponseEntity<?> delete(@PathVariable String id, @RequestParam DataSourceType dataSourceType) {
        boolean deleted = serviceFactory.get(dataSourceType).delete(id);
        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping(path = "/insert")
    public ResponseEntity<?> insert(@RequestBody EntityDTO testShardedEntity,
                                    @RequestParam DataSourceType dataSourceType) {
        EntityDTO persistedEntityDTO = serviceFactory.get(dataSourceType).insert(testShardedEntity);
        return ResponseEntity.ok(persistedEntityDTO.getId());
    }

}
