package com.example.czamora.sci_interware;

/**
 * Created by czamora on 8/10/15.
 */
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;

/**
 * Created by czamora on 6/10/15.
 */
public class BaseVolleyFragment extends Activity{

    private VolleyS volley;
    protected RequestQueue fRequestQueue;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        volley = VolleyS.getInstance(BaseVolleyFragment.this);
        fRequestQueue = volley.getRequestQueue();
    }

    public void addToQueue(Request request) {
        if (request != null) {
            request.setTag(this);
            if (fRequestQueue == null)
                fRequestQueue = volley.getRequestQueue();
            request.setRetryPolicy(new DefaultRetryPolicy(
                    60000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            ));
            onPreStartConnection();
            fRequestQueue.add(request);
        }
    }

    public void onPreStartConnection() {
        this.setProgressBarIndeterminateVisibility(true);
    }

    public void onConnectionFinished() {
        this.setProgressBarIndeterminateVisibility(false);
    }

    public void onConnectionFailed(String error) {
        this.setProgressBarIndeterminateVisibility(false);
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }
}
