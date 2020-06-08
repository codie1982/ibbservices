package com.reactlibrary.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.reactlibrary.Activity.NoApplicationActivity;
import com.reactlibrary.GameFragment;
import com.reactlibrary.R;


public class noApplicationFragment extends Fragment {

    TextView txtMessage;
    NoApplicationActivity noApplicationActivity;
    Bundle bundle;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        noApplicationActivity = (NoApplicationActivity) getActivity();
        assert noApplicationActivity != null;
        bundle = noApplicationActivity.getNoApplicationdata();

        View view = inflater.inflate(R.layout.fragment_no_application, container, false);

        txtMessage = view.findViewById(R.id.txtMessage);

        
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtMessage.setText(bundle.getString("message"));


       /* GameFragment gameFragment = new GameFragment();
        FragmentManager manager = getParentFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.gameFrameLayout, gameFragment, "gamefragment");
        transaction.commit();
        transaction.show(gameFragment);*/
    }
}