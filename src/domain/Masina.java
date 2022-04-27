package domain;

public class Masina extends Entity {

    private String model;
    private int anAchizitie;
    private int nrKm;
    private boolean inGarantie;

    public Masina(String id, String model, int anAchizitie, int nrKm, boolean inGarantie) {
        super(id);
        this.model = model;
        this.anAchizitie = anAchizitie;
        this.nrKm = nrKm;
        this.inGarantie = inGarantie;
    }



    public String getModel() {
        return model;
    }

    public int getAnAchizitie() {
        return anAchizitie;
    }

    public int getNrKm() {
        return nrKm;
    }

    public boolean isInGarantie() {
        return inGarantie;
    }

    public void setInGarantie(boolean inGarantie) {
        this.inGarantie = inGarantie;
    }

    @Override
    public String toString() {
        return "Masina{" +
                "id='" + id + '\'' +
                ", model='" + model + '\'' +
                ", anAchizitie=" + anAchizitie +
                ", nrKm=" + nrKm +
                ", inGarantie=" + inGarantie +
                '}';
    }
}
