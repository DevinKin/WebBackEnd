package cn.itcast.export.webservice.test;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import cn.itcast.export.webservice.ExportService;

public class ExportTest {

	public static void main(String[] args) throws Exception {
		JaxWsProxyFactoryBean proxy  = new JaxWsProxyFactoryBean();
		
		proxy.setAddress("http://localhost:8080/jk28_export/ws/export?wsdl");
		proxy.setServiceClass(ExportService.class);

		ExportService es = (ExportService) proxy.create();
		
		String result = es.exportE("{exportId:'454534',inputDate:'2016-12-12',shipmentPort:'北京',destinationPort:'上海',transportMode:'陆运',priceCondition:'L/C',boxNums:'3',grossWeights:'200',measurements:'500'"+
		",products:[{exportProductId:'ep001',factoryId:'111',productNo:'222',packingUnit:'333',cnumber:'444',boxNum:'555',grossWeight:'666',netWeight:'777',sizeLength:'888',sizeWidth:'999',sizeHeight:'1000',exPrice:'1001',price:'1002',tax:'1003'}]"+"}");
		
		
		System.out.println(result);
	}

}
