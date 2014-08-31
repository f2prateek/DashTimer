/*
 * Copyright 2014 Prateek Srivastava
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.f2prateek.dashtimer.common.services;

import android.app.IntentService;
import android.content.Intent;
import com.f2prateek.dart.Dart;
import com.f2prateek.dashtimer.common.DashTimerApp;
import com.mariux.teleport.lib.TeleportClient;
import com.squareup.otto.Bus;
import javax.inject.Inject;

public abstract class BaseService extends IntentService {
  @Inject Bus bus;
  @Inject TeleportClient teleportClient;

  public BaseService(String name) {
    super(name);
  }

  @Override
  public void onCreate() {
    super.onCreate();

    DashTimerApp.get(this).inject(this);
    bus.register(this);
    teleportClient.connect();
  }

  @Override protected void onHandleIntent(Intent intent) {
    Dart.inject(this, intent.getExtras());
  }

  @Override public void onDestroy() {
    super.onDestroy();
    bus.unregister(this);
    teleportClient.disconnect();
  }
}
