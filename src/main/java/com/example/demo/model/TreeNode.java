package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {
    private String id;
    private String name;
    private List<TreeNode> children;

    public TreeNode(String s, String s1, List<TreeNode> of) {
        this.id = s;
        this.name = s1;
        this.children = of;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }
}
