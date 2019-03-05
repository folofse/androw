package se.folof.androw;

import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;

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

}
