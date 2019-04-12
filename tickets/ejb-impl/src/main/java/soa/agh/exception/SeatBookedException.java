package soa.agh.exception;

public class SeatBookedException extends Exception {
    private String id;
    public SeatBookedException(String id) {
        super();
        this.id = id;
    }

    public String getId() {
        return id;
    }
}

