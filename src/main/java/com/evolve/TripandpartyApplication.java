package com.evolve;

import com.evolve.server.service.TripService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collection;

@SpringBootApplication
public class TripandpartyApplication {
    public static void main(String[] args) {
//        Collection<Integer> friendsVkIds = new TripService().getFriendsVkIds(68098233);
//        friendsVkIds.forEach(System.out::println);
        SpringApplication.run(TripandpartyApplication.class, args);
    }
}
