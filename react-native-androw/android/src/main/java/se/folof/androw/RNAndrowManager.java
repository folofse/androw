package se.folof.androw;


import android.view.View;

import com.facebook.react.bridge.Dynamic;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;


public class RNAndrowManager extends ViewGroupManager<RNAndrowLayout> {

    public static final String REACT_CLASS = "RNAndrow";

    @Override
    protected RNAndrowLayout createViewInstance(ThemedReactContext reactContext) {
        final RNAndrowLayout androwLayout = new RNAndrowLayout(reactContext);
        androwLayout.imageListener = new RNAndrowImageListener(reactContext, androwLayout); //One listener per Androw view.
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
        parent.imageListener.setImageOnLoadListerners(child);
        super.addView(parent, child, index);
    }


}
