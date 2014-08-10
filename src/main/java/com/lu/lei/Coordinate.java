package com.lu.lei;

/**
 * @author LuLei
 * @date 2014-7-20
 */

public class Coordinate {
	private int r;
	private int c;

	public int getR() {
		return r;
	}

	public void setR(int r) {
		this.r = r;
	}

	public int getC() {
		return c;
	}

	public void setC(int c) {
		this.c = c;
	}

	public Coordinate(int r, int c) {
		setR(r);
		setC(c);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + c;
		result = prime * result + r;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coordinate other = (Coordinate) obj;
		if (c != other.c)
			return false;
		if (r != other.r)
			return false;
		return true;
	}

}
