package com.clone.mango.dbdata;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder
@ToString
@Entity(name = "foodinfo")
public class FoodInfo {
	
	@Id @GeneratedValue
	private Long id;
	
	// 상호명
	private String name;
	// 주소
	private String address;
	// 도로명
	private String roadAddress;
	// 전화번호
	private String phone;
	// 음식종류
	private String kind;
	// 가격대
	private String amountRange;
	// 주차여부
	private String parking;
	// 영업시간
	private String time;
	// 메인사진
	private String pictureUrl;
	// 평점
	private Double rating;
	// 위도
	private String latitude;
	// 경도
	private String longitude;

}
