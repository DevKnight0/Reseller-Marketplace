package com.example.valarmorghulis.firebaseauth;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatClass> {
    private Context mContext;
    private List<Chat> mChat;
    FirebaseAuth mAuth;

    public ChatAdapter(Context context, List<Chat> Chat) {
        mContext = context;
        mChat = Chat;
        NetworkConnection networkConnection = new NetworkConnection();
        if (networkConnection.isConnectedToInternet(mContext)
                || networkConnection.isConnectedToMobileNetwork(mContext)
                || networkConnection.isConnectedToWifi(mContext)) {

        } else {
            networkConnection.showNoInternetAvailableErrorDialog(mContext);
            return;
        }

    }

    @NonNull
    @Override
    public ChatClass onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.chat_layout, viewGroup, false);
        mAuth= FirebaseAuth.getInstance();
        return new ChatClass(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatClass chatClass, int i) {
        Chat chat= mChat.get(i);
        String email= chat.getEmailId();
        String message= chat.getMyText();
        String user= mAuth.getCurrentUser().getEmail();
        if(email!= user){
            chatClass.my.setVisibility(View.GONE);
            chatClass.seller.setText(message);

        }else{
            chatClass.seller.setVisibility(View.GONE);
            chatClass.my.setText(message);
        }
    }

    @Override
    public int getItemCount() {
        return mChat.size();
    }

    public class ChatClass extends RecyclerView.ViewHolder{
        public TextView seller;
        public TextView my;

        public ChatClass(@NonNull View itemView) {
            super(itemView);
            seller= itemView.findViewById(R.id.textview_chat_sent);
            my=itemView.findViewById(R.id.textview_chat_received);
        }
    }
}
