package jirapornmtrmutsv.rmutsvservice.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jirapornmtrmutsv.rmutsvservice.MyServiceActivity;
import jirapornmtrmutsv.rmutsvservice.R;

/**
 * Created by lenovo on 9/11/2560.
 */

public class ServiceFragment extends Fragment{


    public static ServiceFragment serviceInstance(String[] strings) {
        ServiceFragment serviceFragment = new ServiceFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArray("Login", strings);
        serviceFragment.setArguments(bundle);

        return serviceFragment;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String[] strings = getArguments().getStringArray("Login");
        Log.d("9novV1", "Login(1) on ServiceFragment ==> " + strings[1]);

//        Create Toolbar
        createToolbar(strings[1]);

    }

    private void createToolbar(String strTitle) {
        Toolbar toolbar = getView().findViewById(R.id.toolbarService);
        ((MyServiceActivity)getActivity()).setSupportActionBar(toolbar);
        ((MyServiceActivity)getActivity()).getSupportActionBar().setTitle(strTitle);
        ((MyServiceActivity)getActivity()).getSupportActionBar().setSubtitle("Who Loged");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_service, container, false);
        return view;




    }
}//Main Class
