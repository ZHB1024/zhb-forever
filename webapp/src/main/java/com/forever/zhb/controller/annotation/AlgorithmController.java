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
		/*
		 * int[] array = { 0, 1, 5, 6, 7, 9, 11, 14, 50, 69 };
		 * System.out.println(binarySearch(array, 7, 0, array.length));
		 */
		int[] array = { 4, 2, 108, 1, 85, 9, 0, 14, 13, 56, 34 };
		shellSort(array);
		return "test.body.index";
	}

	public static void main(String[] args) {

		// 二分查找、折半查找
		
		  /*int[] array = { 0, 2, 5, 6, 7, 9, 11, 14, 50, 69 ,90};
		  System.out.println(binarySearch(array, 2, 0, array.length-1));*/
		 

		/*Object[] array = { 0, 2, 5, 6, 7, 9, 11, 14, 50, 69 ,90};
		int index = binarySearchByComparable(array, 0, array.length, 2);
		System.out.println("位置：" + index );
		if (index >= 0) {
			System.out.println("值为：" + array[index]);
		}*/

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
		//int[] array = { 4, 2, 108, 1, 85, 9, 0, 14, 13, 56, 34 };
		/* shellSort(array); */

		// 冒泡排序
		 //bubbleSort(array);

		// 简单选择排序
		 //selectSort(array); 

		// 归并排序
		/*for (int i : array) {
			System.out.print(i + " ,");
		}
		mergeSort(array, 0, array.length - 1);
		System.out.println();
		for (int i : array) {
			System.out.print(i + " ,");
		}*/

		int[] nums = {4,2,7,3,1,9,0,23,8,97,56,12};
        bubble(nums);

	}

	// 遍历list时，删除其中元素，可能引发的问题
	private void removeList() {
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

     //--------------------------------------------------------查找---------------------------------------------------------------------

	/**
	 * 二分查找、折半查找
	 * @param array 目标有序序列，从小到大排列
	 * @param target  待查找的目标对象
	 * @param fromIndex  数组的开始查找下标位置
	 * @param toIndex    数组的结束查找下标位置
	 * @return 数组的位置
	 */
	private static int binarySearch(int[] array, int target, int fromIndex, int toIndex) {
		if (fromIndex > toIndex) {
			return -(fromIndex+1);
		}

		//int mid = fromIndex + (target-array[fromIndex])/(array[toIndex]-array[fromIndex])*(toIndex-fromIndex);

		while(toIndex >= fromIndex){
			int mid = (fromIndex + toIndex) / 2;
			if (array[mid] == target) {
				return mid;
			}else if (target < array[mid]) {
				toIndex = mid - 1;
			} else {
				fromIndex = mid + 1;
			}
		}
		return -(fromIndex+1);
	}

	private static String binarySearchByRecursion(int[] array, int target, int low, int high) {
		if (low >= high) {
			return "没有";
		}
		int mid = (low + high) / 2;
		if (array[mid] == target) {
			return "值为：" + target + " 位置在：" + mid;
		} else if (target < array[mid]) {
			return binarySearchByRecursion(array, target, low, mid - 1);
		} else {
			return binarySearchByRecursion(array, target, mid + 1, high);
		}
	}

	/**
	 * 二分查找、折半查找
	 * @param array 目标有序序列，从小到大排列
	 * @param key  待查找的目标对象
	 * @param fromIndex  数组的开始查找下标位置
	 * @param arrayLength    array.length
	 * @return 数组的位置
	 */
	private static int binarySearchByComparable(Object[] array, int fromIndex, int arrayLength, Object key) {
		int low = fromIndex;
		int high = arrayLength - 1;

		while (low <= high) {
			int mid = (low + high) >>> 1;
			@SuppressWarnings("rawtypes")
			Comparable midVal = (Comparable) array[mid];
			@SuppressWarnings("unchecked")
			int cmp = midVal.compareTo(key);

			if (cmp < 0)
				low = mid + 1;
			else if (cmp > 0)
				high = mid - 1;
			else
				return mid; // key found
		}
		return -(low + 1); // key not found.
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


	//并行查找，启动几个线程查找无序序列中某个值


    public static void bubble(int[] nums){
	    for (int i = 1;i<nums.length;i++){
	        int max = nums[i];
            int k=i;
            for (;k>0 && (nums[k-1] > max);k--){
                nums[k]=nums[k-1];
            }
            nums[k] = max;
        }

        for (int i:nums) {
            System.out.println(i);
        }
    }



	// ------------------------------------------------------------- 排序----------------------------------------------------------------

	// 冒泡排序O(n^2)
	public static void bubbleSort(int[] values) {
		if (null == values) {
			return;
		}
		for (int i : values) {
			System.out.print(i + " ,");
		}

		int length = values.length;
		for (int i = 1; i < length - 1; i++) {
			for (int j = length - 1; j >= i; j--) {
				if (values[j - 1] > values[j]) {
					swap(values, j - 1, j);
				}
			}
		}

		System.out.println("");
		for (int i : values) {
			System.out.print(i + " ,");
		}

	}

	// 简单选择排序
	private static void selectSort(int[] arrays) {
		if (null == arrays) {
			return;
		}
		for (int i : arrays) {
			System.out.print(i + " ,");
		}

		int length = arrays.length;
		int min, j;
		for (int i = 0; i < length; i++) {
			min = i;
			for (j = i + 1; j < length; j++) {
				if (arrays[min] > arrays[j]) {
					min = j;
				}
			}
			if (min != i) {
				swap(arrays, i, min);
			}
		}

		System.out.println("");
		for (int i : arrays) {
			System.out.print(i + " ,");
		}
	}

	// 插入排序
	private static void insertSort(int[] arrays) {
		if (null == arrays) {
			return;
		}
		for (int i : arrays) {
			System.out.print(i + " ,");
		}

		int length = arrays.length;
		int j;
		for (int i = 1; i < length; i++) {
			int temp = arrays[i];
			for (j = i; j > 0 && temp < arrays[j - 1]; j--) {
				arrays[j] = arrays[j - 1];
			}
			arrays[j] = temp;
		}

		System.out.println("");
		for (int i : arrays) {
			System.out.print(i + " ,");
		}
	}

	// 希尔排序
	private static void shellSort(int[] arrays) {
		if (null == arrays) {
			return;
		}
		for (int i : arrays) {
			System.out.print(i + " ,");
		}

		int j;
		int length = arrays.length;
		for (int gap = length / 2; gap > 0; gap /= 2) {
			for (int i = gap; i < length; i++) {
				int temp = arrays[i];
				for (j = i; j >= gap && temp < arrays[j - gap]; j -= gap) {
					arrays[j] = arrays[j - gap];
				}
				arrays[j] = temp;
			}
		}

		System.out.println("");
		for (int i : arrays) {
			System.out.print(i + " ,");
		}
	}

	// 归并排序
	private static int[] sort(int[] a, int low, int high) {
		if (low < high) {
			int mid = (low + high) / 2;
			sort(a, low, mid);
			sort(a, mid + 1, high);
			// 左右归并
			merge(a, low, mid, high);
		}
		return a;
	}

	/*
	 * private static void merge(int[] a, int low, int mid, int high) { int[]
	 * temp = new int[high - low + 1]; int i = low; int j = mid + 1; int k = 0;
	 * // 把较小的数先移到新数组中 while (i <= mid && j <= high) { if (a[i] < a[j]) {
	 * temp[k++] = a[i++]; } else { temp[k++] = a[j++]; } } // 把左边剩余的数移入数组 while
	 * (i <= mid) { temp[k++] = a[i++]; } // 把右边边剩余的数移入数组 while (j <= high) {
	 * temp[k++] = a[j++]; } // 把新数组中的数覆盖nums数组 for (int x = 0; x < temp.length;
	 * x++) { a[x + low] = temp[x]; } }
	 */

	private static int[] mergeSort(int[] arrays, int low, int high) {
		if (low < high) {
			int mid = (low + high) / 2;
			mergeSort(arrays, low, mid);
			mergeSort(arrays, mid + 1, high);
			merge(arrays, low, mid, high);
		}
		return arrays;
	}

	private static void merge(int[] arrays, int low, int mid, int high) {
		int[] temp = new int[high - low + 1];
		int i = low;
		int j = mid + 1;
		int k = 0;
		// 把较小的放入临时数组中
		while (i <= mid && j <= high) {
			if (arrays[i] < arrays[j]) {
				temp[k++] = arrays[i++];
			} else {
				temp[k++] = arrays[j++];
			}
		}

		// 如果左边还剩余，则把左边剩余的放入临时数组中
		while (i <= mid) {
			temp[k++] = arrays[i++];
		}

		// 如果右边还剩余，则把右边剩余的放入临时数组中
		while (j <= high) {
			temp[k++] = arrays[j++];
		}

		// 把排好序的临时数组，整理到目标数组中
		for (int t = 0; t < temp.length; t++) {
			arrays[t + low] = temp[t];
		}
	}

	private static void swap(int[] values, int left, int right) {
		int temp = values[left];
		values[left] = values[right];
		values[right] = temp;
	}

}
