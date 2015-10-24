package com.hacklm.assist;

import java.util.List;

public class Count {
	private boolean flag;
	private List<Marker> data;

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public List<Marker> getData() {
		return data;
	}

	public void setData(List<Marker> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "flag=" + flag + ",data=" + data.toString();
	}
}
