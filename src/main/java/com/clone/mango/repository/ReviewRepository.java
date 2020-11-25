package com.clone.mango.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clone.mango.dbdata.FoodInfo;
import com.clone.mango.dbdata.Review;

public interface ReviewRepository extends JpaRepository<Review, Long>{

}
