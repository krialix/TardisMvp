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

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;

/**
 * This class represents a delegate which you can use to extend ActivityPresenter's support to any
 * {@link android.app.Activity}
 *
 * @param <V> MvpView
 * @param <P> Presenter
 */
public class ActivityPresenterDelegateImpl<V extends MvpView, P extends Presenter<V>>
        implements ActivityPresenterDelegate<V, P> {

    private final PresenterDelegateCallback<V, P> mDelegateCallback;
    private final AppCompatActivity mActivity;
    private final int mId;
    private P mPresenter;

    /**
     * Instantiates a new Activity presenter delegate.
     *
     * @param activity the activity
     * @param id       the id
     * @param callback the callback
     */
    public ActivityPresenterDelegateImpl(@NonNull Activity activity, final int id,
                                         @NonNull PresenterDelegateCallback<V, P> callback) {
        mActivity = (AppCompatActivity) activity;
        mId = id;
        mDelegateCallback = callback;
    }

    @Override
    public void onCreate() {
        mActivity.getSupportLoaderManager().initLoader(mId, null,
                new LoaderManager.LoaderCallbacks<P>() {
                    @Override
                    public Loader<P> onCreateLoader(int id, Bundle args) {
                        return new PresenterLoader<>(mActivity, mDelegateCallback);
                    }

                    @Override
                    public void onLoadFinished(Loader<P> loader, P presenter) {
                        mPresenter = presenter;
                        mDelegateCallback.onPresenterReady(presenter);
                    }

                    @Override
                    public void onLoaderReset(Loader<P> loader) {
                        mPresenter = null;
                        mDelegateCallback.onPresenterDestroyed();
                    }
                });
    }

    @Override
    public void onStart() {
        mPresenter.onViewAttached(mDelegateCallback.getMvpView());
    }

    @Override
    public void onStop() {
        mPresenter.onViewDetached();
    }
}
