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

import com.krialix.tardis.delegate.FragmentPresenterDelegate;
import com.krialix.tardis.delegate.FragmentPresenterDelegateImpl;
import com.krialix.tardis.delegate.PresenterDelegateCallback;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * The base class for fragments that want to implement Mvp.
 *
 * @param <V> MvpView
 * @param <P> Presenter
 */
public abstract class MvpFragment<V extends MvpView, P extends Presenter<V>>
        extends Fragment implements PresenterDelegateCallback<V, P> {

    private static final int LOADER_ID = 101;
    private FragmentPresenterDelegate<V, P> mDelegate;
    private P mPresenter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getMvpDelegate().onActivityCreated();
    }

    @Override
    public void onStop() {
        super.onStop();
        getMvpDelegate().onStop();
    }

    /**
     * @return The {@link FragmentPresenterDelegateImpl} being used by this Fragment.
     */
    @NonNull
    public FragmentPresenterDelegate<V, P> getMvpDelegate() {
        if (mDelegate == null) {
            mDelegate = new FragmentPresenterDelegateImpl<>(this /* Fragment */, LOADER_ID, this /* Callback */);
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

    // Override in case of fragment not implementing Presenter<View> interface
    @NonNull
    @Override
    public V getMvpView() {
        // noinspection unchecked
        return (V) this;
    }
}
