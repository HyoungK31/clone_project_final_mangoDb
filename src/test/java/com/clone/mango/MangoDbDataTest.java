package com.clone.mango;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.clone.mango.service.FoodInfoService;

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class MangoDbDataTest {
	
	@Autowired
	private FoodInfoService foodInfoService;
	
	@Test
	void getDatabaseInfo() throws Exception {
		foodInfoService.getDatabaseInfo();
	}

}
