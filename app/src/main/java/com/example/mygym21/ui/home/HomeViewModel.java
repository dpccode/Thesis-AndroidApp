package com.example.mygym21.ui.home;

import android.view.View;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mygym21.R;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<View> mV;
    private MutableLiveData<Integer> mView;
    private ImageView picture;

    public HomeViewModel() {

        mView = new MutableLiveData<Integer>();
        mView.setValue(R.layout.krathsh);
    }



    public MutableLiveData<Integer> getmView(){
            return mView;
    }

    public ImageView getPicture() {
        return picture;
    }
}