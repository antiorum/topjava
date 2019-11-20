package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import ru.javawebinar.topjava.repository.JpaUtil;

public abstract class AbstractUserJpaTest extends AbstractUserServiceTest{
    @Autowired
    protected JpaUtil jpaUtil;

    @Autowired
    private CacheManager cacheManager;

    @Before
    public void setUp() throws Exception {
        if (!isJdbcProfile()){
            cacheManager.getCache("users").clear();
            jpaUtil.clear2ndLevelHibernateCache();
        }
    }
}
