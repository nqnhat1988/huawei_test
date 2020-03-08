package com.nhat.huaweitest.ui.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.nhat.huaweitest.common.Resource;

class MainViewModel extends AndroidViewModel {
    private MutableLiveData<Resource<Long>> liveData = new MutableLiveData<>();

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    MutableLiveData<Resource<Long>> getLiveData() {
        return liveData;
    }

    void beginCalculate(Long a, Long b) {
        liveData.postValue(Resource.<Long>loading());
        liveData.postValue(Resource.success(a + b));
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application mApplication;

        public Factory(@NonNull Application application) {
            mApplication = application;
        }

        @SuppressWarnings("unchecked")
        @Override
        @NonNull
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new MainViewModel(mApplication);
        }
    }
}
