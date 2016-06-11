package me.zhang.art.ipc.messenger;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import static android.widget.LinearLayout.VERTICAL;

/**
 * Created by Li on 6/11/2016 6:06 PM.
 */
public class MessengerClientActivity extends AppCompatActivity {

    private static final int MSG_FROM_CLIENT = 0x00000110;
    private static final String MSG = "msg";
    private Messenger messenger;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout container = new LinearLayout(this);
        container.setOrientation(VERTICAL);

        Button sendToServer = new Button(this);
        sendToServer.setText("Send message to server");
        sendToServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Send message to server
                Message msg = Message.obtain(null, MSG_FROM_CLIENT);
                // msg.obj = new Book("Book " + System.currentTimeMillis(), System.currentTimeMillis());
                Bundle data = new Bundle();
                data.putString(MSG, "Hello, this is client, can you hear me?");
                msg.setData(data);

                try {
                    messenger.send(msg);
                    Toast.makeText(MessengerClientActivity.this, "Message is already sent", Toast.LENGTH_SHORT).show();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        container.addView(sendToServer);

        setContentView(container);
    }

    @Override
    protected void onResume() {
        super.onResume();
        bindService(new Intent("me.zhang.art.ipc.messenger.MessengerService"), conn, BIND_AUTO_CREATE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unbindService(conn);
    }

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            messenger = new Messenger(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            messenger = null;
        }
    };

}
