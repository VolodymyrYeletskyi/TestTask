package com.example.testtask.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.testtask.R;
import com.example.testtask.recyclerview.ContactAdapter;
import com.example.testtask.recyclerview.DividerItemDecoration;
import com.example.testtask.dao.ContactDao;
import com.example.testtask.database.App;
import com.example.testtask.database.ContactDatabase;
import com.example.testtask.model.Contact;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

//главный экран приложения
public class MainActivity extends AppCompatActivity implements ContactAdapter.OnDeleteListener{
    private List<Contact> firstContacts = new ArrayList<>();

    RecyclerView rv;
    Button btnReload;

    private ContactDatabase contactDatabase;
    ContactDao contactDao;
    ContactAdapter contactAdapter;
    List<Contact> contacts = new ArrayList<>();
    private static final int REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //создание начальных данных
        firstContacts.add(new Contact(0, "Vova", "Ivanov", "+380547", "vova@ivanov.ru"));
        firstContacts.add(new Contact(1, "Zahar", "Svetlov", "+380474", "zahar@svetlov.ru"));
        firstContacts.add(new Contact(2, "Kostya", "Rebrov", "+380555", "kostya@rebrov.ru"));
        firstContacts.add(new Contact(3, "Vova", "Sidorov", "+380897", "vova@sidorov.ru"));
        firstContacts.add(new Contact(4, "Vasya", "Petrov", "+380123", "vasya@petrov.ru"));

        rv = findViewById(R.id.rv);
        btnReload = findViewById(R.id.btnReload);
        //подключение к базе данных
        contactDatabase = App.getInstance().getDatabaseInstance();
        contactDao = contactDatabase.contactDao();

        //получение списка контактов из базы данных
        contacts = contactDao.getAll();
        //если контактов нет, заполняем базу начальными значениями
        if(contacts.size() == 0)
        {
            contactDao.insert(firstContacts);
            for(int i = 0; i < firstContacts.size(); i++) {
                //клонирование начальных контактов
                try {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ObjectOutputStream ous = new ObjectOutputStream(baos);

                    ous.writeObject(firstContacts.get(i));
                    ous.close();
                    ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
                    ObjectInputStream ois = new ObjectInputStream(bais);

                    contacts.add((Contact) ois.readObject());
                } catch (IOException e) {
                } catch (ClassNotFoundException e) {
                }
            }
        }

        //создание адаптера для контейнера
        contactAdapter = new ContactAdapter(this, contacts);

        //добавление обработки событий изменения и удаления контактов
        contactAdapter.SetOnItemClickListener(new ContactAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                intent.putExtra("contact", contacts.get(position));

                //передаем в новую активность выбранный контакт
                startActivityForResult(intent, REQUEST_CODE);

            }
        });
        contactAdapter.setOnDeleteListener(this);

        //добавление разделительных линий между элементами контейнера
        rv.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(this, R.drawable.items_divider)));
        //установка адаптера для контейнера
        rv.setAdapter(contactAdapter);

        //обработка нажатия на кнопку обновления данных
        btnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //при нажатии, очищаем базу данных, и заново заполняем начальными значениями
                contactDao.drop();
                contacts.clear();
                contactDao.insert(firstContacts);

                for(int i = 0; i < firstContacts.size(); i++) {
                    //клонирование начальных контактов
                    try {
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        ObjectOutputStream ous = new ObjectOutputStream(baos);

                        ous.writeObject(firstContacts.get(i));
                        ous.close();
                        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
                        ObjectInputStream ois = new ObjectInputStream(bais);

                        contacts.add((Contact) ois.readObject());
                    } catch (IOException e) {
                    } catch (ClassNotFoundException e) {
                    }
                }
                ContactAdapter contactAdapter1 = new ContactAdapter(MainActivity.this, contacts);

                contactAdapter1.SetOnItemClickListener(new ContactAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        Intent intent = new Intent(MainActivity.this, EditActivity.class);
                        intent.putExtra("contact", contacts.get(position));

                        //передаем в новую активность выбранный контакт
                        startActivityForResult(intent, REQUEST_CODE);

                    }
                });
                contactAdapter1.setOnDeleteListener(MainActivity.this);
                rv.setAdapter(contactAdapter1);


            }
        });
    }
    //реализация метода для удаления контактов
    @Override
    public void onDelete(Contact contact) {
        contactDao.delete(contact);
    }

    //получение результатов из активности с детальной информацией
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK)
        {
            //обновление выбранного контакта полученной информацией
            Contact contact = (Contact) data.getSerializableExtra("saveContact");
            contactDao.update(contact);

            contacts.set(contact.getId(), contact);
            contactAdapter.notifyItemChanged(contact.getId());
        }
    }
}
