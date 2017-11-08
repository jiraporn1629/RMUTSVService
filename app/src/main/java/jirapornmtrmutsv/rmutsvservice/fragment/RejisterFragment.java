package jirapornmtrmutsv.rmutsvservice.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import jirapornmtrmutsv.rmutsvservice.MainActivity;
import jirapornmtrmutsv.rmutsvservice.R;
import jirapornmtrmutsv.rmutsvservice.utility.MyAlert;
import jirapornmtrmutsv.rmutsvservice.utility.MyConstant;
import jirapornmtrmutsv.rmutsvservice.utility.UploadNewUser;

/**
 * Created by lenovo on 7/11/2560.
 */

public class RejisterFragment extends Fragment{
//    Explicit
    private String nameString, userString, passwordString, categoryString;
    private boolean aBoolean = true;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        Toolbar Controller
        toolbarController();
//        Save Controller
        saveController();
//Category Controller
        categoryController();


    }//Main Method

    private void categoryController() {
        RadioGroup radioGroup = getView().findViewById(R.id.ragCategory);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                aBoolean = false;
                switch (i) {
                    case R.id.radBuyer:
                        categoryString = "Buyer";
                        break;
                    case R.id.radSaler:
                        categoryString = "Saler";
                        break;
                }


            }
        });

    }

    private void saveController() {
        ImageView imageView = getView().findViewById(R.id.imvSave);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Initial View
                EditText nameEditText = getView().findViewById(R.id.edtName);
                EditText userEditText = getView().findViewById(R.id.edtUser);
                EditText passwordEditText = getView().findViewById(R.id.edtPassword);

//                Change Data Type
                nameString = nameEditText.getText().toString().trim();
                userString = userEditText.getText().toString().trim();
                passwordString = passwordEditText.getText().toString().trim();

//                Check Space
                if (nameString.equals("") || userString.equals("") || passwordString.equals("")) {
//                    Have Space
                    MyAlert myAlert = new MyAlert(getActivity());
                    myAlert.myDialog("Have Space","Please Fill All Every Blank");


                } else if (aBoolean) {
//                    Non Choose Choice
                    MyAlert myAlert = new MyAlert(getActivity());
                    myAlert.myDialog("Non Choose Category", "Plese Choose Category");
                } else {
//                    Choosed Choice
                    uploadUserToServer();

                }


            }//onClick
        });
    }

    private void uploadUserToServer() {
        String tag = "8novV1";
        try {

            MyConstant myConstant = new MyConstant();
            UploadNewUser uploadNewUser = new UploadNewUser(getActivity());
            uploadNewUser.execute(nameString, categoryString,userString,passwordString, myConstant.getUrlPostData());
            String result = uploadNewUser.get();
            Log.d(tag,"Result ==> " + result);
            if (Boolean.parseBoolean(result)) {
//                Success Upload
                getActivity().getSupportFragmentManager().popBackStack();
                Toast.makeText(getActivity(),"Success Upload User", Toast.LENGTH_SHORT).show();

            } else {
//                Error Upload
                Toast.makeText(getActivity(),"Cannot Upload User", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Log.d(tag, "e ==> " + e.toString());
        }

    }

    private void toolbarController() {


        Toolbar toolbar = getView().findViewById(R.id.toolbarRegister);
        ((MainActivity)getActivity()).setSupportActionBar(toolbar);
            ((MainActivity) getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.register));
            ((MainActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
            ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getActivity().getSupportFragmentManager().popBackStack();
                }
            });


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container,false);
        return view;
    }
} //Main Class
