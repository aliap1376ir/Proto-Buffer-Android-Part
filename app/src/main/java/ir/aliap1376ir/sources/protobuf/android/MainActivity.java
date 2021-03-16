package ir.aliap1376ir.sources.protobuf.android;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ir.aliap1376ir.sources.protobuf.android.object.PersonOuterClass;
import ir.aliap1376ir.sources.protobuf.android.retrofit.RetrofitConfiguration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    private Button btnRefresh, btnSend;
    private EditText edtName, edtAge;
    private ListView listView;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRefresh = findViewById(R.id.btn_refresh);
        btnSend = findViewById(R.id.btn_send);
        edtName = findViewById(R.id.edt_name);
        edtAge = findViewById(R.id.edt_age);
        listView = findViewById(R.id.list_view);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);

        btnRefresh.setOnClickListener(v -> getAllUsers());
        btnSend.setOnClickListener(v -> register());
    }


    @Override
    protected void onResume() {
        super.onResume();
        getAllUsers();
    }

    private void getAllUsers() {
        Call<PersonOuterClass.People> peopleCall = RetrofitConfiguration.getAPI().all();
        peopleCall.enqueue(new Callback<PersonOuterClass.People>() {
            @Override
            public void onResponse(Call<PersonOuterClass.People> call, Response<PersonOuterClass.People> response) {
                if (response.isSuccessful()) {
                    adapter.clear();
                    PersonOuterClass.People people = response.body();
                    for (PersonOuterClass.Person person : people.getPersonList()) {
                        adapter.add("id:\t" + person.getId() + "\nname:\t" + person.getName() + "\nage:\t" + person.getAge() + "\nbytes:\t" + person.getNameBytes());
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<PersonOuterClass.People> call, Throwable t) {

            }
        });
    }

    private void register() {
        PersonOuterClass.Person person = PersonOuterClass.Person.newBuilder().build();
        person = person.toBuilder().setName(edtName.getText().toString()).build();
        try {
            person = person.toBuilder().setAge(Integer.parseInt(edtAge.getText().toString())).build();
        } catch (Exception e) {
            person = person.toBuilder().setAge(0).build();
        }
        Call<PersonOuterClass.Person> personCall = RetrofitConfiguration.getAPI().register(person);
        personCall.enqueue(new Callback<PersonOuterClass.Person>() {
            @Override
            public void onResponse(Call<PersonOuterClass.Person> call, Response<PersonOuterClass.Person> response) {
                if (response.isSuccessful()) {
                    PersonOuterClass.Person person = response.body();
                    Toast.makeText(MainActivity.this, person.getId() + "", Toast.LENGTH_SHORT).show();
                    getAllUsers();
                    edtName.setText("");
                    edtAge.setText("");
                }
            }

            @Override
            public void onFailure(Call<PersonOuterClass.Person> call, Throwable t) {

            }
        });
    }
}