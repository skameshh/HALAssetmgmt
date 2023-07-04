package sg.com.ocs.halassetmgmt;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.Halliburton.mfgsystems.global.Global;
import com.Halliburton.mfgsystems.global.HalPrefManager;

public class HeaderFragment extends Fragment {
    private Button btnMenu;
    private TextView lbl_user;
    private TextView lbl_Name;
    private TextView lbl_title;
    private static String TAG = "HALAsset_HeaderFragment";

    View rootView = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_header, container, false);

        lbl_user = (TextView) rootView.findViewById(R.id.lbl_user);
        lbl_Name = (TextView) rootView.findViewById(R.id.lbl_Name);

        String username = HalPrefManager.readFromPref(this.getContext(), Global.LOGD_USER);
        lbl_user.setText("User : "+username);
        lbl_Name.setText(getString(R.string.disp_name) +" " +getString(R.string.app_version)); //


        btnMenu = (Button)rootView.findViewById(R.id.btnMenu);

        btnMenu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                doClickMnu();
            }
        });

        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_header, container, false);
        return rootView;
    }





    private void gotoLogin(){
        try {
            Intent intent = new Intent(rootView.getContext(), LoginActivity.class);
            startActivity(intent);
        }catch(Exception e){
            Log.v(TAG,"Error in "+e.getLocalizedMessage());
        }
    }
//
    public void gotoSettings(){
        try {
            Intent intent = new Intent(rootView.getContext(), SettingsActivity.class);
            startActivity(intent);
        }catch(Exception e){
            Log.v(TAG,"Error in "+e.getLocalizedMessage());
        }
    }

    public void gotoNetworkStatus(){
        try {
            Intent intent = new Intent(rootView.getContext(), ConnectionActivity.class);
            startActivity(intent);
        }catch(Exception e){
            Log.v(TAG,"Error in "+e.getLocalizedMessage());
        }
    }

    public void doClickMnu() {
        final Context ctx = this.getContext();
        //Creating the instance of PopupMenu
        PopupMenu popup= new PopupMenu(ctx, btnMenu);
        popup.getMenuInflater().inflate(R.menu.menu_hal,popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item){
                try {
                    Toast.makeText(ctx, "You Clicked:" + item.getTitle(), Toast.LENGTH_SHORT).show();
                    if (item.getTitle().equals("Logout")) {
                        gotoLogin();
                    } else if (item.getTitle().equals("Settings")) {
                        gotoSettings();
                    } else if (item.getTitle().equals("NetworkStatus")) {
                        gotoNetworkStatus();
                    }
                }catch(Exception e){
                    Log.v(TAG,"Error in "+e.getLocalizedMessage());
                }
                return true;
            }
        });
        popup.show();//showing popup menu
    }
}
