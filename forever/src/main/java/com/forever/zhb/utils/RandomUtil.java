package com.forever.zhb.utils;

import java.util.Random;

public class RandomUtil {
	
	private static Random random = new Random();

    public static Object getRandomObject(Object[] array) {
        Random random = new Random();
        int n = array.length;
        int index = random.nextInt(n);
        return array[index];
    }

    public static int getRandomInt(int[] array) {
        Random random = new Random();
        int n = array.length;
        int index = random.nextInt(n);
        return array[index];
    }

    public static String getRandomStrings(String[] array, int length) {
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        int n = array.length;
        for (int i = 0; i < length; ++i) {
            int index = random.nextInt(n);
            sb.append(array[index]);
        }
        return sb.toString();
    }

    public static String getRandomNumbers(int length) {
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; ++i) {
            int value = random.nextInt(10);
            sb.append(String.valueOf(value));
        }
        return sb.toString();
    }

    public static int getRandomInt(int start, int end) {
        Random random = new Random();
        int n = end - start + 1;
        int index = random.nextInt(n);
        return (start + index);
    }

    public static String getRandomString(int length) {
        String base = "0123456789abcdefghijklmnopqrstuvwxyz";
        int baseLength = base.length();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; ++i) {
            int index = random.nextInt(baseLength);
            sb.append(base.charAt(index));
        }
        return sb.toString();
    }

    public static String getRandomCaseSensitiveString(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int baseLength = base.length();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; ++i) {
            int index = random.nextInt(baseLength);
            sb.append(base.charAt(index));
        }
        return sb.toString();
    }

    public static int getRandomChooseByHash(String resource, int range) {
        if ((range < 0) || (null == resource)) {
            return 0;
        }

        int hashCode = Math.abs(resource.hashCode());
        return getRandomChoose(hashCode, range);
    }

    private static int getRandomChoose(int hashCode, int range) {
        int mod = hashCode % range;
        return mod;
    }

}
