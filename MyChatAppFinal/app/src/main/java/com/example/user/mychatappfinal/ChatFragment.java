package com.example.user.mychatappfinal;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment {

    FirebaseDatabase db;
    DatabaseReference ref;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    private FirebaseAuth.AuthStateListener authStateListener;

    View view;
    Button logoutBtn;

    FloatingActionButton actionButtonMsgsend;
    ListView listView;
    EditText msgtxt;

    String userId;
    String stringMsg;
    Record record;
    ArrayList<Record> recordArrayList;
    ListAdapter listAdapter;


    public ChatFragment() {
        // Required empty public constructor
    }

///////google/////////
    @Override
    public void onStart() {
        super.onStart();

        firebaseAuth.addAuthStateListener(authStateListener);
    }
    /////////////////////////////

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_chat, container, false);


        db = FirebaseDatabase.getInstance();
        ref = db.getReference("chatApp");
        recordArrayList = new ArrayList<>();

        firebaseAuth = FirebaseAuth.getInstance();
        //////////google///////
        authStateListener = new FirebaseAuth.AuthStateListener(){

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {



                if (firebaseAuth.getCurrentUser() == null) {

                    startActivity(new Intent(getContext(), MainActivity.class));
                } else {
                    userId = firebaseUser.getUid();
                }
            }
        };
        //////////////////




        firebaseUser = firebaseAuth.getCurrentUser();



        if (firebaseAuth.getCurrentUser() == null) {

            startActivity(new Intent(getContext(), MainActivity.class));
        } else {
            userId = firebaseUser.getUid();//
        }

        /////////////////logout//////////////

        logoutBtn = (Button) view.findViewById(R.id.idLogoutBtn);

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (view == logoutBtn){

                    firebaseAuth.signOut();

                    startActivity(new Intent(getContext(), MainActivity.class));
                }
            }
        });



        ///////////////////////////////////

        listView = (ListView) view.findViewById(R.id.idListView);

        msgtxt = (EditText) view.findViewById(R.id.msgtxtId);


        /////////insert///////////////
        actionButtonMsgsend = (FloatingActionButton) view.findViewById(R.id.sendBtnId);
        actionButtonMsgsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                stringMsg = msgtxt.getText().toString().trim();
                record = new Record("uidName", stringMsg, ref.push().getKey().toString(), userId, 000);
                ref.child(userId).child(record.getKey()).setValue(record);
                msgtxt.setText("");
            }
        });

///////////////////////////////////

        //////////////////delete//////////////////




        /////////////////////////////////////////

        ref.child(userId).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
///////////////////////////read/////////////////////
                record = dataSnapshot.getValue(Record.class);
                recordArrayList.add(record);
                listAdapter = new ListAdapter(recordArrayList, getContext());
                listAdapter.notifyDataSetChanged();
                listView.setAdapter(listAdapter);
            }
///////////////////////read//////////////////
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        // Inflate the layout for this fragment
        return view;
    }

}
