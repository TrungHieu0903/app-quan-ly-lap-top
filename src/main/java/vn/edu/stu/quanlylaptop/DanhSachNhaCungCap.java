package vn.edu.stu.quanlylaptop;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import Dao.DBHelper;
import model.Laptop;
import model.NhaCungCap;
import util.DBConfigUtil;

public class DanhSachNhaCungCap extends AppCompatActivity {
    DBHelper dbHelper;
    ArrayList<NhaCungCap> dsNhaCungCap=new ArrayList<>();
    ArrayList<Laptop> dsLaptop=new ArrayList<>();
    ListView lvNcc;
    ArrayAdapter<NhaCungCap> adapter;
    Button btnThem;
    Button btnTroVe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_nha_cung_cap);
        DBConfigUtil.copyDatabaseFromAssets(DanhSachNhaCungCap.this);
        dbHelper=new DBHelper(DanhSachNhaCungCap.this);
        addControls();
        getDataNcc();
        addEvents();

    }
    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu_option, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.mnuAbout){
            Intent intent = new Intent(DanhSachNhaCungCap.this, about.class);
            startActivity(intent);
            finish();
        }
        else if (item.getItemId()==R.id.mnuExit){
            Intent intent = new Intent(DanhSachNhaCungCap.this, Menu.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    private void addEvents() {
        lvNcc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(DanhSachNhaCungCap.this, FormNhaCungCap.class);
                NhaCungCap ncc=dsNhaCungCap.get(position);
                intent.putExtra("nhacungcap", ncc);
                startActivity(intent);
            }
        });
        lvNcc.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                xuLyAlertDialog(position);
                return false;
            }
        });
        btnTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DanhSachNhaCungCap.this, Menu.class);
                startActivity(intent);
                finish();
            }
        });
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DanhSachNhaCungCap.this, FormNhaCungCap.class);
                startActivity(intent);
                finish();
            }
        });
    }
   private void getDataNcc(){
       dsNhaCungCap=(ArrayList<NhaCungCap>)dbHelper.getAllNhaCungCap();
       adapter=new ArrayAdapter<>(DanhSachNhaCungCap.this,
                android.R.layout.simple_list_item_1,
                dsNhaCungCap);
       lvNcc.setAdapter(adapter);
       adapter.notifyDataSetChanged();
   }
    private void addControls() {

        lvNcc=findViewById(R.id.lvDsNcc);
        btnTroVe=findViewById(R.id.btnTroVe);
        btnThem=findViewById(R.id.btnThem);
    }
    private void xulyXoa(int position) {
        NhaCungCap nhaCungCap = adapter.getItem(position);
        if (dbHelper.hasLaptops(nhaCungCap.getId())) {
            showDeleteConfirmationDialog(position);
        } else {
            dbHelper.deleteNcc(nhaCungCap.getId());
            getDataNcc();
        }

    }
    private void xuLyAlertDialog(int index) {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("Ban co muon xoa");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                xulyXoa(index);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog dialog=builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
    private void showDeleteConfirmationDialog(int index) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Không xóa được vì nhà cung cấp này có laptop");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDataNcc();
    }
}