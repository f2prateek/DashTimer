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

package com.f2prateek.dashtimer.common;

import android.app.Application;
import android.content.Context;
import com.f2prateek.dashtimer.common.data.DataModule;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module(
    library = true,
    includes = {
        UiModule.class, DataModule.class
    },
    injects = {
        DashTimerApp.class
    } //
)
public final class DashTimerModule {
  private final DashTimerApp app;

  public DashTimerModule(DashTimerApp app) {
    this.app = app;
  }

  @Provides @Singleton Application provideApplication() {
    return app;
  }

  @Provides @ForApplication Context provideApplicationContext() {
    return app;
  }
}
