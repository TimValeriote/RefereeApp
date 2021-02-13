package com.example.refereeapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.example.refereeapp.R;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomeFragment extends Fragment {

    private TextView email,first,last,league;
    private FirebaseFirestore db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        db = FirebaseFirestore.getInstance();
        String loginEmail = getActivity().getIntent().getStringExtra("key");
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        email = (TextView) root.findViewById(R.id.userEmail);
        first = (TextView) root.findViewById(R.id.userFirst);
        last = (TextView) root.findViewById(R.id.userLast);
        league = (TextView) root.findViewById(R.id.userLeague);

        DocumentReference docRef = db.collection("users").document(loginEmail);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    email.setText(loginEmail);
                    first.setText(document.getString("firstName"));
                    last.setText(document.getString("lastName"));
                    league.setText(document.getString("league"));
                } else {
                    Toast.makeText(getContext(),"Something went wrong!",Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

        return root;
    }
}
