package com.imagery.imagerymedical;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

import com.imagery.imagerymedical.helper.SQLiteHandler;
import com.imagery.imagerymedical.helper.SessionManager;

public class MainActivityLogin extends Activity {

	private TextView txtName;
	private TextView txtEmail;
	private Button btnLogout;
    private Button btnImagens;

	private SQLiteHandler db;
	private SessionManager session;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_login);

		txtName = (TextView) findViewById(R.id.name);
		txtEmail = (TextView) findViewById(R.id.email);
		btnLogout = (Button) findViewById(R.id.btnLogout);
        btnImagens = (Button) findViewById(R.id.btnImagens);

		// SqLite database handler
		db = new SQLiteHandler(getApplicationContext());

		// session manager
		session = new SessionManager(getApplicationContext());

		if (!session.isLoggedIn()) {
			logoutUser();
		}

		// Fetching user details from sqlite
		HashMap<String, String> user = db.getUserDetails();

		String name = user.get("name");
		String email = user.get("email");

		// Displaying the user details on the screen
		txtName.setText(name);
		txtEmail.setText(email);

		// Logout button click event
		btnLogout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				logoutUser();
			}
		});

        // Imagens button click event
        btnImagens.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                imagens();
            }
        });
	}

	/**
	 * Logging out the user. Will set isLoggedIn flag to false in shared
	 * preferences Clears the user data from sqlite users table
	 * */
	private void logoutUser() {
		session.setLogin(false);

		db.deleteUsers();

		// Launching the login activity
		Intent intent = new Intent(MainActivityLogin.this, LoginActivity.class);
		startActivity(intent);
		finish();
	}
    /**
     * Acesso a activity de imagens
     * */
    private void imagens() {
        // Launching the imagens(Main) activity
        Intent intent = new Intent(MainActivityLogin.this, SplashActivity.class);
        startActivity(intent);
        finish();
    }
}
