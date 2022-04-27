package domain;

public class MasinaCuSumaManopera {
    private Masina masina;
    private double sumaManopera;

    public MasinaCuSumaManopera(Masina masina, double sumaManopera) {
        this.masina = masina;
        this.sumaManopera = sumaManopera;
    }

    public Masina getMasina() {
        return masina;
    }

    public double getSumaManopera() {
        return sumaManopera;
    }

    @Override
    public String toString() {
        return "MasinaCuSumaManopera{" +
                "masina=" + masina +
                ", sumaManopera=" + sumaManopera +
                '}';
    }

}
