package com.spring.data.jpa.service;

import com.spring.data.jpa.models.dao.IAddressDao;
import com.spring.data.jpa.models.dao.ICartDao;
import com.spring.data.jpa.models.dao.IUsuarioDao;
import com.spring.data.jpa.models.entity.Card;
import com.spring.data.jpa.models.entity.cteEnviarA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartServiceImpl implements ICartService{
    @Autowired
    private ICartDao cartDao;

    @Override
    @Transactional
    public Card save(Card cart) {
        return cartDao.save(cart);
    }

    @Override
    public Card findCartByClienteAndFechaalta(String cliente, String fecha) {
        return cartDao.findCartByClienteAndFechaalta(cliente,fecha);
    }

    @Override
    public Card findByID(int id) {
        return cartDao.findByID(id);
    }

    @Override
    public void delete(Card card) {
        cartDao.delete(card);
    }

    @Override
    public String findByRecentDate(String cliente) {
        return cartDao.findByRecentDate(cliente);
    }
}
