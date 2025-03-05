package com.example;

import com.intuit.karate.junit5.Karate;


public class Runner {
    @Karate.Test
    Karate testAll() {
        return Karate.run("pet.feature", "user.feature").relativeTo(getClass());
    }
}
