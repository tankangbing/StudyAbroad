package com.app.studyabroad.entity;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class TreeNode {
	
	private Integer index;
	
	private String name;//�ڵ�����
	
	private String id;//�ڵ����(����ID)
	
	private boolean open;//�Ƿ��
	
	private String parentId ;//��ID����
	
	private boolean checked;//�Ƿ�ѡ��	
	
	private Integer level;//�㼶
	
	private Set<TreeNode> nodes = new LinkedHashSet<TreeNode>(0);//�ӽڵ�
	
	private Map<String, Object> exAttribute = new HashMap<String, Object>();//��չ���ԣ�key - value ��ʽ

	private String exam ;

	public String getExam() {
		return exam;
	}

	public void setExam(String exam) {
		this.exam = exam;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public Map<String, Object> getExAttribute() {
		return exAttribute;
	}

	public void setExAttribute(Map<String, Object> exAttribute) {
		this.exAttribute = exAttribute;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Set<TreeNode> getNodes() {
		return nodes;
	}

	public void setNodes(Set<TreeNode> nodes) {
		this.nodes = nodes;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}
	
	
	

}
