package phoenixtechnologies.co.employee;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private EditText etEmail;
    private EditText etPassword;
    private Button btLogin;
    private ProgressBar mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btLogin = (Button) findViewById(R.id.btLogin);
        mProgress = (ProgressBar) findViewById(R.id.mProgress);


        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInUser(etEmail,etPassword);
            }
        });




    }

    private void signInUser(EditText email,EditText password) {
        String emailText = email.getText().toString();
        String passwordText =  password.getText().toString();
        if(!TextUtils.isEmpty(emailText) && !TextUtils.isEmpty(passwordText)){
            //proceed
            btLogin.setEnabled(false);
            mProgress.setVisibility(View.VISIBLE);

            mAuth.signInWithEmailAndPassword(emailText, passwordText).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        gotoDashboard();
                    }else{
                        Toast.makeText(LoginActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                    }
                    btLogin.setEnabled(true);
                    mProgress.setVisibility(View.INVISIBLE);
                }
            });



        }else{
            Toast.makeText(this, "Fields Empty", Toast.LENGTH_SHORT).show();
        }



    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            gotoDashboard();
        }
    }

    private void gotoDashboard() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }
}
