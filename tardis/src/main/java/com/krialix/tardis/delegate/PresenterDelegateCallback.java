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
import com.krialix.tardis.PresenterFactory;

import android.support.annotation.NonNull;

/**
 * The interface Presenter delegate callback.
 *
 * @param <V> the type parameter
 * @param <P> the type parameter
 */
public interface PresenterDelegateCallback<V extends MvpView, P extends Presenter<V>> {

    /**
     * Gets presenter factory.
     *
     * @return the presenter factory
     */
    @NonNull
    PresenterFactory<P> getPresenterFactory();

    /**
     * On presenter ready.
     *
     * @param presenter the presenter
     */
    void onPresenterReady(P presenter);

    /**
     * On presenter destroyed.
     */
    void onPresenterDestroyed();

    /**
     * Gets mvp view.
     *
     * @return the mvp view
     */
    V getMvpView();
}