package com.clone.mango.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clone.mango.dbdata.FoodInfo;
import com.clone.mango.dbdata.Review;
import com.clone.mango.dbdata.ReviewImage;

public interface ReviewImageRepository extends JpaRepository<ReviewImage, Long>{

}
