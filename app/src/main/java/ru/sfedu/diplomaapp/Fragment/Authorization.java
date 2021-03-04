package ru.sfedu.diplomaapp.Fragment;

import android.accounts.AccountManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import ru.sfedu.diplomaapp.R;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.AccountPicker;

import java.io.IOException;

public class Authorization extends Fragment {
    private final static String TAG = "Authorization";
    /* Доступы для гугловской авторизации */
    private final static String G_PLUS_SCOPE = "oauth2:https://www.googleapis.com/auth/plus.me";
    private final static String USERINFO_SCOPE = "https://www.googleapis.com/auth/userinfo.profile";
    private final static String EMAIL_SCOPE = "https://www.googleapis.com/auth/userinfo.email";
    private final static String SCOPES = G_PLUS_SCOPE + " " + USERINFO_SCOPE + " " + EMAIL_SCOPE;
    private final static int G_SUCCESS_REQUEST = 123;

    private static final int RESULT_OK = -1;

    /* Текстовые поля */
    private final static int email_field_id = R.id.mailFieldTxt;
    private final static int psw_field_id = R.id.passwordFieldTxt;
    EditText email_field , psw_field;

    public Authorization() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_authorization, container, false);
        email_field = (EditText) view.findViewById(email_field_id);
        psw_field = (EditText) view.findViewById(psw_field_id);

        return inflater.inflate(R.layout.fragment_authorization, container, false);
    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        NavController navController = Navigation.findNavController(view);
        view.findViewById(R.id.valid_btn).setOnClickListener(view12 -> {
            navController.navigate(R.id.go_to_chars);
            login();
        });
        view.findViewById(R.id.google_sign_btn).setOnClickListener(view12 -> {
            navController.navigate(R.id.go_to_chars_google);
            googleAuth();
        });
        view.findViewById(R.id.reg_btn).setOnClickListener(view1 -> navController.navigate(R.id.go_to_reg));
        view.findViewById(R.id.forgot_pass_btn).setOnClickListener(view2 -> navController.navigate(R.id.go_to_forgetPas));
    }


    protected void login() {
        Log.d(TAG, "start login");
        Log.d(TAG, email_field.toString());
        String email = email_field.getText().toString();
        String psw = psw_field.getText().toString();
        Log.d(TAG, String.format("Entered data: Email: %s,  Psw: %s", email, psw));
        /* TODO: После появления api добавить функционал*/
    }


    protected void googleAuth() {
        Log.d(TAG, "start googleAuth");
        Intent intent = AccountPicker.newChooseAccountIntent(null, null, new String[]{"com.google"},
                false, null, null, null, null);
        startActivityForResult(intent, G_SUCCESS_REQUEST);
    }

    /* Метод получения токена из гугла, вызывается сам */
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == G_SUCCESS_REQUEST && resultCode == RESULT_OK) {
            final String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
            AsyncTask<Void, Void, String> getToken = new AsyncTask<Void, Void, String>() {
                @Override
                protected String doInBackground(Void... params) {
                    String token = "";
                    try {
                        token = GoogleAuthUtil.getToken(getContext(),accountName,SCOPES);
                        return token;
                    } catch (UserRecoverableAuthException userAuthEx) {
                        startActivityForResult(userAuthEx.getIntent(), 123);
                    } catch (IOException ioEx) {
                        Log.d(TAG, "IOException");
                    } catch (GoogleAuthException fatalAuthEx) {
                        Log.d(TAG, "Fatal Authorization Exception" + fatalAuthEx.getLocalizedMessage());
                    }
                    return token;
                }
                @Override
                protected void onPostExecute(String token) {
                    reg(token);
                }

            };
            getToken.execute(null, null, null);
        }
    }

    /* TODO: вызовется после получения токена гугла, его надо будет отправлять на наш бек */
    private void reg(String token) {
        Log.d(TAG, "resToken: " + token);
    }
}