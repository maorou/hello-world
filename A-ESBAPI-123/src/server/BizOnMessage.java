package server;

import org.apache.log4j.Logger;

import spc.webos.data.IMessage;
import spc.webos.data.Status;
import spc.webos.data.converter.XMLConverter2;
import spc.webos.endpoint.AbstractBizOnMessage;
import spc.webos.queue.AbstractReceiverThread;
import spc.webos.queue.AccessTPool;
import spc.webos.queue.QueueMessage;

public class BizOnMessage extends AbstractBizOnMessage {
	Logger log = Logger.getLogger(getClass());
	String appCd = "MAS"; // 服务方系统编号，ECIF, CBS等//管理会计服务系统编码为MAS

	public void onMessage(Object obj, AccessTPool pool,
			AbstractReceiverThread thread) throws Exception {
		QueueMessage qmsg = (QueueMessage) obj;
		byte[] request = qmsg.buf; // 请求报文
		// 容许ESB2多实例后，追踪执行当前报文请求的ESB2实例号，防止jvm停止没有停掉后台esb2实例
		System.out.println("ESB2 ID:" + esb2.getInstanceId() + ", startTm:"
				+ esb2.getStartTm());
		System.out.println("request:" + new String(request));
		byte[] response = doMessage(obj, pool, thread, request); // 业务处理
		if (response != null) {
			System.out.println("response:" + new String(response));
			esb2.sendResponse(response); // 向REP队列发送应答报文
			// 使用指定队列，指定消息内容模式发送应答报文
			// QueueMessage qmsg = new QueueMessage(response);
			// esb2.send("REP", qmsg);
		}
	}

	// 根据请求的esb xml报文，进行业务处理，并给出业务应答报文
	public byte[] doMessage(Object obj, AccessTPool pool,
			AbstractReceiverThread thread, byte[] request) throws Exception {
		// 调用后台业务处理逻辑..
		// 1. 使用内置的xml解析器将请求的esb xml报文解析为IMessage内部对象
		IMessage msg = XMLConverter2.getInstance().deserialize(request);
		System.out.println(msg.getSeqNb());
		// 2. 根据 msg对象内容做相应业务处理，填充response信息
		//msg.setInResponse("hello", "world");
		msg.setInResponse("bottomRate", "1.1");//测试返回保本利率
		// 3. 根据原请求报文头信息填写应答报文头信息
		msg.setRequest(null); // 删除request标签
		esb2.req2rep(msg); // 根据原报文头生成ref信息
		msg.setMsgCd("MAS.000010011.01"); // 填写应答报文编号, 需要根据不同业务给出不同的业务报文编号		msg.setSndAppCd(appCd); // 设置服务方系统编号
		// 设置应答报文的状态，至少设置三项: appcd, ip, retcd
		Status status = new Status();
		status.setAppCd(appCd);
		status.setIp("127.0.0.1"); // 本机IP
		status.setRetCd("000000"); // 返回码, 通用正确返回码
		msg.setStatus(status);

		return XMLConverter2.getInstance().serialize(msg);
	}
}
