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
		ESB2 esb2 = ESB2.getInstance();// ESB2 ��MQ��������  exe
		esb2.setAsynResponseOnMessage(new AsynResponseOnMessage()); // �����첽Ӧ������   ���󷽵��첽Ӧ��   ʵ��ҵ���߼�����
	//	esb2.setRequestOnMessage(new BizOnMessage()); //test ���񷽵�ʱ���������  ʵ�ֲ��������յ����󣬽���ҵ��������Ӧ��
		shareQ(esb2); // �첽�������ģʽ(REP.ESB.SYN)
		esb2.destory(); // ֹͣesb2�������̨�ڲ��첽��ȡ�̳߳�
	}*/

	// һ�������Ķ����Ⱥ���ʹ��һ���������REP.XXX.SYN, ͨ��ÿ����Ⱥ��ݷ���ı�����ˮ��ǰ2λ�����ּ�Ⱥ�еı��
	static void shareQ(ESB2 esb2) throws Exception {
		esb2.init("esb.properties");
		
			String seqNb = esb2.getSeqNb(); // ���ı�����ESB2�������ɵ���ˮ�š� 
			 String dt = datetime("date");
			String tm = datetime("time"); 
			Executable exe = new Executable();  //���� Executable����  
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
		parseVo.setRpymType("01");//���ʽ 
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
				"<plnId>" + parseVo.getPlnId()+"</plnId>"+  //ҵ��������ˮ�� M
				"<createTime>"+parseVo.getCreateTime() +"</createTime>" + //�������� M
				"<createUser>" +parseVo.getCreateUser() +"</createUser>"+ //����Ա
				"<custNo>" +parseVo.getCustNo()+"</custNo>"+ //�ͻ���
				"<custName>" + parseVo.getCustName()+"</custName>"+ //�ͻ�����
				"<custType>" +parseVo.getCustType() +"</custType>"+ //�ͻ�����
				"<custSize>" +parseVo.getCustSize() +"</custSize>"+ //�ͻ���ģO
				"<custOrgid>" +parseVo.getCustOrgid() +"</custOrgid>"+ //�ͻ�����
				"<productId>" +parseVo.getProductId() +"</productId>"+ //�Ŵ���Ʒ���
				"<mainMortgage>" +parseVo.getMainMortgage() +"</mainMortgage>"+ //��������ʽ
				"<principal>" +parseVo.getPrincipal() +"</principal>"+ //����������
				"<termM>" + parseVo.getTermM()+"</termM>"+ //������
				"<termD>" + parseVo.getTermD()+"</termD>"+ //������
				"<currency>" +parseVo.getCurrency() +"</currency>"+ //�����������
				"<industry>" +parseVo.getIndustry()+"</industry>"+ //Ͷ����ҵ
				"<rpymType>" +parseVo.getRpymType() +"</rpymType>"+ //���ʽ
				"<rateType>" +parseVo.getRateType() +"</rateType>"+ // ��������
				"<isFarm>" + parseVo.getIsFarm()+"</isFarm>"+ //�Ƿ���ũ 
				"<isNoTax>" +parseVo.getIsNoTax()+"</isNoTax>"+ // �Ƿ�������ֵ˰
				"<intRate>" +parseVo.getIntRate() +"</intRate>"+ //ִ������
				"<baseRate>" +parseVo.getBaseRate() +"</baseRate>"+ //��׼����
				"<marginBaseRate>" +parseVo.getMarginBaseRate() +"</marginBaseRate>"+ //��������
				"<mortgageType>" +parseVo.getMortgageType() +"</mortgageType>"+ //����ѺƷ����
				"<mortgageDetailId>" +parseVo.getMortgageDetailId() +"</mortgageDetailId>"+ // ����ѺƷ��ϸ���O
				"<mortgageCurrency>" + parseVo.getMortgageCurrency()+"</mortgageCurrency>"+ //����ѺƷ����O
				"<sceneProductId>" + parseVo.getSceneProductId()+"</sceneProductId>"+ // ������ƷID
				"<sceneCurrency>" +parseVo.getSceneCurrency() +"</sceneCurrency>"+ // ����
				"<sceneProdValue>" +parseVo.getSceneProdValue()+"</sceneProdValue>"+ //�������(Ԫ)
				"<sceneTerm>" + parseVo.sceneTerm+"</sceneTerm>"+ //������ O
				"<sceneInterest>" + parseVo.getSceneInterest()+"</sceneInterest>"+ //�������
				"</request>" + 
			"</body>" + 
			"</transaction>") .getBytes("UTF-8");
			exe.timeout = 10;
			exe.correlationID = (dt + "-" + seqNb).getBytes();
			System.out.println("��������Ϊ==============:request " + "::"+ new String(exe.request));
			try {
				esb2.execute(exe);
				String res = new String(exe.response,"utf-8");
				System.out.println("��ӦΪ============:"+res);
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
			/*Thread.sleep(2000); // ÿ2�뷢һ�ʣ����ڲ���gwin/gwout���й�����ֹͣ����һ����Ӱ�콻��
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
	