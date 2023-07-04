package com.Halliburton.mfgsystems.global;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Global {
    public static String LOGD_USER = "Logd_User";

    public static String SEL_PLANT = "SEL_PLANT";
    public static String SEL_COST_CNTR_NAME = "SEL_COST_CNTR_NAME";
    public static String SEL_COST_CNTR_CODE = "SEL_COST_CNTR_CODE";

    public static String CURRENT_ASSET = "CURRENT_ASSET";

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static String getCurrentDateTime(){
        return sdf.format(new Date());
    }

    private static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
    public static String getCurrentDateTimeNoSpace(){
        return sdf2.format(new Date());
    }

    private static SimpleDateFormat dt1 = new SimpleDateFormat("MM/dd/yyyy");
    public static String getTodayForUI() {
        return dt1.format(new Date());
    }


    public static void ShowSnackBar(ViewGroup lvparent, Context ctx, String message) {
        Snackbar.make(lvparent, message, Snackbar.LENGTH_INDEFINITE)
                .setAction("CLOSE", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                })
                .setActionTextColor(ctx.getResources().getColor(android.R.color.holo_red_light))
                .show();
    }
}
