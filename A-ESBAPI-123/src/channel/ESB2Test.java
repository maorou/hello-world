package channel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ibm.mq.jmqi.JmqiException;

import server.BizOnMessage;
import spc.webos.data.IMessage;
import spc.webos.data.Message;
import spc.webos.data.converter.XMLConverter2;
import spc.webos.endpoint.ESB2;
import spc.webos.endpoint.Executable;

public class ESB2Test {
	/*public static void main(String[] args) throws Exception {
		ESB2 esb2 = ESB2.getInstance();// ESB2 跟MQ创建连接  exe
		esb2.setAsynResponseOnMessage(new AsynResponseOnMessage()); // 设置异步应答处理函数   请求方的异步应答   实现业务逻辑处理
	//	esb2.setRequestOnMessage(new BizOnMessage()); //test 服务方的时候接受请求  实现参数对象，收到请求，进行业务处理，返回应答
		shareQ(esb2); // 异步共享队列模式(REP.ESB.SYN)
		esb2.destory(); // 停止esb2组件，后台内部异步读取线程池
	}*/

	// 一个渠道的多个集群身份使用一个共享队列REP.XXX.SYN, 通过每个集群身份发起的报文流水号前2位来区分集群中的编号
	static void shareQ(ESB2 esb2) throws Exception {
		esb2.init("esb.properties");
		
			String seqNb = esb2.getSeqNb(); // 报文必须由ESB2对象生成的流水号。 
			 String dt = datetime("date");
			String tm = datetime("time"); 
			Executable exe = new Executable();  //加载 Executable对象  
			ParseVo parseVo=new ParseVo();
		parseVo.setPlnId("111"); 
		parseVo.setCreateTime("2018-04-27"); 
		parseVo.setCreateUser("000admin"); 
		parseVo.setCustNo("ABC"); 
		parseVo.setCustName("333"); 
		parseVo.setCustType("111"); 
		parseVo.setCustSize("02"); 
		parseVo.setCustOrgid("111"); 
		parseVo.setProductId("111"); 
		parseVo.setMainMortgage("111"); 
		parseVo.setPrincipal(12d); 
		parseVo.setTermM("111"); 
		parseVo.setTermD("111"); 
		parseVo.setCurrency("111"); 
		parseVo.setIndustry("111"); 
		parseVo.setRpymType("01");//还款方式 
		parseVo.setRateType("111"); 
		parseVo.setIsFarm("111"); 
		parseVo.setIsNoTax("111"); 
		parseVo.setIntRate("111"); 
		parseVo.setBaseRate("111"); 
		parseVo.setMarginBaseRate("111"); 
		parseVo.setMortgageType("111"); 
		parseVo.setMortgageDetailId("111"); 
		parseVo.setMortgageCurrency("111"); 
		parseVo.setSceneProductId("111"); 
		parseVo.setSceneCurrency("111"); 
		parseVo.setSceneProdValue(11D); 
		parseVo.setSceneTerm("111"); 
		parseVo.setSceneInterest("111"); 
			/*exe.request = ("<transaction><header><msg><msgCd>ESB.00000010.01</msgCd><seqNb>"
					+ seqNb
					+ "</seqNb><sndTm>"
					+ "0909009"
					+ "</sndTm><sndDt>"
					+ dt + "</sndDt><sndAppCd>ESB</sndAppCd></msg></header><body><request><_a>a</_a></request></body></transaction>")
					.getBytes();*/
			
			/*exe.request = 
				      ("<?xml version='1.0' encoding='UTF-8'?>" +
				      "<transaction><header><ver>1.0</ver>" +
				      "<msg>" +
				      "<sndAppCd>" + "MAS" + "</sndAppCd>" + 
				      "<callTyp>SYN</callTyp>" + 
				      "<msgCd>TUA.000010020.01</msgCd>" + 
				      "<seqNb>" + seqNb + "</seqNb>" + 
				      "<sndDt>" + dt + "</sndDt>" + 
				      "<sndTm>" + tm + "</sndTm>" + 
				      "<rcvAppCd>TUA</rcvAppCd>" + 
				      "</msg>" + 
				      "</header>" + 
				      "<body>" + 
				      "<request>" + 
				      "<tuaReqHeader>" + 
				      "<chan>" + 55 + "</chan>" + 
				      "<chanDate>" + dt + "</chanDate>" + 
				      "<chanSeqNo>" + seqNb + "</chanSeqNo>" + 
				      "<opUserNo>" + "000admin" + "</opUserNo>" + 
				      "<opBranchNo>" + "1688888888" + "</opBranchNo>" + 
				      "<txCd>1002</txCd>" + 
				      "</tuaReqHeader>" + 
				      "<userNo>" + "000admin" + "</userNo>" + 
				      "<attestType>" + "0" + "</attestType>" + 
				      "<pwd>" + "000000" + "</pwd>" + 
				      "</request>" + 
				      "</body>" + 
				      "</transaction>")
				      .getBytes("UTF-8");*/
			
			exe.request = 
				      ("<?xml version='1.0' encoding='UTF-8'?>" + 
			"<transaction>" + 
			"<header>" + 
			"<ver>1.0</ver>" + 
			"<msg>" + 
			"<sndAppCd>ESB</sndAppCd>" + 
			 "<sndDt>" + dt + "</sndDt>" + 
		      "<sndTm>" + tm + "</sndTm>" + 
		      "<seqNb>" + seqNb + "</seqNb>" + 
		      "<msgCd>MAS.000010010.01</msgCd>" + 
			"</msg>" + 
			"</header>" + 
			"<body>" + 
			"<request>" + 
				"<plnId>" + parseVo.getPlnId()+"</plnId>"+  //业务申请流水号 M
				"<createTime>"+parseVo.getCreateTime() +"</createTime>" + //定价日期 M
				"<createUser>" +parseVo.getCreateUser() +"</createUser>"+ //操作员
				"<custNo>" +parseVo.getCustNo()+"</custNo>"+ //客户号
				"<custName>" + parseVo.getCustName()+"</custName>"+ //客户名称
				"<custType>" +parseVo.getCustType() +"</custType>"+ //客户类型
				"<custSize>" +parseVo.getCustSize() +"</custSize>"+ //客户规模O
				"<custOrgid>" +parseVo.getCustOrgid() +"</custOrgid>"+ //客户机构
				"<productId>" +parseVo.getProductId() +"</productId>"+ //信贷产品编号
				"<mainMortgage>" +parseVo.getMainMortgage() +"</mainMortgage>"+ //主担保方式
				"<principal>" +parseVo.getPrincipal() +"</principal>"+ //贷款申请金额
				"<termM>" + parseVo.getTermM()+"</termM>"+ //期限月
				"<termD>" + parseVo.getTermD()+"</termD>"+ //期限日
				"<currency>" +parseVo.getCurrency() +"</currency>"+ //贷款申请币种
				"<industry>" +parseVo.getIndustry()+"</industry>"+ //投向行业
				"<rpymType>" +parseVo.getRpymType() +"</rpymType>"+ //还款方式
				"<rateType>" +parseVo.getRateType() +"</rateType>"+ // 利率类型
				"<isFarm>" + parseVo.getIsFarm()+"</isFarm>"+ //是否涉农 
				"<isNoTax>" +parseVo.getIsNoTax()+"</isNoTax>"+ // 是否免征增值税
				"<intRate>" +parseVo.getIntRate() +"</intRate>"+ //执行利率
				"<baseRate>" +parseVo.getBaseRate() +"</baseRate>"+ //基准利率
				"<marginBaseRate>" +parseVo.getMarginBaseRate() +"</marginBaseRate>"+ //浮动利率
				"<mortgageType>" +parseVo.getMortgageType() +"</mortgageType>"+ //抵质押品类型
				"<mortgageDetailId>" +parseVo.getMortgageDetailId() +"</mortgageDetailId>"+ // 抵质押品明细金额O
				"<mortgageCurrency>" + parseVo.getMortgageCurrency()+"</mortgageCurrency>"+ //抵质押品币种O
				"<sceneProductId>" + parseVo.getSceneProductId()+"</sceneProductId>"+ // 派生产品ID
				"<sceneCurrency>" +parseVo.getSceneCurrency() +"</sceneCurrency>"+ // 币种
				"<sceneProdValue>" +parseVo.getSceneProdValue()+"</sceneProdValue>"+ //派生金额(元)
				"<sceneTerm>" + parseVo.sceneTerm+"</sceneTerm>"+ //期限月 O
				"<sceneInterest>" + parseVo.getSceneInterest()+"</sceneInterest>"+ //存款利率
				"</request>" + 
			"</body>" + 
			"</transaction>") .getBytes("UTF-8");
			exe.timeout = 10;
			exe.correlationID = (dt + "-" + seqNb).getBytes();
			System.out.println("发送请求为==============:request " + "::"+ new String(exe.request));
			try {
				esb2.execute(exe);
				String res = new String(exe.response,"utf-8");
				System.out.println("响应为============:"+res);
				Message mobj = new Message(exe.response);
				String retCd = mobj.getStatus().getRetCd();
				System.out.println("retCd============:"+retCd);
			} catch(JmqiException mqe){
				esb2.destory();
				System.out.println("catch JmqiException and reload");
				esb2.init("esb_shareQ.properties");
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			/*Thread.sleep(2000); // 每2秒发一笔，用于测试gwin/gwout队列管理器停止其中一个不影响交易
		}*/
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
	public static void main(String[] args) throws Exception {
		Date date =new Date();
		DateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
		String time=format.format(date);
		System.out.println(time);
	}
}
	