package sg.com.ocs.halassetmgmt;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.Halliburton.mfgsystems.global.HalPrefManager;


public class FraHeader extends Fragment {

    private TextView LabelUser;
    private static String TAG = "HALAssetFraHeader";
    public FraHeader() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_fra_header, container, false);
        LabelUser = (TextView) rootView.findViewById(R.id.LabelUser);
        String username = HalPrefManager.readFromPref(this.getContext(), "USER");
        LabelUser.setText("User : "+username);

        Log.v(TAG, "Got User = "+username);
        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_fra_header, container, false);
        return rootView;
    }

}
