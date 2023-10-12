package com.jdk;

import com.jdk.bean.Furn;
import com.jdk.mapper.FurnMapper;
import com.jdk.service.FurnService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class SpringbootFurnApplicationTests {
	@Resource
	private FurnService furnService;
	@Resource
	private FurnMapper furnMapper;
	@Test
	void testMapper() {
		Furn furn = furnMapper.selectById(4);
		System.out.println(furn);
	}
	@Test
	public void testFurnService() {
		List<Furn> list = furnService.list();
		for (Furn furn : list) {
			System.out.println(furn);
		}
	}

}
