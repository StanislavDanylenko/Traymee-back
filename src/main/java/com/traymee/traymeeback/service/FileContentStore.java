package com.traymee.traymeeback.service;

import com.traymee.traymeeback.db.entity.File;
import org.springframework.content.commons.repository.ContentStore;
import org.springframework.content.rest.StoreRestResource;

@StoreRestResource
public interface FileContentStore extends ContentStore<File, String> {
}
