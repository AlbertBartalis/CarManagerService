package repository;

import ExceptieProprie.ExceptiiMasina;
import domain.Entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryRepository <TEntity extends Entity> implements  IRepository<TEntity>{
    Map<String, TEntity> storage=new HashMap<>();

    @Override
    public void create(TEntity entity) {
        if(storage.containsKey(entity.getId())) {
            throw new ExceptiiMasina("Exista deja o entitate cu id-ul"+entity.getId());
        }
        this.storage.put(entity.getId(),entity);
    }

    @Override
    public void update(TEntity entity) {
        if(!storage.containsKey(entity.getId())) {
            throw new ExceptiiMasina("Nu exista nici-o entitate cu id-ul"+entity.getId());
        }
        this.storage.put(entity.getId(),entity);
    }

    @Override
    public List<TEntity> read() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public TEntity read(String idEntity) {
        return storage.get(idEntity);
    }

    @Override
    public void delete(String idEntity) {
        if(!storage.containsKey(idEntity)) {
            throw new RuntimeException("Nu exista nici-o entitate cu id-ul"+idEntity);
        }
        this.storage.remove(idEntity);
    }
}
