package soa.agh.model;

public class Seat {
    private Integer row, seatNumber, seatPrice;
    private boolean seatReserved;

    public Seat(Integer row, Integer seatNumber, Integer seatPrice) {
        this.row = row;
        this.seatNumber = seatNumber;
        this.seatPrice = seatPrice;
        this.seatReserved = false;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public Integer getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(Integer seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getID(){
        return row+";"+seatNumber;
    }

    public Integer getSeatPrice() {
        return seatPrice;
    }

    public void setSeatPrice(Integer seatPrice) {
        this.seatPrice = seatPrice;
    }

    public boolean isSeatReserved() {
        return seatReserved;
    }

    public void setSeatReserved(boolean seatReserved) {
        this.seatReserved = seatReserved;
    }
}
