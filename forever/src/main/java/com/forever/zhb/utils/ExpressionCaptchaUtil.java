package com.forever.zhb.utils;


import ognl.Ognl;
import ognl.OgnlContext;
import ognl.OgnlException;

public class ExpressionCaptchaUtil {

    /**
     * 产生min-max之间的随机数字
     * @param min
     * @param max
     * @return
     */
    public static final long getRandom(int min, int max){
        return Math.round(Math.random()*(max-min)+min);
    }

    /**
     * 获取min-max之间的n个不重复随机数字<br/>
     * n<=max-min否则返回null
     * @param min
     * @param max
     * @param n
     * @return
     */
    public static final long[] getRandoms(int min, int max, int n){
        if(n > max-min)
            return null;
        long[] longRet = new long[n];
        long intRd = 0; // 存放随机数
        int count = 0; // 记录生成的随机数个数
        int flag = 0; // 是否已经生成过标志
        while (count < n) {
            intRd = getRandom(min, max);
            for (int i = 0; i < count; i++) {
                if (longRet[i] == intRd) {
                    flag = 1;
                    break;
                } else {
                    flag = 0;
                }
            }
            if (flag == 0) {
                longRet[count] = intRd;
                count++;
            }
        }
        return longRet;
    }

    /**
     * 随机获取运算符，只取 + 、-和 *
     * @return
     */
    public static final String getOperator(){
        int choose = (int) getRandom(0, 2);
        switch (choose) {
            case 0:
                return "+";
            case 1:
                return "-";
            case 2:
                return "*";
            default:
                return "+";
        }
    }

    /**
     * 计算表达式
     * @param expressionString
     * @return
     * @throws OgnlException
     */
    public static final int getExpressionResults(String expressionString) throws OgnlException {
        OgnlContext ctx = new OgnlContext();
        Object expression = Ognl.parseExpression(expressionString);
        return (Integer) Ognl.getValue(expression, ctx, (Object)null);
    }


    public  static void main(String[] rags){
        //可自定义操作数范围和操作数个数，增加表达式复杂度，目前表达式计算只允许'+'、'-'、'*'运算
        long[] opts = ExpressionCaptchaUtil.getRandoms(0, 10, 3);
        StringBuffer expression = new StringBuffer();
        for (int i = 0; i < opts.length; i++) {
            expression.append(opts[i]);
            if(i < opts.length - 1){
                expression.append(ExpressionCaptchaUtil.getOperator());
            }
        }
        String word = expression.toString();//数学表达式
        try {
            long res = ExpressionCaptchaUtil.getExpressionResults(word);//表达式计算结果
        } catch (OgnlException e) {
            e.printStackTrace();
        }
    }

}
