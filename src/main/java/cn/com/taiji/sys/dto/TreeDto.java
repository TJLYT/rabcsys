package cn.com.taiji.sys.dto;

import java.util.List;

public class TreeDto {
	
	private String text;
	private String icon;
	private String color;
	private String backColor;
	private String selectedIcon;
	private String href;
	private List<TreeDto> nodes;
	
	public TreeDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String getBackColor() {
		return backColor;
	}

	public void setBackColor(String backColor) {
		this.backColor = backColor;
	}

	public String getSelectedIcon() {
		return selectedIcon;
	}

	public void setSelectedIcon(String selectedIcon) {
		this.selectedIcon = selectedIcon;
	}

	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public List<TreeDto> getNodes() {
		return nodes;
	}
	public void setNodes(List<TreeDto> nodes) {
		this.nodes = nodes;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	
	
	
}
