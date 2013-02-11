package de.mmbbs.basicgame;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;

public class Leinwand extends SurfaceView implements OnTouchListener  {

	public float mTouchX=-1;
	public float mTouchY=-1;
	Runner runner;
	int ticks;
	Bitmap ship;
	int shipx = 80;
	int shipy = 80;
	
	public Leinwand(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		setFocusable(true);
        setFocusableInTouchMode(true);
        this.setOnTouchListener(this);
       
        ship = BitmapFactory.decodeResource(this.getResources(),R.drawable.spaceship);
        reset();
        runner = new Runner(this);
        runner.start();
	}
	
	public Leinwand(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		setFocusable(true);
        setFocusableInTouchMode(true);
        this.setOnTouchListener(this);
        
        
        ship = BitmapFactory.decodeResource(this.getResources(),R.drawable.spaceship);
        
        runner = new Runner(this);
        runner.start();
        
	}

	public void update() {
		ticks++;
		
		
	}
	
	public void render(Canvas g) {
		Paint p = new Paint();
		p.setColor(Color.CYAN);
		p.setAntiAlias(true);
		p.setTextSize((float) 20.0);
		g.drawColor(Color.BLACK);
		g.drawText("Touch: ("+mTouchX+"/"+mTouchY+") ticks="+ticks, 20, 20, p);
		p.setColor(Color.WHITE);
		g.drawBitmap(ship, shipx, shipy, p);
		
	}
	/*
	@Override
	protected void onDraw(Canvas g) {
		// TODO Auto-generated method stub
		super.onDraw(g);
		Paint p = new Paint();
		p.setColor(Color.CYAN);
		g.drawText("Touch: ("+mTouchX+"/"+mTouchY+") ticks="+ticks, 20, 20, p);
		//g.drawLine(0, 0, getWidth(), getHeight(), p);

	}
	*/
	public boolean onTouch(View v, MotionEvent event) {
		mTouchX = (int)event.getX();
		mTouchY = (int)event.getY();
		if (mTouchX<shipx) shipx--;
		if (mTouchX>shipx) shipx++;
		if (mTouchY<shipy) shipy--;
		if (mTouchY>shipy) shipy++;
		return true;
	}

	public void reset() {
		// TODO Auto-generated method stub
		shipx=this.getWidth()/2;
		shipy=this.getHeight()/2;
		Log.d(Main.TAG,"Reset to ("+shipx+"/"+shipy+")");
		System.out.println("Reset to ("+shipx+"/"+shipy+")");
	}

	public void reset(int width, int height) {
		// TODO Auto-generated method stub
		shipx=width/2;
		shipy=height/2;
	}
}
