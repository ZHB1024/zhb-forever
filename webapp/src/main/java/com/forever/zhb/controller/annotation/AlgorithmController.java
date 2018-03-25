package com.forever.zhb.controller.annotation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/algorithmController")
public class AlgorithmController {

	private Logger logger = LoggerFactory.getLogger(AlgorithmController.class);

	@RequestMapping("/test")
	public String test(HttpServletRequest request, HttpServletResponse response) throws Exception {
		/*int[] array = { 0, 1, 5, 6, 7, 9, 11, 14, 50, 69 };
		System.out.println(binarySearch(array,  7, 0, array.length));*/
	    int[] array = { 4, 2, 108, 1, 85, 9, 0, 14, 13, 56 ,34};
        shellSort(array);
		return "test.body.index";
	}

	public static void main(String[] args) {

		// 二分查找、折半查找
		/*int[] array = { 0, 2, 5, 6, 7, 9, 11, 14, 50, 69 ,90};
		System.out.println(binarySearch(array, 7, 0, array.length-1));*/

		// 10,10+2,10+2+2,......
		// System.out.println(sum2(4));

		// 斐波那契数
		// System.out.println(getFibo(9));

		// 统计字符串中每个字符出现的次数
		/*
		 * countChar1("aabbbcdd2f2c");
		 * System.out.println("--------------------------------");
		 * countChar2("aabbbcdd2f2c");
		 * System.out.println("--------------------------------");
		 * find("aaabbbcdd2f2c");
		 */
	    int[] array = { 4, 2, 108, 1, 85, 9, 0, 14, 13, 56 ,34};
	    /*shellSort(array);*/

	}
	
	//遍历list时，删除其中元素，可能引发的问题
	private void removeList(){
		List<String> name = new ArrayList<String>();
		name.add("aa");
		name.add("bb");
		name.add("cc");
		name.add("dd");
		int num = name.size();
		Map<Character, Integer> charMap = new HashMap<Character, Integer>();
		charMap.put('a', 12);
		charMap.put('a', 34);
		int temp = charMap.get('a');
		for (int i = 0; i < name.size(); i++) {
			if (name.get(i).equals("bb")) {
				name.remove(i);
			}
		}
	}

	// 二分查找、折半查找
	private static String binarySearch(int[] array, int target, int low, int high) {
		if (low >= high) {
			return "没有";
		}
		int mid = ( low + high ) / 2 ;
		if (array[mid] == target) {
			return "值为：" + target + " 位置在：" + mid;
		} else if (target < array[mid]  ) {
			return binarySearch(array, target, low, mid-1);
		} else {
			return binarySearch(array, target, mid+1, high);
		}
	}

	// 10,10+2,10+2+2,......
	private static int sum2(int num) {
		if (num < 1) {
			return 0;
		}
		if (num == 1) {
			return 10;
		}

		int temp = 2 + sum2(num - 1);
		return temp;
	}

	// 斐波那契数
	private static int getFibo(int i) {
		if (i < 1) {
			return 0;
		}
		if (i == 1 || i == 2) {
			return 1;
		} else {
			return getFibo(i - 1) + getFibo(i - 2);
		}

	}

	// 统计字符串中每个字符出现的次数
	private static void countChar1(String str) {
		if (StringUtils.isBlank(str)) {
			return;
		}
		char[] charArray = str.toCharArray();
		Map<Character, Integer> charMap = new HashMap<Character, Integer>();
		for (char c : charArray) {
			if (charMap.containsKey(c)) {
				charMap.put(c, charMap.get(c) + 1);
			} else {
				charMap.put(c, 1);
			}
		}
		charMap.forEach((k, v) -> {
			System.out.println(k + " : " + v);
		});
	}

	// 统计字符串中每个字符出现的次数
	private static void countChar2(String str) {
		if (StringUtils.isBlank(str)) {
			return;
		}
		int length = 0;
		while (str.length() > 0) {
			String firstChar = String.valueOf(str.charAt(0));
			String newString = str.replaceAll(firstChar, "");
			length = str.length() - newString.length();
			System.out.println(firstChar + " : " + length);
			str = newString;
		}
	}

	public static void find(String s) {
		// aabbbcdd2f2c
		// aaabbbcc
		StringBuffer sb = new StringBuffer(s);
		sb.append('%');
		int i = 0;// 字符串下标
		int j = 1; // 记录重复数
		int max = 1; // 记录最大的重复数
		int m = 0; // 作为note数组的下标
		int note[] = new int[s.length() / 2]; // 该数组是记录输出最大重复的子字符串的下标
		while (i < sb.length() - 1) {
			if (sb.charAt(i) == sb.charAt(i + 1)) {
				j = j + 1;
			} else {
				if (j == max && j != 1) { // 该判断就是为了以防如出现多个最大重复长度的字串，比如aaabbbcc就该有aaa，bbb两个
					m++;
					note[m] = i - max + 1;
				}
				if (j > max) {
					max = j;
					note[0] = i - max + 1;
					for (int n = 1; n < note.length; n++) {
						note[n] = -1; // 出现更大的重复字串则将以前记录的小标清除
					}
				}
				j = 1;
			}
			i++;
		}
		if (max == 1) {
			System.out.println("所有字符都只单个出现，并无连续");
			return;
		}
		for (int p = 0; p < note.length; p++) {
			if (note[p] != -1) {
				System.out.println(sb.substring(note[p], note[p] + max));
			}
		}

	}
	
//----------------------------------   排序   ------------------------------------------------------
	//冒泡排序O(n^2)
	public void bubbleSort(int[] values){
		if (null == values || values.length == 0) {
			System.out.println("--冒泡排序，请插入值");
		}
		int length = values.length;
		for(int i=1 ; i<length ; i++){
			for(int j = length-1; j >= i ; j--){
				if (values[j-1] > values[j]) {
					swap(values, j-1, j);
				}
			}
		}
		for (int i : values) {
			System.out.print(i + " , ");
		}
		
	}
	
	public void sort(int[] values){
		if (null == values || values.length == 0) {
			System.out.println("--排序，请插入值");
		}
		int length = values.length;
		for(int i=1 ; i<length ; i++){
			int low = i;
			for(int j = i; j >= low ; j--){
				if (values[j-1] > values[j]) {
					swap(values, j-1, j);
					low--;
				}
			}
		}
		
		for (int i : values) {
			System.out.print(i + " , ");
		}
		
	}
	
	//插入排序
	private static void insertSort(int[] arrays) {
	    if (null == arrays) {
            return;
        }
	    for (int i : arrays) {
	        System.out.print(i + " ,");
        }
	    
	    int length = arrays.length;
	    int j ;
	    for(int i=1; i< length;i++) {
	        int temp = arrays[i];
	        for(j = i ; j>0 && temp<arrays[j-1];j--) {
	            arrays[j] = arrays[j-1];
	        }
	        arrays[j] = temp;
	    }
	    
	    System.out.println("");
	    for (int i : arrays) {
            System.out.print(i + " ,");
        }
	}
	
	//希尔排序
	private static void shellSort(int[] arrays) {
	    if (null == arrays) {
            return ;
        }
	    for (int i : arrays) {
            System.out.print(i + " ,");
        }
	    
	    int j;
	    int length = arrays.length;
	    for(int gap = length/2;gap > 0; gap /=2) {
	        for(int i = gap ; i<length; i++) {
	            int temp = arrays[i];
	            for(j = i ; j >= gap&& temp<arrays[j-gap];j -= gap) {
	                arrays[j] = arrays[j-gap];
	            }
	            arrays[j]= temp;
	        }
	    }
	    
	    
	    System.out.println("");
        for (int i : arrays) {
            System.out.print(i + " ,");
        }
	}
	
	
	
	private void swap(int[] values,int left,int right){
		int temp = values[left];
		values[left] = values[right];
		values[right] = temp;
	}

}
