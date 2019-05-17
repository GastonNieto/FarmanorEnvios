package com.genv3.gendelivery.Presenter;

import android.content.Context;

import com.genv3.gendelivery.Interface.Ilogin;
import com.genv3.gendelivery.Model.LoginModel;
import com.genv3.gendelivery.Objects.Cadetes;

import java.util.List;

public class LoginPresenter implements Ilogin.presenter {
    private  Ilogin.View view;
    private  Ilogin.model model;

    public LoginPresenter(Ilogin.View view) {
        this.view = view;
        model = new LoginModel(this);
    }

    @Override
    public void loginPresenter(String user, String pass, Context c) {
            if(view != null){
                model.auntenticarPresenter(user,pass, c);
            }
    }


    @Override
    public void autenticarPresenter(  List<Cadetes> c) {
        if(view != null){
            view.loginView(c);
        }
    }

    @Override
    public void OnErrorCharge(int a) {
        if (view !=null){
            view.OnErrorCharge(a);
        }
    }

    @Override
    public void OnSucessCharge() {

    }

    @Override
    public void onDatosIncorrectos(int a) {
        if (view !=null){
            view.onDatosIncorrectos(a);
        }
    }

    @Override
    public void onUserBlock(int a) {
        if (view !=null){
            view.onUserBlock(a);
        }
    }
}
