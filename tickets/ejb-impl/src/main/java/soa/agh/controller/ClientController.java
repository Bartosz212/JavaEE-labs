package soa.agh.controller;

import soa.agh.model.Client;
import soa.agh.exception.NotEnoughMoneyException;
import soa.agh.model.Seat;

import javax.annotation.PostConstruct;
import javax.ejb.Local;
import javax.ejb.Stateful;
import java.util.List;

@Stateful
@Local
public class ClientController {

    Client client;

    @PostConstruct
    void init(){
        client = new Client(50.0);
    }

    public ClientController() {}

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setClientBalance(List<Seat> seats) throws NotEnoughMoneyException {
        if (seats.stream().map(Seat::getSeatPrice).mapToInt(i -> i).sum() > client.getMoney())
            throw new NotEnoughMoneyException();
        else {
            for (Seat s : seats) {
                client.setMoney(client.getMoney() - s.getSeatPrice());
            }
        }
    }
}
