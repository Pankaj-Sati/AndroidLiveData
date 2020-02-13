package com.example.myapplication;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Fragments.FragmentMain;


public class MainActivity extends AppCompatActivity
{

    FragmentManager mFragmentManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFragmentManager=getSupportFragmentManager(); //Manages Support Library Fragments easily

        Fragment fragment=mFragmentManager.findFragmentById(R.id.CL_Fragment_Container); //Container ID acts as fragment id in fragment manager
        if(fragment==null)
        {
            fragment= FragmentMain.createNewInstance(MainActivity.this);
            mFragmentManager.beginTransaction().add(R.id.CL_Fragment_Container,fragment).commit();
        }
        else
        {
            mFragmentManager.beginTransaction().replace(R.id.CL_Fragment_Container,fragment);
        }
    }
}
