package vn.edu.stu.quanlylaptop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class Menu extends AppCompatActivity {
    Button btnDsLaptop;
    Button btnDsNhaCungCap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        addControls();
        addEvents();
    }

    private void addControls() {
        btnDsLaptop =findViewById(R.id.btnLaptop);
        btnDsNhaCungCap=findViewById(R.id.btnNhaCungCap);
    }

    private void addEvents() {
        btnDsNhaCungCap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, DanhSachNhaCungCap.class);
                startActivity(intent);
                finish();
            }
        });
        btnDsLaptop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, DanhSachLaptop.class);
                startActivity(intent);
                finish();
            }
        });
    }
}