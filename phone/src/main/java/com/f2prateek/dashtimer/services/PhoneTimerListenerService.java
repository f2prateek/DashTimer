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

package com.f2prateek.dashtimer.services;

import com.f2prateek.dashtimer.common.data.Timer;
import com.f2prateek.dashtimer.common.services.BaseWearableListenerService;
import com.f2prateek.dashtimer.common.services.TimerService;
import com.google.android.gms.wearable.MessageEvent;
import hugo.weaving.DebugLog;

public class PhoneTimerListenerService extends BaseWearableListenerService {

  @DebugLog @Override public void onMessageReceived(MessageEvent messageEvent) {
    super.onMessageReceived(messageEvent);

    if (messageEvent.getPath().contains(TimerService.ACTION_START_TIMER)) {
      startTimer(messageEvent);
    }
  }

  public void startTimer(MessageEvent messageEvent) {
    Timer timer = get(messageEvent, Timer.class);
    startService(TimerService.makeStartTimerIntent(this, timer.startTime(), timer.duration(),
        PhoneTimerService.class));
  }
}
