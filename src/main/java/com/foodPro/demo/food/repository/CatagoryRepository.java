package com.foodPro.demo.food.repository;

import com.foodPro.demo.catagory.Catagory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatagoryRepository extends JpaRepository<Catagory, Long> {
}
