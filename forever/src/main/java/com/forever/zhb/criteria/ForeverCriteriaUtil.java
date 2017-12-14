package com.forever.zhb.criteria;

import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.forever.zhb.criteria.ForeverCriteria.QueryWords;
import com.forever.zhb.page.Page;
import com.forever.zhb.page.PageUtil;


public class ForeverCriteriaUtil<T> {
	
	protected static final Log logger = LogFactory.getLog(ForeverCriteriaUtil.class);
    private ForeverCriteriaUtil(){}

    public static <T> ForeverCriteriaUtil<T> getInstance(Class<T> clazz){
        return new ForeverCriteriaUtil<T>();
    }
    
    private Map<String, Criteria> associationMap;
    private Map<Integer,String> aliasMap;
    
    public Map<String, Criteria> getAssociationMap() {
        if(null == associationMap) associationMap = new HashMap<String, Criteria>();
        return associationMap;
    }
    
    public void setAssociationMap(Map<String, Criteria> associationMap) {
        this.associationMap = associationMap;
    }
    
    public List<T> search(Criteria cri,List<ForeverCriteria> list){
        boolean pageFlag = false;
        ProjectionList pl = null;
        ForeverPage<T> page = null;
        for(ForeverCriteria query:list){
            switch(query.getLink()){
            case PAGE:{
                pageFlag = true;
                page = (ForeverPage<T>)query.getValues()[0];
                if(null == page)page = new ForeverPage<T>();
                continue;
            }
            case AND:getAbsoluteCriteria(cri, query.getProperty()).add(getCriterion(cri, query));break;
            case OR:or(cri, query);break;
            case CONJUNCTION:conjunction(cri,query);break;
            case SQL:cri.add((Criterion)query.getValues()[0]);break;
            case ORDER:order(cri,query);break;
            case PROJECTIONS:pl = (ProjectionList) query.getValues()[0];break;
            }
        }
        if(pageFlag){
            int roxcount = (Integer)cri.setProjection(Projections.rowCount()).uniqueResult();
            page.setRowCount(roxcount);
            cri.setProjection(pl);
            cri.setFirstResult(page.getPageSize()*(page.getPage()-1));
            cri.setMaxResults(page.getPageSize());
            cri.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
        }
        //使用投影会使list结果为Object[],适用于统计
        if(null != pl)cri.setProjection(pl);
        return cri.list();
    }
    
    private void order(Criteria criteria, ForeverCriteria query) {
        Criteria cri = getAbsoluteCriteria(criteria, query.getProperty());
        String property = getAbsoluteProperty(query.getProperty());
        switch(query.getCompare()){
        case ASC:{
            cri.addOrder(Order.asc(property));
            break;
        }
        case DESC:{
            cri.addOrder(Order.desc(property));
            break;
        }
        }
    }

    private Criterion combineConjunction(Criteria criteria,ForeverCriteria query){
        Conjunction c = Restrictions.conjunction();
        for(ForeverCriteria xc : (ForeverCriteria[])query.getValues()){
            switch(xc.getLink()){
            case CONJUNCTION:{
                c.add(combineConjunction(criteria,xc));
                continue;
            }
            case OR:{
                c.add(combineOr(criteria,xc));
                continue;
            }
            case SQL:{
                Criterion criterion = (Criterion)xc.getValues()[0];
                c.add(criterion);
                continue;
            }
            }
            c.add(getCriterion(criteria,xc));
        }
        if(query.isOpposite()){
            return Restrictions.not(c);
        }
        return c;
    }
    
    private Criterion combineOr(Criteria criteria,ForeverCriteria query) {
        Disjunction d = Restrictions.disjunction();
        for(ForeverCriteria xc : (ForeverCriteria[])query.getValues()){
            switch(xc.getLink()){
            case CONJUNCTION:{
                d.add(combineConjunction(criteria,xc));
                continue;
            }
            case OR:{
                d.add(combineOr(criteria,xc));
                continue;
            }
            case SQL:{
                d.add((Criterion)xc.getValues()[0]);
                continue;
            }
            }
            d.add(getCriterion(criteria,xc));
        }
        if(query.isOpposite()){
            return Restrictions.not(d);
        }
        return d;
    }

    private void conjunction(Criteria criteria, ForeverCriteria query) {
        criteria.add(combineConjunction(criteria,query));
    }
    
    

    public ForeverPage<T> searchForPage(Criteria cri,List<ForeverCriteria> list){
        boolean pageFlag = false;
        ForeverPage<T> page = null;
        ProjectionList pl = null;
        for(ForeverCriteria query:list){
            switch(query.getLink()){
            case PAGE:{
                pageFlag = true;
                page = (ForeverPage<T>)query.getValues()[0];
                if(null == page)page = new ForeverPage<T>();
                continue;
            }
            case AND:getAbsoluteCriteria(cri, query.getProperty()).add(getCriterion(cri, query));break;
            case OR:or(cri, query);break;
            case CONJUNCTION:conjunction(cri,query);break;
            case SQL:cri.add((Criterion)query.getValues()[0]);break;
            case ORDER:order(cri,query);break;
            case PROJECTIONS:pl = (ProjectionList) query.getValues()[0];break;
            }
        }
        if(pageFlag){
            Long roxcount = (Long)cri.setProjection(Projections.rowCount()).uniqueResult();
            page.setRowCount(roxcount.intValue());
            cri.setProjection(pl);
            cri.setFirstResult(page.getPageSize()*(page.getPage()-1));
            cri.setMaxResults(page.getPageSize());
            //对于分页的对象，具有特定的pojo，使用root_entity封装list
            cri.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
            page.setList(cri.list());
        }else{
            cri.setProjection(pl);
            cri.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
            page = new ForeverPage<T>();
            page.setList(cri.list());
        }
        return page;
    }
    private void or(Criteria criteria,ForeverCriteria query){
        criteria.add(combineOr(criteria,query));
    }
    
    private Criterion getCriterion(Criteria criteria,ForeverCriteria query) {
        //除了可以获得Criteria，还通过它建立关联查询
        Criteria cri = getAbsoluteCriteria(criteria, query.getProperty());
        String property = getAbsoluteProperty(query.getProperty());
        Criterion c = null;
        switch(query.getCompare()){
        case BETWEEN:{
            c = Restrictions.between(property, query.getValues()[0], query.getValues()[1]);
            break;
        }
        case LIKE:{
            c = Restrictions.like(property, query.getValues()[0]);
            break;
        }
        case EQ:{
            c = Restrictions.eq(property, query.getValues()[0]);
            break;
        }
        case GT:{
            c = Restrictions.gt(property, query.getValues()[0]);
            break;
        }
        case GE:{
            c = Restrictions.ge(property, query.getValues()[0]);
            break;
        }
        case LT:{
            c = Restrictions.lt(property, query.getValues()[0]);
            break;
        }
        case LE:{
            c = Restrictions.le(property, query.getValues()[0]);
            break;
        }
        case IN:{
            c = Restrictions.in(property, query.getValues());
            break;
        }
        case NE:{
            c = Restrictions.ne(property, query.getValues()[0]);
            break;
        }
        case ISNULL:{
            c = Restrictions.isNull(property);
            break;
        }
        case ISNOTNULL:{
            c = Restrictions.isNotNull(property);
            break;
        }
        default:throw new IllegalArgumentException();
        }
        if(query.isOpposite()){
            return Restrictions.not(c);
        }
        return c;
    }
    
    public void setPageProperties(Integer page,Integer startRow,int pageSize,List<ForeverCriteria> condition,HttpServletRequest request){
        ForeverPage<T> XlrzPage = new ForeverPage<T>();
        if(null != page && page >0)XlrzPage.setPage(page);
        else{
            page = startRow/pageSize+1;
            XlrzPage.setPage(page);
        }
        XlrzPage.setPageSize(pageSize);
        XlrzPage.setStr(getRequestUrlWithParameter(request));
        ForeverCriteria xc = new ForeverCriteria();
        xc.setLink(QueryWords.PAGE);
        xc.setValues(new Object[]{XlrzPage});
        condition.add(xc);
    }
    public ForeverPage<T> getPage(List<ForeverCriteria> condition){
        for(ForeverCriteria w:condition){
            if(w.getLink() == QueryWords.PAGE){
                return (ForeverPage<T>) w.getValues()[0];
            }
        }
        return null;
    }
    
    public Page<T> getPage(ForeverPage<T> p){
        if(null == p) p = new ForeverPage<T>();
        Page<T> page = PageUtil.getPage(p.getList().iterator(), (p.getPage()-1)*p.getPageSize(), p.getPageSize(), p.getRowCount());
        return page;
    }
    
    private Criteria getAbsoluteCriteria(Criteria cri,String str){
        String[] grades = str.split("\\.");
        Criteria ab = cri;
        if(grades == null || grades.length == 0){
            return cri;
        }
        for(int i = 0;i<grades.length - 1;i++){
            String associatePath = grades[i];
            int code = associatePath.hashCode();
            String alias = getAliasMap().get(code);
            if(null == alias){
                alias = "_alias" + associatePath;
                getAliasMap().put(code, alias);
                Criteria ac = ab.createCriteria(associatePath, alias);
                getAssociationMap().put(alias, ac);
                ab = ac;
            }else{
                ab = getAssociationMap().get(getAliasMap().get(code));
            }
        }
        return ab;
    }
    
    private String getAbsoluteProperty(String str){
        if(StringUtils.isBlank(str)) return str;
        String[] grades = str.split("\\.");
        if(grades == null || grades.length < 2)return str;
        String property = getAliasMap().get(grades[grades.length-2].hashCode()) + "." + grades[grades.length-1];
        return property;
    }
    
    private static String getRequestUrlWithParameter(HttpServletRequest request){
        if(request == null)return null;
        StringBuffer url = request.getRequestURL();
        url.append("?");
        Enumeration en = request.getParameterNames();
        while(en.hasMoreElements()){
            String paramName = (String)en.nextElement();
            if(!(paramName.equals("page"))){
                if(url.indexOf("?") != (url.length() - 1))
                    url.append("&");
                url.append(paramName);
                try {
                    url.append("=" + java.net.URLEncoder.encode(request.getParameter(paramName), "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        return url.toString();
    }

    public void setAliasMap(Map<Integer,String> aliasMap) {
        this.aliasMap = aliasMap;
    }

    public Map<Integer,String> getAliasMap() {
        if(null == aliasMap) aliasMap = new HashMap<Integer, String>();
        return aliasMap;
    }

}
