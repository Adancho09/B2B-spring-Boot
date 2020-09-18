package com.spring.data.jpa.models.dao;

import com.spring.data.jpa.models.entity.CardD;
import com.spring.data.jpa.models.entity.CardDApk;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ICardDao extends CrudRepository<CardD, CardDApk> {

    CardD save(CardD card);
    List<CardD> findAllByID(int id);
    List<CardD> save(List<CardD> card);
    CardD findOneByIDAndRenglon(int id,int renglon);
    void delete(CardD card);
    void deleteAllByID(int id);
}
