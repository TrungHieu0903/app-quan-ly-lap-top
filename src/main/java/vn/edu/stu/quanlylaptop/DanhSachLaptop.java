package vn.edu.stu.quanlylaptop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import Dao.DBHelper;
import adapter.LaptopAdapter;
import model.Laptop;
import model.NhaCungCap;
import util.DBConfigUtil;

public class DanhSachLaptop extends AppCompatActivity {
    DBHelper dbHelper;
    ListView lvLaptop;
    LaptopAdapter adapter;
    Button btnThem;
    Button btnTroVe;
    ArrayList<Laptop> dsLaptop=new ArrayList<>();
    ArrayList<NhaCungCap> dsNhaCungCap=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_laptop);
        DBConfigUtil.copyDatabaseFromAssets(DanhSachLaptop.this);
        addControls();
        dbHelper=new DBHelper(DanhSachLaptop.this);
        addEvents();
        hienthiDanhsachLaptop();
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
            Intent intent = new Intent(DanhSachLaptop.this, about.class);
            startActivity(intent);
            finish();
        }
        else if (item.getItemId()==R.id.mnuExit){
            Intent intent = new Intent(DanhSachLaptop.this, Menu.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    private void hienthiDanhsachLaptop() {
        dsLaptop =(ArrayList<Laptop>) dbHelper.getAllLaptop();
        dsNhaCungCap=(ArrayList<NhaCungCap>)dbHelper.getAllNhaCungCap();
        adapter = new LaptopAdapter(
                DanhSachLaptop.this,
                R.layout.custom_listview,
                dsLaptop,
                dbHelper,
                dsNhaCungCap
        );
        lvLaptop.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    private void addEvents() {
        btnTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DanhSachLaptop.this, Menu.class);
                startActivity(intent);
                finish();
            }
        });
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DanhSachLaptop.this, FormLaptop.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void addControls() {
        lvLaptop=findViewById(R.id.lvDsLaptop);
        btnTroVe=findViewById(R.id.btnTroVe);
        btnThem=findViewById(R.id.btnThem);
    }
}