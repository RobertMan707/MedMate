package com.example.medmate;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.MedicineViewHolder> {
    private List<Medicine> medicineList;

    public MedicineAdapter(List<Medicine> medicineList) {
        this.medicineList = medicineList;
    }

    @NonNull
    @Override
    public MedicineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_medicine, parent, false);
        return new MedicineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicineViewHolder holder, int position) {
        Medicine medicine = medicineList.get(position);

        holder.nameTextView.setText(medicine.getName());
        holder.dosageTextView.setText("Dosage: " + medicine.getDosage());
        holder.timeTextView.setText("Time: " + medicine.getTimeSelection());
        holder.typeTextView.setText("Type: " + medicine.getMedicineType());
        holder.daysFrequencyTextView.setText("Frequency: " + medicine.getDaysFrequency());

        int stock = Integer.parseInt(medicine.getMedicineStock());
        int lowStockReminder = Integer.parseInt(medicine.getLowStockReminder());
        if (stock <= lowStockReminder) {
            holder.lowStockWarningTextView.setVisibility(View.VISIBLE);
            holder.lowStockWarningTextView.setText("Low Stock! Reminder set for " + medicine.getLowStockReminder());
        } else {
            holder.lowStockWarningTextView.setVisibility(View.GONE);
        }

        holder.checkbox.setChecked(medicine.isTaken());
        holder.checkbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            medicine.setTaken(isChecked);
            markMedicineAsTaken(medicine);
        });
    }

    @Override
    public int getItemCount() {
        return medicineList.size();
    }

    private void markMedicineAsTaken(Medicine medicine) {
        // TODO: Implement logic to update Firebase when a medicine is marked as taken
    }

    public static class MedicineViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, dosageTextView, timeTextView, typeTextView, daysFrequencyTextView, lowStockWarningTextView;
        CheckBox checkbox;

        public MedicineViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.medicine_name);
            dosageTextView = itemView.findViewById(R.id.medicine_dosage);
            timeTextView = itemView.findViewById(R.id.medicine_time);
            typeTextView = itemView.findViewById(R.id.medicine_type);
            daysFrequencyTextView = itemView.findViewById(R.id.medicine_days_frequency);
            lowStockWarningTextView = itemView.findViewById(R.id.medicine_low_stock_warning);
            checkbox = itemView.findViewById(R.id.medicine_checkbox);
        }
    }
}