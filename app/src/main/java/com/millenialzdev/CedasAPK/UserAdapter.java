package com.millenialzdev.CedasAPK;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.List;

public class UserAdapter extends ArrayAdapter<User>  {

//    public UserAdapter(Context context, List<User> objects) {
//        super(context, 0, objects);
//    }
public UserAdapter(Context context, List<User> objects){
    super (context, 0, objects);
}

    @NonNull
    @Override
    public View getView (int position, @Nullable View convertView, @NonNull ViewGroup parent){
        User user = getItem(position);
        if (convertView == null){
            convertView = LayoutInflater.from((getContext())).inflate(R.layout.item_user, parent, false);
        }

        TextView txtemail = convertView.findViewById(R.id.email);
        TextView txtnama = convertView.findViewById(R.id.nama);
        TextView pssword = convertView.findViewById(R.id.pass);
        TextView alam = convertView.findViewById(R.id.alamat);
        TextView jenisk = convertView.findViewById(R.id.jk);
        TextView nohap = convertView.findViewById(R.id.nohp);
        TextView lahir = convertView.findViewById(R.id.tglahir);

        txtemail.setText(user.getEmail());
        txtnama.setText(user.getUsername());
        pssword.setText(user.getPassword());
        alam.setText(user.getAlamat());
        jenisk.setText(user.getJenis_kelamin());
        nohap.setText(user.getNo_hp());
        lahir.setText(user.getTgl_lahir());



        return convertView;

    }

}

