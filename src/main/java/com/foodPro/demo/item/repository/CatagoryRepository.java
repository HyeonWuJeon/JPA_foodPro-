package com.foodPro.demo.item.repository;

import com.foodPro.demo.catagory.Catagory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatagoryRepository extends JpaRepository<Catagory, Long> {
}
