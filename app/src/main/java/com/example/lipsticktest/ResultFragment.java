package com.example.lipsticktest;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.lipsticktest.ViewModel.LipstickViewModel;

import java.text.DecimalFormat;


/**
 * A simple {@link Fragment} subclass.
 */
public class ResultFragment extends Fragment {
    private Button btn_back;
    private TextView tv_correct,tv_mistake,tv_mistakeRate,tv_grade;
    private LipstickViewModel lipstickViewModel;

    public ResultFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_result, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lipstickViewModel = new ViewModelProvider(requireActivity(),new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())).get(LipstickViewModel.class);

        btn_back = requireActivity().findViewById(R.id.btn_back);
        tv_correct = requireActivity().findViewById(R.id.tv_correct);
        tv_mistake = requireActivity().findViewById(R.id.tv_mistake);
        tv_mistakeRate = requireActivity().findViewById(R.id.tv_mistakeRate);
        tv_grade = requireActivity().findViewById(R.id.tv_grade);


        int correct = lipstickViewModel.getCorrectNum().getValue();
        int total = getResources().getInteger(R.integer.totalQuestion);
        String mistakeRate = new DecimalFormat("#%").format((float)(total-correct)/total);
        String grade = new DecimalFormat("#").format((float)correct/total*100);
        tv_correct.setText(String.valueOf(correct));
        tv_mistake.setText(String.valueOf(total-correct));
        tv_mistakeRate.setText(mistakeRate);
        tv_grade.setText(grade);



        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lipstickViewModel.reset();
                NavController controller = Navigation.findNavController(v);
                controller.navigate(R.id.action_resultFragment_to_firstFragment);
            }
        });
    }
}
