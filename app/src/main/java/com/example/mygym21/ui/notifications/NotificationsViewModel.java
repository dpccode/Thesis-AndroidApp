package com.example.mygym21.ui.notifications;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mygym21.R;

public class NotificationsViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<String> sText;
    private MutableLiveData<ImageView> mPic;

    public NotificationsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");

        sText = new MutableLiveData<>();
        sText.setValue("edwww");



    }



    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<String> getsText() { return sText; }

}