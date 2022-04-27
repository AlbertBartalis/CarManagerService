package tests.Repository;

import com.sun.nio.sctp.IllegalReceiveException;
import domain.Entity;
import domain.Masina;
import org.junit.jupiter.api.Test;
import repository.IRepository;
import repository.InMemoryRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryRepositoryTest {

    IRepository<Entity> getInMemoryRepository(){
        IRepository<Entity> entity=new InMemoryRepository<>();
        Entity obiect=new Entity("1");
        entity.create(obiect);
        return entity;
    }


    @Test
    void create() {
        IRepository<Entity>repositoryEntity=getInMemoryRepository();
        Entity entity=new Entity("2");
        repositoryEntity.create(entity);
        List<Entity>listaObiecte=repositoryEntity.read();
        assertEquals(2,listaObiecte.size());
    }
    IRepository<Masina> getTestMasinaRepository(){
        IRepository<Masina> masinaIRepositoryMasina=new InMemoryRepository<>();
        masinaIRepositoryMasina.create(new Masina("1","BMW",2010,70000,true));
        masinaIRepositoryMasina.create(new Masina("2","VW",2009,150000,false));
        masinaIRepositoryMasina.create(new Masina("3","Dacia",2011,155000,true));
        return masinaIRepositoryMasina;
    }
    @Test
    void update() {
        IRepository<Masina>repositoryMasina=getTestMasinaRepository();
        Masina masina = new Masina("2","VW",2010,150000,false);
        repositoryMasina.update(masina);
        List<Masina>lista=repositoryMasina.read();
        assertEquals(2010,lista.get(1).getAnAchizitie());
    }

    @Test
    void read() {
        IRepository<Entity> entityIRepository=getInMemoryRepository();
        List<Entity>list=entityIRepository.read();
        assertEquals(1,list.size());
    }


    @Test
    void delete() {
        IRepository<Entity>entityIRepository=getInMemoryRepository();
        entityIRepository.delete("1");
        List<Entity>list=entityIRepository.read();
        assertEquals(0,list.size());
    }
}