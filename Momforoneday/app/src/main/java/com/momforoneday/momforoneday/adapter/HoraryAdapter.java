package com.momforoneday.momforoneday.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.momforoneday.momforoneday.R;

import java.util.ArrayList;
import java.util.List;

public class HoraryAdapter extends BaseAdapter {

    private final Context mContext;
    private final int totalHorary = 20;
    private List<Integer> segunda;
    private List<Integer> terça;
    private List<Integer> quarta;
    private List<Integer> quinta;
    private List<Integer> sexta;
    private final List<Integer> horarios;


    public HoraryAdapter(Context context, List<Integer> horarios) {
        this.mContext = context;
        this.horarios = horarios;
        setUpLists();
    }

    private void setUpLists(){
        segunda = new ArrayList<>();
        terça = new ArrayList<>();
        quarta = new ArrayList<>();
        quinta = new ArrayList<>();
        sexta = new ArrayList<>();

        segunda.add(0);segunda.add(5);segunda.add(10);segunda.add(15);

        terça.add(1);terça.add(6);terça.add(11);terça.add(16);

        quarta.add(2);quarta.add(7);quarta.add(12);quarta.add(17);

        quinta.add(3);quinta.add(8);quinta.add(13);quinta.add(18);

        sexta.add(4);sexta.add(9);sexta.add(14);sexta.add(19);

    }

    @Override
    public int getCount() {
        return totalHorary;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    public class Holder {
        View horaryBlock;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final Holder holder = new Holder();
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView;
        rowView = inflater.inflate(R.layout.horary_item, null);

        final int pos = position;
        holder.horaryBlock = rowView.findViewById(R.id.block);

        if (horarios.contains(pos))
            holder.horaryBlock.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorOrange));

        holder.horaryBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String time = "";
                String day = "";

                if (pos < 5) {
                    time = "8h-10h";
                } else if (pos < 10) {
                    time = "10h-12h";
                } else if (pos < 15) {
                    time = "14h-16h";
                } else {
                    time = "16h-18h";
                }

                if (segunda.contains(pos)) {
                    day = "SEG";
                } else if (terça.contains(pos)) {
                    day = "TER";
                } else if (quarta.contains(pos)) {
                    day = "QUA";
                } else if (quinta.contains(pos)) {
                    day = "QUI";
                } else if (sexta.contains(pos)) {
                    day = "SEX";
                }

                Toast.makeText(mContext, day + " " + time, Toast.LENGTH_SHORT).show();
            }
        });

        return rowView;
    }

}