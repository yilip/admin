package com.lip.core.management;

import java.util.Set;

public interface WhitelistCache {
    Set<String> getWhitelist();
    void refresh();
}
