package com.example.lipsticktest.ViewModel;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.Random;

import cn.leancloud.AVObject;
import cn.leancloud.AVQuery;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class LipstickViewModel extends AndroidViewModel {
    private MutableLiveData<List<AVObject>> _dataListLive = new MutableLiveData<>();
    private LiveData<List<AVObject>> dataListLive = _dataListLive;
    private List<AVObject> dataList;
    private MutableLiveData<List<AVObject>> _lipstickListLive = new MutableLiveData<>();
    private LiveData<List<AVObject>> lipstickListLive = _lipstickListLive;
    private AVQuery<AVObject> query = new AVQuery<>("Lipstick");
    private MutableLiveData<Integer> questioinNum;
    private MutableLiveData<Integer> correctNum;


    public LipstickViewModel(@NonNull Application application) {
        super(application);
    }


    public void init(){

        query.findInBackground().subscribe(new Observer<List<AVObject>>() {
            @Override
            public void onSubscribe(Disposable d) {}

            @Override
            public void onNext(List<AVObject> avObjects) {
                _dataListLive.setValue(avObjects);

                //Toast.makeText(getApplication(),dataListLive.getValue().get(0).getString("lipstickBrand"),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(getApplication(),e.getMessage(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {}
        });
    }

    public void generate(List<AVObject> _dataList){

        Random random = new Random();
        int index = random.nextInt(_dataList.size())+1;
        AVQuery<AVObject> queryWhich = new AVQuery<>("Lipstick");
        queryWhich.whereEqualTo("lipstickID",index);
        queryWhich.findInBackground().subscribe(new Observer<List<AVObject>>() {
            @Override
            public void onSubscribe(Disposable d) {}

            @Override
            public void onNext(List<AVObject> avObjects) {
                _lipstickListLive.setValue(avObjects);
                //Toast.makeText(getApplication(),_lipstickListLive.getValue().get(0).getString("lipstickBrand"),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(getApplication(),e.getMessage(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {}


        });
    }

    public LiveData<List<AVObject>> allDataListLive(){
        return dataListLive;
    }

    public LiveData<List<AVObject>> lipstickObject(){
        return lipstickListLive;
    }

    public MutableLiveData<Integer> getQuestioinNum(){
        if(questioinNum == null){
            questioinNum = new MutableLiveData<>();
            questioinNum.setValue(1);
        }
        return questioinNum;
    }

    public void addQuestionNum(){
        questioinNum.setValue(questioinNum.getValue() + 1);
    }

    public void reset(){
        questioinNum = null;
        correctNum = null;
    }

    public MutableLiveData<Integer> getCorrectNum(){
        if (correctNum == null){
            correctNum = new MutableLiveData<>();
            correctNum.setValue(0);
        }
        return correctNum;
    }

    public void addCorrectNum(){
        correctNum.setValue(correctNum.getValue() + 1);
    }

    public int randomOptions(){
        Random random = new Random();
        return random.nextInt(4);
    }





}
