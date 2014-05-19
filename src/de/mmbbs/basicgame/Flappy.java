package de.mmbbs.basicgame;

import android.content.Context;
import android.util.Log;

public class Flappy extends AnimatedObject {

	Acceleration ac;
	int startJumpPos;
	
	public Flappy(int id, Context context, int segments) {
		super(id, context, segments);
		// TODO Auto-generated constructor stub
	}

	public void jump() {
		ac = new Acceleration(this.getY(), -500, 10);
		startJumpPos=(int) this.getY();
		//Log.d("Ttest"," Jump Pos="+this.getY());
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		super.tick();
		if (ac!=null) {
			ac.tick();
			
			this.setPosition((int) this.getX(), ac.getS());
			if (this.getY()>startJumpPos) {
				ac=null;
				this.setPosition((int) this.getX(), startJumpPos);
			}				
		}
	}

	
}
