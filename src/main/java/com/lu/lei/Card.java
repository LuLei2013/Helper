package com.lu.lei;

/**
 * @author LuLei
 * @date 2014-7-20
 */
public class Card {
	private int kind;
	private int crossNum;
	private Direction direction;

	public Card() {
		this.crossNum = 0;
		this.direction = Direction.All;
	}

	public int getKind() {
		return kind;
	}

	public void setKind(int kind) {
		this.kind = kind;
	}

	public int getCrossNum() {
		return crossNum;
	}

	public void setCrossNum(int crossNum) {
		this.crossNum = crossNum;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}
}
