package main;

import obpro.turtle.Turtle;

public class Star extends Turtle {

	public static void main(String[] args) {
		Turtle.startTurtle(new Star(), args);
	}

	public void start() {
		rt(90);
		fd(100);
		rt(144);
		fd(100);
		rt(144);
		fd(100);
		rt(144);
		fd(100);
		rt(144);
		fd(100);
	}

}