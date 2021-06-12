package com.sentaroh.android.togglepasswordtest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView message=(TextView)findViewById(R.id.message);

        final TextInputLayout password_view=(TextInputLayout)findViewById(R.id.password_view);
        final TextInputEditText password=(TextInputEditText)findViewById(R.id.password);
        final TextInputLayout confirm_view=(TextInputLayout)findViewById(R.id.confirm_view);
        final TextInputEditText confirm=(TextInputEditText)findViewById(R.id.confirm);

        password_view.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (password.getTransformationMethod()!=null) {
                    password.setTransformationMethod(null);
                    confirm_view.setVisibility(TextInputLayout.GONE);
                    password.setCustomSelectionActionModeCallback(new ActionMode.Callback() {
                        @Override
                        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                            return true;
                        }
                        @Override
                        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                            menu.removeItem(android.R.id.cut);
                            menu.removeItem(android.R.id.copy);
                            menu.removeItem(android.R.id.shareText);
                            return true;
                        }
                        @Override
                        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                            return false;
                        }
                        @Override
                        public void onDestroyActionMode(ActionMode mode) {
                        }
                    });
                    verifyPassword(message, password_view, confirm_view, password, confirm);
                } else {
                    password.setTransformationMethod(new PasswordTransformationMethod());
                    password.setCustomSelectionActionModeCallback(null);
                    confirm_view.setVisibility(TextInputLayout.VISIBLE);
                    verifyPassword(message, password_view, confirm_view, password, confirm);
                }
            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                verifyPassword(message, password_view, confirm_view, password, confirm);
            }
        });

        confirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                verifyPassword(message, password_view, confirm_view, password, confirm);
            }
        });

        verifyPassword(message, password_view, confirm_view, password, confirm);
    }

    private void verifyPassword(TextView message, TextInputLayout password_view, TextInputLayout confirm_view, TextInputEditText password, TextInputEditText confirm) {
        if (password.getText().length()>0) {
            if (password.getTransformationMethod()!=null) {
                if (!password.getText().toString().equals(confirm.getText().toString())) {
                    message.setText("Password and confirm password unmatched");
                } else {
                    message.setText("");
                }
            } else {
                message.setText("");
            }
        } else {
            message.setText("Specify password");
        }
    }
}