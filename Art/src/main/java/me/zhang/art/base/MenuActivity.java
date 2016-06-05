package me.zhang.art.base;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by Li on 6/5/2016 1:29 PM.
 */
public abstract class MenuActivity extends ListActivity {

    private SortedMap<String, Intent> actions = new TreeMap<>();

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        String key = (String) l.getItemAtPosition(position);
        startActivity(actions.get(key));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prepareMenu();

        String[] keys = actions.keySet().toArray(
                new String[actions.keySet().size()]
        );

        setListAdapter(
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, keys)
        );

    }

    protected void addMenuItem(String label, Class<?> cls) {
        actions.put(label, new Intent(this, cls));
    }

    protected abstract void prepareMenu();
}
