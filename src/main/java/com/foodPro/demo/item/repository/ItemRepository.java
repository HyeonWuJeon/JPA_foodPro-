package com.foodPro.demo.item.repository;

import com.foodPro.demo.item.domain.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query(value = "SELECT m FROM Item m WHERE TYPE(m) = Food ORDER BY m.id desc")
    Page<Item> findAllFood(Pageable pageable);

    @Query(value = "SELECT m FROM Item m WHERE TYPE(m) = Book ORDER BY m.id desc")
    Page<Item> findAllByBook(Pageable pageable);

    @Query(value = "SELECT m FROM Item m WHERE TYPE(m) = Clothes ORDER BY m.id desc")
    Page<Item> findAllByClothes(Pageable pageable);

    @Query(value = "SELECT m FROM Item m ORDER BY m.id desc")
    Page<Item> findAllDesc(Pageable pageable);

}
