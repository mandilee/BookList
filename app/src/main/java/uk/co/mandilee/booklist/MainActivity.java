package uk.co.mandilee.booklist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public static final String SEARCH_TERM = "SEARCH_TERM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button searchButton = (Button) findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText searchFor = (EditText) findViewById(R.id.search_text);
                String searchTerm = String.valueOf(searchFor.getText());
                if (searchTerm.length() == 0) {

                } else {
                    Intent i = new Intent(MainActivity.this, BookActivity.class);//new BookActivity();
                    i.putExtra(SEARCH_TERM, searchTerm);
                    startActivity(i);
                }
            }
        });
    }
}