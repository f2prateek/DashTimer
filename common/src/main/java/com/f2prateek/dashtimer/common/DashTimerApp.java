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
import com.f2prateek.ln.DebugLn;
import com.f2prateek.ln.Ln;
import dagger.ObjectGraph;
import hugo.weaving.DebugLog;

/**
 * A base {@link Application} that sets up the applicationGraph. Subclasses must override {@link
 * #getModules()} to provide additional dependencies.
 */
public abstract class DashTimerApp extends Application {
  private ObjectGraph applicationGraph;

  @Override
  public void onCreate() {
    super.onCreate();

    buildApplicationGraphAndInject();
    Ln.set(DebugLn.from(this));
  }

  @DebugLog
  private void buildApplicationGraphAndInject() {
    applicationGraph = ObjectGraph.create(getModules());
    applicationGraph.inject(this);
  }

  /**
   * Provide any modules that should be in the application graph. At the very least, they must use
   * {@link DashTimerModule} and a module to list their inject points.
   */
  //todo: enforce at compile time with abstract method
  protected abstract Object[] getModules();

  @DebugLog
  public void inject(Object o) {
    applicationGraph.inject(o);
  }

  public ObjectGraph getApplicationGraph() {
    return applicationGraph;
  }

  public static DashTimerApp get(Context context) {
    return (DashTimerApp) context.getApplicationContext();
  }
}
