package service;

import domain.*;
import repository.IRepository;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientService {
    IRepository<Client> clientRepository;
    IRepository<Tranzactie> tranzactieIRepository;
    IRepository<Masina> masinaIRepository;
    ClientValidator clientValidator;

    public ClientService(IRepository<Client> clientRepository, IRepository<Tranzactie> tranzactieIRepository, IRepository<Masina> masinaIRepository, ClientValidator clientValidator) {
        this.clientRepository = clientRepository;
        this.tranzactieIRepository = tranzactieIRepository;
        this.masinaIRepository = masinaIRepository;
        this.clientValidator = clientValidator;
    }

    public void addClient(String id, String nume, String prenume, String cnp, LocalDate dataNasterii, LocalDate dataInregistrarii) {
        Client clientNou = new Client(id, nume, prenume, cnp, dataNasterii, dataInregistrarii);
        clientValidator.validareClient(clientNou, clientRepository, "add");
        clientRepository.create(clientNou);
    }

    public void updateClient(String id, String nume, String prenume, String cnp, LocalDate dataNasterii, LocalDate dataInregistrarii) {
        Client clientUpdate = new Client(id, nume, prenume, cnp, dataNasterii, dataInregistrarii);
        clientValidator.validareClient(clientUpdate, clientRepository, "update");
        clientRepository.update(clientUpdate);
    }

    public void deleteClient(String id) {
        clientRepository.delete(id);
    }

    public List<Client> getAll() {
        return clientRepository.read();
    }

    private double caculeazSumaEconomisitaCuaGarantie(Tranzactie tranzactie, String idMasina) {
        for (Masina masinaCurenta : masinaIRepository.read()) {
            if (masinaCurenta.getId().equals(idMasina)) { // cautam masina cu ID ul trimis ca parametru metodei
                if (masinaCurenta.isInGarantie()) { // daca masina cu id-ul idMasina este in garantie setam suma Pieselor 0
                    return tranzactie.getSumaPiese();
                }
            }
        }
        return 0;
    }

    private double calculeazaReducere(Tranzactie tranzactie) {
        double reducere = 0.1;
        double reducereAplicata = tranzactie.getSumaManopera() * 0.1;
        return reducereAplicata;
    }

    public List<CarduriClientCuReduceri> carduriCuReducereDescrescator() {
        List<CarduriClientCuReduceri> carduriClientCuReduceris = new ArrayList<>();
        Map<String, Double> perechiIdReducere = new HashMap<String, Double>();
        for (Tranzactie tranzactieCurenta : tranzactieIRepository.read()) { // iterez prin tranzactii si adun reducerile (unde este cazul)
            double reducereAplicata = 0;
            double reducereDinGarantie = 0;
            if (!tranzactieCurenta.getIdCardClient().equals("0")) {
                reducereAplicata = calculeazaReducere(tranzactieCurenta);
            } else {
                reducereAplicata = 0;
            }
            if (masinaIRepository.read(tranzactieCurenta.getId_masina()).isInGarantie()==true) {
                reducereDinGarantie = caculeazSumaEconomisitaCuaGarantie(tranzactieCurenta, tranzactieCurenta.getId_masina());
            } else {
                reducereDinGarantie = 0;
            }
            double suma=reducereAplicata+reducereDinGarantie; // calculam reducerea totala
            perechiIdReducere.put(tranzactieCurenta.getIdCardClient(),suma); // pun reducerea acordata in dictionar impreuna cu id-ul client eferent
        }
        for (String idClient: perechiIdReducere.keySet()) {
            carduriClientCuReduceris.add(new CarduriClientCuReduceri(clientRepository.read(idClient),perechiIdReducere.get(idClient)));
        }
        carduriClientCuReduceris.sort(new Comparator<CarduriClientCuReduceri>() {
            @Override
            public int compare(CarduriClientCuReduceri o1, CarduriClientCuReduceri o2) {
                return Double.compare(o2.getReducere(),o1.getReducere());
            }
        });
        return carduriClientCuReduceris;
    }
    public String search(String patternCautat){
        Pattern pattern=Pattern.compile(patternCautat); // se creeaza regex pentru elemente
        String elementeGasite=""; // vom colecta elementele gasite in acest string
        for(Client clientCurent:clientRepository.read()){
            Matcher matcher =pattern.matcher(clientCurent.toString());
            if(matcher.find()){
                elementeGasite+=clientCurent.toString()+"\n";
            }
        }
        return elementeGasite;
    }
}
