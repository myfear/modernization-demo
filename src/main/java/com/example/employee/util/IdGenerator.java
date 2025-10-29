package com.example.employee.util;

import java.util.Random;

public class IdGenerator {
    private static final Random random = new Random();
    
    // Private constructor to prevent instantiation
    private IdGenerator() {
    }
    
    public static String generateEmployeeId() {
        return "E" + String.format("%05d", random.nextInt(100000));
    }
    
    public static String generateEventId() {
        return "EVT" + System.currentTimeMillis();
    }
}

// Made with Bob
