package mx.interware.moonsmileh.dialogframent;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.TabHost;
import android.widget.TimePicker;

import com.weiwangcn.betterspinner.library.BetterSpinner;

/**
 * Created by hsolano on 12/10/15.
 */
public class CustomDialog extends DialogFragment {
    BetterSpinner tipoCaptura, nombreProyecto, faseProyecto;
    TimePicker timePicker;
    TabHost tabs;

    public CustomDialog() {
    }

    public static CustomDialog newInstance(String title) {
        CustomDialog frag = new CustomDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View  view=inflater.inflate(R.layout.time_tabs, null);
        tabs=(TabHost)view.findViewById(R.id.tabHost);


        tabs.setup();

        TabHost.TabSpec tabpage1 = tabs.newTabSpec("one");
        tabpage1.setContent(R.id.beginTime);
        tabpage1.setIndicator("Inicio");

        TabHost.TabSpec tabpage2 = tabs.newTabSpec("two");
        tabpage2.setContent(R.id.endTime);
        tabpage2.setIndicator("Termino");

        tabs.addTab(tabpage1);
        tabs.addTab(tabpage2);

        String title = getArguments().getString("title");
        getDialog().setTitle(title);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        return view;
    }
/*
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tipoCaptura = (BetterSpinner)view.findViewById(R.id.tipo_captura);
        nombreProyecto = (BetterSpinner)view.findViewById(R.id.nombre_proyecto);
        faseProyecto = (BetterSpinner)view.findViewById(R.id.fase_proyecto);
        //  = new TimePicker(getActivity().getApplicationContext(),,android.R.style.Theme_Holo_Light_Dialog_NoActionBar)
        String[] capturas = {"Ayer","Hoy"};
        String[] proyectos = {"Afore CUO PR#105","Metlife PR#201", "Liverpool PR#506"};
        String[] fases = {"Ejecución","Construcción","Pruebas","Terminado"};

        ArrayAdapter<String> adapterCaptura = new ArrayAdapter<>(getActivity().getApplicationContext(), R.layout.custom_spinner, capturas);
        ArrayAdapter<String> adapterNombre = new ArrayAdapter<>(getActivity().getApplicationContext(), R.layout.custom_spinner, proyectos);
        ArrayAdapter<String> adapterFase = new ArrayAdapter<>(getActivity().getApplicationContext(), R.layout.custom_spinner, fases);

        tipoCaptura.setAdapter(adapterCaptura);
        nombreProyecto.setAdapter(adapterNombre);
        faseProyecto.setAdapter(adapterFase);

        String title = getArguments().getString("title");
        getDialog().setTitle(title);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }*/
}
