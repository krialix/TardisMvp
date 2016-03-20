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
import com.krialix.tardis.delegate.PresenterDelegateCallback;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * The type Mvp fragment.
 *
 * @param <V> the type parameter
 * @param <P> the type parameter
 */
public abstract class MvpFragment<V extends MvpView, P extends Presenter<V>>
        extends Fragment implements PresenterDelegateCallback<V, P> {

    private static final int LOADER_ID = 101;
    private final FragmentPresenterDelegate<V, P> mDelegate = new
            FragmentPresenterDelegate<>(this /* Callback */, this /* Fragment */, LOADER_ID);

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mDelegate.onActivityCreated();
    }

    @Override
    public void onStop() {
        super.onStop();
        mDelegate.onStop();
    }

    // Override in case of fragment not implementing Presenter<View> interface
    @NonNull
    @Override
    public V getMvpView() {
        // noinspection unchecked
        return (V) this;
    }
}
