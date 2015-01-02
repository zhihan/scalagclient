package org.mine.gclient;

import com.google.api.client.util.Key;

public class TestJsonData {
    @Key
    private String name;

    public void setName(String n) { name = n;}
    
    public String getName() { return name; }
    
}
