package userInterface;

import domain.Client;
import domain.Masina;
import domain.Tranzactie;
import service.ClientService;
import service.MasinaService;
import service.TranzactieService;

import java.io.Console;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class console {

    private TranzactieService tranzactieService;
    private MasinaService masinaService;
    private ClientService clientService;
    private DateTimeFormatter dateFormatter;
    private DateTimeFormatter dateTimeFormatter;

    public console(TranzactieService tranzactieService, MasinaService masinaService,
                   ClientService clientService, DateTimeFormatter dateFormatter, DateTimeFormatter dateTimeFormatter) {
        this.tranzactieService = tranzactieService;
        this.masinaService = masinaService;
        this.clientService = clientService;
        this.dateFormatter = dateFormatter;
        this.dateTimeFormatter = dateTimeFormatter;
    }

    Scanner scanner = new Scanner(System.in);

    private int GetInteger(String ceFace) {
        System.out.print(ceFace);
        int x = Integer.parseInt(scanner.nextLine());
        return x;
    }

    private String getString(String ceFace) {
        String string;
        System.out.println(ceFace);
        string = scanner.nextLine();
        return string;
    }

    private double getDouble(String ceFace) {
        double x;
        System.out.println(ceFace);
        x = Double.parseDouble(scanner.nextLine());
        return x;
    }

    public void runUI() {
        int optiune = -1;
        while (optiune != 0) {
            this.showMenu();
            optiune = GetInteger("Optiune:");
            switch (optiune) {
                case 1:
                    handleAdaugaMasina();
                    break;
                case 2:
                    handleUpdateMasina();
                    break;
                case 3:
                    handleDeleteMasina();
                    break;
                case 4:
                    handleShowMasini();
                    break;
                case 5:
                    handleAdaugaCardClient();
                    break;
                case 6:
                    handleUpdateClient();
                    break;
                case 7:
                    handleStergeClient();
                    break;
                case 8:
                    handleShowClienti();
                    break;
                case 9:
                    handleAdaugaTranzactie();
                    break;
                case 10:
                    handleUpdateTranzactie();
                    break;
                case 11:
                    handleDeleteTranzactie();
                    break;
                case 12:
                    handleShowTranzactii();
                    break;
                case 13:
                    handleAfisareTranzactiiInterval();
                    break;
                case 14:
                    handleOrdonareMasiniSumManopera();
                    break;
                case 15:
                    handleOrdonareClientOrdDescReduceri();
                    break;
                case 16:
                    handleStergeTranzactiiIntervalZile();
                    break;
                case 17:
                    handleCautareFullText();
                    break;
                case 18:
                    handleActualizareGarantieMasina();
                    break;
            }
        }
    }

    private void handleActualizareGarantieMasina() {
        masinaService.actualizareGarantie();
    }

    private void printString(String toPrint, String ceFace) {
        System.out.println(ceFace);
        System.out.println(toPrint);
    }

    private void handleCautareFullText() {
        String pattern = getString("Pattern-ul cautat: ");
        String masiniCuPattern;
        String cardClientCuPattern;
        masiniCuPattern = masinaService.search(pattern);
        printString(masiniCuPattern, "Pattern gasit in obiectele de tip masina");
        cardClientCuPattern = clientService.search(pattern);
        printString(cardClientCuPattern, "Pattern gasit in obiectele de tip client");
    }

    private LocalDateTime getDataSiOra(String ceFace) { // vom citi de la tastatura captele de interval in care vreom sa stergem tranzactiile
        String dataSiOraString = getString(ceFace);
        LocalDateTime dataSiOra = LocalDateTime.parse(dataSiOraString, dateTimeFormatter);
        return dataSiOra;
    }

    private void handleStergeTranzactiiIntervalZile() {
        LocalDateTime dataInceput = getDataSiOra("Data si Ora Inceput");
        LocalDateTime dataFinal = getDataSiOra("Data si Ora sfarsit");
        tranzactieService.stergeTranzactiiIntervalZile(dataInceput, dataFinal);
    }

    private void handleOrdonareClientOrdDescReduceri() {
        clientService.carduriCuReducereDescrescator().forEach(System.out::println);
    }

    private void handleOrdonareMasiniSumManopera() {
        masinaService.afisareMasiniDescrescatorSumManopera().forEach(System.out::println);
    }

    private void afisareLista(List<Tranzactie> listaTranzactii) {
        for (Tranzactie tranzactieCurenta : listaTranzactii) {
            System.out.println("Tranzactie id:" + tranzactieCurenta.getId() + "ID masina: " + tranzactieCurenta.getId_masina() + "ID client " + tranzactieCurenta.getIdCardClient() + " suma manopera " + tranzactieCurenta.getSumaManopera() + " suma piese: " + tranzactieCurenta.getSumaPiese() + "\n");
        }
    }

    private void handleAfisareTranzactiiInterval() {
        double sumaStart = getDouble("Limita inferioara suma tranzactie:");
        double sumaEnd = getDouble("Limita superioara suma tranzactie:");
        List<Tranzactie> tranzactiiInterval = tranzactieService.afisareTranzactiiInterval(sumaStart, sumaEnd);
        afisareLista(tranzactiiInterval);
    }

    private Tranzactie getInformatiiTranzactie() {
        Tranzactie tranzactieNoua = null;
        try {
            String id = getString("Id-ul tranzactiei:");
            String id_masina = getString("Id-ul masinii:");
            String idCardClient = getString("Id-ul cardului client:");
            double sumaPiese = getDouble("Suma piese:");
            double sumaManopera = getDouble("Suma manopera:");
            String dataSiOraString = getString("Data si ora");
            LocalDateTime dataSiOra = LocalDateTime.parse(dataSiOraString, dateTimeFormatter);

            tranzactieNoua = new Tranzactie(id, id_masina, idCardClient, sumaPiese, sumaManopera, dataSiOra);
        } catch (DateTimeParseException dtp) {
            System.out.println("Nu e corect formatul de data si ora dd.mm.yyyy hh:mm!");
        } catch (RuntimeException rex) {
            System.out.println(rex.getMessage());
        }
        return tranzactieNoua;
    }

    private void handleDeleteTranzactie() {
        String idTranzactieDeSters = getString("Id-ul tranzactiei:");
        tranzactieService.deleteTranzactie(idTranzactieDeSters);
    }

    private void handleUpdateTranzactie() {
        Tranzactie tranzactieUpdatata = getInformatiiTranzactie();
        tranzactieService.updateTranzactie(tranzactieUpdatata.getId(), tranzactieUpdatata.getId_masina(), tranzactieUpdatata.getIdCardClient(), tranzactieUpdatata.getSumaPiese(), tranzactieUpdatata.getSumaManopera(), tranzactieUpdatata.getDataSiOra());
    }

    private void handleAdaugaTranzactie() {
        Tranzactie tranzactieNoua = getInformatiiTranzactie();
        tranzactieService.addTranzactie(tranzactieNoua.getId(), tranzactieNoua.getId_masina(), tranzactieNoua.getIdCardClient(), tranzactieNoua.getSumaPiese(), tranzactieNoua.getSumaManopera(), tranzactieNoua.getDataSiOra());
    }

    private void handleShowTranzactii() {
        for (Tranzactie tranzactieCurenta : tranzactieService.getAll()) {
            System.out.println(tranzactieCurenta);
        }
    }

    private void handleStergeClient() {
        String idClientDeSters = getString("Id Client:");
        clientService.deleteClient(idClientDeSters);
    }

    private void handleUpdateClient() {
        Client clientModificat = getInformatiiCardClient();
        clientService.updateClient(clientModificat.getId(), clientModificat.getNume(), clientModificat.getPrenume(),
                clientModificat.getCnp(), clientModificat.getDataNasterii(), clientModificat.getDataInregistrarii());
    }

    private void handleShowClienti() {
        for (Client clientCurent : clientService.getAll()) {
            System.out.println(clientCurent);
        }
    }

    private Client getInformatiiCardClient() {
        Client clientNou = null;
        try {
            String id = getString("Id client:");
            String nume = getString("Nume:");
            String prenume = getString("Prenume:");
            String cnp = getString("CNP:");
            String dataNasteriiString = getString("Data nasterii:"); // voi folosi SimpleDateFormat
            String dataInregistrariiString = getString("Data inregistrarii:"); // voi folosi SimpleDateFormat
            LocalDate dataNasterii = LocalDate.parse(dataNasteriiString, dateFormatter);
            LocalDate dataInregistrarii = LocalDate.parse(dataInregistrariiString, dateFormatter);

            clientNou = new Client(id, nume, prenume, cnp, dataNasterii, dataInregistrarii);
        } catch (RuntimeException rex) {
            System.out.println(rex.getMessage());
        }
        return clientNou;
    }

    private void handleAdaugaCardClient() {
        Client clientNou = getInformatiiCardClient();
        clientService.addClient(clientNou.getId(), clientNou.getNume(), clientNou.getPrenume(),
                clientNou.getCnp(), clientNou.getDataNasterii(), clientNou.getDataInregistrarii());

    }

    private void handleDeleteMasina() {
        String idMasinaDeSters = getString("Id-ul masinii de sters:");
        masinaService.deleteMasina(idMasinaDeSters);
    }

    private void handleUpdateMasina() {
        Masina masinaModificata = getInformatiiMasina();
        masinaService.updateMasina(masinaModificata.getId(),
                masinaModificata.getModel(),
                masinaModificata.getAnAchizitie(),
                masinaModificata.getNrKm(),
                masinaModificata.isInGarantie());
    }

    private void handleShowMasini() {
        for (Masina masinaCurenta : masinaService.getAll()) {
            System.out.println(masinaCurenta);
        }
    }

    private Masina getInformatiiMasina() {
        Masina masinaNoua = null;
        try {
            String id = getString("Dati id-ul masinii (unic):");
            String model = getString("Dati modelul:");
            int anAchizitie = GetInteger("Dati anul achizitiei:");
            int km = GetInteger("Dati nr. km (> 0):");
            int Garantie = GetInteger("Dati inGarantie");
            boolean garantie;
            if (Garantie > 0) {
                garantie = true;
            } else {
                garantie = false;
            }
            masinaNoua = new Masina(id, model, anAchizitie, km, garantie);
        } catch (RuntimeException rex) {
            System.out.println(rex.getMessage());
        }
        return masinaNoua;
    }

    private void handleAdaugaMasina() {
        Masina masinaNoua = getInformatiiMasina();
        this.masinaService.addMasina(masinaNoua.getId(),
                masinaNoua.getModel(),
                masinaNoua.getAnAchizitie(),
                masinaNoua.getNrKm(),
                masinaNoua.isInGarantie());
    }

    private void showMenu() {
        System.out.println("1. Adauga masina");
        System.out.println("2. Updateaza masina");
        System.out.println("3. Sterge masina");
        System.out.println("4. Afiseaza masini");
        System.out.println("5. Adauga card client");
        System.out.println("6. Updateaza card client");
        System.out.println("7. Sterge card client");
        System.out.println("8. Afiseaza clienti");
        System.out.println("9. Adauga tranzactie");
        System.out.println("10. Updateaza tranzactie");
        System.out.println("11. Sterge tranzactie");
        System.out.println("12. Afiseaza tranzactii");
        System.out.println("13. Afișarea tuturor tranzacțiilor cu suma cuprinsă într-un interval dat");
        System.out.println("14. Afișarea mașinilor ordonate descrescător după suma obținută pe manoperă");
        System.out.println("15. Afișarea cardurilor client ordonate descrescător după valoarea reducerilor obținute");
        System.out.println("16. Ștergerea tuturor tranzacțiilor dintr-un anumit interval de zile");
        System.out.println("17. Căutare mașini și clienți. Căutare full text");
        System.out.println("18. Actualizarea garanției la fiecare mașină");
    }
}
