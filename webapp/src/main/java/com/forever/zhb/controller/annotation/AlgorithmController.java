package com.forever.zhb.controller.annotation;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		return "test.body.index";
	}

	public static void main(String[] args) {

		// 二分查找、折半查找
		/*
		 * int[] array = {0,2,5,6,7,9,11,14,50,69,70};
		 * System.out.println(binarySearch(array, 2, 0, 11));
		 */

		// 10,10+2,10+2+2,......
		System.out.println(sum2(4));
		
		// 斐波那契数
		System.out.println(getFibo(9));

	}

	// 二分查找、折半查找
	private static int binarySearch(int[] array, int target, int low, int high) {
		if (low >= high) {
			return Integer.MAX_VALUE;
		}
		int mid = low + (high - low) / 2;
		if (array[mid] == target) {
			return mid;
		} else if (array[mid] > target) {
			return binarySearch(array, target, low, mid - 1);
		} else {
			return binarySearch(array, target, mid + 1, high);
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
		}else {
			return getFibo(i - 1) + getFibo(i - 2);
		}

	}

}
