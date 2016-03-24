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
import com.krialix.tardis.delegate.ActivityPresenterDelegateImpl;
import com.krialix.tardis.delegate.PresenterDelegateCallback;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * The base class for activities that want to implement Mvp.
 *
 * @param <V> MvpView
 * @param <P> Presenter
 */
public abstract class MvpActivity<V extends MvpView, P extends Presenter<V>> extends AppCompatActivity
        implements PresenterDelegateCallback<V, P> {

    private static final int LOADER_ID = 101;
    private ActivityPresenterDelegate<V, P> mDelegate;
    private P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMvpDelegate().onCreate();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Presenter will ready to use for activity
        getMvpDelegate().onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        getMvpDelegate().onStop();
    }

    /**
     * @return The {@link ActivityPresenterDelegateImpl} being used by this Activity.
     */
    @NonNull
    protected ActivityPresenterDelegate<V, P> getMvpDelegate() {
        if (mDelegate == null) {
            mDelegate = new ActivityPresenterDelegateImpl<>(this /* Activity */, LOADER_ID, this /* Callback */);
        }
        return mDelegate;
    }

    /**
     * Returns current presenter.
     *
     * @return the presenter
     */
    protected P getPresenter() {
        return mPresenter;
    }

    @Override
    public void onPresenterReady(P presenter) {
        mPresenter = presenter;
    }

    // If Activity doesn't implement Presenter<View> interface, override this.
    @NonNull
    @Override
    public V getMvpView() {
        // noinspection unchecked
        return (V) this;
    }
}
