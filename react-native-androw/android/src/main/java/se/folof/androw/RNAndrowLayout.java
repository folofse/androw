package se.folof.androw;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewParent;

import com.facebook.react.bridge.Dynamic;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.MeasureSpecAssertions;
import com.facebook.react.views.view.ReactViewGroup;

import androidx.annotation.NonNull;

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
    private final Paint blurPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint shadowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private Bitmap shadowBitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
    private Bitmap originalBitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
    private Canvas originalCanvas = new Canvas(originalBitmap);
    private boolean originalBitmapHasContent;

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
            mOpacity = opacity;
        }
        super.invalidate();
    }

    public void setShadowRadius(Dynamic nullableRadius) {
        hasShadowRadius = nullableRadius != null && !nullableRadius.isNull();
        float radius = hasShadowRadius ? (float) nullableRadius.asDouble() : 0f;
        hasShadowRadius &= radius > 0f;
        if (hasShadowRadius && mRadius != radius) {
            blurPaint.setMaskFilter(new BlurMaskFilter(radius, BlurMaskFilter.Blur.NORMAL));
            shadowDirty = true;
            mRadius = radius;
        }
        super.invalidate();
    }

    @Override
    @SuppressWarnings("deprecation")
    public ViewParent invalidateChildInParent(final int[] location, final Rect dirty) {
        contentDirty = true;
        shadowDirty = true;
        return super.invalidateChildInParent(location, dirty);
    }

    @Override
    public void onDescendantInvalidated(@NonNull View child, @NonNull View target) {
        super.onDescendantInvalidated(child, target);
        contentDirty = true;
        shadowDirty = true;
    }

    @Override
    public void invalidate() {
        contentDirty = true;
        shadowDirty = true;
        super.invalidate();
    }

    @Override
    @SuppressLint("DrawAllocation")
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        MeasureSpecAssertions.assertExplicitMeasureSpec(widthMeasureSpec, heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(width, height);
        hasPositiveArea = width > 0 && height > 0;
        if (hasPositiveArea) {
            if (originalBitmap.getWidth() == width && originalBitmap.getHeight() == height) {
                return;
            }
            originalBitmap.recycle();
            originalBitmapHasContent = false;
            originalBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            originalCanvas.setBitmap(originalBitmap);
        }
        invalidate();
    }

    @Override
    public void dispatchDraw(Canvas canvas) {
        if (hasPositiveArea) {
            if (contentDirty) {
                if (originalBitmapHasContent) {
                    originalBitmap.eraseColor(Color.TRANSPARENT);
                }
                super.dispatchDraw(originalCanvas);
                originalBitmapHasContent = true;
                contentDirty = false;
            }

            if (hasShadowRadius && hasShadowColor && hasShadowOpacity) {
                if (shadowDirty) {
                    shadowBitmap.recycle();
                    shadowBitmap = originalBitmap.extractAlpha(blurPaint, offsetXY);
                    shadowDirty = false;
                }
                canvas.drawBitmap(shadowBitmap, offsetXY[0] + shadowX, offsetXY[1] + shadowY, shadowPaint);
            }

            canvas.drawBitmap(originalBitmap, 0f, 0f, null);
        } else {
            super.dispatchDraw(canvas);
        }
    }
}
