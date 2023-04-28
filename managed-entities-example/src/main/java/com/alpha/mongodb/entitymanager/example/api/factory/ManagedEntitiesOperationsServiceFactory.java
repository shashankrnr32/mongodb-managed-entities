package com.alpha.mongodb.entitymanager.example.api.factory;

import com.alpha.mongodb.entitymanager.example.api.enumeration.DataSourceType;
import com.alpha.mongodb.entitymanager.example.service.ManagedEntitiesOperationsService;
import com.alpha.mongodb.entitymanager.example.service.impl.TemplateManagedEntityOperationsService;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ManagedEntitiesOperationsServiceFactory {

    @Autowired
    TemplateManagedEntityOperationsService templateManagedEntityOperationsService;

    public ManagedEntitiesOperationsService get(DataSourceType dataSourceType) {
        switch (dataSourceType) {
            case TEMPLATE:
                return templateManagedEntityOperationsService;
            case REPOSITORY:
            default:
                throw new NotImplementedException();
        }
    }
}
