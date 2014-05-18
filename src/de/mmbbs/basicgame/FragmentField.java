package de.mmbbs.basicgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

public class FragmentField {

		Bitmap[] bitmaps= new Bitmap[3];
		float xoffset=0,yoffset=0;
		int speed=1;
		
		int[][]map={
				{2,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,2,0},
				{2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1},
				{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1}};
		
		int xindex=0,yindex=0;
		
		public FragmentField (Context context){
			bitmaps[0] = BitmapFactory.decodeResource(context.getResources(),R.drawable.eins);
			bitmaps[1] = BitmapFactory.decodeResource(context.getResources(),R.drawable.zwei);
			bitmaps[2] = BitmapFactory.decodeResource(context.getResources(),R.drawable.drei);
			
		}
		
		public void paint(Canvas c,Paint p){
			float x = 0,y=0;
			int tempx=xindex,tempy=0;
			
			for(int j=0;j<map.length;j++) {
				tempx=xindex;
				for(int i=0;i<=c.getWidth()/bitmaps[0].getWidth();i++) {
					try {
						c.drawBitmap(bitmaps[map[j][tempx]],x-xoffset,y,p);
					}
					catch (java.lang.ArrayIndexOutOfBoundsException aob) {
						i=0;
						tempx=0;
						c.drawBitmap(bitmaps[map[j][tempx]],x-xoffset,y,p);
					}
					tempx++;
					x+=bitmaps[0].getWidth();
					if(x>c.getWidth()+2*bitmaps[0].getWidth()) {
						break;
					}
				}
				y+=bitmaps[0].getHeight();
				x=0;
			}

		}

		
		public int getElement(float x,float y) {
			try {
				int xi=(int) (xindex+(x+xoffset)/bitmaps[0].getWidth());
				int yi=(int) (y/bitmaps[0].getHeight());
				if (xi>map[0].length) {
					xi=(int) (0+(x+xoffset)/bitmaps[0].getWidth());
				}
				return map[yi][xi];
			}
			catch (java.lang.ArrayIndexOutOfBoundsException aob){
				return -1;
			}
		}
		
		public void tick(int width) {
			// TODO Auto-generated method stub
			
			xoffset+=speed;
			if (xoffset>=bitmaps[0].getWidth()) {
				xindex++;
				xoffset=xoffset%speed;
				
				if (xindex>=map[0].length) {
					xindex=0;
				}
				
			}
			
		}
		
}
