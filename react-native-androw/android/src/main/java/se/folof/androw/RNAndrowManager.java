package se.folof.androw;


import android.content.Context;
import android.view.View;

import com.facebook.react.bridge.Dynamic;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;


public class RNAndrowManager extends ViewGroupManager<RNAndrowLayout> {

    public static final String REACT_CLASS = "RNAndrow";
    public RNAndrowImageListener imageListener;


    @Override
    protected RNAndrowLayout createViewInstance(ThemedReactContext reactContext) {
        final RNAndrowLayout androwLayout = new RNAndrowLayout(reactContext);
        if(this.imageListener == null){//One listener for all androw views
            this.imageListener = new RNAndrowImageListener(reactContext);
        }

        return androwLayout;
    }

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @ReactProp(name = "shadowOffset")
    public void setShadowOffset(RNAndrowLayout view, ReadableMap offsetMap) {
        view.setShadowOffset(offsetMap);
    }

    @ReactProp(name = "shadowColor")
    public void setShadowColor(RNAndrowLayout view, Integer color) {
        view.setShadowColor(color);
    }

    @ReactProp(name = "shadowOpacity")
    public void setShadowOpacity(RNAndrowLayout view, Dynamic opacity) {
        view.setShadowOpacity(opacity);
    }

    @ReactProp(name = "shadowRadius")
    public void setShadowRadius(RNAndrowLayout view, Dynamic radius) {
        view.setShadowRadius(radius);
    }

    @Override
    public void addView(RNAndrowLayout parent, View child, int index) {
        this.imageListener.onAddView(parent, child);
        super.addView(parent, child, index);
    }

    @Override
    public void onDropViewInstance(RNAndrowLayout parent) {
        this.imageListener.tearDown();

    }
}
