package com.forever.zhb.controller.annotation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.forever.zhb.utils.tree.TagTreeNodeHelper;
import com.forever.zhb.utils.tree.TreeNode;
import com.forever.zhb.vo.TagVO;

@Controller
@RequestMapping("/treeController")
public class TreeController {
	
	public static TreeNode getTreeRootNode() {
        // MemCachedUtil.removeByKey(Constants.KEY_ROOT_TAG_TREE_NODE);
        TreeNode root = getSelfDefinedRootNode();
        generateTreeByRoot(root);
        return root;
    }

    private static TreeNode getSelfDefinedRootNode() {
        TreeNode root = new TreeNode();
        root.setParentId("");
        root.setSelfId("id---------------");
        root.setNodeName("hello world系统");
        return root;
    }

    // 根据根节点递归生成树
    private static void generateTreeByRoot(TreeNode parentNode) {
        List<TagVO> childList = new ArrayList<TagVO>();
        List<TreeNode> temp = TagTreeNodeHelper.TagListConvert2TreeNodeList(childList, parentNode);
        if (temp.size() != 0) {
            parentNode.setChildList(temp);
            for (TreeNode node : temp) {
                generateTreeByRoot(node);
            }
        }
    }

}
