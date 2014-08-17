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

package com.f2prateek.dashtimer.common.data.google;

import android.content.Context;
import android.os.Bundle;
import com.f2prateek.dashtimer.common.ForApplication;
import com.f2prateek.ln.Ln;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Wearable;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module(complete = false, library = true)
public final class GoogleApiModule {

  @Provides @Singleton GoogleApiClient.ConnectionCallbacks provideConnectionCallbacks() {
    return new GoogleApiClient.ConnectionCallbacks() {
      @Override public void onConnected(Bundle bundle) {
        Ln.d("onConnected %s", bundle);
      }

      @Override public void onConnectionSuspended(int i) {
        Ln.d("onConnectionSuspended %s", i);
      }
    };
  }

  @Provides @Singleton
  GoogleApiClient.OnConnectionFailedListener provideOnConnectionFailedListener() {
    return new GoogleApiClient.OnConnectionFailedListener() {
      @Override public void onConnectionFailed(ConnectionResult connectionResult) {
        Ln.e("onConnectionFailed %s", connectionResult);
      }
    };
  }

  @Provides @Singleton GoogleApiClient provideGoogleApiClient(@ForApplication Context context,
      GoogleApiClient.ConnectionCallbacks connectionCallbacks,
      GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
    return new GoogleApiClient.Builder(context).addConnectionCallbacks(connectionCallbacks)
        .addOnConnectionFailedListener(onConnectionFailedListener)
        .addApi(Wearable.API)
        .build();
  }
}
