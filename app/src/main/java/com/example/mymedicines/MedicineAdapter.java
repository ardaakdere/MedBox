package com.example.mymedicines;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import java.util.List;

public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.CardViewDesignObjectsHolder>{
    private Context mContext;
    private List<Medicine> medicineList;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    public MedicineAdapter(Context mContext, List<Medicine> medicineList) {
        this.mContext = mContext;
        this.medicineList = medicineList;
    }

    @NonNull
    @Override
    public CardViewDesignObjectsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_design,parent, false);
        return new CardViewDesignObjectsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final CardViewDesignObjectsHolder holder, int position) {
        //İlacın tarihinin geçip geçmediği kontrol ediliyor.
        final Medicine medicine = medicineList.get(position);
        /*Date currentTime = Calendar.getInstance().getTime();
        String dateStr = medicine.getExp_date();
        Date dateObj = null;
        SimpleDateFormat curFormater = new SimpleDateFormat("dd/MM/yyyy");
        try {
            dateObj = curFormater.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
        // currentTime.after(dateObj) false döndürürse tarihi geçmemiş, true döndürür ise tarihi geçmiş anlamına gelecek.
        // Tarihi geçmiş ise firebase database de state i out of date olarak ayarlanacak.
        final DatabaseReference ref = database.getReference("led"+(position+1));
        final DatabaseReference ref_piece = database.getReference("Pieces/piece"+(position+1));
        final DatabaseReference ref_days = database.getReference("Gunler/day"+(position+1));
        final DatabaseReference ref_dates = database.getReference("Dates/date"+(position+1));
        final DatabaseReference ref_names = database.getReference("Names/name"+(position+1));


        //Log.e("karsilastir",String.valueOf(currentTime.after(dateObj)));
        /*if(!currentTime.after(dateObj) )
        {

        }else if(currentTime.after(dateObj))
        {
            //eğer trye dönerse tarihi geçmiş demektir bu durumda cardview state kısmı kırmızı renge dönecek ve objenin date_state değeri false yapılacak. Ve database deki değeri değiştirilecek.
            // database de tarihi geçmiş ilacın ledinin değerini 2 yapıyoruz böylelikle yanıp sönebilir.
            ref.setValue("2");
            holder.state_textView.setBackgroundColor(Color.RED);
            medicine.setDate_state(false);
        }*/
        //-------------------------------------------
        //Firebase realtime database 'den ışıkların on/off durumu çekiliyor. Ve uygulamada gösteriliyor.
        //out of date kontrolu sadece uygulama tarafında yapılıyor!!!!
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = snapshot.getValue(String.class);
                if(medicine.isDate_state()) {
                    Log.e("güzel", medicine.getMedicine_name());
                    holder.state_textView.setText((value.equals("1")) ? "ON" : "OFF");
                    if (value.equals("0"))
                    {
                        medicine.setState(false);
                    }else if(value.equals("1"))
                    {
                        medicine.setState(true);
                    }
                }
                else {
                    holder.state_textView.setText("OUT OF DATE");
                    medicine.setState(false);
                }

                Log.e("value",value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ref_piece.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = snapshot.getValue(String.class);
                medicine.setPiece(Integer.parseInt(value));
                holder.piece_textView.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ref_days.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = snapshot.getValue(String.class);
                medicine.setDays(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ref_dates.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = snapshot.getValue(String.class);
                medicine.setExp_date(value);
                Date currentTime = Calendar.getInstance().getTime();
                String dateStr = medicine.getExp_date();
                Date dateObj = null;
                SimpleDateFormat curFormater = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    dateObj = curFormater.parse(dateStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if(!currentTime.after(dateObj) )
                {
                    medicine.setDate_state(true);
                    //medicine.setState(true);
                }else if(currentTime.after(dateObj))
                {
                    //eğer trye dönerse tarihi geçmiş demektir bu durumda cardview state kısmı kırmızı renge dönecek ve objenin date_state değeri false yapılacak. Ve database deki değeri değiştirilecek.
                    // database de tarihi geçmiş ilacın ledinin değerini 2 yapıyoruz böylelikle yanıp sönebilir.
                    ref.setValue("2");
                    holder.state_textView.setBackgroundColor(Color.RED);
                    holder.state_textView.setText("OUT OF DATE");
                    medicine.setDate_state(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ref_names.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = snapshot.getValue(String.class);
                medicine.setMedicine_name(value);
                holder.medicineName_textView.setText(medicine.getMedicine_name());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //holder.state_textView.setText(String.valueOf(medicine.isState()));
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(mContext,v);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.cardmenu,popup.getMenu());
                popup.show();

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId())
                        {
                            case R.id.action_turn:
                                if(medicine.isState())
                                {
                                    ref.setValue("0");
                                    notifyDataSetChanged();
                                }else
                                {
                                    ref.setValue("1");
                                    notifyDataSetChanged();
                                }
                                break;
                            case R.id.action_edit:
                                Intent intent = new Intent(mContext.getApplicationContext(),MedicineEdit.class);
                                intent.putExtra("name", medicine.getMedicine_name());
                                intent.putExtra("exp_date", medicine.getExp_date());
                                intent.putExtra("piece", medicine.getPiece());
                                intent.putExtra("days", medicine.getDays());
                                intent.putExtra("led_id", medicine.getLed_id());
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                mContext.startActivity(intent);
                                break;
                        }
                        return false;
                    }
                });
            }
        });
        //-----------------------
    }

    @Override
    public int getItemCount() {
        return medicineList.size();
    }

    public class CardViewDesignObjectsHolder extends RecyclerView.ViewHolder {
        public TextView medicineName_textView;
        public TextView state_textView;
        public TextView piece_textView;
        public CardView cv;

        public CardViewDesignObjectsHolder(@NonNull View itemView) {
            super(itemView);
            medicineName_textView = itemView.findViewById(R.id.medicineName_textView);
            state_textView = itemView.findViewById(R.id.state_textView);
            piece_textView = itemView.findViewById(R.id.piece_textView);
            cv = itemView.findViewById(R.id.cv);
        }
    }
}
