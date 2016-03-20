/*
 * Copyright 2016 Yasin Sinan Kayacan.
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

package com.krialix.tardis;

import com.krialix.tardis.delegate.ActivityPresenterDelegate;
import com.krialix.tardis.delegate.PresenterDelegateCallback;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * The type Mvp activity.
 *
 * @param <V> the type parameter
 * @param <P> the type parameter
 */
public abstract class MvpActivity<V extends MvpView, P extends Presenter<V>>
        extends AppCompatActivity
        implements PresenterDelegateCallback<V, P> {

    private static final int LOADER_ID = 101;
    private ActivityPresenterDelegate<V, P> mDelegate =
            new ActivityPresenterDelegate<>(this /* Callback */, this /* Activity */, LOADER_ID);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDelegate.onCreate();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Presenter will ready to use for activity
        mDelegate.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mDelegate.onStop();
    }

    // If Activity doesn't implement Presenter<View> interface, override this.
    @NonNull
    @Override
    public V getMvpView() {
        // noinspection unchecked
        return (V) this;
    }
}
