package com.genv3.gendelivery.View.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.genv3.gendelivery.Adapter.AdapterMisPedidos;
import com.genv3.gendelivery.Adapter.AdapterPedidos;
import com.genv3.gendelivery.Interface.IMisPedidos;
import com.genv3.gendelivery.Objects.EntregasTomadas;
import com.genv3.gendelivery.Presenter.MisPedidosPresenter;
import com.genv3.gendelivery.R;
import com.genv3.gendelivery.View.Activtys.FiltroFechaActivity;
import com.genv3.gendelivery.util.Preferens;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MisPedidosFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MisPedidosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MisPedidosFragment extends Fragment implements IMisPedidos.View, View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int REQUEST_CODE = 369;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private TextView tvganancia;
    private IMisPedidos.Presenter presenter;
    private Long idCadete;
    private Context context;
    private MaterialButton ibtnbuscar, ibtnclear;
    private RecyclerView recyclerView;
    private Dialog dialog;
    private String inicial = null, ffinal = null, unicafecha = null;

    public MisPedidosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MisPedidosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MisPedidosFragment newInstance(String param1, String param2) {
        MisPedidosFragment fragment = new MisPedidosFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mis_pedidos, container, false);
        context = this.getContext();
        presenter = new MisPedidosPresenter(this);
        idCadete = Preferens.getInteger(context, Preferens.getKeyGuardia());
        ibtnbuscar = view.findViewById(R.id.ibtnBuscar);
        ibtnclear = view.findViewById(R.id.ibtnClear);
        tvganancia = view.findViewById(R.id.tvGanancia);
        ibtnbuscar.setOnClickListener(this);
        ibtnclear.setOnClickListener(this);
        recyclerView = view.findViewById(R.id.rvMisPedidos);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                DialogCartel();
                presenter.SolicitarMisPedidos(idCadete, "Tomado");
            }
        }, 400);
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
    public void CargarMisPedidos(ArrayList<EntregasTomadas> entregasTomadas, Double ganancia) {
        AdapterMisPedidos misPedidos = new AdapterMisPedidos(entregasTomadas, context);
        recyclerView.setAdapter(misPedidos);
        misPedidos.notifyDataSetChanged();
        tvganancia.setText("$"+ganancia.toString());
    }

    @Override
    public void OnErrorCharge(int a) {
        dialog.dismiss();
        DialogCartelon("Error", "Sin servicio", a);

    }

    @Override
    public void OnSucessCharge() {
        dialog.dismiss();
    }

    @Override
    public void OnClear(int a) {
        dialog.dismiss();
        DialogCartelon("Alerta", "Sin Entregas", a);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibtnBuscar:
                Intent i = new Intent(context, FiltroFechaActivity.class);
                startActivityForResult(i, REQUEST_CODE);
                break;
            case R.id.ibtnClear:
                DialogCartel();
                presenter.SolicitarMisPedidos(idCadete, "Tomado");
                break;

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                inicial = data.getStringExtra("inicial");
                ffinal = data.getStringExtra("ffinal");
                unicafecha = data.getStringExtra("unicafecha");
                if (inicial == null || ffinal == null) {
                    DialogCartel();
                    presenter.SolicitarFechaUnica(idCadete, "Tomado", unicafecha);
                } else {
                    DialogCartel();
                    presenter.SolicitarFechaRango(idCadete, "Tomado", inicial, ffinal);

                }
            }
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
