package com.example.jola.ftstutorial;

import java.util.Calendar;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class SearchViewActivity extends Activity implements SearchView.OnQueryTextListener,
        SearchView.OnCloseListener {

    private ListView mListView;
    private SearchView searchView;
    private DbAdapter mDbHelper;

    private TextView nameText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        searchView = (SearchView) findViewById(R.id.search);
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(this);

        mListView = (ListView) findViewById(R.id.list);

        mDbHelper = new DbAdapter(this);
        mDbHelper.open();

        //Clean all Customers
        mDbHelper.deleteAllItems();
        //Add search query to database
        mDbHelper.createItem("Uk?ad nerwowy");
        mDbHelper.createItem("Neurony");
        mDbHelper.createItem("Glej");
        mDbHelper.createItem("Synapsy");
        mDbHelper.createItem("Podzia? uk?adu nerwowego");
        mDbHelper.createItem("Mózgowie cz?owieka");
        mDbHelper.createItem("Rdze? przed?u?ony");
        mDbHelper.createItem("Ty?omózgowie wtórne");
        mDbHelper.createItem("Most");
        mDbHelper.createItem("Mó?d?ek");
        mDbHelper.createItem("?ródmózgowie");
        mDbHelper.createItem("Mi?dzymózgowie");
        mDbHelper.createItem("Podwzgórze");
        mDbHelper.createItem("Wzgórze");
        mDbHelper.createItem("Przysadka mózgowa");
        mDbHelper.createItem("Kresomózowie");
        mDbHelper.createItem("Kora mózgowa");
        mDbHelper.createItem("Hipokamp");
        mDbHelper.createItem("Wyspa");
        mDbHelper.createItem("J?dra podstawy");
        mDbHelper.createItem("Spoid?a");
        mDbHelper.createItem("Komory mózgowe");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDbHelper  != null) {
            mDbHelper.close();
        }
    }

    public boolean onQueryTextChange(String newText) {
        showResults(newText + "*");
        return false;
    }

    public boolean onQueryTextSubmit(String query) {
        showResults(query + "*");
        return false;
    }

    public boolean onClose() {
        showResults("");
        return false;
    }

    private void showResults(String query) {

        Cursor cursor = mDbHelper.searchItem((query != null ? query.toString() : "@@@@"));

        if (cursor == null) {
            //
        } else {
            // Specify the columns we want to display in the result
            String[] from = new String[] {
                    DbAdapter.KEY_NAME};

            // Specify the Corresponding layout elements where we want the columns to go
            int[] to = new int[] {
                    R.id.sname};

            // Create a simple cursor adapter for the definitions and apply them to the ListView
            SimpleCursorAdapter dbRecord = new SimpleCursorAdapter(this,R.layout.customerresult, cursor, from, to);
            mListView.setAdapter(dbRecord);

            // Define the on-click listener for the list items
            mListView.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // Get the cursor, positioned to the corresponding row in the result set
                    Cursor cursor = (Cursor) mListView.getItemAtPosition(position);

                    // Get the state's capital from this row in the database.
                    String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));

                    //Check if the Layout already exists
                    LinearLayout customerLayout = (LinearLayout)findViewById(R.id.customerLayout);
                    if(customerLayout == null){
                        //Inflate the Customer Information View
                        LinearLayout leftLayout = (LinearLayout)findViewById(R.id.rightLayout);
                        View customerInfo = getLayoutInflater().inflate(R.layout.customerinfo, leftLayout, false);
                        leftLayout.addView(customerInfo);
                    }

                    //Get References to the TextViews
                    nameText = (TextView) findViewById(R.id.name);

                    // Update the parent class's TextView
                    nameText.setText(name);

                    searchView.setQuery("",true);
                }
            });
        }
    }

}