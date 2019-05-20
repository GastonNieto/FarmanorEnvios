package com.genv3.gendelivery.View.Activtys;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.genv3.gendelivery.Objects.EntregasTomadas;
import com.genv3.gendelivery.R;
import com.genv3.gendelivery.util.FormatDate;
import com.google.android.material.textfield.TextInputEditText;

public class EntregaDetalleView extends AppCompatActivity {
    private TextInputEditText etgdfechaent, sclnom, scldir, scltel, etgdidcomprobante, etgdreceptor,
            etgdpedido, etgddir, etgdtel, etgdfecha, etgdimpentdet;
    private EntregasTomadas entregasTomadas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrega_detalle_view);
        ini();
        Bundle bundle = getIntent().getBundleExtra("DetalleEntrega");
        entregasTomadas = (EntregasTomadas) bundle.getSerializable("objET");
        setDatos(entregasTomadas);
    }

    private void setDatos(EntregasTomadas entregasTomadas) {
        etgdfechaent.setText(FormatDate.formateador(entregasTomadas.getEtgtFechaEntrega()));
        sclnom.setText(entregasTomadas.getEntregasDisponibles().get(0).getSucursal().get(0).getSclNom());
        scldir.setText(entregasTomadas.getEntregasDisponibles().get(0).getSucursal().get(0).getSclDir());
        scltel.setText(entregasTomadas.getEntregasDisponibles().get(0).getSucursal().get(0).getSclTel().toString());
        etgdidcomprobante.setText(entregasTomadas.getEntregasDisponibles().get(0).getEtgdIdComprobante());
        etgddir.setText(entregasTomadas.getEntregasDisponibles().get(0).getEtgdDir());
        etgdreceptor.setText(entregasTomadas.getEntregasDisponibles().get(0).getEtgdReceptor());
        etgdpedido.setText(entregasTomadas.getEntregasDisponibles().get(0).getEtgdPedido());
        etgdtel.setText(entregasTomadas.getEntregasDisponibles().get(0).getEtgdTel().toString());
        etgdfecha.setText(FormatDate.formateador(entregasTomadas.getEntregasDisponibles().get(0).getEtgdFecha()));
        etgdimpentdet.setText("$"+entregasTomadas.getEtgtImporte().toString());
    }

    void ini() {
        etgdfechaent = findViewById(R.id.etgdFechaEntDet);
        sclnom = findViewById(R.id.sclNomDet);
        scldir = findViewById(R.id.sclDirDet);
        scltel = findViewById(R.id.sclTelDet);
        etgdidcomprobante = findViewById(R.id.etgdIdComprobanteDet);
        etgddir = findViewById(R.id.etgdDirDet);
        etgdreceptor = findViewById(R.id.etgdReceptorDet);
        etgdpedido = findViewById(R.id.etgdPedidoDet);
        etgdtel = findViewById(R.id.etgdTelDet);
        etgdfecha = findViewById(R.id.etgdFechaDet);
        etgdimpentdet = findViewById(R.id.etgdImpEntDet);
    }
}
