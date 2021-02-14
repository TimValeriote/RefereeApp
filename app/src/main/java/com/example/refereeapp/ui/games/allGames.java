package com.example.refereeapp.ui.games;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.refereeapp.Game;
import com.example.refereeapp.R;
import com.example.refereeapp.myAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class allGames extends Fragment {
    private FirebaseFirestore db;
    private ArrayList<Game> gamesList = new ArrayList<>();
    private RecyclerView gameContainer;
    private Button getGames,showGames;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        db = FirebaseFirestore.getInstance();
        View root = inflater.inflate(R.layout.fragment_allgames, container, false);
        String loginEmail = getActivity().getIntent().getStringExtra("key");
        Game allGames[] = new Game[10];
        gameContainer = (RecyclerView) root.findViewById(R.id.gameContainer);
        getGames = (Button) root.findViewById(R.id.btnGetGames);
        showGames = (Button) root.findViewById(R.id.btnShowGames);

        CollectionReference games = db.collection("users").document(loginEmail).collection("games");

        getGames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                games.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int i = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Timestamp timestamp = (Timestamp) document.getData().get("date");
                                Date date = timestamp.toDate();
                                gamesList.add(document.toObject(Game.class));
                                gamesList.get(i).setDateTime(date);
                                i++;
                            }
                            setDisplay(gamesList);
                        } else {
                            System.out.println("Something went wrong ");
                            return;
                        }
                    }
                });
            }
        });

        showGames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        
        return root;
    }

    public void setDisplay(ArrayList<Game> games){
        myAdapter myAdapter = new myAdapter(getActivity(),games);
        gameContainer.setAdapter(myAdapter);
        gameContainer.setLayoutManager(new LinearLayoutManager(getActivity()));
    }


}
