package domain;

import java.time.LocalDateTime;

public class Tranzactie extends  Entity{


    private String id_masina;
    private String idCardClient;
    private double sumaPiese;
    private double sumaManopera;
    private LocalDateTime dataSiOra;

    public Tranzactie(String id, String id_masina, String idCardClient, double sumaPiese, double sumaManopera, LocalDateTime dataSiOra) {
        super(id);
        this.id_masina = id_masina;
        this.idCardClient = idCardClient;
        this.sumaPiese = sumaPiese;
        this.sumaManopera = sumaManopera;
        this.dataSiOra = dataSiOra;
    }


    public String getId_masina() {
        return id_masina;
    }

    public String getIdCardClient() {
        return idCardClient;
    }

    public double getSumaPiese() {
        return sumaPiese;
    }

    public double getSumaManopera() {
        return sumaManopera;
    }

    public LocalDateTime getDataSiOra() {
        return dataSiOra;
    }

    public void setSumaManopera(double sumaManopera) {
        this.sumaManopera = sumaManopera;
    }

    public void setSumaPiese(double sumaPiese) {
        this.sumaPiese = sumaPiese;
    }

    @Override
    public String toString() {
        return "Tranzactie{" +
                "id='" + id + '\'' +
                ", id_masina='" + id_masina + '\'' +
                ", idCardClient='" + idCardClient + '\'' +
                ", sumaPiese=" + sumaPiese +
                ", sumaManopera=" + sumaManopera +
                ", dataSiOra='" + dataSiOra + '\'' +
                '}';
    }
}
