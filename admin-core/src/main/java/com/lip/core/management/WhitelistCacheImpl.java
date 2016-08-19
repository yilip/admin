package com.lip.core.management;

import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

@Component
public class WhitelistCacheImpl implements WhitelistCache {

    private Set<String> cache;


    @PostConstruct
    public void init() {
        refresh();
    }

    @Override
    public Set<String> getWhitelist() {
        return cache;
    }

    @Override
    public synchronized void refresh() {
    }
}
