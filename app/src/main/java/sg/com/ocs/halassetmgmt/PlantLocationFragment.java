package sg.com.ocs.halassetmgmt;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.Halliburton.mfgsystems.global.Global;
import com.Halliburton.mfgsystems.global.HalPrefManager;


public class PlantLocationFragment extends Fragment {
    private static String TAG = "HALAssetFragmentPlantLocation";
    private TextView lblPlant;
    private TextView lblLocation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_plant_location, container, false);

        final View rootView = inflater.inflate(R.layout.fragment_plant_location, container, false);

        try {
            lblPlant = (TextView) rootView.findViewById(R.id.lblPlant);
            String plant = HalPrefManager.readFromPref(this.getContext(), Global.SEL_PLANT);
            String loc = HalPrefManager.readFromPref(this.getContext(), Global.SEL_COST_CNTR_NAME);
            lblPlant.setText(plant);
            Log.v(TAG, "Plant = "+plant);


            lblLocation = (TextView) rootView.findViewById(R.id.lblLocation);
            lblLocation.setText(loc);
            Log.v(TAG, "Location = "+loc);
            // Inflate the layout for this fragment
            // return inflater.inflate(R.layout.fragment_fra_header, container, false);
        }catch (Exception ee){
            ee.printStackTrace();
        }
        return rootView;
    }


}
