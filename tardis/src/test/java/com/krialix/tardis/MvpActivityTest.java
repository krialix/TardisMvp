package com.krialix.tardis;

import org.junit.Before;
import org.mockito.Mockito;

public class MvpActivityTest {

    private MvpActivity<MvpView, Presenter<MvpView>> activity;

    @Before
    public void init() {
        activity = Mockito.mock(MvpActivity.class);
        Mockito.doCallRealMethod().when(activity).createPresenter();
    }
}
