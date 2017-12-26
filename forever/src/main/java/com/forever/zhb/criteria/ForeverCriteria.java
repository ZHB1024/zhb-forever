package com.forever.zhb.criteria;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.Type;

public class ForeverCriteria implements Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -4342692124057235010L;
	private QueryWords link;
    private QueryWords compare;
    private boolean opposite;
    private String property;
    private Object[] values;
    
    public ForeverCriteria(){}

    public boolean isOpposite() {
        return opposite;
    }

    public void setOpposite(boolean opposite) {
        this.opposite = opposite;
    }

    public QueryWords getLink() {
        return link;
    }

    public void setLink(QueryWords link) {
        this.link = link;
    }

    public QueryWords getCompare() {
        return compare;
    }

    public void setCompare(QueryWords compare) {
        this.compare = compare;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public Object[] getValues() {
        return values;
    }

    public void setValues(Object[] values) {
        this.values = values;
    }

    public static ForeverCriteria between(String property,Object ...objects){
    	ForeverCriteria xc = new ForeverCriteria();
        xc.setLink(QueryWords.AND);
        xc.setCompare(QueryWords.BETWEEN);
        xc.setProperty(property);
        xc.setValues(objects);
        return xc;
    }
    
    public static ForeverCriteria like(String property,Object ...objects){
        ForeverCriteria xc = new ForeverCriteria();
        xc.setLink(QueryWords.AND);
        xc.setCompare(QueryWords.LIKE);
        xc.setProperty(property);
        xc.setValues(objects);
        return xc;
    }

    public static ForeverCriteria order(String property,QueryWords sortWord){
        ForeverCriteria xc = new ForeverCriteria();
        xc.setLink(QueryWords.ORDER);
        xc.setCompare(sortWord);
        xc.setProperty(property);
        return xc;
    }
    
    public static ForeverCriteria eq(String property,Object object){
        ForeverCriteria xc = new ForeverCriteria();
        xc.setLink(QueryWords.AND);
        xc.setCompare(QueryWords.EQ);
        xc.setProperty(property);
        xc.setValues(new Object[]{object});
        return xc;
    }
    
    public static ForeverCriteria ne(String property,Object object){
        ForeverCriteria xc = new ForeverCriteria();
        xc.setLink(QueryWords.AND);
        xc.setCompare(QueryWords.NE);
        xc.setProperty(property);
        xc.setValues(new Object[]{object});
        return xc;
    }
    
    public static ForeverCriteria ge(String property,Object object){
        ForeverCriteria xc = new ForeverCriteria();
        xc.setLink(QueryWords.AND);
        xc.setCompare(QueryWords.GE);
        xc.setProperty(property);
        xc.setValues(new Object[]{object});
        return xc;
    }
    
    public static ForeverCriteria le(String property,Object object){
        ForeverCriteria xc = new ForeverCriteria();
        xc.setLink(QueryWords.AND);
        xc.setCompare(QueryWords.LE);
        xc.setProperty(property);
        xc.setValues(new Object[]{object});
        return xc;
    }
    
    public static ForeverCriteria gt(String property,Object object){
        ForeverCriteria xc = new ForeverCriteria();
        xc.setLink(QueryWords.AND);
        xc.setCompare(QueryWords.GT);
        xc.setProperty(property);
        xc.setValues(new Object[]{object});
        return xc;
    }
    
    public static ForeverCriteria lt(String property,Object object){
        ForeverCriteria xc = new ForeverCriteria();
        xc.setLink(QueryWords.AND);
        xc.setCompare(QueryWords.LT);
        xc.setProperty(property);
        xc.setValues(new Object[]{object});
        return xc;
    }
    
    /**
     * 同样是实现IN
     * 由于IN有长度限制，所以这个方法会把过长的数组拆分成多个，并用or连接
     * @param property
     * @param objects
     * @return
     */
    public static ForeverCriteria in(String property,Object ...objects){
        if(objects != null && objects.length <= 1000){
            ForeverCriteria xc = new ForeverCriteria();
            xc.setLink(QueryWords.AND);
            xc.setCompare(QueryWords.IN);
            xc.setProperty(property);
            xc.setValues(objects);
            return xc;
        }else{
            List<ForeverCriteria> ors = new ArrayList<ForeverCriteria>();
            for(int i = 0;;i++){
                if(1000*(i+1) >= objects.length-1){
                    int length = objects.length - 1000*i;
                    Object[] split = new Object[length];
                    System.arraycopy(objects, 1000*i, split, 0, length);
                    ForeverCriteria xc = new ForeverCriteria();
                    xc.setLink(QueryWords.AND);
                    xc.setCompare(QueryWords.IN);
                    xc.setProperty(property);
                    xc.setValues(split);
                    ors.add(xc);
                    break;
                }else{
                    int length = 1000;
                    Object[] split = new Object[length];
                    System.arraycopy(objects, 1000*i, split, 0, length);
                    ForeverCriteria xc = new ForeverCriteria();
                    xc.setLink(QueryWords.AND);
                    xc.setCompare(QueryWords.IN);
                    xc.setProperty(property);
                    xc.setValues(split);
                    ors.add(xc);
                }
            }
            return ForeverCriteria.or(ors.toArray(new ForeverCriteria[]{}));
        }
    }
    
    public static ForeverCriteria isNull(String property){
        ForeverCriteria xc = new ForeverCriteria();
        xc.setLink(QueryWords.AND);
        xc.setCompare(QueryWords.ISNULL);
        xc.setProperty(property);
        return xc;
    }
    
    public static ForeverCriteria isNotNull(String property){
        ForeverCriteria xc = new ForeverCriteria();
        xc.setLink(QueryWords.AND);
        xc.setCompare(QueryWords.ISNOTNULL);
        xc.setProperty(property);
        return xc;
    }
    
    public static ForeverCriteria not(ForeverCriteria xc){
        xc.setOpposite(true);
        return xc;
    }
    
    /**
     * 实际为disjunction。
     * 查询对象属性只能在同表中可寻，不支持外联查询非对象引用字段
     * @param conditions
     * @return
     */
    public static ForeverCriteria or(ForeverCriteria...conditions){
        ForeverCriteria xc = new ForeverCriteria();
        xc.setLink(QueryWords.OR);
        xc.setValues(conditions);
        return xc;
    }
    
    public static ForeverCriteria sql(String hql,Object[] values,Type[] types){
        ForeverCriteria xc = new ForeverCriteria();
        xc.setLink(QueryWords.SQL);
        xc.setProperty(hql);
        if(values != null && values.length != 0){
            xc.setValues(new Object[]{Restrictions.sqlRestriction(hql, values, types)});
        }else{
            xc.setValues(new Object[]{Restrictions.sqlRestriction(hql)});
        }
        return xc;
    }
    
    /**
     * 查询对象属性只能在同表中可寻，不支持外联查询非对象引用字段
     * @param conditions
     * @return
     */
    public static ForeverCriteria conjunction(ForeverCriteria...conditions){
        ForeverCriteria xc = new ForeverCriteria();
        xc.setLink(QueryWords.CONJUNCTION);
        xc.setValues(conditions);
        return xc;
    }
    
    public static ForeverCriteria projectionList(ProjectionList list){
        ForeverCriteria xc = new ForeverCriteria();
        xc.setLink(QueryWords.PROJECTIONS);
        xc.setValues(new Object[]{list});
        return xc;
    }
    
    public static enum QueryWords{
        /**
         * 与
         */
        AND,
        /**
         * 或
         */
        OR,
        /**
         * 非
         */
        NOT,
        /**
         * 组合
         */
        CONJUNCTION,
        /**
         * 之间
         */
        BETWEEN,
        /**
         * like
         */
        LIKE,
        /**
         * 排序
         */
        ORDER,
        /**
         * =
         */
        EQ,
        /**
         * !=
         */
        NE,
        /**
         * >=
         */
        GE,
        /**
         * <=
         */
        LE,
        /**
         * 大于
         */
        GT,
        /**
         * 小于
         */
        LT,
        /**
         * 在...范围
         */
        IN,
        /**
         * 升序
         */
        ASC,
        /**
         * 降序
         */
        DESC,
        /**
         * 空
         */
        ISNULL,
        /**
         * 非空
         */
        ISNOTNULL,
        /**
         * HQL
         */
        SQL,
        /**
         * 投影
         */
        PROJECTIONS,
        /**
         * 分页
         */
        PAGE;
    }

}
