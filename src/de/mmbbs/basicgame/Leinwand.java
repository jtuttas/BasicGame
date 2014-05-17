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
	FragmentField fragmentField;
	
	
	public Leinwand(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		setFocusable(true);
        setFocusableInTouchMode(true);
        this.setOnTouchListener(this);
       
        ship = BitmapFactory.decodeResource(this.getResources(),R.drawable.flappy);
        fragmentField = new FragmentField(context);
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
        
        
        ship = BitmapFactory.decodeResource(this.getResources(),R.drawable.flappy);
        fragmentField = new FragmentField(context);
        
        runner = new Runner(this);
        runner.start();
        
	}

	public void update() {
		ticks++;
		fragmentField.tick(this.getWidth());
		
		
	}
	
	public void render(Canvas g,long start) {
		Paint p = new Paint();
		p.setColor(Color.CYAN);
		p.setAntiAlias(true);
		p.setTextSize((float) 15.0);
		g.drawColor(Color.BLACK);
		p.setColor(Color.WHITE);
		fragmentField.paint(g, p);
		g.drawBitmap(ship, shipx, shipy, p);
		long stop= System.currentTimeMillis();
		long diff=(stop-start);
		g.drawText("("+mTouchX+"/"+mTouchY+") ticks="+ticks+" diff="+diff+" ms", 20, 20, p);
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
		if (mTouchX<shipx) shipx-=2;
		if (mTouchX>shipx) shipx+=2;
		if (mTouchY<shipy) shipy-=2;
		if (mTouchY>shipy) shipy+=2;
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
