package com.foodPro.demo.item.repository;

import com.foodPro.demo.item.domain.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ItemRepository extends JpaRepository<Item, Long> {

    /**
     *  카테고리별 아이템
     * @param pageable
     * @return
     */
    @Query(value = "SELECT * FROM item  WHERE ftype =: type ORDER BY item_id desc", nativeQuery = true)
    Page<Item> findAllByCatalogue(Pageable pageable, String type);

    /**
     * 모든 아이템
     * @param pageable
     * @return
     */
    @Query(value = "SELECT m FROM Item m ORDER BY m.id desc")
    Page<Item> findAllDesc(Pageable pageable);

}
