package soa.agh.controller;
import soa.agh.repository.SeatsRepository;
import soa.agh.exception.SeatBookedException;
import soa.agh.model.Seat;

import javax.annotation.PostConstruct;
import javax.ejb.Local;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import java.util.List;

@Singleton
@Local
public class SeatsController {

    List<Seat> seats;

    @PostConstruct
    public void init(){
        seats = SeatsRepository.get();
    }

    @Lock(LockType.READ)
    public List<Seat> getSeats() {
        return seats;
    }


    public int getSeatPrice(String id) {
        return seats.stream().filter((s)->s.getID().equals(id)).findFirst().get().getSeatPrice();
    }

    @Lock(LockType.READ)
    public void checkSeats(String id) throws SeatBookedException {
        if(seats.stream().anyMatch((s)->s.getID().equals(id))){
            Seat toCheck= seats.stream().filter((s)->s.getID().equals(id)).findFirst().get();
            if(toCheck.isSeatReserved())
                throw new SeatBookedException(toCheck.getID());
        }
    }

    @Lock(LockType.WRITE)
    public void buyTicket(String id) throws SeatBookedException {
        if(seats.stream().anyMatch((s)->s.getID().equals(id))){
            Seat toBuy = seats.stream().filter((s)->s.getID().equals(id)).findFirst().get();
            if(!toBuy.isSeatReserved())
                toBuy.setSeatReserved(true);
            else
                throw new SeatBookedException(toBuy.getID());
        }

    }

    public void buyTickets(List<Seat> seats) throws SeatBookedException {
        for (Seat toCheck:seats) {
            this.checkSeats(toCheck.getID());
        }

        for (Seat toBuy:seats) {
            this.buyTicket(toBuy.getID());
        }
    }


    @Lock(LockType.WRITE)
    public void removeBooking(String id) {
        if(seats.stream().anyMatch((s)->s.getID().equals(id))){
            Seat toRemove = seats.stream().filter((s)->s.getID().equals(id)).findFirst().get();
            if(toRemove.isSeatReserved())
                toRemove.setSeatReserved(false);
        }

    }

    public void removeBookings(List<Seat> seats){
        for (Seat toRemove:seats) {
            this.removeBooking(toRemove.getID());
        }
    }

}
