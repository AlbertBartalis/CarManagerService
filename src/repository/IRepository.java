package repository;

import domain.Client;
import domain.Entity;
import domain.Masina;

import java.util.List;

public interface IRepository <T extends Entity> {
     void create(T entity);
     void update(T entity);
    List<T> read();
     T read(String idEntity);
     void delete(String idEntity);
}
