package com.myfrei.qa.platform.service.abstracts.model;

import java.util.List;

public interface ReadWriteService<T, PK>  {
    void persist(T t);

    void update(T t);

    void delete (T t);

    void deleteByFlagById(PK id);

    void deleteByKeyCascadeEnable(PK id);

    void deleteByKeyCascadeIgnore(PK id);

    void deleteByFlag(PK id);

    boolean existsById(PK id);

    T getByKey(PK id);

    List<T> getAll();
}
