package com.krialix.tardis.delegate;

import com.krialix.tardis.MvpView;
import com.krialix.tardis.Presenter;

/**
 * The interface Activity presenter delegate.
 *
 * @param <V> MvpView
 * @param <P> Presenter
 */
public interface ActivityPresenterDelegate<V extends MvpView, P extends Presenter<V>> {

    /**
     * On create.
     */
    void onCreate();

    /**
     * On start.
     */
    void onStart();

    /**
     * On stop.
     */
    void onStop();
}