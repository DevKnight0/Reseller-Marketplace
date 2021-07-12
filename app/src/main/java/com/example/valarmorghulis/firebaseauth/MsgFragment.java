package com.example.valarmorghulis.firebaseauth;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MsgFragment extends Fragment implements View.OnClickListener {

    private EditText editTextMessage;
    private Button buttonSend;
    private String sName;
    private String sEmail;
   private String pName;
    private String bName;
    private String bEmail;
    private Upload upload;
//    private DatabaseReference mDatabaseRef;
//    private List<Chat> mChat;
//    private ValueEventListener mDBListener;
//
//    private RecyclerView mRecyclerView;
//    private ChatAdapter mAdapter;
//    private static final String TAG = "Message";
//    private String[] mArray;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_msg, container, false);
        editTextMessage = (EditText) v.findViewById(R.id.editTextMessage);
        buttonSend = (Button) v.findViewById(R.id.buttonSend);
        buttonSend.setOnClickListener(this);

        Bundle bundle = getArguments();
        if(bundle != null){

            upload = (Upload) bundle.getSerializable("upload");
            sName = upload.getUserName();
            sEmail = upload.getEmail();
            bEmail = bundle.getString("bEmail");
            bName = bundle.getString("bName");
            pName = upload.getName();
        }



//        mDatabaseRef = FirebaseDatabase.getInstance().getReference("chat");
//
//        String myUser= getContatedString(bEmail);
//        String otherUser = getContatedString(sEmail);
//        String [] emailArray={myUser,otherUser};
//        mArray=emailArray;
//        Arrays.sort(mArray);
//        Toast.makeText(getActivity(),mArray[0]+"-"+mArray[1], Toast.LENGTH_SHORT).show();
//        mRecyclerView = v.findViewById(R.id.recycleView);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//
//
//        mChat = new ArrayList<>();
//
//        mAdapter = new ChatAdapter(getActivity(), mChat);
//
//        mRecyclerView.setAdapter(mAdapter);
//
//        mDBListener = mDatabaseRef.child(mArray[0]+"-"+mArray[1]).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                mChat.clear();
//
//                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                    Chat chat = postSnapshot.getValue(Chat.class);
//                    Log.v(TAG, chat.toString());
//                    mChat.add(chat);
//                }
//
//                mAdapter.notifyDataSetChanged();
//
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
        return v;
    }
//    private void update(){
//
//    }
    private String getContatedString(String s){
        int eid= s.indexOf('@');
        String seid= s.substring(0,eid);
        return seid ;
    }
    private void sendEmail() {
        String [] emails = {sEmail};
        String subject = "[2HM] Querry regarding product " + pName;
        String message = editTextMessage.getText().toString().trim() + "\n\nsent by: " + bName + "(" + bEmail + ")\n" ;
//        String message1 =editTextMessage.getText().toString().trim();
//        SendMail sm = new SendMail(getActivity(), email, subject, message);
//        sm.execute();
        Intent intent = new Intent(Intent.ACTION_SEND);

        intent.putExtra(Intent.EXTRA_EMAIL, emails);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        intent.setType("message/rfc822");
        intent.setPackage("com.google.android.gm");
        startActivity(intent);

//
//       Chat chat = new Chat(message1,bEmail);
//        String uploadId = mDatabaseRef.push().getKey();
//
//        mDatabaseRef.child(mArray[0]+"-"+mArray[1]).child(uploadId).setValue(chat);
//        update();

    }

    @Override
    public void onClick(View v) {
        if(editTextMessage.getText().toString().length() < 1){
            editTextMessage.setError("Message can't be empty.");
            editTextMessage.requestFocus();
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Message");
        builder.setMessage("Your message will be send as an email to the owner of this product");

        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sendEmail();
                editTextMessage.setText("");
            }
        });

        builder.setNegativeButton("Discard", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                editTextMessage.setText("");
                Toast.makeText(getActivity(),"Message Discarded", Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog ad = builder.create();
        ad.show();

    }
    @Override
    public void onStart() {
        super.onStart();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();

    }
    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();



    }
}
