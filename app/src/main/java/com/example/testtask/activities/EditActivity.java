package com.example.testtask.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.testtask.R;
import com.example.testtask.model.Contact;

//активность с детальной информацией
public class EditActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView tvChanges;
    private Contact contact;
    Intent sendingIntent = new Intent();
    private EditText etName, etNumber, etEmail, etSurname;
    private ImageView ivPhoto;
    private Button btnSave, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        tvChanges = findViewById(R.id.tvChanges);
        etName = findViewById(R.id.etName);
        etSurname = findViewById(R.id.etSurname);
        ivPhoto = findViewById(R.id.ivPhoto);
        etNumber = findViewById(R.id.etNumber);
        etEmail = findViewById(R.id.etEmail);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);

        tvChanges.setVisibility(View.INVISIBLE);
        //получение данных из главной активности
        Intent intent = getIntent();
        contact = (Contact) intent.getSerializableExtra("contact");
        etName.setText(contact.getName());
        etSurname.setText(contact.getSurname());
        ivPhoto.setImageResource(R.drawable.default_photo);
        etNumber.setText(contact.getNumber());
        etEmail.setText(contact.getEmail());




        btnCancel.setOnClickListener(this);
        btnSave.setOnClickListener(this);


    }
    //обработка нажатий на кнопки
    @Override
    public void onClick(View v) {


        switch (v.getId())
        {
            //если нажата кнопка отмены - выход из активности и возврат результатов в главную активность
            case R.id.btnCancel:
                sendingIntent.putExtra("saveContact", contact);
                setResult(RESULT_OK, sendingIntent);
                finish();
                break;
            //если нажата кнопка сохранения - сохранение внесенных изменений
            case R.id.btnSave:
                contact.setName(etName.getText().toString());
                contact.setSurname(etSurname.getText().toString());
                contact.setNumber(etNumber.getText().toString());
                contact.setEmail(etEmail.getText().toString());
                tvChanges.setVisibility(View.VISIBLE);
                break;
        }

    }
}
