package com.forever.zhb.controller.annotation;

import com.forever.zhb.basic.BasicController;
import com.forever.zhb.utils.AESUtil;
import com.forever.zhb.utils.Geoip2Util;
import com.forever.zhb.utils.IPUtil;
import com.forever.zhb.utils.MessageUtil;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/testController")
public class TestController extends BasicController {

	private Logger logger = LoggerFactory.getLogger(TestController.class);

	@RequestMapping("/contentTest")
	public String contentTest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String groupId = request.getParameter("groupId");
		String decGroupId = AESUtil.decrypt(groupId, AESUtil.findKeyById(""));
		JSONObject jo = new JSONObject();

		List<String> students = new ArrayList<String>();
		students.add("张会彬");
		students.add(decGroupId);
		jo.put("students", students);

		String encryRes = AESUtil.encrypt(jo.toString(), AESUtil.findKeyById(""));

		response.setHeader("Content-Type", "application/json; charset=utf-8");
		// 中文显示
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		pw.append(encryRes);
		pw.close();
		return "test.body.index";
	}

	@RequestMapping("/test")
	public String test(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String message = MessageUtil.getMessage("0001", new Object[] { "张会彬" });
		logger.info(message);
		return "test.body.index";
	}

	@RequestMapping("/ajaxTest")
	public void ajaxTest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<String> names = new ArrayList<String>();
		names.add("北京");
		names.add("上海");
		names.add("天津");
		setResponse(response);
		ajaxMessage.setFlag("true");
		ajaxMessage.setO(names);
		writeJSON(ajaxMessage, response);
	}

	@RequestMapping("/city")
	public void city(HttpServletRequest request, HttpServletResponse responses) {
        //171.108.233.157      111.202.226.100
        //String ip = "111.202.226.100";//172.16.10.215
        String ip = IPUtil.getIpAdrress(request);

        String city = Geoip2Util.getCity(ip);
        String province = Geoip2Util.getProvince(ip);
        String country = Geoip2Util.getCountry(ip);

        logger.info(city);
        logger.info(province);
        logger.info(country);

	}

	@ResponseBody
	@RequestMapping("/ajaxResponseBody")
	public Map<String, Object> ajaxResponseBody(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> names = new HashMap<String, Object>();
		names.put("name","zhanghuibin");
		return names;
		/*setResponse(response);
		ajaxMessage.setFlag("true");
		ajaxMessage.setO(names);
		writeJSON(ajaxMessage, response);*/
	}

	@RequestMapping("/toLayer")
	public String toLayer(HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("----------------------");
		logger.info("----------------------");
		logger.info("----------------------");
		logger.info("----------------------");
		return "layer.welcome.index";
	}

	public static void main(String[] args) throws FileNotFoundException, IOException, InstantiationException,
			IllegalAccessException, ClassNotFoundException, NoSuchMethodException, SecurityException,
			IllegalArgumentException, InvocationTargetException {

		/*
		 * TestController inc = new TestController(); int i = 0; inc.fermin(i);
		 * i = i++; System.out.println(i);
		 */

		// System.out.println("main:" + returnFinally());
		// printPrimeNumber(100);
		
		//TestInstance.printName();
		//TestAbstract.printName();
		/*Integer i = new Integer(10);
		java.lang.Integer in = new java.lang.Integer(10);
		System.out.println(i.toString(10));
		System.out.println( in.toString(10));*/
		//System.out.println( StaticTest.VALUE20);
		//StaticTest.getInstance();
		
		/*Boolean isBoolean = new Boolean(false);
		String isString = new String("false");
		updateBoolean(isBoolean,isString);
		System.out.println(isBoolean + " " + isString);*/
		
		//System.out.println( java.lang.Integer.valueOf(128) == new java.lang.Integer(128));
		
		//线程池
		/*ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 200, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(5));

        for(int i=0;i<15;i++){
            MyTask myTask = new MyTask(i);
            executor.execute(myTask);
            System.out.println("线程池中线程数目："+executor.getPoolSize()+"，队列中等待执行的任务数目："+
            executor.getQueue().size()+"，已执行玩别的任务数目："+executor.getCompletedTaskCount());
        }
        executor.shutdown();*/

		List<String> allValues = new ArrayList<String>();
		allValues.add("1");
		allValues.add("2");
		allValues.add("3");
		allValues.add("4");

		List<String> temp = new ArrayList<String>();
		temp.add("1");
		temp.add("2");
		allValues.removeAll(temp);
		for (String value:allValues) {
			System.out.println(value);
		}

	}
	
	public static void updateBoolean(Boolean isBoo,String isString){
		isBoo = true;
		isString = "true";
	}

	private static int countNumber(String srcText, String findText) {
		int count = 0;
		Pattern p = Pattern.compile(findText);
		Matcher m = p.matcher(srcText);
		while (m.find()) {
			count++;
		}
		return count;
	}

	private static int returnFinally() {
		int value = 0;
		try {
			return value += 1;
		} finally {
			if (value > 0) {
				value += 10;
				System.out.println("value:" + value);
			}
		}

		/*
		 * try { value += 1 ; return value; } finally { value += 2 ; return
		 * value; }
		 */
	}

	private static void printPrimeNumber(int number) {
		for (int i = 2; i < number; i++) {
			boolean flag = true;
			for (int j = 2; j < i; j++) {
				if (i % j == 0) {
					flag = false;
					break;
				}
			}
			if (flag) {
				System.out.println(i + " 是素数");
			}
		}
	}

	void fermin(int i) {
		i++;
	}

	// java 5种 获取对象的方式
	private void getObjects() throws Exception {
		// By using new keyword
		Employee emp1 = new Employee("zhang");
		emp1.setName("Naresh");
		System.out.println(emp1 + ", hashcode : " + emp1.hashCode());

		// By using Class class's newInstance() method
		Employee emp2 = (Employee) Class.forName("com.forever.zhb.controller.annotation.Employee").newInstance();
		// Or we can simply do this
		// Employee emp2 = Employee.class.newInstance();
		emp2.setName("Rishi");
		System.out.println(emp2 + ", hashcode : " + emp2.hashCode());

		// By using Constructor class's newInstance() method
		Constructor<Employee> constructor = Employee.class.getConstructor(String.class);
		Employee emp3 = constructor.newInstance("");
		emp3.setName("Yogesh");
		System.out.println(emp3 + ", hashcode : " + emp3.hashCode());

		// By using clone() method
		Employee emp4 = (Employee) emp3.clone();
		emp4.setName("Atul");
		System.out.println(emp4 + ", hashcode : " + emp4.hashCode());

		// By using Deserialization
		// Serialization
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("data.obj"));
		out.writeObject(emp4);
		out.close();
		// Deserialization
		ObjectInputStream in = new ObjectInputStream(new FileInputStream("data.obj"));
		Employee emp5 = (Employee) in.readObject();
		in.close();
		emp5.setName("Akash");
		System.out.println(emp5 + ", hashcode : " + emp5.hashCode());
	}

}

class Employee implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;
	private String name;

	public Employee() {
		System.out.println("Employee Constructor() Called...");
	}

	public Employee(String name) {
		System.out.println("Employee Constructor(name) Called...");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Employee [name=" + name + "]";
	}

	@Override
	public Object clone() {
		Object obj = null;
		try {
			obj = super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return obj;
	}

}

interface TestInstance{
	static void printName(){
		System.out.println("this interface static method !");
	}
}

abstract class TestAbstract{
	static void printName(){
		System.out.println("this abstract static method !");
	}
}


class MyTask implements Runnable {
    private int taskNum;
 
    public MyTask(int num) {
        this.taskNum = num;
    }
 
    @Override
    public void run() {
        System.out.println("正在执行task "+taskNum);
        try {
        	System.out.println("当前线程：" + Thread.currentThread().getName());
            Thread.currentThread().sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("task "+taskNum+"执行完毕");
    }
}
