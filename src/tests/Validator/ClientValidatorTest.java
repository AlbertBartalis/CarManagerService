package tests.Validator;

import ExceptieProprie.ExceptiiClient;
import ExceptieProprie.ExceptiiMasina;
import domain.Client;
import domain.ClientValidator;
import org.junit.jupiter.api.Test;
import repository.IRepository;
import repository.InMemoryRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

import static org.junit.jupiter.api.Assertions.*;

class ClientValidatorTest {
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.uuuu").withResolverStyle(ResolverStyle.STRICT);
    DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("dd.MM.uuuu HH:mm").withResolverStyle(ResolverStyle.STRICT);
    IRepository<Client> getClientRepository() {
        IRepository<Client> repositoryTest = new InMemoryRepository<>();
        repositoryTest.create(new Client("1", "Maria", "Maldarasanu", "25581561561", LocalDate.parse("19.12.2002", dateFormatter), LocalDate.parse("01.03.2020", dateFormatter)));
        repositoryTest.create(new Client("2", "Iulia", "Silviescu", "245154154145", LocalDate.parse("04.12.2000", dateFormatter), LocalDate.parse("04.03.2018", dateFormatter)));
        repositoryTest.create(new Client("3", "Robert", "Radulescu", "184841684186", LocalDate.parse("25.05.1998", dateFormatter), LocalDate.parse("04.03.2018", dateFormatter)));
        return repositoryTest;
    }

    ClientValidator getClientValidator(){
        ClientValidator clientValidator=new ClientValidator();
        return clientValidator;
    }
    @Test
    void validareClient() {
        IRepository<Client>clientIRepository=getClientRepository();
        ClientValidator clientValidator=getClientValidator();
        Client client = new Client("4", "Robert", "Radulescu", "184841684186", LocalDate.parse("25.05.1998", dateFormatter), LocalDate.parse("04.03.2018", dateFormatter));
        try{
            // masinaService.updateMasina(masina);

            clientValidator.validareClient(client,getClientRepository(),"test");
            ////fail();
        }catch (ExceptiiClient e){
            assertEquals("Nu exista clientul cu id-ul"+client.getId()+"\n",e.getMessage());
        }
    }
}