package com.example.mockband.util;

import java.util.UUID;

public class CommonUtil {

    public synchronized static String randomBankAccount()
    {
        String uuid = UUID.randomUUID().toString().replace("-","");
        return uuid;
    }
}
