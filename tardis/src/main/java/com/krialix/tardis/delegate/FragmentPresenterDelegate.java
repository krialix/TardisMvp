package com.krialix.tardis.delegate;

import com.krialix.tardis.MvpView;
import com.krialix.tardis.Presenter;

/**
 * The interface Fragment presenter delegate.
 *
 * @param <V> MvpView
 * @param <P> Presenter
 */
public interface FragmentPresenterDelegate<V extends MvpView, P extends Presenter<V>> {

    /**
     * On activity created.
     */
    void onActivityCreated();

    /**
     * On stop.
     */
    void onStop();
}
