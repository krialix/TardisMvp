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

package com.krialix.tardis.delegate;

import com.krialix.tardis.MvpView;
import com.krialix.tardis.Presenter;
import com.krialix.tardis.PresenterLoader;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

/**
 * The type Fragment presenter delegate.
 *
 * @param <V> the type parameter
 * @param <P> the type parameter
 */
public final class FragmentPresenterDelegate<V extends MvpView, P extends Presenter<V>> {

    private final PresenterDelegateCallback<V, P> mDelegateCallback;
    private final Fragment mFragment;
    private final int mId;
    private P mPresenter;

    /**
     * A boolean flag to avoid delivering the result twice. Calling initLoader in onActivityCreated
     * makes onLoadFinished will be called twice during configuration change.
     */
    private boolean delivered = false;

    /**
     * Instantiates a new Fragment presenter delegate.
     *
     * @param delegateCallback the delegate callback
     * @param fragment         the fragment
     * @param id               the id
     */
    public FragmentPresenterDelegate(@NonNull PresenterDelegateCallback<V, P> delegateCallback,
                                     @NonNull Fragment fragment, final int id) {
        mDelegateCallback = delegateCallback;
        mFragment = fragment;
        mId = id;
    }

    /**
     * On activity created.
     */
    public void onActivityCreated() {
        mFragment.getLoaderManager().initLoader(mId, null,
                new LoaderManager.LoaderCallbacks<P>() {
                    @Override
                    public Loader<P> onCreateLoader(int id, Bundle args) {
                        return new PresenterLoader<>(mFragment.getContext(),
                                mDelegateCallback.getPresenterFactory());
                    }

                    @Override
                    public void onLoadFinished(Loader<P> loader, P presenter) {
                        if (!delivered) {
                            mPresenter = presenter;
                            delivered = true;
                            mDelegateCallback.onPresenterReady(presenter);
                            presenter.onViewAttached(mDelegateCallback.getMvpView());
                        }
                    }

                    @Override
                    public void onLoaderReset(Loader<P> loader) {
                        mPresenter = null;
                        mDelegateCallback.onPresenterDestroyed();
                    }
                });
    }

    /**
     * On stop.
     */
    public void onStop() {
        mPresenter.onViewDetached();
    }
}
