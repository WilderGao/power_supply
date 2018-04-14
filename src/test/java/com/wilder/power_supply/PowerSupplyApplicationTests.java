package com.wilder.power_supply;

import com.wilder.power_supply.service.Impl.MeterialServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PowerSupplyApplicationTests {

	@Autowired
	private MeterialServiceImpl meterialService;

	@Test
	public void contextLoads() throws Exception {
		meterialService.meterialHandler("F:\\QG\\项目\\外包\\禅城供电局\\业扩材料表（分类）20180410.xls");
	}

}
