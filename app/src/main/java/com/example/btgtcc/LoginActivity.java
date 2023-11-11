package com.example.btgtcc;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.IntDef;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = "Login Activity";

    TextView mTextViewNewUser, mTextViewForgotPassword;
    Button mButtonLogin;
    EditText mEditTextEmail, mEditTextPassword;
    ProgressBar mProgressBarLogin;
    String mStringUserName, mStringPassword, mStringEmail;
    int userId;
    SharedPreferences mSharedPreferencesLogin;

    private boolean isRequiredPassword() {
        return TextUtils.isEmpty(mEditTextPassword.getText());
    }

    private boolean isValidEmail(String mStringEmail) {
        if (mStringEmail == null || mStringEmail.isEmpty()) {
            return false;
        }
        return Patterns.EMAIL_ADDRESS.matcher(mStringEmail).matches();
    }

    private void showNavigation() {
        Intent mIntent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(mIntent);
        finish();
    }

//    private void verifyLogged() {
//        if (mSharedPreferencesLogin.getString("logged", "false").equals("true")) {
//            showNavigation();
//        }
//    }

    private void postData() {
        mStringEmail = String.valueOf(mEditTextEmail.getText()).toLowerCase(Locale.ROOT);
        mStringPassword = String.valueOf(mEditTextPassword.getText());

        if (!isValidEmail(mStringEmail)) {
            String mTextMessage = getString(R.string.text_email_not_valid);
            Toast.makeText(getApplicationContext(), mTextMessage, Toast.LENGTH_SHORT).show();
            return;
        }

        if (isRequiredPassword()) {
            String mTextMessage = getString(R.string.text_error_fill_mandatory_information);
            Toast.makeText(getApplicationContext(), mTextMessage, Toast.LENGTH_SHORT).show();
            return;
        }

        mProgressBarLogin.setVisibility(View.VISIBLE);

        User mUser = new User(mStringPassword, mStringEmail);

        int userId = UserDao.authenticateUser(mUser, getApplicationContext());

        if (userId != -1) {
            SharedPreferences.Editor mEditor = mSharedPreferencesLogin.edit();
            mEditor.putString("logged", "true");
            mEditor.putInt("userId", userId);  // Armazena o ID do usuário
            mEditor.apply();

            Intent mIntent = new Intent(getApplicationContext(), HomeActivity.class);
            // Você pode passar o userId para a próxima atividade se necessário.
            mIntent.putExtra("EXTRA_USER_ID", userId);
            startActivity(mIntent);
            finish();
        } else {
            // Lidar com o erro de login
            String mTextMessage = getString(R.string.text_email_or_password_incorrect);
            Toast.makeText(getApplicationContext(), mTextMessage, Toast.LENGTH_SHORT).show();
        }

        mProgressBarLogin.setVisibility(View.GONE);
    }


    public class ClickMyButtonLogin implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            postData();
        }
    }

    private void showSignUp() {
        Intent mIntent = new Intent(getApplicationContext(), SingUpActivity.class);
        startActivity(mIntent);
        finish();
    }

    public class ClickMyNewUserSingUp implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            showSignUp();
        }
    }

    //impedir a visualização teclado
    public class EditTextAction implements TextView.OnEditorActionListener {
        @Override
        public boolean onEditorAction(TextView textView, int actionId, KeyEvent KeyEvent) {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                postData();
            }
            return false;
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


        mEditTextEmail = findViewById(R.id.editText_email_login);

        mEditTextPassword = findViewById(R.id.editText_password_login);
        mEditTextPassword.setOnEditorActionListener(new EditTextAction());  //comportamento "MY" antes do TextAction

        mButtonLogin = findViewById(R.id.button_login);
        mButtonLogin.setOnClickListener(new ClickMyButtonLogin());  //comportamento

        mProgressBarLogin = findViewById(R.id.progressBarLogin);

        mTextViewNewUser = findViewById(R.id.textView_new_user);
        mTextViewNewUser.setOnClickListener(new ClickMyNewUserSingUp()); //comportamento

        mSharedPreferencesLogin = getSharedPreferences("MyAppName", MODE_PRIVATE);

//        verifyLogged();


    }


}


//