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

import com.krialix.tardis.delegate.PresenterDelegateCallback;

import android.content.Context;
import android.support.v4.content.Loader;

/**
 * The core class which handles of all {@link Presenter<V>} lifecycle.
 *
 * @param <V> MvpView
 * @param <P> Presenter
 */
public class PresenterLoader<V extends MvpView, P extends Presenter<V>> extends Loader<P> {

    private final PresenterDelegateCallback<V, P> mCallback;
    private P mPresenter;

    /**
     * Instantiates a new Presenter loader.
     *
     * @param context  the context
     * @param callback the callback
     */
    public PresenterLoader(Context context, PresenterDelegateCallback<V, P> callback) {
        super(context);
        mCallback = callback;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();

        // We already have a presenter, so pass it.
        if (mPresenter != null) {
            deliverResult(mPresenter);
            return;
        }

        // Otherwise, force a load.
        forceLoad();
    }

    @Override
    protected void onForceLoad() {
        super.onForceLoad();

        // Create a presenter
        mPresenter = mCallback.createPresenter();

        // Deliver result
        deliverResult(mPresenter);
    }

    @Override
    protected void onReset() {
        super.onReset();

        if (mPresenter != null) {
            mPresenter.onViewDetached();
            mPresenter = null;
        }
    }
}
