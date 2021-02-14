package com.example.refereeapp.ui.games;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.refereeapp.Game;
import com.example.refereeapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class newGame extends Fragment {

    private EditText location,league,pay,ref2,ref3,ref4;
    private Button date,time,submit;
    private TextView test;
    private DatePickerDialog.OnDateSetListener datePicker;
    private TimePickerDialog.OnTimeSetListener timePicker;
    private Spinner position;
    private FirebaseFirestore db;
    private CollectionReference games;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    private String stringDate = "";
    Calendar dateTime = Calendar.getInstance();
    int y,m,d,h,min;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        db = FirebaseFirestore.getInstance();
        String loginEmail = getActivity().getIntent().getStringExtra("key");
        View root = inflater.inflate(R.layout.fragment_newgame, container, false);
        location = (EditText) root.findViewById(R.id.gameLocation);
        position = (Spinner) root.findViewById(R.id.gamePos);
        league = (EditText) root.findViewById(R.id.gameLeague);
        pay = (EditText) root.findViewById(R.id.gamePay);
        ref2 = (EditText) root.findViewById(R.id.gameRef2);
        ref3 = (EditText) root.findViewById(R.id.gameRef3);
        ref4 = (EditText) root.findViewById(R.id.gameRef4);
        test = (TextView) root.findViewById(R.id.txtTest);
        date = (Button) root.findViewById(R.id.btnDate);
        time = (Button) root.findViewById(R.id.btnTime);
        submit = (Button) root.findViewById(R.id.btnSubmit);
        String[] pos = new String[]{"Linesman","Referee"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, pos);
        position.setAdapter(adapter);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = Integer.parseInt(pay.getText().toString());
                System.out.println(stringDate);
                Date temp = getDateFromString(stringDate);
                Game game = new Game(temp,location.getText().toString(),league.getText().toString(),i,position.getSelectedItem().toString(),
                    ref2.getText().toString(),ref3.getText().toString(),ref4.getText().toString());
                db.collection("users").document(loginEmail).collection("games").add(game).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        test.setText("Success!");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        test.setText("Failure!");
                    }
                });
            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONDAY);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(getActivity(),android.R.style.Theme_DeviceDefault_Dialog,datePicker,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        datePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                stringDate += year + "-";
                stringDate += month+1 + "-";
                stringDate += dayOfMonth;
                stringDate += "T";
            }
        };

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                TimePickerDialog dialog = new TimePickerDialog(getActivity(),android.R.style.Theme_DeviceDefault_Dialog,timePicker,
                        cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE),false);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        timePicker = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                stringDate += hourOfDay + ":";
                stringDate += minute + ":";
                stringDate +=  "00Z";
            }
        };

        return root;
    }

    public Date getDateFromString(String dateToSave){
        try {
            Date date = format.parse(dateToSave);
            System.out.println("Good!");
            return date;
        } catch (ParseException e){
            System.out.println("Bad!");
            return null;
        }
    }

}