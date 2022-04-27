package service;

import domain.Masina;
import domain.MasinaCuSumaManopera;
import domain.MasinaValidator;
import domain.Tranzactie;
import repository.IRepository;
import repository.InMemoryRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MasinaService {
    IRepository<Masina> masinaRepository;
    IRepository<Tranzactie> tranzactieRepository;
    MasinaValidator masinaValidator;

    public MasinaService(IRepository<Masina> masinaRepository, IRepository<Tranzactie> tranzactieRepository, MasinaValidator masinaValidator) {
        this.masinaRepository = masinaRepository;
        this.tranzactieRepository = tranzactieRepository;
        this.masinaValidator = masinaValidator;
    }

    public void addMasina(String id, String model, int anAchizitie, int nrKm, boolean inGarantie) {
        Masina masinaNoua = new Masina(id, model, anAchizitie, nrKm, inGarantie);
        masinaValidator.validate(masinaNoua, masinaRepository, "adauga");
        masinaRepository.create(masinaNoua);
    }

    public void updateMasina(String id, String model, int anAchizitie, int nrKm, boolean inGarantie) {
        Masina masinaUpdate = new Masina(id, model, anAchizitie, nrKm, inGarantie);
        masinaValidator.validate(masinaUpdate, masinaRepository, "update");
        masinaRepository.update(masinaUpdate);
    }

    public void deleteMasina(String id) {
        masinaRepository.delete(id);
    }

    public List<Masina> getAll() {
        return masinaRepository.read();
    }

    public List<MasinaCuSumaManopera> afisareMasiniDescrescatorSumManopera() {
        List<MasinaCuSumaManopera> masinaDescSumManopera = new ArrayList<>();
        Map<String, Double> perechiIDSumManopera = new HashMap<String, Double>(); // vom aduna sumele pe manopera pt. aceeasi masina
        for (Tranzactie tranzactieCurenta : tranzactieRepository.read()) {
            String id = tranzactieCurenta.getId_masina();
            double sumManopera = tranzactieCurenta.getSumaManopera();
            if (perechiIDSumManopera.containsKey(id)) {
                double sumaIntermediara = perechiIDSumManopera.get(id);
                perechiIDSumManopera.put(id, sumaIntermediara + sumManopera);
            } else {
                perechiIDSumManopera.put(id, sumManopera);
            }
        }

        for (String idMasina : perechiIDSumManopera.keySet()) {
            masinaDescSumManopera.add(new MasinaCuSumaManopera(masinaRepository.read(idMasina), perechiIDSumManopera.get(idMasina)));
        }
        masinaDescSumManopera.sort(new Comparator<MasinaCuSumaManopera>() {
            @Override
            public int compare(MasinaCuSumaManopera o1, MasinaCuSumaManopera o2) {
                return Double.compare(o2.getSumaManopera(), o1.getSumaManopera());
            }
        });
        return masinaDescSumManopera;
    }
    public String search(String patternCautat){
        Pattern pattern=Pattern.compile(patternCautat); // se creeaza regex pentru elemente
        String elementeGasite=""; // vom colecta elementele gasite in acest string
        for(Masina masinaCurenta:masinaRepository.read()){
            Matcher matcher =pattern.matcher(masinaCurenta.toString());
            if(matcher.find()){
                elementeGasite+=masinaCurenta.toString()+"\n";
            }
        }
        return elementeGasite;
    }
    public void actualizareGarantie(){
        for (Masina masinaCurenta:masinaRepository.read()) {
            int anAchizitie=masinaCurenta.getAnAchizitie();
            int anCurent=Calendar.getInstance().get(Calendar.YEAR);
            int varstaMasina=anCurent-anAchizitie;
            if(varstaMasina>3 || masinaCurenta.getNrKm()>60000){
                masinaCurenta.setInGarantie(false);
                System.out.println("La masina cu id-ul: "+masinaCurenta.getId()+" nu mai este in garantie");
            }
        }
    }
}
