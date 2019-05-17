package com.genv3.gendelivery.View.Fragments;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.genv3.gendelivery.Interface.IEntrega;
import com.genv3.gendelivery.Objects.EntregasTomadas;
import com.genv3.gendelivery.Presenter.EntregaPresenter;
import com.genv3.gendelivery.R;
import com.genv3.gendelivery.util.Preferens;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.math.BigInteger;
import java.util.ArrayList;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EntregaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EntregaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EntregaFragment extends Fragment implements IEntrega.View, View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextInputEditText tfscldir, tfsclnom, tfscltel,
            tfetgdidcomprobante, tfetgdreceptor, tfetgdpedido, tfetgddir, tfetgdtel, tfetgdfecha;
    private MaterialButton btnentrega, btnrechazar;
    private Context context;
    private Long idCadete;
    private BigInteger etgtId;
    private IEntrega.Presenter presenter;
    private LinearLayout layout;
    private OnFragmentInteractionListener mListener;
    private Dialog dialog;
    private String Latitud, Longitud;
    private LocationListener locationListener;
    private LocationManager locationManager;

    public EntregaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EntregaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EntregaFragment newInstance(String param1, String param2) {
        EntregaFragment fragment = new EntregaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_entrega, container, false);
        context = getContext();
        presenter = new EntregaPresenter(this);
        idCadete = Preferens.getInteger(context, Preferens.getKeyGuardia());
        layout = view.findViewById(R.id.lySinEntregas);
        layout.setVisibility(View.GONE);
        tfsclnom = view.findViewById(R.id.tfsclNom);
        tfscldir = view.findViewById(R.id.tfsclDir);
        tfscltel = view.findViewById(R.id.tfsclTel);
        tfetgdidcomprobante = view.findViewById(R.id.tfetgdIdComprobante);
        tfetgdreceptor = view.findViewById(R.id.tfetgdReceptor);
        tfetgdpedido = view.findViewById(R.id.tfetgdPedido);
        tfetgddir = view.findViewById(R.id.tfetgdDir);
        tfetgdtel = view.findViewById(R.id.tfetgdTel);
        tfetgdfecha = view.findViewById(R.id.tfetgdFecha);
        btnentrega = view.findViewById(R.id.btnEntregar);
        btnrechazar = view.findViewById(R.id.btnRechazar);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                DialogCartel();
                presenter.SolicitarEntrega(idCadete);

            }
        }, 400);

        btnentrega.setOnClickListener(this);
        btnrechazar.setOnClickListener(this);


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void CargarEntrega(ArrayList<EntregasTomadas> tomadas) {
        layout.setVisibility(View.VISIBLE);
        etgtId = tomadas.get(0).getEtgtId();
        tfsclnom.setText(tomadas.get(0).getEntregasDisponibles().get(0).getSucursal().get(0).getSclNom());
        tfscldir.setText(tomadas.get(0).getEntregasDisponibles().get(0).getSucursal().get(0).getSclDir());
        tfscltel.setText(tomadas.get(0).getEntregasDisponibles().get(0).getSucursal().get(0).getSclTel().toString());
        tfetgdidcomprobante.setText(tomadas.get(0).getEntregasDisponibles().get(0).getEtgdIdComprobante());
        tfetgdreceptor.setText(tomadas.get(0).getEntregasDisponibles().get(0).getEtgdReceptor());
        tfetgdpedido.setText(tomadas.get(0).getEntregasDisponibles().get(0).getEtgdPedido());
        tfetgddir.setText(tomadas.get(0).getEntregasDisponibles().get(0).getEtgdDir());
        tfetgdtel.setText(tomadas.get(0).getEntregasDisponibles().get(0).getEtgdTel().toString());
        tfetgdfecha.setText(tomadas.get(0).getEntregasDisponibles().get(0).getEtgdFecha());
    }

    @Override
    public void OnErrorCharge(int a) {
        dialog.dismiss();
        DialogCartelon("Error", "Sin servicio", a);
    }

    @Override
    public void OnSucessCharge() {
        dialog.dismiss();
      //  Toast.makeText(getContext(), "SALGO POR AQUI", Toast.LENGTH_LONG).show();
    }

    @Override
    public void OnClear(int a) {
        dialog.dismiss();
        layout.setVisibility(View.GONE);
        DialogCartelon("Alerta", "No tienes entregas", a);
    }

    @Override
    public void OnEntrega(int a) {
        dialog.dismiss();
        DialogCartelonEntregaRechazo("Buen Trabajo", "La Entrega fue registrada", a);


    }

    @Override
    public void OnEntregaError(int a) {

    }

    @Override
    public void OnRechazo(int a) {
        dialog.dismiss();
        DialogCartelonEntregaRechazo("Alerta", "La Entrega fue rechazada", a);

    }

    public void DialogConfirmar() {
        final Dialog dialog3 = new Dialog(context);
        dialog3.setContentView(R.layout.dialog_confirmar);
        TextView tvtitulo = dialog3.findViewById(R.id.tvdialogConfirTitulo);
        TextView tvdescripcion = dialog3.findViewById(R.id.tvdialogConfirDescripcion);
        tvtitulo.setText("Confirmar");
        tvdescripcion.setText("¿Desea confirmar entrega?");
        MaterialButton btnsi = dialog3.findViewById(R.id.btnDialogConfirSi);
        MaterialButton btnno = dialog3.findViewById(R.id.btnDialogConfirNo);
        btnsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog3.dismiss();
            }
        });
        btnno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog3.dismiss();
            }
        });
        dialog3.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog3.setCancelable(false);
        dialog3.show();
    }

    public void DialogCartel() {
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

    public void DialogCartelonEntregaRechazo(String titulo, String descripcion, int imagen) {
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
                presenter.SolicitarEntrega(idCadete);
                dialog2.dismiss();
            }
        });
        dialog2.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog2.setCancelable(false);
        dialog2.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnEntregar:
                final Dialog dialog3 = new Dialog(context);
                dialog3.setContentView(R.layout.dialog_confirmar);
                TextView tvtitulo = dialog3.findViewById(R.id.tvdialogConfirTitulo);
                TextView tvdescripcion = dialog3.findViewById(R.id.tvdialogConfirDescripcion);
                tvtitulo.setText("Confirmar");
                tvdescripcion.setText("¿Desea confirmar la entrega?");
                MaterialButton btnsi = dialog3.findViewById(R.id.btnDialogConfirSi);
                MaterialButton btnno = dialog3.findViewById(R.id.btnDialogConfirNo);
                btnsi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog3.dismiss();
                        DialogCartel();
                        int permissionCheck = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION);
                        int permissionCheck2 = ContextCompat.checkSelfPermission(getContext(), ACCESS_COARSE_LOCATION);
                        locationManager = (LocationManager) getContext().getSystemService(getContext().LOCATION_SERVICE);
                        locationListener = new LocationListener() {
                            @Override
                            public void onLocationChanged(Location location) {
                                Latitud = ("" + location.getLatitude());
                                Longitud = ("" + location.getLongitude());
                                dialog.dismiss();
                                presenter.ProcesarEntrega(etgtId, idCadete, Latitud, Longitud);
                                locationManager.removeUpdates(this);
                            }

                            @Override
                            public void onStatusChanged(String provider, int status, Bundle extras) {

                            }

                            @Override
                            public void onProviderEnabled(String provider) {

                            }

                            @Override
                            public void onProviderDisabled(String provider) {

                            }
                        };
                        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
                    }
                });
                btnno.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog3.dismiss();

                    }
                });
                dialog3.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog3.setCancelable(false);
                dialog3.show();

                break;
            case R.id.btnRechazar:
                final Dialog dialog4 = new Dialog(context);
                dialog4.setContentView(R.layout.dialog_confirmar);
                TextView tvtitulorechazo = dialog4.findViewById(R.id.tvdialogConfirTitulo);
                TextView tvdescripcionrechazo = dialog4.findViewById(R.id.tvdialogConfirDescripcion);
                tvtitulorechazo.setText("Confirmar");
                tvdescripcionrechazo.setText("¿Desea rechazar la entrega?");
                MaterialButton btnsirechazo = dialog4.findViewById(R.id.btnDialogConfirSi);
                MaterialButton btnnorechazo = dialog4.findViewById(R.id.btnDialogConfirNo);
                btnsirechazo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog4.dismiss();
                        DialogCartel();
                        presenter.ProcesarRechazo(etgtId, idCadete);
                        dialog.dismiss();
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

                break;


        }
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
