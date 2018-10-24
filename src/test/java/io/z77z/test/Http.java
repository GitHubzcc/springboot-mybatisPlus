package io.z77z.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Http {
	static int a = 0;

	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			threadOne();
		}
	}

	private static void threadOne() {
		MutliThread m1 = new MutliThread();
		Thread t1 = new Thread(m1);
		t1.start();
	}

	public static String getRandomString(int length, String string) { // length表示生成字符串的长度
		String base = string;
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return URL 所代表远程资源的响应结果
	 */
	public static String sendGet(String url, String param) {
		String result = "";
		BufferedReader br = null;
		BufferedWriter bw = null;
		try {
			String urlNameString = url + "?" + param;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
				System.out.println(key + "--->" + map.get(key));
			}
			// 定义 BufferedReader输入流来读取URL的响应
			br = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			FileWriter fw = new FileWriter("D:/test.html", true);
			bw = new BufferedWriter(fw);
			String line;
			if (!connection.getContentType().toString().equals("404")) {
				synchronized (Http.class) {
					while ((line = br.readLine()) != null) {
						bw.write(line);// 按行来读取url的响应，并循环将每行追加到文件中
					}
				}
			}
			System.out.println("``````````"
					+ connection.getContentType().toString());
			result += connection.getContentEncoding();
			result += connection.getContentLength();
			result += connection.getContentType();
			result += connection.getDate();
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (br != null) {
					br.close();
				}
				if (bw != null) {
					bw.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader br = null;
		BufferedWriter bw = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			HttpURLConnection conn = (HttpURLConnection) realUrl
					.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "application/json, text/javascript, */*; q=0.01");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setRequestProperty("Cookie", "stb_session=o4isVdU8DGlQHG%2BiZ1omFtdwbzG4vgKecsNXTUuFE6qgfGSFWCVMjld%2Bi4JUfNa6rCI%2FwZZEKcC5zhj2k7Qozsn2fiVIEDP0KnFGaf6th4GuYq122VacYDsQPtbQCikb3npdft4kw6HOiSciO2GVrAsivc11%2FcMree8P0gNSa%2FHgORNwKHcRFFZa91uHE9V40FLa9qfcf04imjfW989NWnZtJ7RbRp46otha37RDiBdbWwsXNhdIUjw4Fi3v2BWjfQvNmwjgqhKuH7YRicB2kjq1IgPCdwfNjk9ovxPgLHLYjWl3Xw1l0fpZj21C0riLA0p2P1UK9yIX2mWjaNv6BaRjq035JrugcK3Sh93sTAURlb%2BU7rTA%2FH75BL2%2BRvN0IWIWiTc5tDiThqLXcFUJVf4hUytWL8zUOzyht5hW%2BSf6FLU6eQvl0SyvhsYzRr0aMJGy%2BZAT6yrcvbquyi%2FmUKCLuHID%2Bc7xpygNeViL7w%2FLHyugdEev%2B45uCvQBBRzGVEb1Ngt%2F4UPDJ9TC2nPCJgPpdD7iAntSrkelzzvgLtqMu43RNGgW4IpJ6WuSDww0Iub4iVWhlNu2h%2BEYl%2F3aeKcC5GpRhXE7LBvJ0lGPwk4EsWKT5lf1RJ6GJoxTjHoUeohNJBYQzklWVYHYAXaquSVQ%2BOMPUdpf6w65IUO8dCuGPY1lrqTRmsntx0WiCpIJb3DLWREbVZRJMqNxY5ziizcwOAgjT9aNhtHpEVE1QG2PqP4%2Fw4DbPNb3qG0aKRMbb1mS5SYMvCJXaJOaRpoZNHYO9bivgXQsYMajnevmUkkaEuDirqWhdnbL8wRBKkOd5Mo%2F4vglZAxMZpE7fFVpZVv8ltpx4bdzgPkZJ9eIDUkYhiR59Vk9%2Bpv8bkWYOuYivAaTBNN2YH6OBiPmcQuGkciC0AJO7S%2FfPoj%2BLXtosTs%3D; stb_csrf_cookie=7a07bb186d8482975a1a42f31b43bec9");// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应

			br = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			FileWriter fw = new FileWriter("D:/test.html", true);
			bw = new BufferedWriter(fw);
			String line;
			synchronized (Http.class) {
				while ((line = br.readLine()) != null) {
					bw.write(line);// 按行来读取url的响应，并循环将每行追加到文件中
				}
			}
			result += conn.getContentEncoding();
			result += conn.getContentLength();
			result += conn.getContentType();
			result += conn.getDate();
			result += conn.getResponseCode();
		} catch (Exception e) {
			a = a + 1;
			System.out.println("错误次数：" + a);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (bw != null) {
					bw.close();
				}
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}

class MutliThread implements Runnable {
	public void run() {
		while(true){
			// System.getProperties().setProperty("http.proxyHost","119.53.126.150");
			// // 动态代理地址
			// System.getProperties().setProperty("http.proxyPort", "8118");

			// System.out.println(Http.sendPost("http://www.xdmeng.cn/",
			// "Cookie=thinkphp_show_page_trace=0|0; AJSTAT_ok_times=1; PHPSESSID=ebnqbfvmjh9lfctpvae9s18fd6; thinkphp_show_page_trace=0|0; Hm_lvt_e610e48c5e960326a67382f1f6de2bf3=1473042065,1473063671,1473316546,1473317343; Hm_lpvt_e610e48c5e960326a67382f1f6de2bf3=1473318413&Referer=http://www.xdmeng.cn/"));
			// System.out.println(Http.sendPost("http://www.xdmeng.cn/data/upload/20160711/5782c3766658a.jpg","Cookie=PHPSESSID=9su5j0hot0u6qv4mbddin68al7; thinkphp_show_page_trace=0|0; Hm_lvt_e610e48c5e960326a67382f1f6de2bf3=1473058676,1473316007; Hm_lpvt_e610e48c5e960326a67382f1f6de2bf3=1473316365&Referer=http://www.xdmeng.cn/"));
			// System.out.println(Http.sendPost("http://www.xiniu.com/",""));
			System.out.println(Http.sendPost("http://bbs.anbig.com/index.php/message/send", "receiver_uid=1&content=%82&stb_csrf_token=7a07bb186d8482975a1a42f31b43bec9"));//System.out.println(Http.sendGet("http://www.javacoder.top/platform/feedback/doFeedback",""));
			// System.out.println(Http.sendGet("http://115.28.18.158/css/jcala-md.min.css",""));
			// Http.getRandomString(1,"34578")+
			// Http.getRandomString(1,"234567890")
			// +Http.getRandomString(8,"0123456789")+"&yzm=5189&submit=我要领奖"));
		}
	}
}