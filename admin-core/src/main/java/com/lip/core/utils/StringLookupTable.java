package com.lip.core.utils;

import com.google.common.collect.Interner;
import com.google.common.collect.Interners;

public class StringLookupTable {
    private static Interner<String> canonicalStrings = Interners.newWeakInterner();
    
    public static String canonicalise(final String candidate) {
        if(null != candidate) return canonicalStrings.intern(candidate);
        return candidate;
    }
}
