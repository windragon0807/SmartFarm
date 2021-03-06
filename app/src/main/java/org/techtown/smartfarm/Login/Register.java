package org.techtown.smartfarm.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.techtown.smartfarm.Main.MainActivity;
import org.techtown.smartfarm.R;

public class Register extends AppCompatActivity {
    EditText rUserName, rUserEmail, rUserPass, rUserConfPass;
    Button syncAccount;
    TextView loginAct;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        rUserName = findViewById(R.id.register_userName);
        rUserEmail = findViewById(R.id.register_userEmail);
        rUserPass = findViewById(R.id.register_password);
        rUserConfPass = findViewById(R.id.register_passwordCon);

        syncAccount = findViewById(R.id.register_signUp);
        loginAct = findViewById(R.id.login);

        fAuth = FirebaseAuth.getInstance();

        loginAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });

        syncAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String uUsername = rUserName.getText().toString();
                String uUserEmail = rUserEmail.getText().toString();
                String uUserPass = rUserPass.getText().toString();
                String uConfPass = rUserConfPass.getText().toString();

                if(uUserEmail.isEmpty() || uUsername.isEmpty() || uUserPass.isEmpty() || uConfPass.isEmpty()){
                    Toast.makeText(Register.this, "All Fields Are Required.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!uUserPass.equals(uConfPass)){
                    rUserConfPass.setError("Password Do not Match.");
                }

                if(uUserPass.equals(uConfPass)) {
                    final ProgressDialog mDialog = new ProgressDialog(Register.this);
                    mDialog.setMessage("??????????????????.");
                    mDialog.show();

                    //????????????????????? ???????????? ????????????
                    fAuth.createUserWithEmailAndPassword(uUserEmail, uUserPass).addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            //?????? ?????????
                            if (task.isSuccessful()) {
                                mDialog.dismiss();


                                //????????? ?????????????????? ?????? ????????? ????????????.
                                Intent intent = new Intent(Register.this, Login.class);
                                startActivity(intent);
                                finish();
                                Toast.makeText(Register.this, "??????????????? ?????????????????????.", Toast.LENGTH_SHORT).show();

                            } else {  // ???????????? ?????? ????????????.
                                mDialog.dismiss();
                                Toast.makeText(Register.this, "?????? ???????????? ????????? ?????????.", Toast.LENGTH_SHORT).show();
                                return;  // ?????? ????????? ????????? ????????? ????????????.

                            }

                        }
                    });
                }



            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        startActivity(new Intent(this, MainActivity.class));
        finish();
        return super.onOptionsItemSelected(item);
    }
}
