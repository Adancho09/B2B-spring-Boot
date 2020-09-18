package com.spring.data.jpa.service;
import com.spring.data.jpa.models.entity.CardD;
import java.util.List;

public interface ICardService {
    CardD saveCard(CardD cardD);
    public List<CardD> findAllByID(int id);
    Integer findByID(int id);
    List<CardD> save(List<CardD> card);

    CardD findOneByIDAndRenglon(int id,int renglon);
    void delete(CardD card);
    void deleteAllByID(int id);

}
