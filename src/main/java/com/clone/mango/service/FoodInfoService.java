package com.clone.mango.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clone.mango.dbdata.FoodInfo;
import com.clone.mango.dbdata.Review;
import com.clone.mango.dbdata.ReviewImage;
import com.clone.mango.repository.FoodInfoRepository;
import com.clone.mango.repository.ReviewImageRepository;
import com.clone.mango.repository.ReviewRepository;

@Service
public class FoodInfoService {
	
	@Autowired
	private FoodInfoRepository foodInfoRepository;
	
	@Autowired
	private ReviewRepository reviewRepository;
	
	@Autowired
	private ReviewImageRepository imageRepository;
	
	private static String BASE_URL = "https://stage.mangoplate.com/api/v5/search/by_keyword.json?keyword=논현역&start_index=0&request_count=20&language=kor";
	private static String URL = "https://www.mangoplate.com/restaurants/";
	private static String REVIEW_URL_FRONT = "https://stage.mangoplate.com/api/v5/restaurants/";
	private static String REVIEW_URL_BACK = "/reviews.json?language=kor&device_uuid=onqHi1606263910530339542zCA&device_type=web&start_index=0&request_count=10&sort_by=2";
	
	public void getDatabaseInfo() throws IOException  {
		
		URL url = new URL(BASE_URL);
		StringBuilder sb = new StringBuilder();
		BufferedReader bReader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
		
		// 객체 한글자씩 불러오기 => 1byte 씩?
		int num = 0;
		while (( num = bReader.read()) != -1) {
			sb.append((char)num);
			System.out.println(sb);
		}
		
		String str = sb.toString();
		//System.out.println(str);
		JSONObject root = new JSONObject(str);
		//System.out.println(root);
		
		JSONArray items = root.getJSONArray("result");
		
		for (int i=0; i< items.length(); i++) {
			
			JSONObject item = items.getJSONObject(i);
			
			String key = item.getJSONObject("restaurant").getString("restaurant_key");
			String name = item.getJSONObject("restaurant").getString("name");
			String address = item.getJSONObject("restaurant").getString("address");
			String roadAddress = item.getJSONObject("restaurant").getString("road_address");
			String latitude = item.getJSONObject("restaurant").getString("latitude");
			String longitude = item.getJSONObject("restaurant").getString("longitude");
			String phone = item.getJSONObject("restaurant").getString("phone_number");
			String pictureUrl= item.getJSONObject("restaurant").getString("picture_url");
			Double rating= item.getJSONObject("restaurant").getDouble("rating");
			
			String tempUrl = null;
			tempUrl = URL + key;
			
			Document doc = Jsoup.connect(tempUrl).get();
			Elements contents = doc.select("table tbody tr");
			
			Elements tdContents = contents.select("td");
			
			//System.out.println(tdContents);
			if( tdContents.size() < 6) {
				continue;
			}
			
			FoodInfo fi = FoodInfo.builder()
										.name(name)
										.address(address)
										.roadAddress(roadAddress)
										.phone(phone)
										.kind(tdContents.get(2).text())
										.amountRange(tdContents.get(3).text())
										.parking(tdContents.get(4).text())
										.time(tdContents.get(5).text())
										.pictureUrl(pictureUrl)
										.rating(rating)
										.latitude(latitude)
										.longitude(longitude)
									.build();
			
			fi = this.foodInfoRepository.save(fi);
			
			String reviewUrl = null;
			reviewUrl = REVIEW_URL_FRONT + key + REVIEW_URL_BACK;
						
			URL reviewUrlData = new URL(reviewUrl);
			//System.out.println("========================================" + reviewUrlData);
			StringBuilder sbReview = new StringBuilder();
			BufferedReader bReaderReview = new BufferedReader(new InputStreamReader(reviewUrlData.openStream(), "UTF-8"));
			
			int numReview = 0;
			while (( numReview = bReaderReview.read()) != -1) {
				sbReview.append((char)numReview);
			}
			
			String strReview = sbReview.toString();
			//System.out.println(strReview);
			JSONArray rootReview = new JSONArray(strReview);
			//System.out.println("***************************************" + rootReview);
			//JSONArray result = rootReview.getJSONArray("result");
			
			for(int j=0; j<rootReview.length(); j++) {
				
				JSONObject reviews = rootReview.getJSONObject(j);
				String nickName = reviews.getJSONObject("comment").getJSONObject("user").getString("nick_name");
				String comment = reviews.getJSONObject("comment").getString("comment");
				String regTime = reviews.getJSONObject("comment").getString("reg_time");
				long actionValue = reviews.getJSONObject("comment").getJSONObject("action").getLong("action_value");
				
				System.out.println(nickName);
				System.out.println(actionValue);
				System.out.println(regTime);
				
				Review reviewData = Review.builder()
											.foodInfo(fi)
											.nickName(nickName)
											.comment(comment)
											.regTime(regTime)
											.actionValue(actionValue)
										.build();
				reviewData = this.reviewRepository.save(reviewData);
				
				JSONArray pictures = reviews.getJSONArray("pictures");
				//System.out.println("====================================="+pictures);
				System.out.println("====================================="+pictures.length());
				
				if(pictures.length()>0) {
					
					for(int l=0; l<pictures.length(); l++) {
						JSONObject picture = pictures.getJSONObject(l);
						String reviewImageUrl = picture.getString("picture_url");
						
						ReviewImage reviewImageData = ReviewImage.builder()
																	.review(reviewData)
																	.reviewImageUrl(reviewImageUrl)
																.build();
						
						//System.out.println( reviewImageData.toString());
						this.imageRepository.save(reviewImageData);
					}
				}
			}
			
			
		}
		
	}

}
