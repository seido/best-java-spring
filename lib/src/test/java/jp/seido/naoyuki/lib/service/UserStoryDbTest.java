package jp.seido.naoyuki.lib.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import jp.seido.naoyuki.lib.repository.UserRepository;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = { UserStoreDb.class,
        UserRepository.class }))
public class UserStoryDbTest extends UserStoreTest {

    @Autowired
    private UserStoreDb userStoreDb;

    @Override
    UserStore createUserStore() {
        return userStoreDb;
    }
}
