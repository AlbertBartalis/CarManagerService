package tests.Validator;

import ExceptieProprie.ExceptiiMasina;
import domain.Client;
import domain.Masina;
import domain.MasinaValidator;
import domain.Tranzactie;
import org.junit.jupiter.api.Test;
import repository.IRepository;
import repository.InMemoryRepository;
import service.MasinaService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

import static org.junit.jupiter.api.Assertions.*;

class MasinaValidatorTest {

    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.uuuu").withResolverStyle(ResolverStyle.STRICT);
    DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("dd.MM.uuuu HH:mm").withResolverStyle(ResolverStyle.STRICT);
    MasinaService getMasinaService(){
        MasinaService masinaService=new MasinaService(getTestMasinaRepository(),getTranzactieRepository(),getMasinaValidoator());
        return masinaService;
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
    MasinaValidator getMasinaValidoator(){
        MasinaValidator masinaValidator=new MasinaValidator();
        return masinaValidator;
    }
    @Test
    void validate() {
        MasinaValidator masinaValidator=getMasinaValidoator();
        Masina masina =new Masina("5","VW",2009,150000,false);

        try{
           // masinaService.updateMasina(masina);
            masinaValidator.validate(masina,getTestMasinaRepository(),"test");
            ////fail();
        }catch (ExceptiiMasina e){
            assertEquals("Nu exista o masina cu id-ul"+masina.getId()+"\n",e.getMessage());
        }


    }
}