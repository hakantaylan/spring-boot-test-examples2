package com.examples.junit5testorder.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    @Test
    void testSave() {
        User user = User.builder().name("Wim").build();
        User saved = repository.save(user);
        assertThat(saved).isNotNull();
    }
}
