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
	Flappy flappy;
	Object2D apfel;
	Object2D ball;
	FragmentField fragmentField;
	Bitmap bg;
	private Rect srcRect,dstRect;
	Acceleration ac;
	int fps=0,fpsCounter;
	long start=0,stop=0;
	long fpsTimeStamp=0;
	
	
	
	public Leinwand(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		
		init(context);
	}
	
	public Leinwand(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init(context);
	}
	

	private void init(Context context) {
		setFocusable(true);
        setFocusableInTouchMode(true);
        this.setOnTouchListener(this);
        bg = BitmapFactory.decodeResource(context.getResources(),R.drawable.bg);
        srcRect=new Rect(0,0,bg.getWidth(),bg.getHeight());
        flappy = new Flappy(R.drawable.flappyani, context,3);
        apfel = new Object2D(R.drawable.apfel, context);
        ball = new Object2D(R.drawable.ball2, context);
        
        fragmentField = new FragmentField(context);
        ac =new Acceleration(0,0, 12);

        reset();
        runner = new Runner(this);
        runner.start();
        fpsTimeStamp=System.currentTimeMillis();

	}
	
	public void exit() {
		runner.stop();
	}
	
	public void update() {
		start=System.currentTimeMillis();
		fpsCounter++;
		if (System.currentTimeMillis()>fpsTimeStamp+1000) {
			fpsTimeStamp=System.currentTimeMillis();
			fps=fpsCounter;
			fpsCounter=0;
		}
		ticks++;
		flappy.tick();
		fragmentField.tick(this.getWidth());
		ac.tick();
		ball.setPosition((int)ball.getX(), (int) (ac.getS()));
		if (this.getHeight()!=0 && ball.getY()>(this.getHeight()-3*ball.getHeight())) {
			
			ac.bounce();
		}

		//Log.d("Ttest"," Ball Pos="+ball.getY());
		apfel.left(1);
		
		if (apfel.getX() <=0) {
			apfel.setPosition(this.getWidth(), (int) apfel.getY());
		}
		
		if (flappy.collide(apfel)) {
			apfel.setPosition(this.getWidth(), (int) apfel.getY());
		}
		fragmentField.getElement(0, 0);
		if (
				fragmentField.getElement(flappy.getX()+flappy.getWidth()/2, flappy.getY())==1 || 
				fragmentField.getElement(flappy.getX()+flappy.getWidth(), flappy.getY()+flappy.getHeight()/2)==1 ||
				fragmentField.getElement(flappy.getX()+flappy.getWidth()/2, flappy.getY()+flappy.getHeight())==1 ||
				fragmentField.getElement(flappy.getX(), flappy.getY()+flappy.getHeight()/2)==1
				)	{
			
			flappy.setPosition(this.getWidth()/2, this.getHeight()/2);
			Log.d("Ttest"," Kollision von Flappy mit Stein 2");
		}
		
		
		
	}
	
	public void render(Canvas g) {
		Paint p = new Paint();
		p.setColor(Color.CYAN);
		p.setAntiAlias(true);
		p.setTextSize((float) 15.0);
		g.drawBitmap(bg, srcRect,dstRect,p);
		p.setColor(Color.RED);
		fragmentField.paint(g, p);
		flappy.paint(g, p);
		apfel.paint(g, p);
		ball.paint(g, p);
		stop= System.currentTimeMillis();
		long diff=(stop-start);
		g.drawText("("+mTouchX+"/"+mTouchY+") FPS="+fps+" gameloop="+diff+" ms", 20, 20, p);
	}

	public boolean onTouch(View v, MotionEvent event) {
		mTouchX = (int)event.getX();
		mTouchY = (int)event.getY();
		if (flappy.dotInObject((int)mTouchX,(int)mTouchY)) {
			Log.d("Ttest","HitFlappy");
			flappy.jump();
		}
		if (mTouchX<flappy.getX()) flappy.left(2);
		if (mTouchX>flappy.getX()) flappy.right(2);
		if (mTouchY<flappy.getY()) flappy.up(2);
		if (mTouchY>flappy.getY()) flappy.down(2);
		return true;
	}

	public void reset() {
		this.reset(this.getWidth(),this.getHeight());
	}

	public void reset(int width, int height) {
		flappy.setPosition(width/2, height/2);
		apfel.setPosition(width, height/2);
		dstRect=new Rect(0,0,width,height);
		ball.setPosition(width/4, 0);
		ac.setS0(height/2);
	}
}
