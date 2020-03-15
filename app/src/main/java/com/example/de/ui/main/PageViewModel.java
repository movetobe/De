package com.example.de.ui.main;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class PageViewModel extends ViewModel {
    private MutableLiveData<String> mString_timeline = new MutableLiveData<>();
    private MutableLiveData<String> mString_focus = new MutableLiveData<>();
    private MutableLiveData<String> mString_personal_todo = new MutableLiveData<>();
    private MutableLiveData<String> mString_work_todo = new MutableLiveData<>();

    private LiveData<String> mText_timeline = Transformations.map(mString_timeline, new Function<String, String>() {
        @Override
        public String apply(String input) {
            return input;
        }
    });
    private LiveData<String> mText_focus = Transformations.map(mString_focus, new Function<String, String>() {
        @Override
        public String apply(String input) {
            return input;
        }
    });
    private LiveData<String> mText_personal_todo = Transformations.map(mString_personal_todo, new Function<String, String>() {
        @Override
        public String apply(String input) {
            return input;
        }
    });
    private LiveData<String> mText_work_todo = Transformations.map(mString_work_todo, new Function<String, String>() {
        @Override
        public String apply(String input) {
            return input;
        }
    });

    public void setmString(String s, int index) {
        switch (index) {
            case 0:
                mString_timeline.setValue(s);
                break;
            case 1:
                mString_focus.setValue(s);
                break;
            case 2:
                mString_personal_todo.setValue(s);
                break;
            case 3:
                mString_work_todo.setValue(s);
                break;
            default:
                break;
        }
    }
    public LiveData<String> getmText(int index) {
        switch (index) {
            case 0:
                return mText_timeline;
            case 1:
                return mText_focus;
            case 2:
                return mText_personal_todo;
            case 3:
                return mText_work_todo;
            default:
                return null;
        }
    }
}