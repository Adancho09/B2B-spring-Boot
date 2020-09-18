package com.spring.data.jpa.service;

import com.spring.data.jpa.models.entity.Card;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

public interface ICartService {

    Card save(Card cart);
    Card findCartByClienteAndFechaalta(String cliente,String fecha);
    Card findByID(int id);
    void delete(Card card);
    @Query("SELECT ID from Card " +
            "WHERE fechaalta = (" +
            "    SELECT MAX (fechaalta)" +
            "    from Card " +
            ") and cliente =?1  and isactive = '1' ")
    String findByRecentDate(String cliente);

}
