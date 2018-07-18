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
	String appCd = "MAS"; // ����ϵͳ��ţ�ECIF, CBS��//�����Ʒ���ϵͳ����ΪMAS
	@Override
	public void onMessage(Object obj, AccessTPool accesstpool,
			AbstractReceiverThread abstractreceiverthread) throws Exception {
		// TODO Auto-generated method stub
		QueueMessage qmsg = (QueueMessage) obj;
		byte[] request = qmsg.buf; // ������
		System.out.println("������request:========" + new String(request));
		    // 1. ʹ�����õ�xml�������������esb xml���Ľ���ΪIMessage�ڲ�����
			IMessage msg = XMLConverter2.getInstance().deserialize(request);
			//System.out.println(msg.getSeqNb());
			ParseVo parseVo=parseVO(msg);
			
			
			
			
			
			
	/*		
			
			
			// 2. ���� msg������������Ӧҵ�������response��Ϣ
			msg.setInResponse("pCode", "ABC-Test");
			msg.setInResponse("bottomRate", "1.1");//���Է��ر�������
			
			// 3. ����ԭ������ͷ��Ϣ��дӦ����ͷ��Ϣ
			msg.setRequest(null); // ɾ��request��ǩ
			esb2.req2rep(msg); // ����ԭ����ͷ����ref��Ϣ
			msg.setMsgCd("MAS.000010011.01"); // ��дӦ���ı��, ��Ҫ���ݲ�ͬҵ�������ͬ��ҵ���ı��		msg.setSndAppCd(appCd); // ���÷���ϵͳ���
			//msg.setSeqNb();//��ԭ����
			String dt = datetime("date");
			String tm = datetime("time"); 
			msg.setSndTm(tm);
			msg.setSndDt(dt);
			
			
			// ����Ӧ���ĵ�״̬��������������: appcd, ip, retcd
			Status status = new Status();
			status.setAppCd(appCd);
			status.setIp("127.0.0.1"); // ����IP
			status.setRetCd("000000"); // ������, ͨ����ȷ������
			msg.setStatus(status);
			byte[] response=XMLConverter2.getInstance().serialize(msg);
		
		
		if (response != null) {
			System.out.println("response:" + new String(response));
			esb2.sendResponse(response); // ��REP���з���Ӧ����
			// ʹ��ָ�����У�ָ����Ϣ����ģʽ����Ӧ����
			// QueueMessage qmsg = new QueueMessage(response);
			// esb2.send("REP", qmsg);
		}*/
	}
	
	public ParseVo parseVO(IMessage msg){
		String str=msg.toString();
		ParseVo parseVo=new ParseVo();
		parseVo.setPlnId(parseStr(str,"plnId"));  //ҵ��������ˮ�� M
		parseVo.setCreateTime(parseStr(str,"createTime")); //�������� M
		parseVo.setCreateUser(parseStr(str,"createUser")); //����Ա
		parseVo.setCustNo(parseStr(str,"custNo"));  //�ͻ���
		parseVo.setCustName(parseStr(str,"custName")); //�ͻ�����
		parseVo.setCustType(parseStr(str,"custType")); //�ͻ�����
		parseVo.setCustSize(parseStr(str,"custSize")); //�ͻ���ģO
		parseVo.setCustOrgid(parseStr(str,"custOrgid")); //�ͻ�����
		parseVo.setProductId(parseStr(str,"productId")); //�Ŵ���Ʒ���
		parseVo.setMainMortgage(parseStr(str,"mainMortgage")); //��������ʽ
		parseVo.setPrincipal(Double.parseDouble(parseStr(str,"principal"))); //����������
		parseVo.setTermM(parseStr(str,"termM")); //������
		parseVo.setTermD(parseStr(str,"termD")); //������
		parseVo.setCurrency(parseStr(str,"currency"));  //�����������
		parseVo.setIndustry(parseStr(str,"industry")); //Ͷ����ҵ
		parseVo.setRpymType(parseStr(str,"rpymType"));//���ʽ 
		parseVo.setRateType(parseStr(str,"rateType")); // ��������
		parseVo.setIsFarm(parseStr(str,"isFarm")); //�Ƿ���ũ 
		parseVo.setIsNoTax(parseStr(str,"isNoTax")); // �Ƿ�������ֵ˰
		parseVo.setIntRate(parseStr(str,"intRate"));  //ִ������
		parseVo.setBaseRate(parseStr(str,"baseRate")); //��׼����
		parseVo.setMarginBaseRate(parseStr(str,"marginBaseRate")); //��������
		parseVo.setMortgageType(parseStr(str,"mortgageType")); //����ѺƷ����
		parseVo.setMortgageDetailId(parseStr(str,"mortgageDetailId")); // ����ѺƷ��ϸ���O
		parseVo.setMortgageCurrency(parseStr(str,"mortgageCurrency")); //����ѺƷ����O
		parseVo.setSceneProductId(parseStr(str,"sceneProductId")); // ������ƷID
		parseVo.setSceneCurrency(parseStr(str,"sceneCurrency")); // ����
		parseVo.setSceneProdValue(Double.parseDouble(parseStr(str,"sceneProdValue")));  //�������(Ԫ)
		parseVo.setSceneTerm(parseStr(str,"sceneTerm")); //������ O
		parseVo.setSceneInterest(parseStr(str,"sceneInterest")); //�������
		return parseVo;
	}
	
	public String parseStr(String str,String filed){
		int startStr=str.indexOf("<"+filed+">");
		int endstr=str.indexOf("</"+filed+">");
		String ss=str.substring(startStr+(filed.length()+2),endstr);
		filed = str.substring(str.indexOf("<"+filed+">")+(filed.length()+2),str.indexOf("</"+filed+">"));
		/*if(filed.equals("principal")||filed.equals("sceneProdValue")){//���double
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
