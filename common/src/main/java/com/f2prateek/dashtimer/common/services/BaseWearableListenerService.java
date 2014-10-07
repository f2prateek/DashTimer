package com.f2prateek.dashtimer.common.services;

import com.f2prateek.dashtimer.common.DashTimerApp;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;
import com.google.gson.Gson;
import com.squareup.otto.Bus;
import javax.inject.Inject;

public abstract class BaseWearableListenerService extends WearableListenerService {
  @Inject Bus bus;
  @Inject Gson gson;

  @Override
  public void onCreate() {
    super.onCreate();
    DashTimerApp.get(this).inject(this);
    bus.register(this);
  }

  @Override public void onDestroy() {
    super.onDestroy();
    bus.unregister(this);
  }

  public <T> T get(MessageEvent event, Class<T> clazz) {
    return gson.fromJson(new String(event.getData()), clazz);
  }
}
