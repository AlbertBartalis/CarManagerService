package service;

import domain.*;
import repository.IRepository;

import repository.InMemoryRepository;


import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TranzactieService {
    private TranzactieValidator tranzactieValidator;
    private IRepository<Tranzactie> tranzactieRepository;
    private IRepository<Masina> masinaRepository;
    private IRepository<Client> clientRepository;

    public TranzactieService(IRepository<Masina> masinaRepository, IRepository<Client> clientRepository, IRepository<Tranzactie> tranzactieRepository, TranzactieValidator tranzactieValidator) {
        this.masinaRepository = masinaRepository;
        this.clientRepository = clientRepository;
        this.tranzactieRepository = tranzactieRepository;
        this.tranzactieValidator = tranzactieValidator;
    }

    public void addTranzactie(String id, String id_masina, String idCardClient, double sumaPiese, double sumaManopera, LocalDateTime dataSiOra) {
        Tranzactie tranzactie = new Tranzactie(id, id_masina, idCardClient, sumaPiese, sumaManopera, dataSiOra); // // vedem de reducere
        if (!idCardClient.equals("0")) {
            aplicareReducere(tranzactie);
        }
        aplicareGarantie(tranzactie, id_masina); // metoda va verifica, daca masina cu id_masina are garantie, daca da => suma piese=0
        tranzactieValidator.validareTranzactie(tranzactie, tranzactieRepository, masinaRepository, clientRepository, "adauga");
        this.tranzactieRepository.create(tranzactie);
    }

    public void updateTranzactie(String id, String id_masina, String idCardClient, double sumaPiese, double sumaManopera, LocalDateTime dataSiOra) {
        Tranzactie tranzactieUpdate = new Tranzactie(id, id_masina, idCardClient, sumaPiese, sumaManopera, dataSiOra); // vedem de reducere
        if (!idCardClient.equals("0")) {
            aplicareReducere(tranzactieUpdate);
        }
        aplicareGarantie(tranzactieUpdate, id_masina); // metoda va verifica, daca masina cu id_masina are garantie, daca da => suma piese=0
        tranzactieValidator.validareTranzactie(tranzactieUpdate, tranzactieRepository, masinaRepository, clientRepository, "update");
        tranzactieRepository.update(tranzactieUpdate);
    }

    public void deleteTranzactie(String id) {
        tranzactieRepository.delete(id);
    }

    public List<Tranzactie> getAll() {
        return this.tranzactieRepository.read();
    }

    private void aplicareGarantie(Tranzactie tranzactie, String idMasina) {
        for (Masina masinaCurenta : masinaRepository.read()) {
            if (masinaCurenta.getId().equals(idMasina)) { // cautam masina cu ID ul trimis ca parametru metodei
                if (masinaCurenta.isInGarantie()) { // daca masina cu id-ul idMasina este in garantie setam suma Pieselor 0
                    tranzactie.setSumaPiese(0);
                    System.out.println("Masina cu id-ul " + idMasina + " este in garantie, astfel piesele sunt gratuite \n");
                }
            }
        }
    }

    private void aplicareReducere(Tranzactie tranzactie) {
        double reducere = 0.1;
        double manoperaRedusa = tranzactie.getSumaManopera() - tranzactie.getSumaManopera() * 0.1;
        System.out.println(tranzactie.getIdCardClient() + " a avut reducere 10%, in loc de " + tranzactie.getSumaManopera() + " a platit " + manoperaRedusa);
        tranzactie.setSumaManopera(manoperaRedusa);
    }

    public List<Tranzactie> afisareTranzactiiInterval(double sumaStart, double sumaEnd) {
        List<Tranzactie> tranzactiiValidate = new ArrayList<Tranzactie>();
        for (Tranzactie tranzactieCurenta : tranzactieRepository.read()) {
            double totalSumaTranzactie = tranzactieCurenta.getSumaManopera() + tranzactieCurenta.getSumaPiese();
            if (totalSumaTranzactie >= sumaStart && totalSumaTranzactie <= sumaEnd) {
                tranzactiiValidate.add(tranzactieCurenta);
            }
        }
        return tranzactiiValidate;
    }

    public void stergeTranzactiiIntervalZile(LocalDateTime startDate, LocalDateTime endDate) {
        for (Tranzactie tranzactie : tranzactieRepository.read()) {
            if (tranzactie.getDataSiOra().compareTo(startDate) > 0 && tranzactie.getDataSiOra().compareTo(endDate) < 0) {
                tranzactieRepository.delete(tranzactie.getId());
            }
        }
    }

}
