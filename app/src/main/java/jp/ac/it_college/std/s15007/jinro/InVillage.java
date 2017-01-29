package jp.ac.it_college.std.s15007.jinro;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by samuel on 17/01/26.
 */

public class InVillage extends Activity{

    private String player_name;
    private String player_job;
    private String village_name;
    private int village_id;
    private int player_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.in_village);

        Intent intent = getIntent();

        String author_name = intent.getStringExtra("author_name");
        player_name = intent.getStringExtra("player_name");
        player_job = intent.getStringExtra("player_job");
        village_name = intent.getStringExtra("village_name");
        village_id = intent.getIntExtra("village_id", 0);

        TextView player = (TextView) findViewById(R.id.txt_player_name);
        TextView village = (TextView) findViewById(R.id.txt_village_name);

        AlertDialog.Builder alertDlg = new AlertDialog.Builder(InVillage.this);
        alertDlg.setMessage("あなたの名前は " + player_name + " です");
        alertDlg.setPositiveButton("OK", null);

        alertDlg.create().show();

        player.setText("作成者: " + author_name);
        village.setText("村: " + village_name);

        show_users(village_id);

        Button btn_start_game = (Button) findViewById(R.id.btn_start_game);
        btn_start_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
            }
        });

    }

    private void show_users(int v_id) {

        JinroDBHelper myDb = new JinroDBHelper(this);
        SQLiteDatabase db = myDb.getReadableDatabase();

        ListView myListView = (ListView) findViewById(R.id.player_in_list);

        Cursor cursor = db.query("users", new String[]{"name"},
                "village_id = ?", new String[]{"" + v_id},
                null, null, null);

        int Index = cursor.getColumnIndex("name");
        ArrayList<String> name_list = new ArrayList<>();

        while (cursor.moveToNext()) {
            name_list.add(cursor.getString(Index));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.in_village_list_row, name_list);
        myListView.setAdapter(adapter);

    }

    private void startGame() {
        Intent intent = new Intent(this, GameWindowDay.class);

        intent.putExtra("village_id", village_id);
        intent.putExtra("village_name", village_name);
        intent.putExtra("player_name", player_name);
        intent.putExtra("player_job", player_job);
        intent.putExtra("player_id", player_id);

        startActivity(intent);
    }
}