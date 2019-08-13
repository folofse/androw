package se.folof.androw;

import android.os.CountDownTimer;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.EventDispatcher;
import com.facebook.react.uimanager.events.EventDispatcherListener;
import com.facebook.react.views.image.ReactImageView;
import com.facebook.react.views.view.ReactViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RNAndrowImageListener implements EventDispatcherListener {

    private Map<Integer,RNAndrowLayout> imageIds =  new HashMap<Integer,RNAndrowLayout>();
    private List<RNAndrowLayout> viewsToFadeIn = new ArrayList<RNAndrowLayout>();
    private ReactContext reactContext;
    private EventDispatcher eventDispatcher;

    private CountDownTimer fadeTimer;

    public RNAndrowImageListener(ReactContext reactContext) {
        this.reactContext = reactContext;

        this.eventDispatcher = reactContext.getNativeModule(UIManagerModule.class).getEventDispatcher();
        this.eventDispatcher.addListener(this);
    }

    public void onAddView(RNAndrowLayout parent, View child){
        if(child instanceof ReactImageView){
            ((ReactImageView)child).setShouldNotifyLoadEvents(true);
            this.imageIds.put(child.getId(), parent);
        }else if(child instanceof ReactViewGroup){
            for(int index = 0; index<((ViewGroup)child).getChildCount(); ++index) {
                View nextChild = ((ViewGroup)child).getChildAt(index);
                this.onAddView(parent, nextChild);
            }
        }
    }

    @Override
    public void onEventDispatch(final Event event) {
        // Events can be dispatched from any thread so we have to make sure handleEvent is run from the
        // UI thread.

        if (UiThreadUtil.isOnUiThread()) {
            handleEvent(event);
        } else {
            UiThreadUtil.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    handleEvent(event);
                }
            });
        }
    }

    private void handleEvent(Event event) {
        //Make sure it only redraws on image events in current androw view
        if(event.getEventName() == "topLoadEnd" && this.imageIds.containsKey(event.getViewTag())){

            RNAndrowLayout androwLayout = this.imageIds.get(event.getViewTag());
            this.viewsToFadeIn.add(androwLayout);

            if(this.fadeTimer != null){
                this.fadeTimer.cancel();
            }

            this.fadeTimer = new CountDownTimer(500, 33) {
                @Override
                public void onTick(long millisUntilFinished) {
                    for (RNAndrowLayout view : viewsToFadeIn) {
                        view.invalidate();
                    }
                }

                @Override
                public void onFinish() {
                    for (RNAndrowLayout view : viewsToFadeIn) {
                        view.invalidate();
                    }
                    viewsToFadeIn.clear();
                }
            }.start();
        }
    }

    public void tearDown(){
        this.eventDispatcher.removeListener(this);
    }
}
