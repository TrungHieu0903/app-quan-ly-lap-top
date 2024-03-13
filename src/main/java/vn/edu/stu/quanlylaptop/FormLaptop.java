package vn.edu.stu.quanlylaptop;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import Dao.DBHelper;
import adapter.LaptopAdapter;
import model.Laptop;
import model.NhaCungCap;
import util.DBConfigUtil;

public class FormLaptop extends AppCompatActivity {
    static final int REQUEST_CHOOSE_PHOTO=321;
    ImageView imageLaptop;
    Spinner spinner;
    EditText etTenLaptop,etPrice,etSoLuong,etMota;
    DBHelper dbHelper;

    ArrayList<NhaCungCap> dsNhaCungCap = new ArrayList<>();
    ArrayAdapter<String> spinnerTen;
    Button btnLuu,btnTroVe,btnChon;
    Laptop laptop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_laptop);
        Intent intent = getIntent();
        laptop = (Laptop) intent.getSerializableExtra("laptop");
        dbHelper=new DBHelper(FormLaptop.this);
        DBConfigUtil.copyDatabaseFromAssets(FormLaptop.this);
        addControls();
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
            Intent intent = new Intent(FormLaptop.this, about.class);
            startActivity(intent);
            finish();
        }
        else if (item.getItemId()==R.id.mnuExit){
            Intent intent = new Intent(FormLaptop.this, DanhSachLaptop.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    private void addEvents() {
        btnChon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePhoto();
            }
        });
        btnTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FormLaptop.this, DanhSachLaptop.class);
                startActivity(intent);
                finish();
            }
        });
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(laptop!=null){
                    laptop.setTen(etTenLaptop.getText().toString());
                    laptop.setPrice(Double.parseDouble(etPrice.getText().toString()));
                    laptop.setQuantity(Integer.parseInt(etSoLuong.getText().toString()));
                    laptop.setMota(etMota.getText().toString());
                    laptop.setImage(getBytesFromImageView(imageLaptop));
                    dbHelper.updateLaptop(laptop);
                }
                else {
                    laptop=new Laptop();
                    laptop.setTen(etTenLaptop.getText().toString());
                    laptop.setPrice(Double.parseDouble(etPrice.getText().toString()));
                    laptop.setQuantity(Integer.parseInt(etSoLuong.getText().toString()));
                    laptop.setMota(etMota.getText().toString());
                    laptop.setImage(getBytesFromImageView(imageLaptop));
                    dbHelper.addLaptop(laptop);
                }
                Intent intent = new Intent(FormLaptop.this, DanhSachLaptop.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void addControls() {
        this.spinner = (Spinner) findViewById(R.id.spinner);
        etTenLaptop = findViewById(R.id.etTenLaptop);
        etPrice=findViewById(R.id.etPrice);
        etSoLuong=findViewById(R.id.etSoLuong);
        etMota=findViewById(R.id.etMota);
        btnLuu= findViewById(R.id.btnLuu);
        btnTroVe= findViewById(R.id.btnTroVe);
        btnChon=findViewById(R.id.btnChon);
        imageLaptop=(ImageView) findViewById(R.id.imageLaptop);
        if(laptop!=null){
            etTenLaptop.setText(laptop.getTen());
            etPrice.setText(laptop.getPrice()+"");
            etSoLuong.setText(laptop.getQuantity()+"");
            etMota.setText(laptop.getMota());
            Bitmap bmImageLaptop= BitmapFactory.decodeByteArray(laptop.getImage(),0,laptop.getImage().length);
            imageLaptop.setImageBitmap(bmImageLaptop);
            selectedAdapterNcc();
        }
        else {
            hienthiAdapterNcc();
        }
    }
    private void selectedAdapterNcc(){
        dsNhaCungCap=(ArrayList<NhaCungCap>) dbHelper.getAllNhaCungCap();
        ArrayList<String> dsTenNCC = new ArrayList<>();
        int pos = -1;
        for(int i=0;i<dsNhaCungCap.size();i++) {
            dsTenNCC.add(dsNhaCungCap.get(i).getTen());
            if(dsNhaCungCap.get(i).getId()==laptop.getIdncc()){
                pos = i;
            }
        }
        spinnerTen = new ArrayAdapter(FormLaptop.this, android.R.layout.simple_spinner_dropdown_item, dsTenNCC);
        spinnerTen.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerTen);
        spinner.setSelection(pos);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                laptop.setIdncc(dsNhaCungCap.get(i).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void hienthiAdapterNcc(){
        dsNhaCungCap=(ArrayList<NhaCungCap>) dbHelper.getAllNhaCungCap();
        ArrayList<String> ds = new ArrayList<>();
        for(int i=0;i<dsNhaCungCap.size();i++)
            ds.add(dsNhaCungCap.get(i).getTen());
        spinnerTen = new ArrayAdapter(FormLaptop.this, android.R.layout.simple_spinner_dropdown_item, ds);
        spinnerTen.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerTen);
    }
    private void choosePhoto(){
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,REQUEST_CHOOSE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if (requestCode==REQUEST_CHOOSE_PHOTO){
                try {
                    Uri imageUri=data.getData();
                    InputStream inputStream=getContentResolver().openInputStream(imageUri);
                    Bitmap bitmap= BitmapFactory.decodeStream(inputStream);
                    imageLaptop.setImageBitmap(bitmap);
                }catch (FileNotFoundException e){
                    e.printStackTrace();
                }
            }
        }
    }
    private byte[] getBytesFromImageView(ImageView imageLaptop) {
        BitmapDrawable drawable=(BitmapDrawable) imageLaptop.getDrawable();
        Bitmap bitmap=drawable.getBitmap();
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] byteArray=stream.toByteArray();
        return byteArray;
    }
}