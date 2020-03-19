package com.apollopharmacy.mpospharmacist.utils;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class TestClass {

    public static void main(String[] args) {
        int rand_int2 = ThreadLocalRandom.current().nextInt();
        System.out.println("Random Integers: " + rand_int2);
        int random = (int)(Math.random() * 50 + 1);
        System.out.println("Random Generate: " + random);
        System.out.println(generatorOTP(8));
        System.out.println(generatorOTP(8));
        System.out.println(generatorOTP(8));
        System.out.println(generatorOTP(8));
    }

    public static char[] generatorOTP(int length)
    {
        System.out.print("Your OTP is : ");
        //Creating object of Random class
        Random obj = new Random();
        char[] otp = new char[length];
        for (int i=0; i<length; i++)
        {
            otp[i]= (char)(obj.nextInt(10)+48);
        }
        return otp;
    }
}
