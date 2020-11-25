package com.clone.mango.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clone.mango.dbdata.FoodInfo;

public interface FoodInfoRepository extends JpaRepository<FoodInfo, Long>{

}
