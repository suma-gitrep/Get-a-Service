package com.example.getaservice;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class Shared extends Application {
    SharedPreferences preference;
    SharedPreferences.Editor editor;
    Context mContext;

    public Shared() {

    }

    public Shared(Context context) {
        // TODO Auto-generated constructor stub
        mContext = context;

        preference = mContext.getSharedPreferences("GetAService", Context.MODE_PRIVATE);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //MultiDex.install(base);
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
    }

    public String getWorkerModel() {
        return preference.getString("WorkerModel", "");
    }

    public void setWorkerModel(String WorkerModel) {
        editor = preference.edit();
        editor.putString("WorkerModel", WorkerModel);
        editor.commit();
    }

    public String getIsUserLoggedIn() {
        return preference.getString("IsUserLoggedIn", "");
    }

    public void setIsUserLoggedIn(String IsUserLoggedIn) {
        editor = preference.edit();
        editor.putString("IsUserLoggedIn", IsUserLoggedIn);
        editor.commit();
    }
}
