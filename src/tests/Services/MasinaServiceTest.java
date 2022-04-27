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

class MasinaServiceTest {

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

    @Test
    void addMasina() {
        MasinaService masinaService=getMasinaService();
        masinaService.addMasina("4","Rover",2007,2400000,true);
        List<Masina>masinaList=masinaService.getAll();
        assertEquals(4,masinaList.size());
    }

    @Test
    void updateMasina() {
        MasinaService masinaService=getMasinaService();
        masinaService.updateMasina("3","Rover",2007,2400000,true);
        List<Masina>listaMasini=masinaService.getAll();
        assertEquals("Rover",listaMasini.get(2).getModel());
    }

    @Test
    void deleteMasina() {
        MasinaService masinaService=getMasinaService();
        masinaService.deleteMasina("1");

        assertEquals(2, masinaService.getAll().size());
    }

    @Test
    void getAll() {
        List<Masina> listaMasina=getMasinaService().getAll();
        assertEquals(3,listaMasina.size());
    }

    @Test
    void afisareMasiniDescrescatorSumManopera() {
        MasinaService masinaService=getMasinaService();
        masinaService.addMasina("4","Rover",2007,2400000,true);
        List<MasinaCuSumaManopera>masiniSortate=masinaService.afisareMasiniDescrescatorSumManopera();
        assertEquals(1,masiniSortate.size());
    }

    @Test
    void search() {
        String stringTest="1";
        MasinaService masinaService=getMasinaService();
        String patternGasit=masinaService.search(stringTest);
        assertTrue(patternGasit.contains("1"));
    }

    @Test
    void actualizareGarantie() {

        IRepository<Masina> masinaIRepository=getTestMasinaRepository();
        MasinaService masinaService=getMasinaService();
        masinaService.actualizareGarantie();
        Masina masina=masinaIRepository.read("1");
        assertEquals(masina.isInGarantie()==true,masina.isInGarantie());
    }
}