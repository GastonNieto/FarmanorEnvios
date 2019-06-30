package com.genv3.gendelivery.View.Activtys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.genv3.gendelivery.Interface.ITomarPedidos;
import com.genv3.gendelivery.Objects.DetalleComprobante;
import com.genv3.gendelivery.Objects.EntregasDisponibles;
import com.genv3.gendelivery.Presenter.TomarPedidosPresenter;
import com.genv3.gendelivery.R;
import com.genv3.gendelivery.util.FormatDate;
import com.genv3.gendelivery.util.Preferens;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TomarPedidosView extends AppCompatActivity implements View.OnClickListener, ITomarPedidos.View {
    private TextInputEditText scldir, scltel, etgdidcomprobante, etgdreceptor, etgdpedido, etgddir, etgdtel, etgdfecha;
    private EntregasDisponibles entregasDisponibles;
    private BroadcastReceiver receiver;
    public static final String UPDATE = "UPDATE_PEDIDOS";
    private TextInputEditText sclnom;
    private MaterialButton btntomarpedido;
    private Context context;
    private Long idCadete;
    private BigInteger idPedido;
    private ITomarPedidos.Presener presener;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tomar_pedidos_view);
        context = this;
        presener = new TomarPedidosPresenter(this);
        ini();
        Bundle bundle = getIntent().getBundleExtra("suerte");
        entregasDisponibles = (EntregasDisponibles) bundle.getSerializable("objED");
        setDatos(entregasDisponibles);
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //  Toast.makeText(context, "funciona", Toast.LENGTH_LONG).show();
                //getsPotst();
            }
        };

        btntomarpedido.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter(UPDATE));

    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);

    }

    void ini() {
        sclnom = findViewById(R.id.sclNom);
        scldir = findViewById(R.id.sclDir);
        scltel = findViewById(R.id.sclTel);
        etgdidcomprobante = findViewById(R.id.etgdIdComprobante);
        etgdreceptor = findViewById(R.id.etgdReceptor);
        etgdpedido = findViewById(R.id.etgdPedido);
        etgddir = findViewById(R.id.etgdDir);
        etgdtel = findViewById(R.id.etgdTel);
        etgdfecha = findViewById(R.id.etgdFecha);
        btntomarpedido = findViewById(R.id.btnTomarPedido);
    }

    void setDatos(EntregasDisponibles datos) {
        String pedidos = "";
        idCadete = Preferens.getInteger(context, Preferens.getKeyGuardia());
        idPedido = datos.getEtgdId();
        sclnom.setText(datos.getSucursal().get(0).getSclNom());
        scldir.setText(datos.getSucursal().get(0).getSclDir());
        scltel.setText(datos.getSucursal().get(0).getSclTel().toString());
        etgdidcomprobante.setText(datos.getEtgdIdComprobante());
        etgdreceptor.setText(datos.getEtgdReceptor());
        for (DetalleComprobante aux : datos.getEtgdPedido()) {
            pedidos = pedidos + aux.getDes_articulo() + " *Cant. " + aux.getCantidad() + "\n" + "--------" + "\n";
        }
        etgdpedido.setText(pedidos);
        etgddir.setText(datos.getEtgdDir());
        etgdtel.setText(datos.getEtgdTel().toString());
        etgdfecha.setText(FormatDate.formateador(datos.getEtgdFecha()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnTomarPedido:
                final Dialog dialog4 = new Dialog(context);
                dialog4.setContentView(R.layout.dialog_confirmar);
                TextView tvtitulorechazo = dialog4.findViewById(R.id.tvdialogConfirTitulo);
                TextView tvdescripcionrechazo = dialog4.findViewById(R.id.tvdialogConfirDescripcion);
                tvtitulorechazo.setText("Confirmar");
                tvdescripcionrechazo.setText("¿Desea tomar este pedido?");
                MaterialButton btnsirechazo = dialog4.findViewById(R.id.btnDialogConfirSi);
                MaterialButton btnnorechazo = dialog4.findViewById(R.id.btnDialogConfirNo);
                btnsirechazo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog4.dismiss();
                        DialogCargando();
                        presener.SolicitarPedidoLibre(idCadete, idPedido, "Tomado");
                    }
                });
                btnnorechazo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog4.dismiss();
                    }
                });
                dialog4.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog4.setCancelable(false);
                dialog4.show();
            /*    DialogCargando();
                //Preferens.setEntregasDisponibles(context,Preferens.getKeyObjeto(),entregasDisponibles);
                presener.SolicitarPedidoLibre(idCadete, idPedido, "Tomado");*/
                break;
        }

    }


    @Override
    public void onTomado(int a) {
        dialog.dismiss();
        DialogCartelon("Correcto", "Tomaste el pedido", a);
    }

    @Override
    public void onOcupado(int a) {
        dialog.dismiss();
        DialogCartelon("Alerta", "Este pedido fue tomado", a);
    }

    @Override
    public void onError(int a) {
        dialog.dismiss();
        DialogCartelon("Error", "Sin servicio", a);
    }

    @Override
    public void onPedidoPendiente(int a) {
        dialog.dismiss();
        DialogCartelon("Alerta", "Tienes un pedido pendiente", a);

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
                setResult(RESULT_OK);
                finish();

            }
        });
        dialog2.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog2.setCancelable(false);
        dialog2.show();
    }
}
