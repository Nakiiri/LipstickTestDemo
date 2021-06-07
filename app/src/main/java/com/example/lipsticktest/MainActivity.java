package com.example.lipsticktest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.DialogInterface;
import android.os.Bundle;

import com.example.lipsticktest.ViewModel.LipstickViewModel;

public class MainActivity extends AppCompatActivity {
    NavController controller;
    private LipstickViewModel lipstickViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        controller = Navigation.findNavController(this,R.id.fragment);
        NavigationUI.setupActionBarWithNavController(this,controller);
    }

    @Override
    public boolean onSupportNavigateUp() {
        lipstickViewModel = new ViewModelProvider(this,new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(LipstickViewModel.class);
        if(controller.getCurrentDestination().getId() == R.id.topicDetailFragment || controller.getCurrentDestination().getId() == R.id.randomTopicDetailFragment
                || controller.getCurrentDestination().getId() == R.id.submitFragment || controller.getCurrentDestination().getId() == R.id.checkFragment ||
                controller.getCurrentDestination().getId() == R.id.checkTopicDetailFragment){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.quit_title);
            builder.setPositiveButton(R.string.positive_message, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    lipstickViewModel.reset();
                    controller.navigate(R.id.firstFragment);
                }
            });
            builder.setNegativeButton(R.string.negative_message, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }else if(controller.getCurrentDestination().getId() == R.id.resultFragment){
            controller.navigate(R.id.firstFragment);
        } else{
            controller.navigateUp();
        }
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        onSupportNavigateUp();
    }
}
