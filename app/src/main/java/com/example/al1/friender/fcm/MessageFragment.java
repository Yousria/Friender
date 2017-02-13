package com.example.al1.friender.fcm;



import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.al1.friender.R;
import com.example.al1.friender.server.GetToken;
import com.example.al1.friender.server.SendMessageRequest;

import java.util.concurrent.ExecutionException;

import static com.example.al1.friender.R.id.pseudo;


public class MessageFragment extends Fragment {

    public MessageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_message, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences dest = this.getActivity().getSharedPreferences("destinataires", 0);
        final String pseudo = dest.getString("dest1", "nothing");
        System.out.println("pseudo="+pseudo);
        Button button = (Button) getView().findViewById(R.id.envoyer_message);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String token = new GetToken().execute(pseudo).get();
                    System.out.println(token);
                    EditText editText = (EditText) getView().findViewById(R.id.message);
                    String message = editText.getText().toString();
                    new SendMessageRequest().execute(token, message);
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
