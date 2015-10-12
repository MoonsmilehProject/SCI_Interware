package mx.interware.moonsmileh.dialogframent;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

/**
 * Created by hsolano on 12/10/15.
 */
public class TimeTabs extends DialogFragment {

        TabHost tabs;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            //return inflater.inflate(R.layout.invite_friend_tabbed_dialog, null);
            View  view=inflater.inflate(R.layout.time_tabs, null);

            // Add tabs
            tabs.findViewById(R.id.tabHost);


            return view;

        }


}
