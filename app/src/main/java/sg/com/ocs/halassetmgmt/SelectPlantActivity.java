package sg.com.ocs.halassetmgmt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.Halliburton.mfgsystems.asstmgt.db.CostCenterDao;
import com.Halliburton.mfgsystems.asstmgt.db.DBUtils;
import com.Halliburton.mfgsystems.global.Global;
import com.Halliburton.mfgsystems.global.HalPrefManager;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;


public class SelectPlantActivity extends AppCompatActivity {
    private static String TAG = "HALAssetSelectPlant";
    private Spinner spinnerPlant;
    private Spinner spinnerLocation;
    private TextView lbl_title;
    private ConstraintLayout lvparent;
    private Button btnPlantContinue;
    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_plant);

        ctx = this;

        lvparent = (ConstraintLayout)findViewById(R.id.lvparent);
        btnPlantContinue = (Button)findViewById(R.id.btnPlantContinue);
        btnPlantContinue.setVisibility(View.GONE);

        spinnerPlant = (Spinner)findViewById(R.id.spinnerPlant);
        spinnerLocation = (Spinner)findViewById(R.id.spinnerLocation);

        lbl_title = (TextView)findViewById(R.id.lbl_title);
        lbl_title.setText("Select Plant & Location ");

       Global.ShowSnackBar(lvparent, this, "Load Plant & Location ");
        //loadPlant();
        //loadLocation();

        spinnerPlant.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                String plnt = (String) spinnerPlant.getSelectedItem();
                spinnerLocation.setAdapter(null);
                Log.v(TAG," selected Plant = "+ plnt);
                FetchLocationForPlant pnt = new FetchLocationForPlant(plnt);
                pnt.execute("");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });



        FetchPlant fl = new FetchPlant();
        fl.execute("");
    }



/*    private void loadFraHeader(){
        try {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            FraHeader fraHeader = new FraHeader();
            fragmentTransaction.add(R.id.fragment, fraHeader);
            fragmentTransaction.commit();
        }catch (Exception ee){
            Log.v(TAG, "Error = "+ee.getLocalizedMessage());
        }
    }*/




    public void doContinue(View view){
        try {
            String sel_plant = (String) spinnerPlant.getSelectedItem();
            String sel_location = (String)spinnerLocation.getSelectedItem();

            String sel_cost_cntr_code = DBUtils.getCostCenterCDForCCName(sel_location, sel_plant);

            HalPrefManager.putInPref(this, Global.SEL_PLANT, sel_plant);
            HalPrefManager.putInPref(this, Global.SEL_COST_CNTR_NAME, sel_location);
            if(sel_cost_cntr_code!=null ) {
                HalPrefManager.putInPref(this, Global.SEL_COST_CNTR_CODE, sel_cost_cntr_code);
            }
            Log.v(TAG," Plant = "+sel_plant +", location="+sel_location +", sel_cost_cntr_code="+sel_cost_cntr_code);

        }catch(Exception e){
            Log.v(TAG,"Error in "+e.getLocalizedMessage());
        }

        try {
            Intent intent = new Intent(SelectPlantActivity.this, AssetTagVisibilityActivity.class);
            //intent.putExtra("user_name", txtUserName.getText().toString());
            startActivity(intent);
        }catch(Exception e){
            Log.v(TAG,"Error in "+e.getLocalizedMessage());
        }
    }





    private class FetchPlant extends AsyncTask<String, Void, String> {

        ArrayAdapter<String> plant_adapter = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... strings) {

            loadPlant();

            if(plant_adapter!=null ) {
                return "Success";
            }else{
                return "failed";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if(result.equals("Success")){
                Global.ShowSnackBar(lvparent, ctx,"Plant and Location loaded successfully");
                spinnerPlant.setAdapter(plant_adapter);

                btnPlantContinue.setVisibility(View.VISIBLE);
            }else{
                Global.ShowSnackBar(lvparent, ctx,"failed to load ");
            }
        }

        private void loadPlant(){
            try {
                ArrayList plants_list = DBUtils.getAllPlants();
                if(plants_list.size()>0) {
                    Log.v(TAG, "Got Plants = " + plants_list.size());
                    plant_adapter = new ArrayAdapter<String>(
                            SelectPlantActivity.this,
                            android.R.layout.simple_spinner_item,
                            plants_list
                    );
                }

            }catch (Exception ee){
                Log.v(TAG, "Error in getting plants = "+ee.getLocalizedMessage());
            }
        }
    }


    private class FetchLocationForPlant extends AsyncTask<String, Void, String> {
        ArrayAdapter<String> loc_adapter = null;

        private String plant = "";
        public FetchLocationForPlant(String plant){
            this.plant = plant;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            loadLocation();
            if( loc_adapter!=null) {
                return "Success";
            }else{
                return "failed";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if(result.equals("Success")){
                Global.ShowSnackBar(lvparent, ctx,"Location loaded successfully");
                spinnerLocation.setAdapter(loc_adapter);
                btnPlantContinue.setVisibility(View.VISIBLE);
            }else{
                Global.ShowSnackBar(lvparent, ctx,"failed to load ");
            }
        }

        private void loadLocation(){
            try {
                ArrayList cc_list =  DBUtils.getAllCostCenterSP(plant);;//DBUtils.getAllCostCenter();
                if(cc_list.size()>0) {
                    ArrayList ccnameList = new ArrayList();
                    for (int x = 0; x < cc_list.size(); x++) {
                        CostCenterDao dao = (CostCenterDao) cc_list.get(x);
                        if(dao.getCostCenterName()!=null)
                            ccnameList.add(dao.getCostCenterName());
                    }

                    Log.v(TAG, "Got location = " + ccnameList.size());
                    loc_adapter = new ArrayAdapter<String>(
                            SelectPlantActivity.this,
                            android.R.layout.simple_spinner_item,
                            ccnameList
                    );
                }

            }catch (Exception ee){
                Log.v(TAG, "Error in getting plants = "+ee.getLocalizedMessage());
            }
        }
    }

}
