package com.example.btgtcc;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class  SingUpActivity extends AppCompatActivity {

    private EditText mEditTextUserName, mEditTextEmail, mEditTextPassword, mEditTextPassword2;
    private Button mButtonSignUp;
    private TextView mTextViewAlreadyLogin;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Impede que o teclado seja exibido automaticamente ao iniciar a atividade.
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        // Inicialize os elementos de interface do usuário
        mEditTextEmail = findViewById(R.id.editText_email);
        mEditTextUserName = findViewById(R.id.editText_user_name);
        mEditTextPassword = findViewById(R.id.editText_password_sign_up);
        mEditTextPassword2 = findViewById(R.id.editText_password_sign_up_2);

        mTextViewAlreadyLogin = findViewById(R.id.textView_already);
        mTextViewAlreadyLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Redireciona o usuário de volta para a tela de login
                performActivityLogin();
            }
        });

        mProgressBar = findViewById(R.id.progressBarSignUp);

        mButtonSignUp = findViewById(R.id.button_sign_up);
        mButtonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Trata o clique no botão "Sign Up"
                handleSignUp();
            }
        });
    }

    private void performActivityLogin() {
        // Redireciona o usuário para a tela de login
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void handleSignUp() {
        // Validação dos campos
        if (TextUtils.isEmpty(mEditTextUserName.getText()) ||
                TextUtils.isEmpty(mEditTextEmail.getText()) ||
                TextUtils.isEmpty(mEditTextPassword.getText()) ||
                TextUtils.isEmpty(mEditTextPassword2.getText())) {
            // Campos obrigatórios não preenchidos
            showMessage(getString(R.string.text_error_all_fields_required));
            return;
        }

        String password1 = mEditTextPassword.getText().toString().trim();
        String password2 = mEditTextPassword2.getText().toString().trim();

        if (!password1.equals(password2)) {
            // Senhas não coincidem
            showMessage(getString(R.string.text_password_are_not_same));
            return;
        }

        // Os campos estão preenchidos corretamente, proceda com o registro do usuário
        String name = mEditTextUserName.getText().toString();
        String email = mEditTextEmail.getText().toString().toLowerCase(Locale.ROOT);

        mProgressBar.setVisibility(View.VISIBLE);

        // Crie um objeto User e insira no banco de dados
        User user = new User(name, password1, email);
        int result = UserDao.insertUser(user, getApplicationContext());

        User mUser = new User(password1, name);

        int userId = UserDao.pegaId(mUser, getApplicationContext());

        mProgressBar.setVisibility(View.GONE);

        if (result > 0) {
            // Registro bem-sucedido
            showMessage(getString(R.string.text_insert_success));

            // Redireciona o usuário para a tela de HomeActivity ou outra tela apropriada
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
            finish();
        } else {
            // Ocorreu um erro durante o registro
            showMessage(getString(R.string.text_insert_error));
        }
    }

    private void showMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
