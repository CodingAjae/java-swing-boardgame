package net.java.boardgame.model.shape;

import net.java.boardgame.model.BaseShape;

public class HexaShape extends BaseShape {
	private static final long serialVersionUID = -3223965387986938888L;
	private int index = 0;
	private int array[] = new int[]{0,0,0};

	public HexaShape() {
		array[0] = nextval();
		array[1] = nextval();
		array[2] = nextval();
		
		set(array[index]);
		set(array[index+1]);
		set(array[index+2]);
	}
	
	public HexaShape(int a, int b, int c) {
		set(a);
		set(b);
		set(c);
	}
	
	@Override
	public void rotate() {
		index = (index+1)%3;
		update(0, 0, array[index]);
		update(1, 0, array[(index+1)%3]);
		update(2, 0, array[(index+2)%3]);
	}
	
	private int nextval() {
	    double dValue = Math.random();
		return (int)(dValue * 6)+2;
	}
}
