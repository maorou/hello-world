package server;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import channel.ParseVo;

import spc.webos.data.IMessage;
import spc.webos.data.Status;
import spc.webos.data.converter.XMLConverter2;
import spc.webos.endpoint.AbstractBizOnMessage;
import spc.webos.queue.AbstractReceiverThread;
import spc.webos.queue.AccessTPool;
import spc.webos.queue.QueueMessage;

public class PrpPriceTest extends AbstractBizOnMessage{
	Logger log = Logger.getLogger(getClass());
	String appCd = "MAS"; // 服务方系统编号，ECIF, CBS等//管理会计服务系统编码为MAS
	@Override
	public void onMessage(Object obj, AccessTPool accesstpool,
			AbstractReceiverThread abstractreceiverthread) throws Exception {
		// TODO Auto-generated method stub
		QueueMessage qmsg = (QueueMessage) obj;
		byte[] request = qmsg.buf; // 请求报文
		System.out.println("请求报文request:========" + new String(request));
		    // 1. 使用内置的xml解析器将请求的esb xml报文解析为IMessage内部对象
			IMessage msg = XMLConverter2.getInstance().deserialize(request);
			//System.out.println(msg.getSeqNb());
			ParseVo parseVo=parseVO(msg);
			
			
			
			
			
			
	/*		
			
			
			// 2. 根据 msg对象内容做相应业务处理，填充response信息
			msg.setInResponse("pCode", "ABC-Test");
			msg.setInResponse("bottomRate", "1.1");//测试返回保本利率
			
			// 3. 根据原请求报文头信息填写应答报文头信息
			msg.setRequest(null); // 删除request标签
			esb2.req2rep(msg); // 根据原报文头生成ref信息
			msg.setMsgCd("MAS.000010011.01"); // 填写应答报文编号, 需要根据不同业务给出不同的业务报文编号		msg.setSndAppCd(appCd); // 设置服务方系统编号
			//msg.setSeqNb();//用原来的
			String dt = datetime("date");
			String tm = datetime("time"); 
			msg.setSndTm(tm);
			msg.setSndDt(dt);
			
			
			// 设置应答报文的状态，至少设置三项: appcd, ip, retcd
			Status status = new Status();
			status.setAppCd(appCd);
			status.setIp("127.0.0.1"); // 本机IP
			status.setRetCd("000000"); // 返回码, 通用正确返回码
			msg.setStatus(status);
			byte[] response=XMLConverter2.getInstance().serialize(msg);
		
		
		if (response != null) {
			System.out.println("response:" + new String(response));
			esb2.sendResponse(response); // 向REP队列发送应答报文
			// 使用指定队列，指定消息内容模式发送应答报文
			// QueueMessage qmsg = new QueueMessage(response);
			// esb2.send("REP", qmsg);
		}*/
	}
	
	public ParseVo parseVO(IMessage msg){
		String str=msg.toString();
		ParseVo parseVo=new ParseVo();
		parseVo.setPlnId(parseStr(str,"plnId"));  //业务申请流水号 M
		parseVo.setCreateTime(parseStr(str,"createTime")); //定价日期 M
		parseVo.setCreateUser(parseStr(str,"createUser")); //操作员
		parseVo.setCustNo(parseStr(str,"custNo"));  //客户号
		parseVo.setCustName(parseStr(str,"custName")); //客户名称
		parseVo.setCustType(parseStr(str,"custType")); //客户类型
		parseVo.setCustSize(parseStr(str,"custSize")); //客户规模O
		parseVo.setCustOrgid(parseStr(str,"custOrgid")); //客户机构
		parseVo.setProductId(parseStr(str,"productId")); //信贷产品编号
		parseVo.setMainMortgage(parseStr(str,"mainMortgage")); //主担保方式
		parseVo.setPrincipal(Double.parseDouble(parseStr(str,"principal"))); //贷款申请金额
		parseVo.setTermM(parseStr(str,"termM")); //期限月
		parseVo.setTermD(parseStr(str,"termD")); //期限日
		parseVo.setCurrency(parseStr(str,"currency"));  //贷款申请币种
		parseVo.setIndustry(parseStr(str,"industry")); //投向行业
		parseVo.setRpymType(parseStr(str,"rpymType"));//还款方式 
		parseVo.setRateType(parseStr(str,"rateType")); // 利率类型
		parseVo.setIsFarm(parseStr(str,"isFarm")); //是否涉农 
		parseVo.setIsNoTax(parseStr(str,"isNoTax")); // 是否免征增值税
		parseVo.setIntRate(parseStr(str,"intRate"));  //执行利率
		parseVo.setBaseRate(parseStr(str,"baseRate")); //基准利率
		parseVo.setMarginBaseRate(parseStr(str,"marginBaseRate")); //浮动利率
		parseVo.setMortgageType(parseStr(str,"mortgageType")); //抵质押品类型
		parseVo.setMortgageDetailId(parseStr(str,"mortgageDetailId")); // 抵质押品明细金额O
		parseVo.setMortgageCurrency(parseStr(str,"mortgageCurrency")); //抵质押品币种O
		parseVo.setSceneProductId(parseStr(str,"sceneProductId")); // 派生产品ID
		parseVo.setSceneCurrency(parseStr(str,"sceneCurrency")); // 币种
		parseVo.setSceneProdValue(Double.parseDouble(parseStr(str,"sceneProdValue")));  //派生金额(元)
		parseVo.setSceneTerm(parseStr(str,"sceneTerm")); //期限月 O
		parseVo.setSceneInterest(parseStr(str,"sceneInterest")); //存款利率
		return parseVo;
	}
	
	public String parseStr(String str,String filed){
		int startStr=str.indexOf("<"+filed+">");
		int endstr=str.indexOf("</"+filed+">");
		String ss=str.substring(startStr+(filed.length()+2),endstr);
		filed = str.substring(str.indexOf("<"+filed+">")+(filed.length()+2),str.indexOf("</"+filed+">"));
		/*if(filed.equals("principal")||filed.equals("sceneProdValue")){//金额double
		}*/
		return filed;
	}
	public static String datetime(String type){
		Date date =new Date();
		DateFormat format=new SimpleDateFormat("yyyyMMdd,HHmmss");
		String time=format.format(date);
		String[] dt = time.split(",");
		if(type.equals("time")||type=="time"){
			return dt[1];
		}else if(type.equals("date")||type=="date"){
			return dt[0];
		}else{
			return "000000";
		}
	}
}
