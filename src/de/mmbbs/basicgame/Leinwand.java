package de.mmbbs.basicgame;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
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
	Object2D flappy;
	Object2D apfel;
	FragmentField fragmentField;
	Bitmap bg;
	private Rect srcRect,dstRect;
	
	
	public Leinwand(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		setFocusable(true);
        setFocusableInTouchMode(true);
        this.setOnTouchListener(this);
        bg = BitmapFactory.decodeResource(context.getResources(),R.drawable.bg);
        srcRect=new Rect(0,0,bg.getWidth(),bg.getHeight());
        flappy = new Object2D(R.drawable.flappy, context);
        apfel = new Object2D(R.drawable.apfel, context);
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
        
        bg = BitmapFactory.decodeResource(context.getResources(),R.drawable.bg);
        srcRect=new Rect(0,0,bg.getWidth(),bg.getHeight());
        flappy = new Object2D(R.drawable.flappy, context);
        apfel = new Object2D(R.drawable.apfel, context);
        fragmentField = new FragmentField(context);
        
        runner = new Runner(this);
        runner.start();
        
	}

	public void update() {
		ticks++;
		fragmentField.tick(this.getWidth());
		
		apfel.left(1);
		
		if (apfel.getX() <=0) {
			apfel.setPosition(this.getWidth(), (int) apfel.getY());
		}
		
		if (flappy.collide(apfel)) {
			apfel.setPosition(this.getWidth(), (int) apfel.getY());
		}
		
		
	}
	
	public void render(Canvas g,long start) {
		Paint p = new Paint();
		p.setColor(Color.CYAN);
		p.setAntiAlias(true);
		p.setTextSize((float) 15.0);
		g.drawBitmap(bg, srcRect,dstRect,p);
		p.setColor(Color.RED);
		fragmentField.paint(g, p);
		flappy.paint(g, p);
		apfel.paint(g, p);
		long stop= System.currentTimeMillis();
		long diff=(stop-start);
		g.drawText("("+mTouchX+"/"+mTouchY+") ticks="+ticks+" diff="+diff+" ms", 20, 20, p);
	}

	public boolean onTouch(View v, MotionEvent event) {
		mTouchX = (int)event.getX();
		mTouchY = (int)event.getY();
		if (mTouchX<flappy.getX()) flappy.left(2);
		if (mTouchX>flappy.getX()) flappy.right(2);
		if (mTouchY<flappy.getY()) flappy.up(2);
		if (mTouchY>flappy.getY()) flappy.down(2);
		return true;
	}

	public void reset() {
		// TODO Auto-generated method stub
		flappy.setPosition(this.getWidth()/2, this.getHeight()/2);
		apfel.setPosition(this.getWidth(), this.getHeight()/2);
		dstRect=new Rect(0,0,this.getWidth(),this.getHeight());
	}

	public void reset(int width, int height) {
		flappy.setPosition(width/2, height/2);
		apfel.setPosition(width, height/2);
		dstRect=new Rect(0,0,width,height);
	}
}
