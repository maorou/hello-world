package server;

import spc.webos.data.IMessage;
import spc.webos.data.Message;

public class ParseXMLTest {
	public static void main(String[] args) throws Exception {
		Message msg = new Message(
				"<transaction><header><msg><msgCd>xxxx</msgCd></msg></header><body><request><a>a1</a><a>a2</a></request></body></transaction>"
						.getBytes());
		
		Message msg1 = new Message("<?xml version='1.0' encoding='UTF-8'?><transaction><header><ver>1.0</ver><msg><sndAppCd>ESB</sndAppCd><sndDt>20180427</sndDt><sndTm>090035</sndTm><seqNb>A09083503289800</seqNb><msgCd>MAS.000010010.01</msgCd></msg></header><body><request><plnId>111</plnId><createTime>2018-04-27</createTime><createUser>000admin</createUser><custNo>ABC</custNo><custName>333</custName><custType>111</custType><custSize>02</custSize><custOrgid>111</custOrgid><productId>111</productId><mainMortgage>111</mainMortgage><principal>12.0</principal><termM>111</termM><termD>111</termD><currency>111</currency><industry>111</industry><rpymType>01</rpymType><rateType>111</rateType><isFarm>111</isFarm><isNoTax>111</isNoTax><intRate>111</intRate><baseRate>111</baseRate><marginBaseRate>111</marginBaseRate><mortgageType>111</mortgageType><mortgageDetailId>111</mortgageDetailId><mortgageCurrency>111</mortgageCurrency><sceneProductId>111</sceneProductId><sceneCurrency>111</sceneCurrency><sceneProdValue>11.0</sceneProdValue><sceneTerm>111</sceneTerm><sceneInterest>111</sceneInterest></request></body></transaction>".getBytes());;
		String str="<baseRate>0.0435</baseRate>"+
	"<marginBaseRate>2.242345</marginBaseRate>"+
	"<mortlist>"+
	"	 <mortgageType>A0</mortgageType>"+
	"	<mortgageDetailId>5000000</mortgageDetailId>"+
	"	<mortgageCurrency>01</mortgageCurrency>"+
	"</mortlist>"+
	"<mortlist>"+
	"	 <mortgageType>A0</mortgageType>"+
	"	<mortgageDetailId>1000000</mortgageDetailId>"+
	"	<mortgageCurrency>01</mortgageCurrency>"+
	"</mortlist>"+
	"<scenelist>"+
	"	<sceneProductId></sceneProductId>"+
	"	<sceneCurrency></sceneCurrency>"+
	"	<sceneProdValue></sceneProdValue>"+
	"	<sceneTerm></sceneTerm>"+
	"	<sceneInterest/>"+
	"</scenelist>"+
	"</request>"+
	"</body>";
		
		
		getPar(str,"mortlist");
		//System.out.println(msg1.getMsgCd());
		//System.out.println(msg1.toXml(true));
	
		
	}
	static String getPar(String str,String filed){
		
		while(true){
			int startStr=str.indexOf("<"+filed+">");
			if(startStr==-1){//如果字符串中没有该标签，则跳出
				break;
			}
			int endstr=str.indexOf("</"+filed+">");
			//拼接数组
			String strFiled=str.substring(startStr+(filed.length()+2),endstr);
			if(strFiled.indexOf("<mortgageType>")!=-1){//抵质押品类型
				String s1=str.substring(str.indexOf("<mortgageType>")+("mortgageType".length()+2),str.indexOf("</mortgageType>"));
				System.out.println(s1);
			}
			if(strFiled.indexOf("<mortgageDetailId>")!=-1){//抵质押品明细金额
				String s1=str.substring(str.indexOf("<mortgageDetailId>")+("mortgageDetailId".length()+2),str.indexOf("</mortgageDetailId>"));
				System.out.println(s1);
			}
			if(strFiled.indexOf("<mortgageCurrency>")!=-1){//抵押品币种
				String s1=str.substring(str.indexOf("<mortgageCurrency>")+("mortgageCurrency".length()+2),str.indexOf("</mortgageCurrency>"));
				System.out.println(s1);
			}
			str=str.substring(endstr+filed.length()+3);//剩余字符串
		}
		
		//System.out.println(filed);
		return filed;
	}
	
	
	
	
	public static String parseStr(String str,String filed){
		int startStr=str.indexOf("<"+filed+">");
		int endstr=str.indexOf("</"+filed+">");
		String ss=str.substring(startStr+(filed.length()+2),endstr);
		filed = str.substring(str.indexOf("<"+filed+">")+(filed.length()+2),str.indexOf("</"+filed+">"));
		/*if(filed.equals("principal")||filed.equals("sceneProdValue")){//金额double
		}*/
		return filed;
	}
	
}
