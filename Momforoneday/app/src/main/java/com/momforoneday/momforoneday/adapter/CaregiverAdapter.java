package com.momforoneday.momforoneday.adapter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.momforoneday.momforoneday.R;
import com.momforoneday.momforoneday.fragment.CaregiverDetailFragment;
import com.momforoneday.momforoneday.fragment.ContractListFragment;
import com.momforoneday.momforoneday.fragment.ContractsFragment;
import com.momforoneday.momforoneday.model.Caregiver;
import com.momforoneday.momforoneday.model.Contract;
import com.momforoneday.momforoneday.model.User;
import com.momforoneday.momforoneday.service.AppService;

import java.util.List;

/**
 * Created by gabrielguimo on 22/03/17.
 */

public class CaregiverAdapter extends RecyclerView.Adapter {

    private List<Caregiver> caregiverList;
    private Context context;

    public CaregiverAdapter(List<Caregiver> caregiverList, Context context) {
        this.caregiverList = caregiverList;
        this.context = context;
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

        holder.contractCaregiver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Contract contract = new Contract(currentCaregiver, new User("Gabriel", "Gabrielg@gmail.com"));
                currentCaregiver.setContract(contract);
                AppService.setContractedCaregiver(currentCaregiver);

                Toast.makeText(context, "Contratou o " + currentCaregiver.getName(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return this.caregiverList.size();
    }
}

class MyHolder extends RecyclerView.ViewHolder {

    final TextView caregiverName;
    final TextView caregiverAge;
    final AppCompatButton contractCaregiver;

    public MyHolder(View view) {
        super(view);

        caregiverName = (TextView) view.findViewById(R.id.caregiver_name);
        caregiverAge = (TextView) view.findViewById(R.id.caregiver_age);
        contractCaregiver = (AppCompatButton) view.findViewById(R.id.contract_btn);

    }

}