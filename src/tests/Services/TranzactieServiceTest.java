package tests.Services;

import domain.*;
import org.junit.jupiter.api.Test;
import repository.IRepository;
import repository.InMemoryRepository;
import service.ClientService;
import service.MasinaService;
import service.TranzactieService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TranzactieServiceTest {
    TranzactieValidator tranzactieValidator=new TranzactieValidator();
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.uuuu").withResolverStyle(ResolverStyle.STRICT);
    DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("dd.MM.uuuu HH:mm").withResolverStyle(ResolverStyle.STRICT);

    MasinaService getMasinaService(){
        MasinaService masinaService=new MasinaService(getTestMasinaRepository(),getTranzactieRepository(),getMasinaValidoator());
        return masinaService;
    }
    IRepository<Client> getClientRepository() {
        IRepository<Client> repositoryTest = new InMemoryRepository<>();
        repositoryTest.create(new Client("1", "Maria", "Maldarasanu", "25581561561", LocalDate.parse("19.12.2002", dateFormatter), LocalDate.parse("01.03.2020", dateFormatter)));
        repositoryTest.create(new Client("2", "Iulia", "Silviescu", "245154154145", LocalDate.parse("04.12.2000", dateFormatter), LocalDate.parse("04.03.2018", dateFormatter)));
        repositoryTest.create(new Client("3", "Robert", "Radulescu", "184841684186", LocalDate.parse("25.05.1998", dateFormatter), LocalDate.parse("04.03.2018", dateFormatter)));
        return repositoryTest;
    }
    MasinaValidator getMasinaValidoator(){
        MasinaValidator masinaValidator=new MasinaValidator();
        return masinaValidator;
    }
    ClientValidator getClientValidator(){
        ClientValidator clientValidator=new ClientValidator();
        return clientValidator;
    }
    TranzactieService getTranzactieService(){
        TranzactieService tranzactieService=new TranzactieService(getTestMasinaRepository(),getClientRepository(),getTranzactieRepository(),tranzactieValidator);
        return tranzactieService;
    }
    ClientService getClientService(){
        ClientService clientService=new ClientService(getClientRepository(),getTranzactieRepository(),getTestMasinaRepository(),getClientValidator());
        return clientService;
    }
    IRepository<Masina> getTestMasinaRepository(){
        IRepository<Masina> masinaIRepositoryMasina=new InMemoryRepository<>();
        masinaIRepositoryMasina.create(new Masina("1","BMW",2010,70000,true));
        masinaIRepositoryMasina.create(new Masina("2","VW",2009,150000,false));
        masinaIRepositoryMasina.create(new Masina("3","Dacia",2011,155000,true));
        return masinaIRepositoryMasina;
    }
    IRepository<Tranzactie> getTranzactieRepository(){
        IRepository<Tranzactie> tranzactieRepository=new InMemoryRepository<>();
        tranzactieRepository.create(new Tranzactie("1","1","2",100,100, LocalDateTime.parse("27.02.2022 10:23",dateTimeFormatter)));

        return tranzactieRepository;
    }
    MasinaValidator getTestMasinaValidator(Masina masinaTest,String ceFace){
        MasinaValidator masinaValidatorTest=new MasinaValidator();
        masinaValidatorTest.validate(masinaTest,getTestMasinaRepository(),ceFace);
        return masinaValidatorTest;
    }
    TranzactieValidator getTranzactieValidator(){
        TranzactieValidator tranzactieValidator=new TranzactieValidator();
        return tranzactieValidator;
    }

    @Test
    void addTranzactie() {
        TranzactieService tranzactieService=new TranzactieService(getTestMasinaRepository(),getClientRepository(),getTranzactieRepository(),getTranzactieValidator());
        tranzactieService.addTranzactie("4","4","3",500,400,LocalDateTime.parse("27.02.2022 10:23",dateTimeFormatter));
        assertEquals(1,getTranzactieRepository().read().size());
    }

    @Test
    void updateTranzactie() {
        Tranzactie tranzactieUpdate = new Tranzactie("4","1","3",500,400,LocalDateTime.parse("27.02.2022 10:23",dateTimeFormatter));
        TranzactieValidator tranzactieValidator=getTranzactieValidator();
        IRepository<Tranzactie> repositoryTest=getTranzactieRepository();
        TranzactieService service=getTranzactieService();
        service.addTranzactie("3","1","3",600,400,LocalDateTime.parse("27.02.2022 10:23",dateTimeFormatter));
        service.updateTranzactie("3","1","3",360,400,LocalDateTime.parse("27.02.2022 10:23",dateTimeFormatter));
        List<Tranzactie> tranzactieList=service.getAll();
        assertEquals(360,tranzactieList.get(1).getSumaManopera());
    }

    @Test
    void deleteTranzactie() {
        TranzactieService tranzactieService=getTranzactieService();
        tranzactieService.deleteTranzactie("1");
        assertEquals(0,tranzactieService.getAll().size());
    }

    @Test
    void getAll() {
        TranzactieService tranzactieService=getTranzactieService();
        List<Tranzactie>listaTranzactii=tranzactieService.getAll();
        assertEquals(1,listaTranzactii.size());
    }

    @Test
    void afisareTranzactiiInterval() {
        double sumaStart=50;
        double sumaEnd=800;
        TranzactieService tranzactieService=getTranzactieService();
        tranzactieService.addTranzactie("4","1","1",600,500,LocalDateTime.parse("27.02.2022 10:23",dateTimeFormatter));
        tranzactieService.addTranzactie("2","3","2",600,400,LocalDateTime.parse("27.02.2022 10:23",dateTimeFormatter));
        tranzactieService.addTranzactie("3","2","3",600,4100,LocalDateTime.parse("27.02.2022 10:23",dateTimeFormatter));
        List<Tranzactie>tranzactieList= tranzactieService.afisareTranzactiiInterval(sumaStart,sumaEnd);
        assertEquals(3,tranzactieList.size());
    }

    @Test
    void stergeTranzactiiIntervalZile() {
        LocalDateTime startDate=LocalDateTime.parse("27.01.2022 10:23",dateTimeFormatter);
        LocalDateTime endDate=LocalDateTime.parse("03.03.2022 10:23",dateTimeFormatter);
        TranzactieService tranzactieService=getTranzactieService();
        tranzactieService.stergeTranzactiiIntervalZile(startDate,endDate);
        assertEquals(0,tranzactieService.getAll().size());
    }
}