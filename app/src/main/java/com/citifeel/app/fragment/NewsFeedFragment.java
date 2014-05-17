package com.citifeel.app.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.citifeel.app.BaseFragment;
import com.citifeel.app.R;

/**
 * Created by roytang on 17/5/14.
 */
public class NewsFeedFragment extends BaseFragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_newsfeed, container, false);

        return rootView;
    }
}
