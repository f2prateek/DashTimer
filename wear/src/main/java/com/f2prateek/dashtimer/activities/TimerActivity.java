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

package com.f2prateek.dashtimer.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.f2prateek.dashtimer.R;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;
import javax.inject.Inject;

import static android.provider.AlarmClock.ACTION_SET_TIMER;
import static android.provider.AlarmClock.EXTRA_LENGTH;

public class TimerActivity extends BaseWearActivity {
  @Inject GoogleApiClient googleApiClient;
  @InjectView(R.id.text) TextView textView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    final WatchViewStub stub = ButterKnife.findById(this, R.id.watch_view_stub);
    stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
      @Override
      public void onLayoutInflated(WatchViewStub stub) {
        ButterKnife.inject(TimerActivity.this, stub);
        handleIntent(getIntent());
      }
    });
  }

  private void handleIntent(Intent intent) {
    if (intent == null || !ACTION_SET_TIMER.equals(intent.getAction())) {
      return;
    }
    if (!intent.hasExtra(EXTRA_LENGTH)) {
      // todo: prompt for input
      return;
    }

    final long length = 1000L * intent.getIntExtra(EXTRA_LENGTH, 0);
    textView.setText(String.valueOf(getIntent().getExtras().getInt(EXTRA_LENGTH)));

    PutDataMapRequest dataMap = PutDataMapRequest.create("/count");
    dataMap.getDataMap().putInt("count", 10);
    PutDataRequest request = dataMap.asPutDataRequest();
    PendingResult<DataApi.DataItemResult> pendingResult =
        Wearable.DataApi.putDataItem(googleApiClient, request);
  }
}
