package com.genv3.gendelivery.View.Activtys;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.genv3.gendelivery.Interface.Ilogin;
import com.genv3.gendelivery.Objects.Cadetes;
import com.genv3.gendelivery.Objects.Posts;
import com.genv3.gendelivery.Presenter.LoginPresenter;
import com.genv3.gendelivery.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class LoginView extends AppCompatActivity implements View.OnClickListener, Ilogin.View {


    public static final String UPDATE = "UPDATE_PEDIDOS";
    private BroadcastReceiver receiver;
    private Dialog dialog;
    TextInputEditText etpass, etuser;
    MaterialButton btnlogin;
    Context context;
    private Ilogin.presenter presenter;

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseMessaging.getInstance().subscribeToTopic("cadete")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = getString(R.string.msg_subscribed);
                        if (!task.isSuccessful()) {
                            msg = getString(R.string.msg_subscribe_failed);
                        }
                        Log.d("hola", msg);
                    }
                });
        context = this;
        validaPermisos();
        presenter = new LoginPresenter(this);
        etuser = findViewById(R.id.etUser);
        etpass = findViewById(R.id.etPass);
        btnlogin = findViewById(R.id.btnLogin);
        btnlogin.setOnClickListener(this);


    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter(UPDATE));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btnLogin:

                if (etuser.getText().toString().trim().isEmpty() && etpass.getText().toString().trim().isEmpty()) {
                    DialogCartelon("Alerta", "Ingrese su usuario y contraseña", R.drawable.ic_twotone_alerta);

                } else if (etuser.getText().toString().trim().isEmpty()) {
                    DialogCartelon("Alerta", "Ingrese su usuario", R.drawable.ic_twotone_alerta);
                } else if (etpass.getText().toString().trim().isEmpty()) {
                    DialogCartelon("Alerta", "Ingrese su contraseña", R.drawable.ic_twotone_alerta);

                } else {
                    DialogCargando();
                    String user = etuser.getText().toString().trim();
                    String pass = etpass.getText().toString().trim();

                    presenter.loginPresenter(user, pass, context);
                }

        }
    }

    @Override
    public void loginView(List<Cadetes> c) {
        //textView.setText("GOL");
        dialog.dismiss();
        Intent intent = new Intent(this, BaseView.class);
        startActivity(intent);
    }

    @Override
    public void OnErrorCharge(int a) {
        dialog.dismiss();
        DialogCartelon("Error", "Sin servicio", a);

    }

    @Override
    public void OnSucessCharge() {

    }

    @Override
    public void onDatosIncorrectos(int a) {
        dialog.dismiss();
        DialogCartelon("Alerta", "Usuario y/o Contraseña incorrectos", a);
    }

    @Override
    public void onUserBlock(int a) {
        dialog.dismiss();
        DialogCartelon("Alerta", "Usuario Bloqueado", a);
    }

    public void DialogCargando() {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_progress);
        MaterialButton btnaceptar = dialog.findViewById(R.id.btnDialogAceptar);
        btnaceptar.setVisibility(View.GONE);
        btnaceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCancelable(false);
        dialog.show();
    }

    public void DialogCartelon(String titulo, String descripcion, int imagen) {
        final Dialog dialog2 = new Dialog(context);
        dialog2.setContentView(R.layout.dialog_progress);
        TextView tvtitulo = dialog2.findViewById(R.id.tvdialogTitulo);
        TextView tvdescripcion = dialog2.findViewById(R.id.tvdialogDescripcion);
        ImageView ivdialog = dialog2.findViewById(R.id.ivDialog);
        ProgressBar pbdialog = dialog2.findViewById(R.id.pbDialog);
        pbdialog.setVisibility(View.GONE);
        ivdialog.setImageResource(imagen);
        ivdialog.setVisibility(View.VISIBLE);
        tvtitulo.setText(titulo);
        tvdescripcion.setText(descripcion);
        MaterialButton btnaceptar = dialog2.findViewById(R.id.btnDialogAceptar);
        btnaceptar.setVisibility(View.VISIBLE);
        btnaceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog2.dismiss();

            }
        });
        dialog2.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog2.setCancelable(false);
        dialog2.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    DialogoPermiso("Alerta", "Debe permitir el permiso de Ubicación", R.drawable.ic_twotone_alerta);
                }
            }
            break;


        }

    }

    private boolean validaPermisos() {
//si buld.version.sdk_int es menor a build.VERSION_CODES.M
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;

        }//en caso de que sea falso pregunto si los permisos estan activos
        //checkeo si los permisos estan aceptados
        if ((checkSelfPermission(ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) && (checkSelfPermission(ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
            return true;
        }
        //en caso de que la version no sea menor y los permisos no se hayan dado debe solicitar los permisos
        if ((shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) || (shouldShowRequestPermissionRationale(ACCESS_COARSE_LOCATION))) {
            DialogoPermiso("Alerta", "Debe permitir el permiso de Ubicación", R.drawable.ic_twotone_alerta);

        }//solicita los permisos
        else {

            requestPermissions(new String[]{ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION}, 1);

        }
        return false;

    }

    public void DialogoPermiso(String titulo, String descripcion, int imagen) {
        final Dialog dialog2 = new Dialog(context);
        dialog2.setContentView(R.layout.dialog_progress);
        TextView tvtitulo = dialog2.findViewById(R.id.tvdialogTitulo);
        TextView tvdescripcion = dialog2.findViewById(R.id.tvdialogDescripcion);
        ImageView ivdialog = dialog2.findViewById(R.id.ivDialog);
        ProgressBar pbdialog = dialog2.findViewById(R.id.pbDialog);
        pbdialog.setVisibility(View.GONE);
        ivdialog.setImageResource(imagen);
        ivdialog.setVisibility(View.VISIBLE);
        tvtitulo.setText(titulo);
        tvdescripcion.setText(descripcion);
        MaterialButton btnaceptar = dialog2.findViewById(R.id.btnDialogAceptar);
        btnaceptar.setVisibility(View.VISIBLE);
        btnaceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION}, 1);
                }
            }


        });
        dialog2.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog2.setCancelable(false);
        dialog2.show();
    }
}
