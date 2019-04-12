package soa.agh.model;

public class Client {
    private Double money;

    public Client(Double money){
        this.money = money;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }
}
