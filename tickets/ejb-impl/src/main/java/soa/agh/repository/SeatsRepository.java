package soa.agh.repository;

import soa.agh.model.Seat;

import java.util.Arrays;
import java.util.List;

public class SeatsRepository {

    public static List<Seat> get(){
        List<Seat> list = Arrays.asList(
                new Seat(1, 1, 10),
                new Seat(1, 2, 15),
                new Seat(1, 3, 15),
                new Seat(1, 4, 15),
                new Seat(1, 5, 15),
                new Seat(1, 6, 10),
                new Seat(2, 1, 15),
                new Seat(2, 2, 20),
                new Seat(2, 3, 20),
                new Seat(2, 4, 20),
                new Seat(2, 5, 20),
                new Seat(2, 6, 15),
                new Seat(3, 1, 20),
                new Seat(3, 2, 25),
                new Seat(3, 3, 25),
                new Seat(3, 4, 25),
                new Seat(3, 5, 25),
                new Seat(3, 6, 20)
        );
        return list;
    }
}
