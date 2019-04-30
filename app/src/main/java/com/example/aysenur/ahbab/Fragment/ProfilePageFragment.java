package com.example.aysenur.ahbab.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.aysenur.ahbab.Adapter.MedicineAdapter;
import com.example.aysenur.ahbab.MainActivity;
import com.example.aysenur.ahbab.Model.Medicine;
import com.example.aysenur.ahbab.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProfilePageFragment extends Fragment{

    private DatabaseReference databaseReferenceMedicines;

    MedicineAdapter medicineAdapter;


    View generalView;
    TextView txtPatientName, txtPatientAge, txtPatientNumber, txtPatientEmail;
    RecyclerView rvMedicineList;
    RecyclerView.LayoutManager mLayoutManager;
    RelativeLayout relAddMedicine;
    private Medicine medicine;

    List<Medicine> listMedicine = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.profile_page, container, false);

        generalView = rootView;
        txtPatientName = generalView.findViewById(R.id.txtPatientName);
        txtPatientAge = generalView.findViewById(R.id.txtPatientAge);
        txtPatientNumber = generalView.findViewById(R.id.txtPatientNumber);
        txtPatientEmail = generalView.findViewById(R.id.txtPatientEmail);
        rvMedicineList = generalView.findViewById(R.id.rvMedicineList);
        relAddMedicine = generalView.findViewById(R.id.relAddMedicine);

        relAddMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMedicine();
            }
        });

        FirebaseDatabase mFirebaseInstance = FirebaseDatabase.getInstance();
        databaseReferenceMedicines = mFirebaseInstance.getReference("medicine");

        databaseReferenceMedicines.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listMedicine.clear();

                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Medicine m = postSnapshot.getValue(Medicine.class);
                    listMedicine.add(m);
                }

                mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                rvMedicineList.setLayoutManager(mLayoutManager);

                medicineAdapter = new MedicineAdapter(getContext(), listMedicine);
                rvMedicineList.setAdapter(medicineAdapter);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        container.removeAllViews();
        return rootView;
    }

    private void addMedicine() {

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        final View dialogView = inflater.inflate(R.layout.dialog_add_medicine, null );
        dialogBuilder.setView(dialogView);

        final EditText txtAddMedicineName = dialogView.findViewById(R.id.txtAddMedicineName);
        final EditText txtAddMedicineDate = dialogView.findViewById(R.id.txtAddMedicineDate);
        final EditText txtAddMedicineFrequency = dialogView.findViewById(R.id.txtAddMedicineFrequency);

        final RadioGroup rdGroup = dialogView.findViewById(R.id.rdGroup);
        int selectedID = rdGroup.getCheckedRadioButtonId();
        final RadioButton rdButton = dialogView.findViewById(selectedID);

        final AlertDialog a = dialogBuilder.create();
        a.show();

        RelativeLayout diaAddMedicine = dialogView.findViewById(R.id.diaAddMedicine);

        diaAddMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseDatabase mFirebaseInstance = FirebaseDatabase.getInstance();
                DatabaseReference mFirebaseDatabase = mFirebaseInstance.getReference("medicine");

                String medicineID = null;
                if (TextUtils.isEmpty(medicineID)) {
                    medicineID = mFirebaseDatabase.push().getKey();
                }

                medicine = new Medicine(txtAddMedicineName.getText().toString(), txtAddMedicineDate.getText().toString(),
                                            txtAddMedicineFrequency.getText().toString());

                mFirebaseDatabase.child(medicineID).setValue(medicine);

                a.dismiss();
            }
        });
    }
 }

