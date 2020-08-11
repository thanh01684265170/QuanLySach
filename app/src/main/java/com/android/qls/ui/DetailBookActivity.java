package com.android.qls.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.qls.DatabaseHelper;
import com.android.qls.R;
import com.android.qls.adapter.BookTypeAdapter;
import com.android.qls.model.Book;
import com.android.qls.model.BookType;

import java.util.ArrayList;
import java.util.List;

public class DetailBookActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "EXTRA_POSITION";
    public static final int ADD_POSITION = -1;
    public static boolean EDIT = false;

    public static void openDetailToyActivity(Activity activity, int position) {
        Intent intent = new Intent(activity, DetailBookActivity.class);
        intent.putExtra(EXTRA_POSITION, position);
        activity.startActivity(intent);
    }

    private DatabaseHelper database;
    private Book book;
    private BookType bookType;
    private String img = "";
    private TextView inputId;
    private TextView inputName;
    private TextView inputDescription;
    private TextView inputReview;
    private TextView inputType;
    private ImageView image;
    private Button buttonUpdate;
    private List<BookType> bookTypes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_book);
        database = new DatabaseHelper(this);
        bookTypes = new ArrayList<>();
        bookTypes = database.getAllToyType();

        int position = getIntent().getIntExtra(EXTRA_POSITION, ADD_POSITION);
        if (position != ADD_POSITION) {
            book = database.getAllToy().get(position);
            EDIT = true;
        } else EDIT = false;
        addViews();
        addListeners();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            getContentResolver().takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
            img = uri.toString();
            image.setImageURI(uri);
        }
    }

    private void addViews() {
        buttonUpdate = findViewById(R.id.button_update);
        inputId = findViewById(R.id.input_id);
        inputName = findViewById(R.id.input_name);
        inputDescription = findViewById(R.id.input_description);
        inputReview = findViewById(R.id.input_review);
        inputType = findViewById(R.id.tvChooseType);
        image = findViewById(R.id.image);
        if (EDIT) {
            String type = "";
            for (int i = 0; i < bookTypes.size(); i++) {
                if (book.getIdType() == bookTypes.get(i).getId()) {
                    type = bookTypes.get(i).getName();
                }
            }
            inputId.setText(String.valueOf(book.getId()));
            inputName.setText(book.getName());
            inputDescription.setText(book.getDescription());
            inputReview.setText(book.getReview());
            inputType.setText(type);
            buttonUpdate.setText("Update");
            image.setImageURI(book.getImageUri());
        } else {
            buttonUpdate.setText("Add");
        }
    }

    private void addListeners() {
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 2);
            }
        });
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (EDIT) editToy();
                else addToy();
            }
        });

        inputType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(DetailBookActivity.this);
                LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
                View content = inflater.inflate(R.layout.custom_dialog_type, null);
                dialog.setContentView(content);
                ListView listView = content.findViewById(R.id.lv_type);

                BookTypeAdapter typeAdapter = new BookTypeAdapter();
                typeAdapter.setTypeList(bookTypes, true);
                listView.setAdapter(typeAdapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        bookType = bookTypes.get(i);
                        inputType.setText(bookType.getName());
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
    }

    private void addToy() {
        Book s = new Book(
                Integer.parseInt(inputId.getText().toString()),
                inputName.getText().toString(),
                bookType.getId(),
                inputDescription.getText().toString(),
                img,
                inputReview.getText().toString());
        database.addBook(s);
        onBackPressed();
        database.closeDB();
    }

    private void editToy() {
        book.setName(inputName.getText().toString());
        book.setDescription(inputDescription.getText().toString());
        book.setReview(inputReview.getText().toString());
        book.setImage(img);
        database.updateToy(book);
        onBackPressed();
        database.closeDB();
    }
}
