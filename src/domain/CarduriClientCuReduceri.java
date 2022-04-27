package domain;

public class CarduriClientCuReduceri {
    Client client;
    double reducere;

    public CarduriClientCuReduceri(Client client, double reducere) {
        this.client = client;
        this.reducere = reducere;
    }

    public Client getClient() {
        return client;
    }

    public double getReducere() {
        return reducere;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "CarduriClientCuReduceri{" +
                "client=" + client +
                ", reducere=" + reducere +
                '}';
    }

    public void setReducere(double reducere) {
        this.reducere = reducere;
    }
}
