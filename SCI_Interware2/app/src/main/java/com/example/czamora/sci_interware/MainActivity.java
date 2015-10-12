package com.example.czamora.sci_interware;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends BaseVolleyFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView logotipo = (ImageView) findViewById(R.id.logotype);
        RelativeLayout formLogin = (RelativeLayout) findViewById(R.id.loginForm);
        animLogo(logotipo);
        animForm(formLogin);

        Button buttonLogin = (Button)findViewById(R.id.buttonLogin);
        final EditText userText, passwordText;
        userText = (EditText)findViewById(R.id.txt_user);
        passwordText = (EditText)findViewById(R.id.txt_contrashena);
        final ImageView lockIcon = (ImageView)findViewById(R.id.password_icon);


        passwordText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b)
                    lockIcon.setImageResource(R.drawable.ic_action_lock_open);
                else
                    lockIcon.setImageResource(R.drawable.ic_action_https);
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /****** VOLLEY *******/

                final String user = userText.getText().toString();
                String password = passwordText.getText().toString();

                String url = "http://interware.com.mx/NuevoSCI/rest/Mobile/login";
                Map<String, String> jsonParams = new HashMap<>();
                jsonParams.put("login", user);
                jsonParams.put("password", password);

                JsonObjectRequest postRequest = new JsonObjectRequest(
                        Request.Method.POST,
                        url,
                        new JSONObject(jsonParams),

                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                //verificationSuccess(response);
                                //label.setText(response.toString());
                                try {
                                    JSONObject jObj = new JSONObject(response.toString());
                                    String id = jObj.getString("empleadosfid");
                                    String nombre = jObj.getString("nombre");
                                    String correo = jObj.getString("email");
                                    if(id.equals(user) && !id.equals("")){
                                        Toast.makeText(MainActivity.this,id,Toast.LENGTH_SHORT).show();
                                        Intent ventana = new Intent(MainActivity.this, Navigation.class);
                                        Bundle mensaje = new Bundle();
                                        mensaje.putString("nombre", nombre);
                                        mensaje.putString("correo", correo);
                                        ventana.putExtras(mensaje);
                                        startActivity(ventana);
                                    }else{
                                        Toast.makeText(MainActivity.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
                                    }
                                    //Toast.makeText(MainActivity.this,id,Toast.LENGTH_SHORT).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                //verificationFailed(error);
                            }
                        }) {

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<>();
                        //headers.put("Content-Type", "application/json");
                        headers.put("Content-Type", "application/json; charset=utf-8");
                        //headers.put("User-agent", "My useragent");
                        return headers;
                    }
                };

                Volley.newRequestQueue(MainActivity.this).add(postRequest);


                /************************/


                /*if ((userText.getText().toString().trim().equals("user")) && (passwordText.getText().toString().trim().equals("user"))) {
                    Toast.makeText(MainActivity.this, "Login success", Toast.LENGTH_SHORT).show();
                    Intent ventana = new Intent(MainActivity.this, Navigation.class);
                    startActivity(ventana);
                } else {
                    Toast.makeText(MainActivity.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
                }*/
            }
        });
    }

    public void animLogo(final ImageView logo){
        ScaleAnimation scale = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        AlphaAnimation alpha = new AlphaAnimation(0.0f, 1.0f);
        Animation fadeIn = new AlphaAnimation(0, 1);
        final Animation moveUp = AnimationUtils.loadAnimation(this, R.anim.scale);
        AnimationSet set = new AnimationSet(true);
        set.addAnimation(scale);
        set.addAnimation(fadeIn);
        set.addAnimation(alpha);
        set.setDuration(2000);
        moveUp.setInterpolator((new AccelerateDecelerateInterpolator()));
        moveUp.setFillAfter(true);
        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                logo.startAnimation(moveUp);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        logo.startAnimation(set);
    }
    public void animForm(final RelativeLayout form){
        Animation fadeIn = new AlphaAnimation(0, 1);
        AnimationSet setAnim = new AnimationSet(true);
        setAnim.addAnimation(fadeIn);
        setAnim.setStartOffset(3000);
        setAnim.setDuration(2000);
        form.startAnimation(setAnim);
    }

}
