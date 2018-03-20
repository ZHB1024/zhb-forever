package com.forever.zhb.utils.tree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

public class TreeNode implements Comparable<TreeNode>,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3053218440772155078L;

	private String parentId;
    private String selfId;
    private String description;
    private int sort;
    protected String nodeName;
    protected TreeNode parentNode;
    protected List<TreeNode> childList;
  
    public TreeNode() {
        initChildList();
    }  
  
    public TreeNode copyTreeNode() {
        TreeNode newNode = new TreeNode();
        try {
            BeanUtils.copyProperties(newNode, this);
            List<TreeNode> list = new ArrayList<TreeNode>();
            list.addAll(this.getChildList());
            newNode.setChildList(list);
        }catch (Exception e) {
            System.out.println("复制Node出错！");
        }
        return newNode;
    }  
  
    public boolean isLeaf() {
        if (childList == null) {
            return true;
        } else {
            if (childList.isEmpty()) {
                return true;
            } else {
                return false;
            }  
        }  
    }  
  
    /* 插入一个child节点到当前节点中 */  
    public void addChildNode(TreeNode treeNode) {
        initChildList();
        childList.add(treeNode);
    }  
  
    public void initChildList() {
        if (childList == null)  
            childList = new ArrayList<TreeNode>();
    }  
  
    public boolean isValidTree() {
        return true;
    }  
  
    /* 返回当前节点的父辈节点集合 */  
    public List<TreeNode> getElders() {
        List<TreeNode> elderList = new ArrayList<TreeNode>();
        TreeNode parentNode = this.getParentNode();
        if (parentNode == null) {
            return elderList;
        } else {
            elderList.add(parentNode);
            elderList.addAll(parentNode.getElders());
            return elderList;
        }  
    }  
  
    public List<TreeNode> getSortedElders() {
        List<TreeNode> list = getElders();
        if(null != list && list.size() != 0){
            Collections.reverse(list);
        }
        return list;
    }
    
    public TreeNode getOrig(){
        List<TreeNode> list = getSortedElders();
        return null != list && list.size() > 1 ? list.get(1) : this ;
    }
    
    /* 返回当前节点的晚辈集合 */  
    public List<TreeNode> getJuniors() {
        List<TreeNode> juniorList = new ArrayList<TreeNode>();
        List<TreeNode> childList = this.getChildList();
        if (childList == null) {
            return juniorList;
        } else {
            int childNumber = childList.size();
            for (int i = 0; i < childNumber; i++) {
                TreeNode junior = childList.get(i);
                juniorList.add(junior);
                juniorList.addAll(junior.getJuniors());
            }  
            return juniorList;
        }  
    }
    
    /* 返回当前节点的叶子节点集合 */  
    public List<TreeNode> getLeafNodes() {
        List<TreeNode> leafList = new ArrayList<TreeNode>();
        List<TreeNode> childList = this.getChildList();
        if (childList == null) {
            return leafList;
        } else {
            int childNumber = childList.size();
            for (int i = 0; i < childNumber; i++) {
                TreeNode junior = childList.get(i);
                if(junior.isLeaf()){
                    leafList.add(junior);
                }
                leafList.addAll(junior.getLeafNodes());
            }  
            return leafList;
        }  
    }  
  
    /* 返回当前节点的孩子集合 */  
    public List<TreeNode> getChildList() {
        return childList;
    }  
  
    /* 删除节点和它下面的晚辈 */  
    public void deleteNode() {
        TreeNode parentNode = this.getParentNode();
        String id = this.getSelfId();
  
        if (parentNode != null) {
            parentNode.deleteChildNode(id);
        }  
    }  
  
    /* 删除当前节点的某个子节点 */  
    public void deleteChildNode(String childId) {
        List<TreeNode> childList = this.getChildList();
        int childNumber = childList.size();
        for (int i = 0; i < childNumber; i++) {
            TreeNode child = childList.get(i);
            if (childId.equals(child.getSelfId())) {
                childList.remove(i);
                return;
            }  
        }  
    }  
  
    /* 动态的插入一个新的节点到当前树中 */  
    public boolean insertJuniorNode(TreeNode treeNode) {
        String juniorParentId = treeNode.getParentId();
        if (this.selfId.equals(juniorParentId)) {
            addChildNode(treeNode);
            return true;
        } else {
            List<TreeNode> childList = this.getChildList();
            int childNumber = childList.size();
            boolean insertFlag;
  
            for (int i = 0; i < childNumber; i++) {
                TreeNode childNode = childList.get(i);
                insertFlag = childNode.insertJuniorNode(treeNode);
                if (insertFlag == true)  
                    return true;
            }  
            return false;
        }  
    }  
  
    /* 找到一颗树中某个节点 */  
    public TreeNode findTreeNodeById(String id) {
        if (this.selfId.equals(id))  
            return this;
        if (childList.isEmpty() || childList == null) {
            return null;
        } else {
            int childNumber = childList.size();
            for (int i = 0; i < childNumber; i++) {
                TreeNode child = childList.get(i);
                TreeNode resultNode = child.findTreeNodeById(id);
                if (resultNode != null) {
                    return resultNode;
                }  
            }  
            return null;
        }  
    }  
  
    /* 遍历一棵树，层次遍历 */  
    public void traverse() {
        if (null == selfId || "".equals(selfId))  
            return;
        print(this.selfId + this.nodeName);
        if (childList == null || childList.isEmpty())  
            return;
        int childNumber = childList.size();
        for (int i = 0; i < childNumber; i++) {
            TreeNode child = childList.get(i);
            child.traverse();
        }  
    }  
  
    public void print(String content) {
        System.out.println(content);
    }  
  
    public void setChildList(List<TreeNode> childList) {
        this.childList = childList;
    }  
  
    public String getParentId() {
        return parentId;
    }  
  
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }  
  
    public String getSelfId() {
        return selfId;
    }  
  
    public void setSelfId(String selfId) {
        this.selfId = selfId;
    }  
  
    public TreeNode getParentNode() {
        return parentNode;
    }  
  
    public void setParentNode(TreeNode parentNode) {
        this.parentNode = parentNode;
    }  
  
    public String getNodeName() {
        return nodeName;
    }  
  
    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int compareTo(TreeNode o) {
        return this.sort - o.sort;
    }
    
    public String toString() {
        return String.format("父节点ID：%s，节点ID：%s，节点名称：%s，节点SORT:%s", this.parentId,this.selfId,this.nodeName,this.sort);
    }

}
