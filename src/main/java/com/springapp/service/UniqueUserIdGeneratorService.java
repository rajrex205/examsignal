package com.springapp.service;

import org.apache.commons.lang3.StringUtils;

import java.util.Random;

public class UniqueUserIdGeneratorService {

    public String getUniqueCode(String type, String firstName, String lastName, long generatedId){
        String paddedID = StringUtils.leftPad(String.valueOf(generatedId),6,"0");
        Random r = new Random();
        return firstName.substring(0,1).toUpperCase()+lastName.substring(0,1).toUpperCase()+paddedID+type.toUpperCase()+r.nextInt(10);
    }
}
