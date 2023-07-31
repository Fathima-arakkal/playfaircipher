package com.example.playfaircipher;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editTextText;
    private EditText editTextKey;
    private Button buttonEncrypt;
    private Button buttonDecrypt;
    private TextView textViewEncryptedText;
    private TextView textViewDecryptedText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextText = findViewById(R.id.editTextText);
        editTextKey = findViewById(R.id.editTextKey);
        buttonEncrypt = findViewById(R.id.buttonEncrypt);
        buttonDecrypt = findViewById(R.id.buttonDecrypt);
        textViewEncryptedText = findViewById(R.id.textViewEncryptedText);
        textViewDecryptedText = findViewById(R.id.textViewDecryptedText);

        buttonEncrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editTextText.getText().toString();
                String key = editTextKey.getText().toString();

                String encryptedText = encrypt(text, key);
                textViewEncryptedText.setText(encryptedText);
            }
        });

        buttonDecrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String encryptedText = textViewEncryptedText.getText().toString();
                String key = editTextKey.getText().toString();

                String decryptedText = decrypt(encryptedText, key);
                textViewDecryptedText.setText(decryptedText);
            }
        });
    }

    // Encryption method
    private String encrypt(String text, String key) {
        PlayfairCipher cipher = new PlayfairCipher(key);
        return cipher.encrypt(text);
    }

    // Decryption method
    private String decrypt(String text, String key) {
        PlayfairCipher cipher = new PlayfairCipher(key);
        return cipher.decrypt(text);
    }
}