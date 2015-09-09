package com.example.aleung_c.ft_hangouts;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;


public class search_contacts extends Activity implements View.OnKeyListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_contacts);
        set_autocomp_bar();
        display_list("");

    }

    public void push_search_btn(View view) {
        EditText search_text = (EditText) findViewById(R.id.search_contact_name);
        String text_entered = search_text.getText().toString();
        display_list(text_entered);
    }

    private void set_autocomp_bar() {
        DatabaseHandler db = new DatabaseHandler(this);
        List<Contact> db_contacts = db.getAllContacts();

        // set the adapter with contact list.
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, db_contacts);
        EditText etsearch = (EditText) findViewById(R.id.search_contact_name);
        // on text change events.
        etsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                EditText etsearch = (EditText) findViewById(R.id.search_contact_name);
                String textinbar = etsearch.getText().toString();
                display_list(textinbar);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etsearch.setOnKeyListener(this);
        db.close();
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        EditText search_bar = (EditText) findViewById(R.id.search_contact_name);
        String textinbar = search_bar.getText().toString();
        if (keyCode == 66) // == ENTER
        {
            display_list(textinbar);
        }
        return true;
    }

    private void display_list(String display_type) {
        DatabaseHandler db = new DatabaseHandler(this);

        List<Contact> contacts;
        if (display_type.equals(""))
            contacts = db.getAllContacts();
        else
            contacts = db.getAllContactsfromName_start(display_type);
        ListView listContent = (ListView) findViewById(R.id.contact_listview);
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, contacts);
        listContent.setAdapter(adapter);
        db.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display_contacts, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}