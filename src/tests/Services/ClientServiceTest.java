package tests.Services;

import domain.*;
import org.junit.jupiter.api.Assertions;
import repository.IRepository;
import repository.InMemoryRepository;
import service.ClientService;
import service.TranzactieService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClientServiceTest {
    TranzactieValidator tranzactieValidator=new TranzactieValidator();
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.uuuu").withResolverStyle(ResolverStyle.STRICT);
    DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("dd.MM.uuuu HH:mm").withResolverStyle(ResolverStyle.STRICT);
    IRepository<Tranzactie> getTranzactieRepository(){
        IRepository<Tranzactie> tranzactieRepository=new InMemoryRepository<>();
        tranzactieRepository.create(new Tranzactie("1","1","2",100,100, LocalDateTime.parse("27.02.2022 10:23",dateTimeFormatter)));
        return tranzactieRepository;
    }

    ClientValidator getClientValidator(){
        ClientValidator clientValidator=new ClientValidator();
        return clientValidator;
    }
    IRepository<Client> getClientRepository() {
        IRepository<Client> repositoryTest = new InMemoryRepository<>();
        repositoryTest.create(new Client("1", "Maria", "Maldarasanu", "25581561561", LocalDate.parse("19.12.2002", dateFormatter), LocalDate.parse("01.03.2020", dateFormatter)));
        repositoryTest.create(new Client("2", "Iulia", "Silviescu", "245154154145", LocalDate.parse("04.12.2000", dateFormatter), LocalDate.parse("04.03.2018", dateFormatter)));
        repositoryTest.create(new Client("3", "Robert", "Radulescu", "184841684186", LocalDate.parse("25.05.1998", dateFormatter), LocalDate.parse("04.03.2018", dateFormatter)));
        return repositoryTest;
    }
    IRepository<Masina> getTestMasinaRepository(){
        IRepository<Masina> masinaIRepositoryMasina=new InMemoryRepository<>();
        masinaIRepositoryMasina.create(new Masina("1","BMW",2010,50000,true));
        masinaIRepositoryMasina.create(new Masina("2","VW",2009,150000,false));
        masinaIRepositoryMasina.create(new Masina("3","Dacia",2011,155000,true));
        return masinaIRepositoryMasina;
    }
    TranzactieService getTranzactieService(){
        TranzactieService tranzactieService=new TranzactieService(getTestMasinaRepository(),getClientRepository(),getTranzactieRepository(),tranzactieValidator);
        return tranzactieService;
    }
    ClientService getClientService(){
        ClientService clientService=new ClientService(getClientRepository(),getTranzactieRepository(),getTestMasinaRepository(),getClientValidator());
        return clientService;
    }

    @org.junit.jupiter.api.Test
    void addClient() {
        ClientService clientService=getClientService();
        clientService.addClient("4", "NumeTest", "PrenumeTest", "19411111111", LocalDate.parse("29.11.1992", dateFormatter), LocalDate.parse("11.11.2011", dateFormatter));
        List<Client>clientList=clientService.getAll();
        assertEquals(4,clientList.size());
    }

    @org.junit.jupiter.api.Test
    void updateClient() {
        ClientService clientService=getClientService();
        clientService.updateClient("3", "NumeTestUpdate", "PrenumeTest", "19411111111", LocalDate.parse("29.11.1992", dateFormatter), LocalDate.parse("11.11.2011", dateFormatter));
        List<Client>clientList=clientService.getAll();
        Client clientUpdatat=clientList.get(2);
        assertEquals("NumeTestUpdate",clientUpdatat.getNume());
    }

    @org.junit.jupiter.api.Test
    void deleteClient() {
        ClientService clientService=getClientService();
        clientService.deleteClient("1");
        List<Client>clientList=clientService.getAll();
        assertEquals(2, clientList.size());
    }

    @org.junit.jupiter.api.Test
    void getAll() {
        ClientService clientService=getClientService();
        List<Client>clientList=clientService.getAll();
        assertEquals(3,clientList.size());
    }

    @org.junit.jupiter.api.Test
    void carduriCuReducereDescrescator() {
        ClientService clientService=getClientService();
        TranzactieService tranzactieService=getTranzactieService();
        tranzactieService.addTranzactie("4","1","3",100,100,LocalDateTime.parse("27.02.2022 10:23",dateTimeFormatter));
        List<Tranzactie>tranzactieList=tranzactieService.getAll();
        assertEquals(90,tranzactieList.get(1).getSumaManopera());
    }

    @org.junit.jupiter.api.Test
    void search() {
        String stringTest="1";
        ClientService clientServiceTest=getClientService();
        String patternGasit=clientServiceTest.search(stringTest);
        assertTrue(patternGasit.contains("1"));
    }
}