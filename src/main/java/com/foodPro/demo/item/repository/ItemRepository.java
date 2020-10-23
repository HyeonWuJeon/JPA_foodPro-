package com.foodPro.demo.item.repository;

import com.foodPro.demo.item.domain.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query(value = "SELECT m FROM Item m ORDER BY m.id DESC ")
    Page<Item> findAllDesc(Pageable pageable);
}
