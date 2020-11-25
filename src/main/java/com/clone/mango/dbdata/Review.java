package com.clone.mango.dbdata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
@Entity(name = "review")
public class Review {
	
	@Id @GeneratedValue
	private Long id;
	
	// FK food id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn( name = "food_id", referencedColumnName = "id")
	private FoodInfo foodInfo;
	
	// 닉네임(작성자)
	private String nickName;
	// 내용
	@Column(columnDefinition = "TEXT")
	private String comment;
	// 작성일
	private String regTime;
	// 평가
	private Long actionValue;

}
