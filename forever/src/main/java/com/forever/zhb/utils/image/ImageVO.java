package com.forever.zhb.utils.image;

public class ImageVO {
	
	private String suffix;
	private int x;
	private int y;
	private int width;
	private int height;
	private int radian;
	private boolean ok;
	private String msg;

	public ImageVO() {
	}

	public ImageVO(String suffix, int x, int y, int width, int height, int radian) {
		this.suffix = suffix;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.radian = radian;
	}

	public ImageVO(int x, int y, int width, int height, int radian) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.radian = radian;
	}

	public int getX() {
		return this.x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return this.y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return this.width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return this.height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getRadian() {
		return this.radian;
	}

	public void setRadian(int radian) {
		this.radian = radian;
	}

	public String getSuffix() {
		return this.suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public boolean isOk() {
		return this.ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}

	public String getMsg() {
		return this.msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
