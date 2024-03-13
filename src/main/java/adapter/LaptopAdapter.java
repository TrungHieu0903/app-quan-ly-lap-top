package adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import Dao.DBHelper;
import model.Laptop;
import model.NhaCungCap;
import vn.edu.stu.quanlylaptop.FormLaptop;
import vn.edu.stu.quanlylaptop.R;

public class LaptopAdapter extends ArrayAdapter<Laptop> {
    Activity context;
    int resource;
    List<Laptop> laptops;
    DBHelper dbHelper;

    ArrayList<NhaCungCap> dsNhaCungCap;


    public LaptopAdapter(Activity context, int resource, List<Laptop> laptops, DBHelper dbHelper, ArrayList<NhaCungCap> dsNhaCungCap) {
        super(context, resource, laptops);
        this.context = context;
        this.resource =  resource;
        this.laptops = laptops;
        this.dbHelper = dbHelper;
        this.dsNhaCungCap = dsNhaCungCap;
    }

    public String getTenNhaCungCap(int ma, ArrayList<NhaCungCap> ds){
        for (NhaCungCap ncc : ds) {
            if(ncc.getId()==ma) {
                return ncc.getTen();
            }
        }
        return null;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View item = inflater.inflate(this.resource, null);
        ImageView imageLaptop=(ImageView)item.findViewById(R.id.imageLaptops);
        TextView txtIdLaptop = item.findViewById(R.id.tvIdLaptop);
        TextView txtTenLapTop = item.findViewById(R.id.tvTenLaptop);
        TextView txtNhaCungCap = item.findViewById(R.id.tvNhaCungCap);
        TextView txtPrice = item.findViewById(R.id.tvPrice);
        final Button btnSua = item.findViewById(R.id.btnSua);
        final Button btnXoa = item.findViewById(R.id.btnXoa);
        final Laptop laptop = laptops.get(position);
        txtIdLaptop.setText(laptop.getId()+"");
        txtTenLapTop.setText(laptop.getTen());
        txtNhaCungCap.setText(getTenNhaCungCap(laptop.getIdncc(), dsNhaCungCap));
        txtPrice.setText(laptop.getFormattedPrice());
        Bitmap bmImageLaptop= BitmapFactory.decodeByteArray(laptop.getImage(),0,laptop.getImage().length);
        imageLaptop.setImageBitmap(bmImageLaptop);
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Intent = new Intent(context, FormLaptop.class);
                Laptop lt=laptops.get(position);
                Intent.putExtra("dsNhaCungCap",dsNhaCungCap);
                Intent.putExtra("laptop", lt);
                context.startActivityForResult(Intent,789);
            }
        });
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xác nhận xóa");
                builder.setMessage("Bạn có chắc muốn xóa lap top này?");

                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbHelper.deleteLaptop(laptops.get(position).getId());
                        laptops.remove(position);
                        notifyDataSetChanged();
                    }
                });

                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        return item;
    }
}
