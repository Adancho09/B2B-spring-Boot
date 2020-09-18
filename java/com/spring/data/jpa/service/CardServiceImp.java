package com.spring.data.jpa.service;

import com.spring.data.jpa.models.dao.ICardDao;
import com.spring.data.jpa.models.entity.CardD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CardServiceImp implements ICardService {
    @Autowired
    private ICardDao cardDao;
    @Override
    public CardD saveCard(CardD cardD) {
        return cardDao.save(cardD);
    }

    @Override
    public List<CardD> findAllByID(int id) {
        return  cardDao.findAllByID(id);
    }

    @Override
    public Integer findByID(int id) {
        return null;
    }

 @Override
    @Transactional
    public List<CardD> save(List<CardD> card) {
        return cardDao.save(card);
    }

    @Override
    public CardD findOneByIDAndRenglon(int id, int renglon) {
        return cardDao.findOneByIDAndRenglon(id,renglon);
    }



    @Override
    public void delete(CardD card) {
cardDao.delete(card);
    }

    @Override
    public void deleteAllByID(int id) {
        cardDao.deleteAllByID(id);
    }


}
