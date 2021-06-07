package com.example.lipsticktest;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.lipsticktest.ViewModel.LipstickViewModel;

import java.util.List;

import cn.leancloud.AVObject;
import cn.leancloud.AVQuery;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * A simple {@link Fragment} subclass.
 */
public class RandomTopicFirstFragment extends Fragment {
    private Button btn_start;
    private LiveData<List<AVObject>> dataListLive;
    private LipstickViewModel lipstickViewModel;
    private AVObject lipstickObject;
    private AVQuery<AVObject> queryAll = new AVQuery<>("Lipstick");

    public RandomTopicFirstFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_random_topic_first, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btn_start = requireActivity().findViewById(R.id.btn_start_test);
        lipstickViewModel = new ViewModelProvider(requireActivity(),new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())).get(LipstickViewModel.class);
        //lipstickViewModel.init();
        //dataListLive = lipstickViewModel.allDataListLive();

        queryAll.findInBackground().subscribe(new Observer<List<AVObject>>() {
            @Override
            public void onSubscribe(Disposable d) {}

            @Override
            public void onNext(List<AVObject> avObjects) {
                btn_start.setOnClickListener(v -> {
                        //Toast.makeText(requireActivity(),avObjects.get(0).getString("lipstickBrand"),Toast.LENGTH_SHORT).show();
                        NavController controller = Navigation.findNavController(v);
                        controller.navigate(R.id.action_randomTopicFirstFragment_to_randomTopicDetailFragment);

                });
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(requireActivity(),e.getMessage(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {}
        });




    }
}
