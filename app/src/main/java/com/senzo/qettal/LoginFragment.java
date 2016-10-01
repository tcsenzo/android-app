package com.senzo.qettal;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.wang.avi.AVLoadingIndicatorView;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by leonardo on 16/09/29.
 */
public class LoginFragment extends Fragment {
    @BindView(R.id.edt_email)
    EditText edtEmail;
    @BindView(R.id.edt_password)
    EditText edtPassword;
    @BindView(R.id.loading_indicator)
    AVLoadingIndicatorView avi;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.btn_login)
    public void login() {
        new AsyncTask<Void, Void, Void>() {

            private String email;
            private String password;

            @Override
            protected void onPreExecute() {
                email = edtEmail.getText().toString().trim();
                password = edtPassword.getText().toString().trim();
                avi.show();
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                avi.hide();
                getFragmentManager().popBackStack();
            }

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    OkHttpClient client = RestClient.getClient();

                    RequestBody body = new FormBody.Builder()
                            .add("email", email)
                            .add("password", password)
                            .build();
                    Request request = new Request.Builder()
                            .url("http://auth.qettal.com/login")
                            .post(body)
                            .build();

                    client.newCall(request).execute();
                    return null;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }.execute();
    }

}
