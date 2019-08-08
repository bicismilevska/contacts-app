package com.example.biljanasapp;

import android.app.Activity;
import android.arch.persistence.room.Update;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    public static final String TAG="UserAdapter";
    List<User> users;
    Context context;
    AppDatabase ad;
    public UserAdapter(AppDatabase ad,List<User> users,Context context)
    {     this.ad=ad;
        this.context=context;
        this.users=users;
        Collections.sort(users, new Comparator<User>() {
            @Override
            public int compare(User lhs, User rhs) {
                return lhs.getFirstname().compareTo(rhs.getFirstname());
            }
        });
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate((R.layout.rowdesign),viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final UserAdapter.ViewHolder viewHolder, int i) {
      viewHolder.textView.setText(users.get(i).getFirstname());
      viewHolder.textView2.setText(users.get(i).getLastname());
      viewHolder.textView3.setText(users.get(i).getNumber());

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        TextView  textView2;
        TextView textView3;
        LinearLayout layout;
        ImageButton call;
        ImageButton update;
        ImageButton delete;
         int position;
        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            textView=itemView.findViewById(R.id.first);
            textView2=itemView.findViewById(R.id.last);
            textView3=itemView.findViewById(R.id.phone);
            layout=itemView.findViewById(R.id.bibe);
            update=itemView.findViewById(R.id.edit);
            call=itemView.findViewById(R.id.call);
            delete=itemView.findViewById(R.id.delete);
            call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String s=textView3.getText().toString();
                    Log.i(TAG,"Clicced calling"+ s);
                    Uri number = Uri.parse("tel:"+s);
                    Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                    view.getContext().startActivity(callIntent);
                }
            });

            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i(TAG,"here i am");
                     position=users.get(getAdapterPosition()).getId();
                    Log.i(TAG,"is "+position + " "+users.get(getAdapterPosition()).getFirstname());
                    Intent intente = new Intent(view.getContext(), UpdateUser.class);
                    intente.putExtra("id",position);
                    view.getContext().startActivity(intente);
                   // Log.i(TAG,"update button clicked clicked position " + users.get(getAdapterPosition()).getId());
                   // UpdateDialog ud=new UpdateDialog();
                //  ud.show(((AppCompatActivity) context).getSupportFragmentManager(),"Update Contact");
                   // Log.i(TAG,"what is happening");

                }
            });
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog alertDialog = new AlertDialog.Builder(view.getContext()).create();
                      alertDialog.setTitle("Delete Contact");
                  alertDialog.setMessage("Are you sure you want to delete this contact?");
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES",
                new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int which) {
                       position=users.get(getAdapterPosition()).getId();
                       ad.userDao().deleteUser(position);
                       List<User> users=ad.userDao().getAllUsers();
                       for (int i = 0; i <users.size() ; i++) {
                           Log.i("DeleteUser", users.get(i).getFirstname());
                       }
                       //users.remove(position);
                       notifyItemChanged(position);
                      context.startActivity(new Intent(context,MainActivity.class));
                    }
               });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alertDialog.show();


                }
            });
        }

    }
}
