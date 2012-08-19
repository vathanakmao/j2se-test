package com.vathanakmao.testproj.j2setest.concurrency;

import net.sf.ehcache.CacheManager;

import org.junit.Test;

public class EhcacheTest {

    @Test
    public void test1() {
        CacheManager singletonCacheManager = CacheManager.create();
        CacheManager cacheManager = new CacheManager(EhcacheTest.class.getResource("/ehcache.xml"));
        
        for (String name : cacheManager.getCacheNames()) {
            System.out.println("name = " + name);
        }
        
        System.out.println("---------------");
        
        for (String name : singletonCacheManager.getCacheNames()) {
            System.out.println("name = " + name);
        }
    }

}
