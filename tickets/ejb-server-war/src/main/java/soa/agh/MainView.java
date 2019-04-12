package soa.agh;


import soa.agh.controller.ClientController;
import soa.agh.controller.SeatsController;
import soa.agh.exception.NotEnoughMoneyException;
import soa.agh.exception.SeatBookedException;
import soa.agh.model.Seat;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class MainView implements Serializable {

    @EJB
    SeatsController seatsController;
    @EJB
    ClientController clientController;

    private List<Seat> selectedSeats;

    public List<Seat> getSeats() {
        return seatsController.getSeats();
    }

    public List<Seat> getSelectedSeats() {
        return selectedSeats;
    }

    public void setSelectedSeats(List<Seat> selectedSeats) {
        this.selectedSeats = selectedSeats;
    }

    public Double getAvailableFunds(){
        return clientController.getClient().getMoney();
    }

    public void buyTickets(){
        try {
            seatsController.buyTickets(selectedSeats);
            clientController.setClientBalance(selectedSeats);
        } catch (SeatBookedException ex) {
            String[] msg = ex.getId().split(";");
            FacesContext.getCurrentInstance()
                    .addMessage(null, new FacesMessage("STOP!", "Miejsce jest zajęte! Rząd:"+msg[0]+" Miejsce:"+ msg[1]));
        } catch (NotEnoughMoneyException ex) {
            seatsController.removeBookings(selectedSeats);
            FacesContext.getCurrentInstance()
                    .addMessage(null, new FacesMessage("STOP!", "Nie masz wystarczająco pieniędzy!"));
        }
    }
}
