package com.foodPro.demo.food.repository;

import com.foodPro.demo.food.domain.Item;
import com.foodPro.demo.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query(value = "SELECT m FROM Item m ORDER BY m.id DESC")
    List<Item> findAllDesc();
}
