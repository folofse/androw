package se.folof.androw;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.graphics.Paint;

import com.facebook.react.bridge.Dynamic;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.MeasureSpecAssertions;
import com.facebook.react.views.view.ReactViewGroup;

public class RNAndrowLayout extends ReactViewGroup {

    private int mColor;
    private float mRadius;
    private float mOpacity;
    private float shadowY;
    private float shadowX;

    private boolean shadowDirty;
    private boolean contentDirty;
    private boolean hasPositiveArea;

    private boolean hasShadowColor;
    private boolean hasShadowRadius;
    private boolean hasShadowOpacity;

    private final int[] offsetXY = {0, 0};
    private final Paint shadowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private static final Bitmap.Config ARGB_8888 = Bitmap.Config.ARGB_8888;
    private static final ColorSpace SRGB = ColorSpace.get(ColorSpace.Named.SRGB);

    private Bitmap shadowBitmap = Bitmap.createBitmap(null, 1, 1, ARGB_8888, true, SRGB);
    private Bitmap originalBitmap = Bitmap.createBitmap(null, 1, 1, ARGB_8888, true, SRGB);
    private Canvas originalCanvas = new Canvas(originalBitmap);

    public RNAndrowLayout(Context context) {
        super(context);
    }

    public void setShadowOffset(ReadableMap offsetMap) {
        boolean hasMap = offsetMap != null;

        if (hasMap && offsetMap.hasKey("width")) {
            shadowX = (float) offsetMap.getDouble("width");
        } else {
            shadowX = 0f;
        }

        if (hasMap && offsetMap.hasKey("height")) {
            shadowY = (float) offsetMap.getDouble("height");
        } else {
            shadowY = 0f;
        }

        super.invalidate();
    }

    public void setShadowColor(Integer color) {
        hasShadowColor = color != null;
        if (hasShadowColor && mColor != color) {
            shadowPaint.setColor(color);
            shadowDirty = true;
            mColor = color;
        }
        super.invalidate();
    }

    public void setShadowOpacity(Dynamic nullableOpacity) {
        hasShadowOpacity = nullableOpacity != null && !nullableOpacity.isNull();
        float opacity = hasShadowOpacity ? (float) nullableOpacity.asDouble() : 0f;
        hasShadowOpacity &= opacity > 0f;
        if (hasShadowOpacity && mOpacity != opacity) {
            shadowPaint.setAlpha(Math.round(255 * opacity));
            shadowDirty = true;
            mOpacity = opacity;
        }
        super.invalidate();
    }

    public void setShadowRadius(Dynamic nullableRadius) {
        hasShadowRadius = nullableRadius != null && !nullableRadius.isNull();
        float radius = hasShadowRadius ? (float) nullableRadius.asDouble() : 0f;
        hasShadowRadius &= radius > 0f;
        if (hasShadowRadius && mRadius != radius) {
            shadowPaint.setMaskFilter(new BlurMaskFilter(radius, BlurMaskFilter.Blur.NORMAL));
            shadowDirty = true;
            mRadius = radius;
        }
        super.invalidate();
    }

    public void invalidate() {
        super.invalidate();
        shadowDirty = true;
        contentDirty = true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        MeasureSpecAssertions.assertExplicitMeasureSpec(widthMeasureSpec, heightMeasureSpec);
        int measuredHeight = MeasureSpec.getSize(heightMeasureSpec);
        int measuredWidth = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(measuredWidth, measuredHeight);
        int area = measuredWidth * measuredHeight;
        hasPositiveArea = area > 0;
        /*
        if (originalBitmap != null && area * 32 < originalBitmap.getAllocationByteCount()) {
            originalBitmap.reconfigure(measuredWidth, measuredHeight, ARGB_8888);
        }
        */
        if (hasPositiveArea) {
            if (originalBitmap.getWidth() == measuredWidth && originalBitmap.getHeight() == measuredHeight) {
                return;
            }
            originalBitmap.recycle();
            originalBitmap = Bitmap.createBitmap(null, measuredWidth, measuredHeight, ARGB_8888, true, SRGB);
            // originalBitmap = Bitmap.createScaledBitmap(originalBitmap, measuredWidth, measuredHeight, false);
            originalCanvas.setBitmap(originalBitmap);
        }
        invalidate();
    }

    @Override
    public void dispatchDraw(Canvas canvas) {
        if (hasPositiveArea) {
            if (contentDirty) {
                originalBitmap.eraseColor(Color.TRANSPARENT);
                super.dispatchDraw(originalCanvas);
                contentDirty = false;
            }

            if (hasShadowRadius && hasShadowColor && hasShadowOpacity) {
                if (shadowDirty) {
                    shadowBitmap.recycle();
                    shadowBitmap = originalBitmap.extractAlpha(shadowPaint, offsetXY);
                    shadowDirty = false;
                }
                canvas.drawBitmap(shadowBitmap, offsetXY[0] + shadowX, offsetXY[1] + shadowY, null);
            }

            canvas.drawBitmap(originalBitmap, 0f, 0f, null);
        } else {
            super.dispatchDraw(canvas);
        }
    }

}
