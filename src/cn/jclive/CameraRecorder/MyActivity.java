package cn.jclive.CameraRecorder;

import java.net.InetAddress;
import java.net.UnknownHostException;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import tv.inhand.streaming.Session;
import tv.inhand.streaming.SessionBuilder;



public class MyActivity extends Activity implements SurfaceHolder.Callback, OnClickListener {
    private static final String TAG = "JCameara";

    private Button start;
    private Button stop;
    private Session session;
    private SurfaceView surfaceview;
    private SurfaceHolder surfaceHolder;
  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.main2);
        surfaceview = (SurfaceView) this.findViewById(R.id.surfaceview);
        surfaceHolder = surfaceview.getHolder();
        surfaceHolder.addCallback(this);
        // We still need this line for backward compatibility reasons with android 2
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);


        start = (Button) this.findViewById(R.id.start);
        stop = (Button) this.findViewById(R.id.stop);  
        start.setOnClickListener(this);
        stop.setOnClickListener(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        surfaceHolder = holder;
        try {
            SessionBuilder.getInstance()
                    .setContext(getApplicationContext())
                    .setSurfaceHolder(surfaceHolder)
                    .setHost("192.168.50.19")
                    .setAppName("hls").build();
        } catch (Exception e) {
            Log.e(TAG, "Can't build session", e);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void onClick(View v) {
        if (v == start) {
            try {
                session = SessionBuilder.getInstance().build();
                session.setStreamName("android");
                session.start();
            } catch (Exception e) {
                Log.e(TAG, "video session", e);
            }

        }
        if (v == stop) {
            if (session != null) {
                session.stop();
            }
        }
    }
}  