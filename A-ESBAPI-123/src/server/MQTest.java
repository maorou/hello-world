package server;

import java.util.Hashtable;

import spc.webos.queue.ibmmq.MQManager;
import spc.webos.util.FileUtil;
import spc.webos.util.StringX;

import com.ibm.mq.MQC;
import com.ibm.mq.MQException;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQPutMessageOptions;
import com.ibm.mq.MQQueue;

public class MQTest {
	public static void put() throws Exception {

		byte[] xml = "aaaa".getBytes();
		String qname = "REP.TLR";

		MQManager.MQ_TCP_TIMEOUT = 10;
		MQManager mqm = new MQManager("192.168.7.59:33300:SVRCONN_GW", -1);
		mqm.MQ_TCP_TIMEOUT = 30;
		long start = System.currentTimeMillis();
		try {
			System.out.println("start to access qm...");
			mqm.connect(1);
			System.out.println("success to access qm..."
					+ (System.currentTimeMillis() - start));

			int openOptions = MQC.MQOO_OUTPUT + MQC.MQOO_SET_IDENTITY_CONTEXT;// +
																				// MQC.MQOO_SET_ALL_CONTEXT;
			MQMessage msg = new MQMessage();
			msg.write(xml);

			MQQueue queue = mqm.accessQueue(qname, openOptions);
			System.out.println("success to access queue...");
			MQPutMessageOptions pmo = new MQPutMessageOptions();
			pmo.options += MQC.MQPMO_SET_IDENTITY_CONTEXT;
			for (int i = 0; i < 5; i++) {
				System.out.println("start to put:" + i);
				start = System.currentTimeMillis();
				queue.put(msg, pmo);
				System.out.println("suc to put:" + i);
				Thread.sleep(60 * 1000);
			}
			System.out.println("put ok!!!");
		} finally {
			System.out.println("cost: " + (System.currentTimeMillis() - start));
			if (mqm != null)
				mqm.disconnect();
		}
	}

	public static void main(String[] args) throws Exception {
		System.out.println(MQException.MQCC_OK);
		System.out.println(MQException.MQRC_NONE);
		System.out.println(System
				.getProperty("com.ibm.mq.cfg.TCP.Connect_Timeout"));
		System.setProperty("com.ibm.mq.cfg.TCP.Connect_Timeout", "10");
		System.out.println(System
				.getProperty("com.ibm.mq.cfg.TCP.Connect_Timeout"));
		// put();
		System.out.println(StringX.byte2hex(FileUtil.gzip(
				"1234567890".getBytes(), 0, 10)));
		System.out.println(StringX.byte2hex(FileUtil.gzip(
				"1234567899".getBytes(), 0, 10)));

		// String xml =
		// "PGVzYkluZm8+PHN0YXJ0VGltZT4xNDEzNTEyMDUxMDg2PC9zdGFydFRpbWU+PGNvcnJlbElEPk1CMTE0MTAxNzEyMDUxMDgyNTY5ODY4MzwvY29ycmVsSUQ+PHhtbD5INHNJQUFBQUFBQUFBRjFUeTFMck1BeGRsNjlndXFlMnkzTWh6SEF2bEdFR3VtbFpzR0xTV0ZBUGVXRTdwZmw3WkNzTnVUY2JTK2ZvS05hSkFqZjdzamplb2ZPMnJxNm5haWFueDFqbHRiSFZ4L1gwWmIwNHVacmVhQWd1cTN5V0J5clNSeFBZWW1iUVVUUUJrbXBTZ2RqMVFPay80amtCWDVuYnB2bHI5UDNxRDRnaE8zQjNRYytsT2xOU1hTYVc4Z08xTHJXS2xKS244OFFSd0J4K0xUZGF6ZVY1cE9TRlV1ZnlnaW9TbkNybzdmU094WG8xay93b09aTUtCT09wSk0rS1l0MDFldlc2QkhGSUV1UHlIZCtSOUNDR0xFNGxlQ3dRdytpd3FVMlhPSWRmTGZyKytpUk5RWEltbXFyZmcwL3VlRFl2VXUvZno3VkJUZmZxb3g2MlJhRGV3YlZJRENmTStKQ0YxbXV5dVk5NnVHNWRqc3ZZNGZiaGpYeCtpN09PVUM0clk3aXhWZVk2R3VRWGI1eXRuUTJkUGdNeHhFeUZya0g5QkNLZERPVmJ6RDk5V3o1ajJOWkdSL1ArUmJqTVdOL1UzcVpWb2I3amxBdHdiOG1zVnhBY01QaUoyS3pheloxMXNmRW82d2Q5RE1nN01FeTlzQVhxUUw3UHdqNGNoazRnUzhSSUEyYXNOeVQ2MzdNQkc5V2taa3lOKy83MkF0Ri9iVnFXWVFkQThHS1FkNk9mNWdmalZabWNhQU1BQUE9PTwveG1sPjxtc2c+PGhkck1zZ0VsZW1lbnQ+PGhhbmRsZV8+NjkxNzUyOTAyNzc4MDkzMzM3NjwvaGFuZGxlXz48aXNBdHRhY2hlZD50cnVlPC9pc0F0dGFjaGVkPjxpc1JlYWRPbmx5PmZhbHNlPC9pc1JlYWRPbmx5PjwvaGRyTXNnRWxlbWVudD48eG1sRWxlbWVudD48aGFuZGxlXz42OTE3NTI5MDI3NzgwOTI5MDU2PC9oYW5kbGVfPjxpc0F0dGFjaGVkPnRydWU8L2lzQXR0YWNoZWQ+PGlzUmVhZE9ubHk+ZmFsc2U8L2lzUmVhZE9ubHk+PC94bWxFbGVtZW50Pjx0cmFjZVZhcmlhYmxlRWxlbWVudD48aGFuZGxlXz42OTE3NTI5MDI3NzczOTcyNjcyPC9oYW5kbGVfPjxpc0F0dGFjaGVkPnRydWU8L2lzQXR0YWNoZWQ+PGlzUmVhZE9ubHk+ZmFsc2U8L2lzUmVhZE9ubHk+PC90cmFjZVZhcmlhYmxlRWxlbWVudD48YXNzZW1ibHk+PG1lc3NhZ2U+PGNsZWFyZWQ+ZmFsc2U8L2NsZWFyZWQ+PHJlYWRPbmx5PnRydWU8L3JlYWRPbmx5PjxpbnB1dENvbnRleHRIYW5kbGVfPjA8L2lucHV0Q29udGV4dEhhbmRsZV8+PGhhbmRsZV8+NjkxNzUyOTAyNzc2OTAyMzgyNDwvaGFuZGxlXz48bXVzdEZpbmFsaXplPmZhbHNlPC9tdXN0RmluYWxpemU+PC9tZXNzYWdlPjxyZWFkT25seU1iTWVzc2FnZXM+dHJ1ZTwvcmVhZE9ubHlNYk1lc3NhZ2VzPjxoYW5kbGVfPjY5MTc1MjkwMjc3ODAxMDk0NTY8L2hhbmRsZV8+PGdsb2JhbEVudmlyb25tZW50PjxjbGVhcmVkPmZhbHNlPC9jbGVhcmVkPjxyZWFkT25seT5mYWxzZTwvcmVhZE9ubHk+PGlucHV0Q29udGV4dEhhbmRsZV8+MDwvaW5wdXRDb250ZXh0SGFuZGxlXz48aGFuZGxlXz4tNjkxNzUyOTAzMDQ2MTc0ODg0ODwvaGFuZGxlXz48bXVzdEZpbmFsaXplPmZhbHNlPC9tdXN0RmluYWxpemU+PC9nbG9iYWxFbnZpcm9ubWVudD48L2Fzc2VtYmx5Pjxib2R5RWxlbWVudD48aGFuZGxlXz42OTE3NTI5MDI3NzgwOTI5ODU2PC9oYW5kbGVfPjxpc0F0dGFjaGVkPnRydWU8L2lzQXR0YWNoZWQ+PGlzUmVhZE9ubHk+ZmFsc2U8L2lzUmVhZE9ubHk+PC9ib2R5RWxlbWVudD48cm9vdEVsZW1lbnQ+PGhhbmRsZV8+NjkxNzUyOTAyNzc4MDkyOTY5NjwvaGFuZGxlXz48aXNBdHRhY2hlZD50cnVlPC9pc0F0dGFjaGVkPjxpc1JlYWRPbmx5PmZhbHNlPC9pc1JlYWRPbmx5Pjwvcm9vdEVsZW1lbnQ+PG91dFB1dE1lc3NhZ2U+PGNsZWFyZWQ+ZmFsc2U8L2NsZWFyZWQ+PHJlYWRPbmx5PmZhbHNlPC9yZWFkT25seT48aW5wdXRDb250ZXh0SGFuZGxlXz4tNjkxNzUyOTAzMDQ2MTc4NzkyMDwvaW5wdXRDb250ZXh0SGFuZGxlXz48aGFuZGxlXz42OTE3NTI5MDI3NzY5MDI0MTc2PC9oYW5kbGVfPjxtdXN0RmluYWxpemU+dHJ1ZTwvbXVzdEZpbmFsaXplPjwvb3V0UHV0TWVzc2FnZT48aGVhZEVsZW1lbnQ+PGhhbmRsZV8+NjkxNzUyOTAyNzc4MDkzMzA1NjwvaGFuZGxlXz48aXNBdHRhY2hlZD50cnVlPC9pc0F0dGFjaGVkPjxpc1JlYWRPbmx5PmZhbHNlPC9pc1JlYWRPbmx5PjwvaGVhZEVsZW1lbnQ+PHZhcmlhYmxlRWxlbWVudD48aGFuZGxlXz42OTE3NTI5MDI3NzczOTcyMzg0PC9oYW5kbGVfPjxpc0F0dGFjaGVkPnRydWU8L2lzQXR0YWNoZWQ+PGlzUmVhZE9ubHk+ZmFsc2U8L2lzUmVhZE9ubHk+PC92YXJpYWJsZUVsZW1lbnQ+PG1xbWQ+PGhhbmRsZV8+NjkxNzUyOTAyNzc4MDgyNTY5NjwvaGFuZGxlXz48aXNBdHRhY2hlZD50cnVlPC9pc0F0dGFjaGVkPjxpc1JlYWRPbmx5PmZhbHNlPC9pc1JlYWRPbmx5PjwvbXFtZD48cmVxdWVzdEVsZW1lbnQ+PGhhbmRsZV8+NjkxNzUyOTAyNzc4MDkzMDAxNjwvaGFuZGxlXz48aXNBdHRhY2hlZD50cnVlPC9pc0F0dGFjaGVkPjxpc1JlYWRPbmx5PmZhbHNlPC9pc1JlYWRPbmx5PjwvcmVxdWVzdEVsZW1lbnQ+PC9tc2c+PG1zZ3NuPkVTQi0yMDE0MTAxNy0xMjA1MTAzMjA2MTE1MDY8L21zZ3NuPjxmd01vZGU+MTwvZndNb2RlPjwvZXNiSW5mbz4";
		// System.out.println(xml.length());
		// byte[] buf = StringX.decodeBase64(xml.getBytes());
		// System.out.println(new String(buf));
		// byte[] gzip = FileUtil.gzip(buf, 0, buf.length);
		// System.out.println(gzip.length);
		// byte[] msg = FileUtil.ungzip(gzip, 0, gzip.length);
		// System.out.println(new String(msg));
		//
		// xml =
		// "H4sIAAAAAAAAAF1Ty1LrMAxdl69guqe2y3MhzHAvlGEGumlZsGLSWFAPeWE7pfl7ZCsNuTcbS+foKNaJAjf7sjjeofO2rq6naianx1jltbHVx/X0Zb04uZreaAguq3yWByrSRxPYYmbQUTQBkmpSgdj1QOk/4jkBX5nbpvlr9P3qD4ghO3B3Qc+lOlNSXSaW8gO1LrWKlJKn88QRwBx+LTdazeV5pOSFUufygioSnCro7fSOxXo1k/woOZMKBOOpJM+KYt01evW6BHFIEuPyHd+R9CCGLE4leCwQw+iwqU2XOIdfLfr++iRNQXImmqrfg0/ueDYvUu/fz7VBTffqox62RaDewbVIDCfM+JCF1muyuY96uG5djsvY4fbhjXx+i7OOUC4rY7ixVeY6GuQXb5ytnQ2dPgMxxEyFrkH9BCKdDOVbzD99Wz5j2NZGR/P+RbjMWN/U3qZVob7jlAtwb8msVxAcMPiJ2KzazZ11sfEo6wd9DMg7MEy9sAXqQL7Pwj4chk4gS8RIA2asNyT637MBG9WkZkyN+/72AtF/bVqWYQdA8GKQd6Of5gfjVZmcaAMAAA==";
		// System.out.println(xml.length());
		// buf = StringX.decodeBase64(xml.getBytes());
		// msg = FileUtil.ungzip(buf, 0, buf.length);
		// System.out.println(new String(msg));
	}
}
