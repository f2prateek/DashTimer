package com.f2prateek.dashtimer.common.services;

import com.f2prateek.dashtimer.common.DashTimerApp;
import com.mariux.teleport.lib.TeleportService;
import com.squareup.otto.Bus;
import javax.inject.Inject;

public abstract class BaseTeleportService extends TeleportService {
  @Inject Bus bus;

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
}
