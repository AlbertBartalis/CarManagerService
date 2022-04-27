package tests.Validator;

import ExceptieProprie.ExceptiiMasina;
import ExceptieProprie.ExceptiiTranzactie;
import domain.Client;
import domain.Masina;
import domain.Tranzactie;
import domain.TranzactieValidator;
import org.junit.jupiter.api.Test;
import repository.IRepository;
import repository.InMemoryRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

import static org.junit.jupiter.api.Assertions.*;

class TranzactieValidatorTest {
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.uuuu").withResolverStyle(ResolverStyle.STRICT);
    DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("dd.MM.uuuu HH:mm").withResolverStyle(ResolverStyle.STRICT);

    IRepository<Masina> getTestMasinaRepository(){
        IRepository<Masina> masinaIRepositoryMasina=new InMemoryRepository<>();
        masinaIRepositoryMasina.create(new Masina("1","BMW",2010,70000,true));
        masinaIRepositoryMasina.create(new Masina("2","VW",2009,150000,false));
        masinaIRepositoryMasina.create(new Masina("3","Dacia",2011,155000,true));
        return masinaIRepositoryMasina;
    }
    IRepository<Client> getClientRepository() {
        IRepository<Client> repositoryTest = new InMemoryRepository<>();
        repositoryTest.create(new Client("1", "Maria", "Maldarasanu", "25581561561", LocalDate.parse("19.12.2002", dateFormatter), LocalDate.parse("01.03.2020", dateFormatter)));
        repositoryTest.create(new Client("2", "Iulia", "Silviescu", "245154154145", LocalDate.parse("04.12.2000", dateFormatter), LocalDate.parse("04.03.2018", dateFormatter)));
        repositoryTest.create(new Client("3", "Robert", "Radulescu", "184841684186", LocalDate.parse("25.05.1998", dateFormatter), LocalDate.parse("04.03.2018", dateFormatter)));
        return repositoryTest;
    }
    IRepository<Tranzactie> getTranzactieRepository(){
        IRepository<Tranzactie> tranzactieRepository=new InMemoryRepository<>();
        tranzactieRepository.create(new Tranzactie("1","1","2",100,100, LocalDateTime.parse("27.02.2022 10:23",dateTimeFormatter)));
        return tranzactieRepository;
    }
    TranzactieValidator getTranzactieValidator(){
        TranzactieValidator tranzactieValidator=new TranzactieValidator();
        return tranzactieValidator;
    }
    @Test
    void validareTranzactie() {
        IRepository<Tranzactie>tranzactieIRepository=getTranzactieRepository();
        IRepository<Client>clientIRepository=getClientRepository();
        IRepository<Masina>masinaIRepository=getTestMasinaRepository();
        TranzactieValidator tranzactieValidator=getTranzactieValidator();
        Tranzactie tranzactie=new Tranzactie("4","1","2",100,-100, LocalDateTime.parse("27.02.2022 10:23",dateTimeFormatter));
        try{
            // masinaService.updateMasina(masina);
            tranzactieValidator.validareTranzactie(tranzactie,tranzactieIRepository,masinaIRepository,clientIRepository,"test");
            ////fail();
        }catch (ExceptiiTranzactie e){
            assertEquals("Manopera nu poate fi suma negativa!\n",e.getMessage());
        }
    }
}