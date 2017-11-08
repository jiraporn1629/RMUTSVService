package jirapornmtrmutsv.rmutsvservice.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import jirapornmtrmutsv.rmutsvservice.R;
import jirapornmtrmutsv.rmutsvservice.utility.GetAllData;
import jirapornmtrmutsv.rmutsvservice.utility.MyAlert;
import jirapornmtrmutsv.rmutsvservice.utility.MyConstant;

/**
 * Created by lenovo on 6/11/2560.
 */

public class MainFragment extends Fragment{
    private String userString, passwordString;

    private boolean userABoolean = true; //true ==> User False

//    Manager Work after onCreateView Success
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        Rejister Controller
        rejisterController();

//        Login Controler
        loginControler();


           }


    private void loginControler() {
    Button button = getView().findViewById(R.id.btnLogin);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText userEdiText = getView().findViewById(R.id.edtUser);
                EditText passwordEdiText = getView().findViewById(R.id.edtPassword);
                userString = userEdiText.getText().toString().trim();
                passwordString = passwordEdiText.getText().toString().trim();
                if (userString.equals("") || passwordString.equals("")) {
                    MyAlert myAlert = new MyAlert(getActivity());
                    myAlert.myDialog("Have Space", "Plese Fill All Blank");

                } else {
                    CheckUserAnPass();
                }
            }//Onclink

    });
    } //Main Method

    private void CheckUserAnPass() {
        try {
            MyConstant myConstant = new MyConstant();
            String tag = "8novV1";
            GetAllData getAllData = new GetAllData(getActivity());
            getAllData.execute(myConstant.getUrlGetAllUser());
            String strJSON = getAllData.get();
            Log.d(tag, "JSON ==> " + strJSON);
            String[] strings = new String[]{"id","Name","Category","User","Password"};
            String[] userStrings1 = new String[strings.length];

            JSONArray jsonArray = new JSONArray(strJSON);
            for (int i=0;i<jsonArray.length();i+=1) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (userString.equals(jsonObject.getString("User"))) {
                    userABoolean = false;



                    for (int i1=0;i1<strings.length; i1+=1) {
                        userStrings1[i1] = jsonObject.getString(strings[i1]);
                    }

                }
            }//For

            if (userABoolean) {
                MyAlert myAlert = new MyAlert(getActivity());
                myAlert.myDialog("User False", "No This User in my Database");


            } else if (passwordString.equals(userStrings1[4])) {
                Toast.makeText(getActivity(),"Welcom " + userStrings1[1],Toast.LENGTH_SHORT).show();

            } else {
                MyAlert myAlert = new MyAlert(getActivity());
                myAlert.myDialog("Password Fals", "Plese Try Again Password");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }




    }

    private void rejisterController() {
        TextView textView = getView().findViewById(R.id.txtRegister);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Replace Fragment
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contentFragmentMain, new RejisterFragment())
                        .addToBackStack(null)
                        .commit();


         }
// Onclick
            });

        }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main,container,false);
        return view;
    }
}//Main Class
