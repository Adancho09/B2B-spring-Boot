package com.spring.data.jpa.models.dao;

import com.spring.data.jpa.models.entity.Card;
import com.spring.data.jpa.models.entity.CardD;
import com.spring.data.jpa.models.entity.CartApk;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ICartDao extends PagingAndSortingRepository<Card, Integer> {

    Card save(Card cart);
    Card findCartByClienteAndFechaalta(String cliente,String fecha);
    Card findByID(int id);


    void delete(Card card);

  /*  @Query("SELECT ID from Card " +
            "WHERE fechaalta = (" +
            "    SELECT MAX (fechaalta)" +
            "    from Card " +
            ") and cliente =?1  and isactive = '1' ")*/
   @Query(value = "select TOP 1 ID from card where isActive = 1 and cliente=?1 order By id DESC ",nativeQuery = true)
    String findByRecentDate(String cliente);


}
