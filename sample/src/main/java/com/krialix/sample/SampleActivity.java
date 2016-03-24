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

package com.krialix.sample;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.krialix.sample.presenter.SamplePresenter;
import com.krialix.sample.presenter.SamplePresenterImpl;
import com.krialix.tardis.MvpActivity;

public class SampleActivity extends MvpActivity<SampleView, SamplePresenter>
        implements SampleView {

    private static final String TAG = "SampleActivity";

    private TextView mListTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListTextView = (TextView) findViewById(R.id.number_list);
        Button testBtn = (Button) findViewById(R.id.testButton);

        if (savedInstanceState != null) {
            mListTextView.setText(savedInstanceState.getCharSequence("test"));
        }
        if (testBtn != null) {
            testBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getPresenter().onTest("Atölye 15!!!!");
                }
            });
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharSequence("test", mListTextView.getText());
    }

    @NonNull
    @Override
    public SamplePresenter createPresenter() {
        return new SamplePresenterImpl();
    }

    @Override
    public void onPresenterDestroyed() {
        Log.i(TAG, "onPresenterDestroyed: ");
    }

    @Override
    public void showData(String text) {
        mListTextView.setText(text);
    }
}
