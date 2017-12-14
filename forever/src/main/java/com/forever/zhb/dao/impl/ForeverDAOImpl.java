package com.forever.zhb.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.forever.zhb.criteria.ForeverCriteria;
import com.forever.zhb.criteria.ForeverCriteriaUtil;
import com.forever.zhb.dao.IForeverDAO;
import com.forever.zhb.pojo.FileInfoData;
import com.forever.zhb.pojo.FunctionInfoData;
import com.forever.zhb.pojo.LoginInfoData;
import com.forever.zhb.pojo.NewsInfoData;
import com.forever.zhb.pojo.RoleInfoData;
import com.forever.zhb.pojo.User;
import com.forever.zhb.pojo.UserInfoData;
import com.forever.zhb.transformer.AliasToArrayListResultTransformer;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class ForeverDAOImpl implements IForeverDAO {

    private static Class<User> clazz = User.class;
    
    private static Class<FileInfoData> fileInfoDataClass = FileInfoData.class;

    private SessionFactory sessionFactory;
    
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

/*function_info*/    
    public void addFunctionInfo(FunctionInfoData functionInfoData){
        sessionFactory.getCurrentSession().save(functionInfoData);
    }
    
    public FunctionInfoData getFunctionInfoByName(String name){
        String hql = "select p from FunctionInfoData p where p.name = :name";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString("name", name);
        List<FunctionInfoData> result = query.list();
        return result.size() == 0?null :result.get(0);
    }
    
    public List<FunctionInfoData> getFunctionInfos(){
        String hql = "select p from FunctionInfoData p ";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        return query.list();
    }

/*role_info*/    
    public void addRole(RoleInfoData roleInfoData){
        sessionFactory.getCurrentSession().save(roleInfoData);
    }
    public RoleInfoData getRoleInfoByName(String name){
        String hql = "select p from RoleInfoData p where p.name = :name";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString("name", name);
        List<RoleInfoData> result = query.list();
        return result.size() == 0?null :result.get(0);
    }
    public List<RoleInfoData> getRoleInfos(){
        String hql = "select p from RoleInfoData p ";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        return query.list();
    }

/*user_info*/
    public void addUserInfo(UserInfoData userInfoData){
        if (null == userInfoData) {
            return ;
        }
        if (StringUtils.isBlank(userInfoData.getId())) {
            sessionFactory.getCurrentSession().save(userInfoData);
        }else{
            sessionFactory.getCurrentSession().update(userInfoData);
        }
        
    }
    
    
    public UserInfoData getUserInfoByName(String name){
        String hql = "select p from UserInfoData p where p.name = :name ";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString("name", name);
        List<UserInfoData> result = query.list();
        return result.size() == 0?null :result.get(0);
    }
    
/*login_info*/    
    public void addLoginInfo(LoginInfoData loginInfoData){
        if (null == loginInfoData) {
            return ;
        }
        if (StringUtils.isBlank(loginInfoData.getId())) {
            sessionFactory.getCurrentSession().save(loginInfoData);
        }else{
            sessionFactory.getCurrentSession().update(loginInfoData);
        }
    }
    
    public LoginInfoData getLoginInfoByName(String name){
        String hql = "select p from LoginInfoData p where p.userInfoData.name = :name ";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString("name", name);
        List<LoginInfoData> result = query.list();
        return result.size() == 0?null :result.get(0);
    }
    
    public UserInfoData getLoginInfo(String name,String password){
        String hql = "select p.userInfoData from LoginInfoData p where p.userInfoData.name = :name and p.password = :password ";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString("name", name);
        query.setString("password", password);
        List<UserInfoData> result = query.list();
        return result.size() == 0?null :result.get(0);
    }
    
    public UserInfoData getUserInfoById(String id){
        String hql = "select p from UserInfoData p where p.id = :id ";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString("id", id);
        List<UserInfoData> result = query.list();
        return result.size() == 0?null :result.get(0);
    }
    
    
    public void addUser(User user) {
        sessionFactory.getCurrentSession().save(user);
    }
    
    public List<User> getAllUser() {
        String hql = "select p from User p ";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        
       /* String sql = " select userName,age from t_user ";
        Query sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql);
        List<Object[]> results = sqlQuery.list();*/
        
        return query.list();
    }

    public boolean delUser(String id) {
        String hql = "delete User u where u.id=?";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setString(0, id);
        
        return (query.executeUpdate() > 0);
    }

    public List<User> getUsersByConditions(List<ForeverCriteria> conditions) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(clazz);
        ForeverCriteriaUtil<User> ucu = ForeverCriteriaUtil.getInstance(clazz);
        return ucu.search(criteria, conditions);
    }
    
    public List<List<Object[]>> exportExcel(){
        String sql = " select userName 姓名,age 年龄  from t_user ";
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
        query.setResultTransformer(new AliasToArrayListResultTransformer());
        List<List<Object[]>> results = query.list();
        return results;
    }
    
    /*操作MongoDB数据库*/
    public void operateMongoDB(){
    	//连接到MongoDB服务 如果是远程连接可以替换“localhost”为服务器所在IP地址  
        //ServerAddress()两个参数分别为 服务器地址 和 端口  
        ServerAddress serverAddress = new ServerAddress("localhost",27017);  
        List<ServerAddress> addrs = new ArrayList<ServerAddress>();  
        addrs.add(serverAddress);                
        //MongoCredential.createScramSha1Credential()三个参数分别为 用户名 数据库名称 密码  
        MongoCredential credential = MongoCredential.createScramSha1Credential("zhb", "zhbDB", "123456".toCharArray());  
        List<MongoCredential> credentials = new ArrayList<MongoCredential>();  
        credentials.add(credential);                
        //通过连接认证获取MongoDB连接  
        MongoClient mongoClient = new MongoClient(addrs,credentials);                
        //连接到数据库  
        MongoDatabase mongoDatabase = mongoClient.getDatabase("zhbDB");
        MongoCollection<Document> collection = mongoDatabase.getCollection("student");
        if (null != collection) {
        	FindIterable<Document> findIterable = collection.find();  //获取迭代器
        	MongoCursor<Document> mongoCursor = findIterable.iterator();  //获取游标
            while(mongoCursor.hasNext()){  //循环获取数据
            	System.out.println(mongoCursor.next());  
            }
		}
    }
    
    public void saveFileInfoData(FileInfoData fileInfoData){
        sessionFactory.getCurrentSession().save(fileInfoData);
    }
    
    public List<FileInfoData> getFileInfoDataByIdOrName(List<ForeverCriteria> conditions){
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(fileInfoDataClass);
        ForeverCriteriaUtil<FileInfoData> ucu = ForeverCriteriaUtil.getInstance(fileInfoDataClass);
        return ucu.search(criteria, conditions);
    }
    
    public List<NewsInfoData> getNewsInfos() {
        String hql = "select p from NewsInfoData p ";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        return query.list();
    }

}
