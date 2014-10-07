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
import com.f2prateek.ln.Ln;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;
import com.google.gson.Gson;
import com.squareup.otto.Bus;
import hugo.weaving.DebugLog;
import java.util.Collection;
import java.util.HashSet;
import javax.inject.Inject;

public abstract class BaseService extends IntentService {
  @Inject Bus bus;
  @Inject GoogleApiClient googleApiClient;
  @Inject Gson gson;

  public BaseService(String name) {
    super(name);
  }

  @Override
  public void onCreate() {
    super.onCreate();

    DashTimerApp.get(this).inject(this);
    bus.register(this);
    googleApiClient.connect();
  }

  @Override protected void onHandleIntent(Intent intent) {
    Dart.inject(this, intent.getExtras());
  }

  @Override public void onDestroy() {
    super.onDestroy();
    bus.unregister(this);
    googleApiClient.disconnect();
  }

  @DebugLog Collection<String> getNodes() {
    googleApiClient.blockingConnect();
    HashSet<String> results = new HashSet<String>();
    NodeApi.GetConnectedNodesResult nodes =
        Wearable.NodeApi.getConnectedNodes(googleApiClient).await();
    for (Node node : nodes.getNodes()) {
      results.add(node.getId());
    }
    return results;
  }

  @DebugLog public void sendMessage(String path, Object object) {
    for (String node : getNodes()) {
      MessageApi.SendMessageResult result =
          Wearable.MessageApi.sendMessage(googleApiClient, node, path,
              gson.toJson(object).getBytes()).await();
      if (!result.getStatus().isSuccess()) {
        Ln.e("Failed to send Message: " + result.getStatus());
      }
    }
  }
}
