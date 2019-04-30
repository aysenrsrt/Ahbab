package com.example.aysenur.ahbab.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aysenur.ahbab.Model.Medicine;
import com.example.aysenur.ahbab.R;

import java.util.List;


public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.ViewHolder> {

    List<Medicine> medicineList;
    Context context;

    public MedicineAdapter(Context context, List<Medicine> medicineList){
        this.medicineList = medicineList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_medicine, viewGroup, false);

        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.txtMedicineName.setText(medicineList.get(i).getMedicineName());
        viewHolder.txtMedicineDate.setText(medicineList.get(i).getMedicineDate());
        viewHolder.txtMedicineFrequency.setText(medicineList.get(i).getMedicineFrequency());
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView txtMedicineName,txtMedicineDate,txtMedicineFrequency,txtMedicineContinuity;
        public ImageView imgEditMedicine, imgDeleteMedicine;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtMedicineName = itemView.findViewById(R.id.txtMedicineName);
            txtMedicineDate = itemView.findViewById(R.id.txtMedicineDate);
            txtMedicineFrequency = itemView.findViewById(R.id.txtMedicineFrequency);
            txtMedicineContinuity = itemView.findViewById(R.id.txtMedicineContinuity);

            imgEditMedicine = itemView.findViewById(R.id.imgEditMedicine);
            imgDeleteMedicine = itemView.findViewById(R.id.imgDeleteMedicine);
        }

        @Override
        public void onClick(View v) {
        }

    }

    @Override
    public int getItemCount() {
        return medicineList.size();
    }


}
