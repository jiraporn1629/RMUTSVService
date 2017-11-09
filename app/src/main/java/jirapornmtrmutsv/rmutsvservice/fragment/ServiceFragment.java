package jirapornmtrmutsv.rmutsvservice.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import jirapornmtrmutsv.rmutsvservice.MyServiceActivity;
import jirapornmtrmutsv.rmutsvservice.R;
import jirapornmtrmutsv.rmutsvservice.utility.DeleteData;
import jirapornmtrmutsv.rmutsvservice.utility.GetAllData;
import jirapornmtrmutsv.rmutsvservice.utility.ListViewAdapter;
import jirapornmtrmutsv.rmutsvservice.utility.MyConstant;

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

//        Create ListView
        createListView();


    }

    private void createListView() {
        ListView listView = getView().findViewById(R.id.livUser);
        MyConstant myConstant = new MyConstant();


        try {
            GetAllData getAllData = new GetAllData(getActivity());
            getAllData.execute(myConstant.getUrlGetAllUser());
            String resultJSON = getAllData.get();
            Log.d("9novV1", "JSON ==>  " + resultJSON);
            JSONArray jsonArray = new JSONArray(resultJSON);
            final String[] nameString = new String[jsonArray.length()];
            String[] catString = new String[jsonArray.length()];
            String[] userString = new String[jsonArray.length()];
            String[] passwordString = new String[jsonArray.length()];

            for (int i=0; i<jsonArray.length(); i+=1) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);

                nameString[i] = jsonObject.getString("Name");
                catString[i] = jsonObject.getString("Category");
                userString[i] = jsonObject.getString("User");
                passwordString[i] = jsonObject.getString("Password");

            }//end for

            ListViewAdapter listViewAdapter = new ListViewAdapter(getActivity(),
                    nameString, catString, userString, passwordString);
            listView.setAdapter(listViewAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    confirmDialog(nameString[i]);
                }
            });



        } catch (Exception e) {

            e.printStackTrace();

        }





    }

    private void confirmDialog(final String nameString) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        builder.setIcon(R.drawable.ic_action_alert);
        builder.setTitle("You Choose" + nameString);
        builder.setMessage("Who do You want ? ");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteDataWhere(nameString);

            dialogInterface.dismiss();
            }
        });


    }

    private void deleteDataWhere(String nameString) {
        try {
            MyConstant myConstant = new MyConstant();
            DeleteData deleteData = new DeleteData(getActivity());
            deleteData.execute(nameString, myConstant.getUrlDelete());
            if (Boolean.parseBoolean(deleteData.get())) {
                Toast.makeText(getActivity(), "Delete Success",Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(getActivity(), "Delete Error",Toast.LENGTH_SHORT).show();
            }
                createListView();

        } catch (Exception e) {
            e.printStackTrace();
        }


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
