package com.genv3.gendelivery.View.Activtys;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.genv3.gendelivery.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FiltroFechaActivity extends AppCompatActivity implements View.OnClickListener {
    private TextInputEditText tffechaunica, tffechainicial, tffechafinal;
    private MaterialButton ibtnBuscarFechaUnica, ibtnBuscarEntreFecha;
    Context context;
    private DatePickerDialog.OnDateSetListener dateSetListenerFecha, dateSetListenerFechaInicial, dateSetListenerFechaFinal;
    private String inicial = null;
    private String ffinal = null;
    private String fecha = null;
    private String inicialMostrar = null;
    private String finalMostrar = null;
    private String fechaMostrar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtro_fecha);
        tffechaunica = findViewById(R.id.tfFechaUnica);
        tffechainicial = findViewById(R.id.tfFechaInicial);
        tffechafinal = findViewById(R.id.tfFechaFinal);
        ibtnBuscarFechaUnica = findViewById(R.id.ibtnBuscarFechaUnica);
        ibtnBuscarEntreFecha = findViewById(R.id.ibtnBuscarEntrefechas);
        context = this;
        tffechaunica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        context,
                        dateSetListenerFecha,
                        year, month, day);

                dialog.show();
            }
        });
        dateSetListenerFecha = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String mes = String.valueOf(month);
                String dia = String.valueOf(day);
                if (month < 10) {
                    mes = "0" + month;
                }
                if (day < 10) {
                    dia = "0" + day;
                }
                fecha = year + "-" + mes + "-" + dia;
                fechaMostrar = dia + "-" + mes + "-" + year;
                tffechaunica.setText(fechaMostrar);

            }
        };

        tffechainicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        context,
                        dateSetListenerFechaInicial,
                        year, month, day);

                dialog.show();
            }
        });

        dateSetListenerFechaInicial = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String mes = String.valueOf(month);
                String dia = String.valueOf(dayOfMonth);
                if (month < 10) {
                    mes = "0" + month;
                }
                if (dayOfMonth < 10) {
                    dia = "0" + dayOfMonth;
                }
                inicial = year + "-" + mes + "-" + dia;
                inicialMostrar = dia + "-" + mes + "-" + year;
                tffechainicial.setText(inicialMostrar);
            }
        };
        tffechafinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        context,
                        dateSetListenerFechaFinal,
                        year, month, day);

                dialog.show();
            }
        });

        dateSetListenerFechaFinal = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String mes = String.valueOf(month);
                String dia = String.valueOf(dayOfMonth);
                if (month < 10) {
                    mes = "0" + month;
                }
                if (dayOfMonth < 10) {
                    dia = "0" + dayOfMonth;
                }
                ffinal = year + "-" + mes + "-" + dia;
                finalMostrar = dia + "-" + mes + "-" + year;
                tffechafinal.setText(finalMostrar);
            }
        };

        ibtnBuscarFechaUnica.setOnClickListener(this);
        ibtnBuscarEntreFecha.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibtnBuscarFechaUnica:
                if (tffechaunica.getText().toString().isEmpty()) {
                    DialogCartelon("Alerta", "Seleccione una fecha", R.drawable.ic_twotone_alerta);
                } else {
                    Intent intent = new Intent();
                    intent.putExtra("unicafecha",fecha);
                    setResult(Activity.RESULT_OK,intent);
                    finish();
                }
                break;
            case R.id.ibtnBuscarEntrefechas:
                if (tffechainicial.getText().toString().isEmpty() || tffechafinal.getText().toString().isEmpty()) {
                    DialogCartelon("Alerta", "Seleccione dos fechas", R.drawable.ic_twotone_alerta);

                } else {
                    String incio = inicial;
                    String Final = ffinal;
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        Date a = sdf.parse(incio);
                        Date b = sdf.parse(Final);
                        if (tffechainicial.getText().toString().isEmpty() || tffechafinal.getText().toString().isEmpty()) {
                            DialogCartelon("Alerta", "Seleccione dos fechas", R.drawable.ic_twotone_alerta);

                        }
                        if (a.equals(b)) {
                            DialogCartelon("Alerta", "Fechas iguales", R.drawable.ic_twotone_alerta);
                        } else if (b.before(a)) {
                            DialogCartelon("Alerta", "Fecha final anterior a la Inicial", R.drawable.ic_twotone_alerta);

                        } else {
                        /*    Intent returnIntent = new Intent();
                            returnIntent.putExtra("desde", desde);
                            returnIntent.putExtra("hasta", hasta);
                            setResult(Activity.RESULT_OK, returnIntent);
                            finish();*/
                            Intent intent = new Intent();
                            intent.putExtra("inicial",inicial);
                            intent.putExtra("ffinal",ffinal);
                            setResult(Activity.RESULT_OK,intent);
                            finish();
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
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
}
