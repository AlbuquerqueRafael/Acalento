package com.momforoneday.momforoneday.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.momforoneday.momforoneday.R;
import com.momforoneday.momforoneday.controller.FirebaseController;
import com.momforoneday.momforoneday.fragment.CaregiverDetailFragment;
import com.momforoneday.momforoneday.fragment.ContractListFragment;
import com.momforoneday.momforoneday.fragment.ContractsFragment;
import com.momforoneday.momforoneday.model.Caregiver;
import com.momforoneday.momforoneday.model.Contract;
import com.momforoneday.momforoneday.model.User;
import com.momforoneday.momforoneday.service.AppService;
import com.momforoneday.momforoneday.util.CircleTransform;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gabrielguimo on 22/03/17.
 */

public class CaregiverAdapter extends RecyclerView.Adapter {

    private List<Caregiver> caregiverList;
    private Context context;
    private List<Integer> segunda;
    private List<Integer> terça;
    private List<Integer> quarta;
    private List<Integer> quinta;
    private List<Integer> sexta;

    public CaregiverAdapter(List<Caregiver> caregiverList, Context context) {
        this.caregiverList = caregiverList;
        this.context = context;

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
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_caregiver, parent, false);

        MyHolder holder = new MyHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        MyHolder holder = (MyHolder) viewHolder;

        final Caregiver currentCaregiver = caregiverList.get(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppService.setSelectedCaregiver(currentCaregiver);
                AppService.setPreviousFragment(new ContractListFragment());

                android.support.v4.app.FragmentTransaction fragmentTransaction =
                        ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content, new CaregiverDetailFragment());
                fragmentTransaction.commit();
            }
        });

        holder.caregiverName.setText(currentCaregiver.getName());
        holder.caregiverAge.setText(String.valueOf(currentCaregiver.getAge()) + " anos");

        holder.caregiverImg.setImageBitmap(AppService.getImage());

        holder.contractCaregiver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppService.getContractedCaregiver() == null) {
                    showContractDialog(currentCaregiver);
                } else {
                    Toast.makeText(context, "Você já possui um contrato ativo!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.caregiverList.size();
    }

    private void showContractDialog(final Caregiver currentCaregiver){
        final Dialog inputDialog = new Dialog(context);
        inputDialog.setContentView(R.layout.contract_dialog);

        final RadioGroup radioGroup = (RadioGroup) inputDialog.findViewById(R.id.radio_group);
        final Button okButton = (Button) inputDialog.findViewById(R.id.ok_button);
        final Button cancelButton = (Button) inputDialog.findViewById(R.id.cancel_button);

        List<Integer> horarios = currentCaregiver.getSchedules();

        for (Integer sc: horarios) {
            String text = getTextFromSchedule(sc);
            RadioButton rb = new RadioButton(context);
            rb.setId(sc);
            rb.setText(text);
            radioGroup.addView(rb);
        }

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int radioButtonID = radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) radioGroup.findViewById(radioButtonID);

                contractCaregiver(currentCaregiver, radioButton.getText().toString());
                inputDialog.dismiss();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputDialog.dismiss();
            }
        });

        inputDialog.show();
    }

    private String getTextFromSchedule(int sc) {
        String time = "";
        String day = "";

        if (sc < 5) {
            time = "8h às 10h";
        } else if (sc < 10) {
            time = "10h às 12h";
        } else if (sc < 15) {
            time = "14h às 16h";
        } else {
            time = "16h às 18h";
        }

        if (segunda.contains(sc)) {
            day = "Segunda";
        } else if (terça.contains(sc)) {
            day = "Terça";
        } else if (quarta.contains(sc)) {
            day = "Quarta";
        } else if (quinta.contains(sc)) {
            day = "Quinta";
        } else if (sexta.contains(sc)) {
            day = "Sexta";
        }

        String result = day + ": " + time;

        return result;
    }

    private void contractCaregiver(Caregiver currentCaregiver, String schedule){

        User currentUser = AppService.getCurrentUser();
        Contract contract = new Contract(currentCaregiver.getName(), currentUser, schedule);

        FirebaseController.setContract(contract);

        currentCaregiver.setContract(contract);
        AppService.setContractedCaregiver(currentCaregiver);

        Toast.makeText(context, "Contratou " + currentCaregiver.getName() + " para " + schedule, Toast.LENGTH_SHORT).show();

    }

}

class MyHolder extends RecyclerView.ViewHolder {

    final TextView caregiverName;
    final TextView caregiverAge;
    final ImageView caregiverImg;
    final AppCompatButton contractCaregiver;

    public MyHolder(View view) {
        super(view);

        caregiverName = (TextView) view.findViewById(R.id.caregiver_name);
        caregiverAge = (TextView) view.findViewById(R.id.caregiver_age);
        contractCaregiver = (AppCompatButton) view.findViewById(R.id.contract_btn);
        caregiverImg = (ImageView) view.findViewById(R.id.caregiver_img);

    }

}