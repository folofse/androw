package se.folof.androw;

import android.util.Log;

import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;


public class RNAndrowManager extends ViewGroupManager<RNAndrowLayout> {

    public static final String REACT_CLASS = "RNAndrow";

    @Override
     protected RNAndrowLayout createViewInstance(ThemedReactContext reactContext) {
        return new RNAndrowLayout(reactContext);
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
    public void setShadowColor(RNAndrowLayout view, int color) {
        Log.d("shadowColor", Integer.toString(color));
        view.setShadowColor(color);
    }
    @ReactProp(name = "shadowOpacity")
    public void setShadowOpacity(RNAndrowLayout view, float opacity) {
        view.setShadowOpacity(opacity);
    }
    @ReactProp(name = "shadowRadius")
    public void setShadowRadius(RNAndrowLayout view, float radius) {
        view.setShadowRadius(radius);
    }
}
