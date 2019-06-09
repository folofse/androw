package se.folof.androw;

import android.os.CountDownTimer;
import android.util.Log;
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
import java.util.List;



public class RNAndrowImageListener implements EventDispatcherListener {

    private List<Integer> imageIds = new ArrayList<Integer>();
    private RNAndrowLayout androwLayout;

    private CountDownTimer fadeTimer;

    public RNAndrowImageListener(ReactContext reactContext, RNAndrowLayout androwLayout) {
        this.androwLayout = androwLayout;

        final EventDispatcher mEventDispatcher = reactContext.getNativeModule(UIManagerModule.class).getEventDispatcher();
        mEventDispatcher.addListener(this);
    }

    public void setImageOnLoadListerners(View child){
        if(child instanceof ReactImageView){
            ((ReactImageView)child).setShouldNotifyLoadEvents(true);
            this.imageIds.add(child.getId());
        }else if(child instanceof ReactViewGroup){
            for(int index = 0; index<((ViewGroup)child).getChildCount(); ++index) {
                View nextChild = ((ViewGroup)child).getChildAt(index);
                this.setImageOnLoadListerners(nextChild);
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
        if(event.getEventName() == "topLoadEnd" && this.imageIds.contains(event.getViewTag())){
            this.fadeTimer = new CountDownTimer(300, 33) {
                @Override
                public void onTick(long millisUntilFinished) {
                    androwLayout.invalidate();
                }

                @Override
                public void onFinish() {

                }
            }.start();
        }
    }
}
