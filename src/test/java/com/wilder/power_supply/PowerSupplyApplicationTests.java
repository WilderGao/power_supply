//package com.wilder.power_supply;
//
//import com.wilder.power_supply.dto.ResultInfo;
//import com.wilder.power_supply.exception.DeviceException;
//import com.wilder.power_supply.exception.ExcelException;
//import com.wilder.power_supply.exception.MeterialException;
//import com.wilder.power_supply.exception.ProjectException;
//import com.wilder.power_supply.model.Device;
//import com.wilder.power_supply.model.Meterial;
//import com.wilder.power_supply.model.Project;
//import com.wilder.power_supply.service.DeviceService;
//import com.wilder.power_supply.service.Impl.ExcelServiceImpl;
//import com.wilder.power_supply.service.Impl.MeterialServiceImpl;
//import com.wilder.power_supply.service.ProjectService;
//import com.wilder.power_supply.utils.ExcelUtil;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.io.IOException;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class PowerSupplyApplicationTests {
//
//	@Autowired
//	private MeterialServiceImpl meterialService;
//
//	@Autowired
//	private ExcelServiceImpl excelService;
//
//	@Autowired
//	private DeviceService deviceService;
//
//	@Autowired
//	private ProjectService projectService;
//
//	@Test
//	public void meterialSearch() throws MeterialException {
//		System.out.println(meterialService.searchMaterial(null, null));
//	}
//
//	@Test
//	public void excelUtilTest() throws ExcelException {
//		excelService.excelHandler("F:\\QG\\项目\\外包\\禅城供电局\\物资需求计划表.xls", "Project");
//	}
//
//	@Test
//	public void deviceDetailTest() throws DeviceException, IOException, ExcelException {
//		ResultInfo<Device> resultInfo = deviceService.deviceDetailHandler(1);
//		for (Meterial meterial : resultInfo.getInfo().getMeterials()) {
//			System.out.println(meterial);
//		}
////		ExcelUtil.exportExcel(resultInfo.getInfo().getMeterials(), resultInfo.getInfo().getDeviceName());
//
//	}
//
//	@Test
//	public void getProjectList() throws ProjectException {
//		for (Project project : projectService.projectListHandler().getInfo()) {
//			System.out.println(project);
//		}
//	}
//
//}
