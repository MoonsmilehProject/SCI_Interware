package mx.interware.moonsmileh.dialogframent;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showAlertDialog();
    }
    private void showAlertDialog() {
        FragmentManager fm = getSupportFragmentManager();
        CustomDialog alertDialog = CustomDialog.newInstance("Captura de horas");
        alertDialog.show(fm, "fragment_alert");
    }
}
