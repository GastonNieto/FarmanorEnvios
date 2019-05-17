package com.genv3.gendelivery.Interface;

import android.content.Context;

import com.genv3.gendelivery.Objects.Cadetes;

import java.util.List;

public interface Ilogin {
    interface View {
        void loginView(List<Cadetes> c);

        void OnErrorCharge(int a);

        void OnSucessCharge();

        void onDatosIncorrectos(int a);

        void onUserBlock(int a);
    }

    interface presenter {
        void loginPresenter(String user, String pass, Context c);

        void autenticarPresenter(List<Cadetes> c);

        void OnErrorCharge(int a);

        void OnSucessCharge();

        void onDatosIncorrectos(int a);

        void onUserBlock(int a);

    }

    interface model {
        void auntenticarPresenter(String user, String pass, Context c);

    }

}
