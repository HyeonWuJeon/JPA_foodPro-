package com.foodPro.demo.item.repository;

import com.foodPro.demo.item.domain.CatagoryItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CatagoryItemRepository extends JpaRepository<CatagoryItem, Long> {

    @Query("SELECT distinct c FROM CatagoryItem c join c.item")
    Page<CatagoryItem> findAllDesc(Pageable pageable);
}
