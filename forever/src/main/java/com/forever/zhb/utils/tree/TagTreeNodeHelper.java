package com.forever.zhb.utils.tree;

import java.util.ArrayList;
import java.util.List;

import com.forever.zhb.vo.TagVO;

public class TagTreeNodeHelper {
	
	/**
     * @param tagVO 标签
     * @param parentNode 父node，如果是根则传null
     * @return
     */
    public static TreeNode TagConvert2TreeNode(TagVO tagVO, TreeNode parentNode){
        TreeNode node = new TreeNode();
        node.setSelfId(tagVO.getId());
        node.setNodeName(tagVO.getName());
        node.setDescription(tagVO.getDescription());
        node.setSort(tagVO.getSeqNum());
        node.setParentId(tagVO.getParentTagId());
        node.setParentNode(parentNode);
        return node;
    }
    
    public static List<TreeNode> TagListConvert2TreeNodeList(List<TagVO> tagVOList, TreeNode parentNode){
        List<TreeNode> nodeList = new ArrayList<TreeNode>();
        for(TagVO tag : tagVOList){
            nodeList.add(TagTreeNodeHelper.TagConvert2TreeNode(tag, parentNode));
        }
        return nodeList;
    }
    
    public static TagVO TreeNodeConvert2Tag(TreeNode node){
        TagVO tag = new TagVO();
        tag.setId(node.getSelfId());
        tag.setName(node.getNodeName());
        tag.setDescription(node.getDescription());
        tag.setSeqNum(node.getSort());
        tag.setParentTagId(node.getParentId());
        return tag;
    }

}
