package com.genv3.gendelivery.View.Fragments;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.genv3.gendelivery.Adapter.AdapterPedidos;
import com.genv3.gendelivery.Interface.IEntregasDisponibles;
import com.genv3.gendelivery.Objects.EntregasDisponibles;
import com.genv3.gendelivery.Presenter.PedidosPresenter;
import com.genv3.gendelivery.R;
import com.genv3.gendelivery.View.Activtys.TomarPedidosView;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PedidosFragmen.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PedidosFragmen#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PedidosFragmen extends Fragment implements IEntregasDisponibles.View, View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public IEntregasDisponibles.Presenter presenter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private BroadcastReceiver receiver;
    public static final String REFRESH = "REFRESH";
    public static final String UPDATE = "UPDATE_PEDIDOS";
    public static final String FAIL = "FAIL";
    public static final int REQUEST_CODE = 9009;
    private ImageButton btnactualizar;
    private RecyclerView rvpedidos;
    Context context;
    ArrayList<EntregasDisponibles> entregasDisponibles;
    Dialog dialog;

    //  static IEntregasDisponibles.Presenter presenter;
    public PedidosFragmen() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PedidosFragmen.
     */
    // TODO: Rename and change types and number of parameters
    public static PedidosFragmen newInstance(String param1, String param2) {
        PedidosFragmen fragment = new PedidosFragmen();
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
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(context).unregisterReceiver(receiver);

    }

    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(context).registerReceiver(receiver, new IntentFilter(UPDATE));


    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pedidos, container, false);
        context = this.getContext();
        presenter = new PedidosPresenter(this);
        entregasDisponibles = new ArrayList<>();
        rvpedidos = view.findViewById(R.id.rvPedidos);
        btnactualizar = view.findViewById(R.id.btnActualizar);
        btnactualizar.setOnClickListener(this);
        rvpedidos.setLayoutManager(new LinearLayoutManager(context));
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                DialogCartel();
                presenter.SolicitarPedidos();
            }
        }, 300);

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context2, Intent intent) {
                Bundle bundle = intent.getBundleExtra(UPDATE);
                if (bundle != null) {
                    Intent i = new Intent(context, TomarPedidosView.class);
                    i.putExtra("suerte", bundle);
                    startActivityForResult(i, REQUEST_CODE);
                } else {
                    String respuesta = intent.getStringExtra(UPDATE);
                    if (respuesta == FAIL) {
                        DialogCartelon("Error", "Sin servicio", R.drawable.ic_twotone_sinservicio);
                    } else if (respuesta == REFRESH) {
                        DialogCartelon("Aviso", "Este pedido ya fue tomado", R.drawable.ic_twotone_clear);
                        presenter.SolicitarPedidos();

                    } else {
                        presenter.SolicitarPedidos();

                    }
                }
            }
        };
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
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                presenter.SolicitarPedidos();
            } else if (resultCode == RESULT_CANCELED) {
                presenter.SolicitarPedidos();

            }
        }
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

    @Override
    public void cargarRecycler(ArrayList<EntregasDisponibles> entregasDisponibles) {
        AdapterPedidos adapterRecycler = new AdapterPedidos(entregasDisponibles, context);
        rvpedidos.setAdapter(adapterRecycler);
        adapterRecycler.notifyDataSetChanged();
    }

    @Override
    public void OnErrorCharge(int imagen) {
        dialog.dismiss();
        DialogCartelon("Error", "Sin servicio", imagen);
    }

    @Override
    public void OnSucessCharge() {
        dialog.dismiss();
    }

    @Override
    public void OnClear(int imagen) {
        dialog.dismiss();
        DialogCartelon("Alerta", "Sin pedidos libres", imagen);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnActualizar) {
            DialogCartel();
            presenter.SolicitarPedidos();
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
