package server;

import spc.webos.endpoint.ESB2;
import spc.webos.queue.IOnMessage;

public class ESBServerTest {
	public static void main(String[] args) throws Exception {
		//BizOnMessage bizOnMessage=new BizOnMessage();
		server(ESB2.getInstance(), new PrpPriceTest()); // 一个jvm环境中单实例esb2模式启动
		// server2(new ESB2(),bizOnMessage); // 非单例模式，适合一个服务系统连接多套ESB环境
		//Thread.sleep(1000);
	}

	// 使用配置文件，单实例启动ESB服务
	static void server(ESB2 esb2, IOnMessage onMessage) throws Exception {
		esb2.setRequestOnMessage(onMessage); // 使用当前MQ读取线程作为业务处理线程
		esb2.init("esb.properties"); // 根据classpath目录下esb.properties的配置信息，启动内部线程池等组件
		// jvm停止后需要 destroy ESB对象来关闭后台线程等各种组件
	}

	// 非配置文件模式启动ESB
	static void server2(ESB2 esb2, IOnMessage onMessage) throws Exception {
		esb2.setRequestOnMessage(onMessage); // 使用当前MQ读取线程作为业务处理线程
		// 双队列管理器的MQ长连接池配置信息，用于发送应答报文
		esb2.setAccess("[{name:'GWIN',maxCnnNum:1,channel:'219.143.38.252:33300/SVRCONN_GW'},{name:'GWOUT',maxCnnNum:1,channel:'219.143.38.252:33399/SVRCONN_GW'}]");
		// 双队列管理器MQ消息读取线程池，用于读取REQ.ECIF业务队列的信息:
		// size:2表示2个独立的MQ队列读取线程, 默认为1， 此值无需太大，最多设置5，MQ读取性能相当快
		// bizPoolSize:50表示有50个业务线程来处理业务请求
		// maxBlockSize:0表示业务线程的最大阻塞数量，当并发交易大，50个业务线程都正在执行时是否容许阻塞线程来处理新的请求。如果不容许消息会挤压在MQ队列中，
		// 默认为0
		// channel:是MQ的配置信息
		esb2.setRequestPools("[{qname:'REQ.MAS',size:1,bizPoolSize:50,channel:'219.143.38.252:33300/SVRCONN_GW'},{qname:'REQ.MAS',size:1,bizPoolSize:50,channel:'219.143.38.252:33399/SVRCONN_GW'}]");
		esb2.init(); // 启动内部线程池等组件
		// jvm停止后需要 destroy ESB对象来关闭后台线程等各种组件
	}

	// 测试多业务线程处理
	// static void bizpool() throws Exception {
	// IOnMessage onMessage = new IOnMessage() {
	// public void onMessage(Object obj, AccessTPool pool,
	// AbstractReceiverThread thread) throws Exception {
	// System.out.println("start " + obj + ":"
	// + Thread.currentThread().getName());
	// Thread.sleep(1000);
	// System.out.println("end " + obj + ":"
	// + Thread.currentThread().getName());
	// }
	// };
	// BizPoolOnMessage pool = new BizPoolOnMessage(2, onMessage, 0);
	// for (int i = 0; i < 5; i++) {
	// System.out.println("add: " + i);
	// pool.onMessage("i=" + i, null, null);
	// System.out.println("suc add: " + i);
	// }
	// }
}
