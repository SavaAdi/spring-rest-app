package com.adisavaspring.rest.springrestapp.bootstrap;

import com.adisavaspring.rest.springrestapp.model.persisted.UserEntity;
import com.adisavaspring.rest.springrestapp.repositories.UserRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class Dataloader implements ApplicationListener<ContextRefreshedEvent> {

    private final UserRepository repository;

    public Dataloader(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
            UserEntity userEntity = new UserEntity();
            userEntity.setUserId("fdsfsd");
            userEntity.setEncryptedPassword("fdsfd");
            userEntity.setFirstName("fsdfdsf");
            userEntity.setLastName("sdfsdfsd");
            userEntity.setEmail("guguu.com");
            repository.save(userEntity);
    }
}
