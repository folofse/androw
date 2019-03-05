package se.folof.androw;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.views.view.ReactViewGroup;


public class RNAndrowLayout extends ReactViewGroup {

    private static int SAFE_AREA = 5;

    private int mShadowColor;
    private ReadableMap mShadowOffset;
    private float mShadowOpacity = 1.0f;
    private int mShadowRadius = 0;

    private Paint mPaint;
    private Paint mOrgPaint;
    private Rect mOriginalRect;

    public RNAndrowLayout(Context context) {
        super(context);

        this.mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        setLayerType(LAYER_TYPE_SOFTWARE, this.mPaint);
        this.mOrgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        setLayerType(LAYER_TYPE_SOFTWARE, this.mOrgPaint);
    }
    public void setShadowOffset(ReadableMap offsetMap) {
        this.mShadowOffset = offsetMap;
        this.invalidate();
    }
    public void setShadowColor(int color) {
        this.mShadowColor = color;
        this.invalidate();
    }
    public void setShadowOpacity(float opacity) {
        this.mShadowOpacity = opacity;
        this.invalidate();
    }
    public void setShadowRadius(float radius) {
        this.mShadowRadius = (int)radius;
        this.invalidate();
    }

    @Override
    public void invalidate(){
        if(this.mOriginalRect != null){
            this.updateClipping(this.mOriginalRect.left, this.mOriginalRect.top, this.mOriginalRect.right, this.mOriginalRect.bottom);
        }

        super.invalidate();
    }
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        this.mOriginalRect = new Rect(left, top, right, bottom);
        this.updateClipping(left, top, right, bottom);
        super.onLayout(changed, left, top, right, bottom);
        try {
            ((ReactViewGroup) getParent()).setClipChildren(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {

        if(this.getMeasuredWidth() > 0 && this.getMeasuredHeight() > 0){

            final Bitmap shadowBitmap = Bitmap.createBitmap(this.getMeasuredWidth(), this.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
            final Canvas sCanvas = new Canvas(shadowBitmap);
            super.dispatchDraw(sCanvas);

            final Bitmap originalBitmap = shadowBitmap.copy(Bitmap.Config.ARGB_8888, true);


            int shadowX = 0;
            if(this.mShadowOffset != null && this.mShadowOffset.hasKey("width")){
                shadowX = this.mShadowOffset.getInt("width");
            }

            int shadowY = 0;
            if(this.mShadowOffset != null && this.mShadowOffset.hasKey("height")){
                shadowY = this.mShadowOffset.getInt("height");
            }


            int orgX = this.mShadowRadius;
            int orgY = this.mShadowRadius;
            if(shadowX < 0){
                orgX = (shadowX*-1)+this.mShadowRadius;
                shadowX = this.mShadowRadius;
            }else{
                shadowX += this.mShadowRadius;
            }
            if(shadowY < 0){
                orgY = (shadowY*-1)+this.mShadowRadius;
                shadowY = this.mShadowRadius;
            }else{
                shadowY += this.mShadowRadius;
            }


            this.mPaint.setColor(this.mShadowColor);
            this.mPaint.setAlpha(Math.round(255*this.mShadowOpacity));
            if(this.mShadowRadius > 0){
                this.mPaint.setMaskFilter(new BlurMaskFilter(this.mShadowRadius, BlurMaskFilter.Blur.NORMAL));
            }

            //canvas.drawColor(0xFFFFFF00); //DEBUG
            canvas.drawBitmap(shadowBitmap.extractAlpha(), shadowX+SAFE_AREA, shadowY+SAFE_AREA, this.mPaint);
            canvas.drawBitmap(originalBitmap, orgX+SAFE_AREA, orgY+SAFE_AREA, this.mOrgPaint);
        }else{
            super.dispatchDraw(canvas);
        }
    }
    private void updateClipping(int left, int top, int right, int bottom ){
        int shadowX = 0;
        if(this.mShadowOffset  != null && this.mShadowOffset.hasKey("width")){
            shadowX = this.mShadowOffset.getInt("width");
        }

        int shadowY = 0;
        if(this.mShadowOffset  != null && this.mShadowOffset.hasKey("height")){
            shadowY = this.mShadowOffset.getInt("height");
        }

        int radius = this.mShadowRadius;
        if(shadowX < 0){
            left -= ((shadowX*-1)+radius);
            right += radius;
        }else{
            left -= radius;
            right += shadowX+radius;
        }
        if(shadowY < 0){
            top -= ((shadowY*-1)+radius);
            bottom += radius;
        }else{
            top -= radius;
            bottom += shadowY+radius;
        }

        this.setLeft(left-SAFE_AREA);
        this.setRight(right+SAFE_AREA);
        this.setTop(top-SAFE_AREA);
        this.setBottom(bottom+SAFE_AREA);

        this.updateClippingRect();
    }
}
