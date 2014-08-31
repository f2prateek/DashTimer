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

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.f2prateek.dashtimer.R;
import com.f2prateek.dashtimer.services.TimerService;
import javax.inject.Inject;

import static android.provider.AlarmClock.ACTION_SET_TIMER;
import static android.provider.AlarmClock.EXTRA_LENGTH;

public class SetTimerActivity extends BaseWearActivity {
  @Inject NotificationManager notificationManager;
  @Inject AlarmManager alarmManager;
  @InjectView(R.id.text) TextView textView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    handleIntent(getIntent());
  }

  private void handleIntent(Intent intent) {
    if (intent == null || !ACTION_SET_TIMER.equals(intent.getAction())) {
      return;
    }

    if (!intent.hasExtra(EXTRA_LENGTH)) {
      // todo: prompt for entry
      setContentView(R.layout.activity_main);
      final WatchViewStub stub = ButterKnife.findById(this, R.id.watch_view_stub);
      stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
        @Override
        public void onLayoutInflated(WatchViewStub stub) {
          textView.setText("No duration received!");
        }
      });
      return;
    }

    final long duration = 1000 * intent.getIntExtra(EXTRA_LENGTH, 0);
    startService(TimerService.makeStartTimerIntent(this, System.currentTimeMillis(), duration));
    finish();
  }
}
