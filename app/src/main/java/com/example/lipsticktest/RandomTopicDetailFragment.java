package com.example.lipsticktest;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.lipsticktest.ViewModel.LipstickViewModel;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import cn.leancloud.AVObject;
import cn.leancloud.AVQuery;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * A simple {@link Fragment} subclass.
 */
public class RandomTopicDetailFragment extends Fragment {
    private Button btn_submit;
    private TextView tv_qusNum,tv_qus;
    private RadioButton btnA,btnB,btnC,btnD;
    private ImageView img_lipstick;
    private AVQuery<AVObject> queryRandomLipstick = new AVQuery<>("Lipstick");
    private LipstickViewModel lipstickViewModel;
    private List<Integer> IDNum = new LinkedList<>();
    private int correctOption;

    public RandomTopicDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_random_topic_detail, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        lipstickViewModel = new ViewModelProvider(requireActivity(),new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())).get(LipstickViewModel.class);

        btn_submit = requireActivity().findViewById(R.id.btn_test_submit);
        tv_qusNum = requireActivity().findViewById(R.id.tv_QusNum);
        tv_qus = requireActivity().findViewById(R.id.tv_Qus);
        btnA = requireActivity().findViewById(R.id.rdio_btn1);
        btnB = requireActivity().findViewById(R.id.rdio_btn2);
        btnC = requireActivity().findViewById(R.id.rdio_btn3);
        btnD = requireActivity().findViewById(R.id.rdio_btn4);
        img_lipstick = requireActivity().findViewById(R.id.image_lipstick);


        builder.setTitle("还没选择哦")
                .setNegativeButton("好的", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //generateLipstickList();
                    }
                })
                .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //generateLipstickList();
                    }
                });


        for(int i = 0 ;i < getResources().getInteger(R.integer.totalQuestion);i++){
            IDNum.add(i);
        }


        btn_submit.setOnClickListener(v -> {
//            NavController controller = Navigation.findNavController(v);
//            controller.navigate(R.id.action_randomTopicDetailFragment_to_submitFragment);

            lipstickViewModel.addQuestionNum();
            tv_qusNum.setText(Objects.requireNonNull(lipstickViewModel.getQuestioinNum().getValue()).toString() + ".");
            //generateLipstickList();
            if(btnA.isChecked() || btnB.isChecked() || btnC.isChecked() || btnD.isChecked()){
                generateLipstickList();
                lipstickViewModel.getCorrectNum();

                if(btnA.isChecked() && correctOption == 0){
                    lipstickViewModel.addCorrectNum();
                }else if (btnB.isChecked() && correctOption == 1){
                    lipstickViewModel.addCorrectNum();
                }else if (btnC.isChecked() && correctOption == 2){
                    lipstickViewModel.addCorrectNum();
                }else if (btnD.isChecked() && correctOption == 3){
                    lipstickViewModel.addCorrectNum();
                }
                Toast.makeText(requireActivity(),String.valueOf(lipstickViewModel.getCorrectNum().getValue()),Toast.LENGTH_SHORT).show();

            }else{
                builder.show();
            }

            resetBtn();

        });

        btnA.setOnClickListener(v -> {
            btnB.setChecked(false);
            btnC.setChecked(false);
            btnD.setChecked(false);
        });

        btnB.setOnClickListener(v -> {
            btnA.setChecked(false);
            btnC.setChecked(false);
            btnD.setChecked(false);
        });

        btnC.setOnClickListener(v -> {
            btnA.setChecked(false);
            btnB.setChecked(false);
            btnD.setChecked(false);
        });

        btnD.setOnClickListener(v -> {
            btnA.setChecked(false);
            btnC.setChecked(false);
            btnB.setChecked(false);
        });

        generateLipstickList();

       /* String url = "https://github.com/wasabeef/glide-transformations/blob/main/art/demo-org.jpg?raw=true";
        String url2 = "https://thumbnail0.baidupcs.com/thumbnail/55380e5f3he739109f5bbe1f16917445?fid=590169272-250528-1060709261686179&rt=pr&sign=FDTAER-DCb740ccc5511e5e8fedcff06b081203-T7LOGnsXrJCkbQTOpCpTcL9KJxA%3d&expires=8h&chkbd=0&chkv=0&dp-logid=415681354203248381&dp-callid=0&time=1615273200&size=c1739_u979&quality=90&vuk=590169272&ft=image&autopolicy=1";

        Glide.with(requireActivity())
                .load(url2)
                .into(img_lipstick);*/


    }

    public void generateLipstickList(){
        queryRandomLipstick.findInBackground().subscribe(new Observer<List<AVObject>>() {
            @Override
            public void onSubscribe(Disposable d) {}

            @Override
            public void onNext(List<AVObject> avObjects) {
                generate(avObjects);
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(requireActivity(),e.getMessage(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {}
        });
    }


    public void generate(List<AVObject> _dataList){
        Random random = new Random();


        if(IDNum.size()!=0){
            int IDNumIndex = random.nextInt(IDNum.size());
            int indexNum = IDNum.get(IDNumIndex);
            int index = _dataList.get(indexNum).getInt("lipstickID");
            //Toast.makeText(requireActivity(),String.valueOf(IDNum.size()),Toast.LENGTH_SHORT).show();

            if(IDNum.contains(index)){
                AVQuery<AVObject> queryWhich = new AVQuery<>("Lipstick");
                queryWhich.whereEqualTo("lipstickID",index);
                queryWhich.findInBackground().subscribe(new Observer<List<AVObject>>() {
                    @Override
                    public void onSubscribe(Disposable d) {}

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onNext(List<AVObject> avObjects) {
                        tv_qusNum.setText(Objects.requireNonNull(lipstickViewModel.getQuestioinNum().getValue()).toString() + ".");
                        tv_qus.setText(avObjects.get(0).getString("question"));
                        Glide.with(requireActivity())
                                .load(avObjects.get(0).getString("pictureAddress"))
                                .placeholder(R.drawable.ic_launcher_background)
                                .listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        return false;
                                    }
                                })
                                .override(800,600)
                                .into(img_lipstick);
                        switch (lipstickViewModel.randomOptions()){
                            case 0:
                                btnA.setText("A."+avObjects.get(0).getString("lipstickBrand"));
                                btnB.setText("B."+avObjects.get(0).getString("interference1"));
                                btnC.setText("C."+avObjects.get(0).getString("interference2"));
                                btnD.setText("D."+avObjects.get(0).getString("interference3"));
                                correctOption = 0;
                                break;
                            case 1:
                                btnB.setText("B."+avObjects.get(0).getString("lipstickBrand"));
                                btnA.setText("A."+avObjects.get(0).getString("interference1"));
                                btnC.setText("C."+avObjects.get(0).getString("interference2"));
                                btnD.setText("D."+avObjects.get(0).getString("interference3"));
                                correctOption = 1;
                                break;
                            case 2:
                                btnC.setText("C."+avObjects.get(0).getString("lipstickBrand"));
                                btnA.setText("A."+avObjects.get(0).getString("interference1"));
                                btnB.setText("B."+avObjects.get(0).getString("interference2"));
                                btnD.setText("D."+avObjects.get(0).getString("interference3"));
                                correctOption = 2;
                                break;
                            case 3:
                                btnD.setText("D."+avObjects.get(0).getString("lipstickBrand"));
                                btnA.setText("A."+avObjects.get(0).getString("interference1"));
                                btnB.setText("B."+avObjects.get(0).getString("interference2"));
                                btnC.setText("C."+avObjects.get(0).getString("interference3"));
                                correctOption = 3;
                                break;
                        }



                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(requireActivity(),e.getMessage(),Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {}
                });

                IDNum.remove((Integer)indexNum);
            }else{

            }
        }else{
            NavController controller = Navigation.findNavController(btn_submit);
            controller.navigate(R.id.action_randomTopicDetailFragment_to_submitFragment);
        }


    }

    public void resetBtn(){
        btnA.setChecked(false);
        btnB.setChecked(false);
        btnC.setChecked(false);
        btnD.setChecked(false);
    }

}
