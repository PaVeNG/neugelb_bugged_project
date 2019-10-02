package com.bugged.themoviedb.data.local;

public interface UserDataHelper {


    public void storeValue(String key, String value);

    public String getValue(String key);

    void clearAll();

}
