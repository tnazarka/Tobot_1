package com.example.tobot;


import android.app.MediaRouteButton;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;



public class MainActivity extends AppCompatActivity implements
        CompoundButton.OnCheckedChangeListener,
        AdapterView.OnItemClickListener,
        View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName ();
    // private static File FILE_NAME = new File ("test.txt");
    private final static String FILE_NAME = "test.txt";
    public static final int REQUEST_CODE_LOC = 1;
    static int[] twoD = new int[16];


    private static final int REQ_ENABLE_BT = 10;
    public static final int BT_BOUNDED = 21;
    public static final int BT_SEARCH = 22;

    LinkedList<String> NumberPozichion = new LinkedList<> ();


    private static String text_vse = new String ("123");

    public static final int LED_RED = 30;
    public static final int LED_GREEN = 31;
    public static String Connect_chek = "";

    private FrameLayout frameMessage;
    private LinearLayout frameControls;
    private RelativeLayout frame_dance;
    private RelativeLayout programing;
    private RelativeLayout frameusercontrol1;
    private RelativeLayout frameLedControls;

    private Button btnDisconnect;
    private Switch switchRedLed;
    private Switch switchGreenLed;
    private Button button_2, button2, button3, button5, button6, button7, button8, button9, user_control_1, user_control_2, user_control_3, button_dance, button_over, button_over2, button_over3, btn_open_text, btn_save_text, button_over4;
    private TextView Algoritme, data, servo_0, servo_1, servo_2, servo_3, servo_4, servo_5, servo_6, servo_7, servo_8, servo_9, servo_10, servo_11, servo_12, servo_13, servo_14, servo_15;

    private Switch switchEnableBt;
    private Button btnEnableSearch;
    private ProgressBar pbProgress;
    private ListView listBtDevices;
    private Spinner spinner;

    private BluetoothAdapter bluetoothAdapter;
    private BtListAdapter listAdapter;
    private ArrayList<BluetoothDevice> bluetoothDevices;

    private ConnectThread connectThread;
    private ConnectedThread connectedThread;
    private MediaRouteButton switch_enable_bt;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);

        frameMessage = findViewById (R.id.frame_message);
        frameControls = findViewById (R.id.frame_control);

        switchEnableBt = findViewById (R.id.switch_enable_bt);
        btn_open_text = findViewById (R.id.btn_openText);
        btn_save_text = findViewById (R.id.btn_save_text);
        button2 = findViewById (R.id.button2);
        button_2 = findViewById (R.id.button_2);
        button3 = findViewById (R.id.button3);
        button5 = findViewById (R.id.button5);
        button6 = findViewById (R.id.button6);
        button7 = findViewById (R.id.button7);
        button8 = findViewById (R.id.button8);
        button9 = findViewById (R.id.button9);
        button_over4 = findViewById (R.id.button_over4);
        user_control_1 = findViewById (R.id.user_control_1);
        user_control_2 = findViewById (R.id.user_control_2);
        user_control_3 = findViewById (R.id.user_control_3);
        button_dance = findViewById (R.id.button_dance);
        button_over = findViewById (R.id.button_over);
        // button_over2 = findViewById (R.id.button_over2);
        button_over3 = findViewById (R.id.button_over3);

        NumberPozichion.add (0, "1");
        NumberPozichion.add (1, "2");
        NumberPozichion.add (2, "3");
        NumberPozichion.add (3, "4");


        Algoritme = findViewById (R.id.Algoritme);
        data = findViewById (R.id.data);
        servo_0 = findViewById (R.id.servo_0);
        servo_1 = findViewById (R.id.servo_1);
        servo_2 = findViewById (R.id.servo_2);
        servo_3 = findViewById (R.id.servo_3);
        servo_4 = findViewById (R.id.servo_4);
        servo_5 = findViewById (R.id.servo_5);
        servo_6 = findViewById (R.id.servo_6);
        servo_7 = findViewById (R.id.servo_7);
        servo_8 = findViewById (R.id.servo_8);
        servo_9 = findViewById (R.id.servo_9);
        servo_10 = findViewById (R.id.servo_10);
        servo_11 = findViewById (R.id.servo_11);
        servo_12 = findViewById (R.id.servo_12);
        servo_13 = findViewById (R.id.servo_13);
        servo_14 = findViewById (R.id.servo_14);
        servo_15 = findViewById (R.id.servo_15);


        btnEnableSearch = findViewById (R.id.btn_enable_search);
        pbProgress = findViewById (R.id.pb_progress);
        listBtDevices = findViewById (R.id.lv_bt_device);

        frameusercontrol1 = findViewById (R.id.frameusercontrol1);
        frame_dance = findViewById (R.id.frame_dance);
        frameLedControls = findViewById (R.id.frameLedControls);
        programing = findViewById (R.id.programing);
        btnDisconnect = findViewById (R.id.btn_disconnect);
        switchGreenLed = findViewById (R.id.switch_led_green);
        switchRedLed = findViewById (R.id.switch_led_red);


        switchEnableBt.setOnCheckedChangeListener (this);
        btnEnableSearch.setOnClickListener (this);
        listBtDevices.setOnItemClickListener (this);


        btnDisconnect.setOnClickListener (this);
        btn_save_text.setOnClickListener (this);
        btn_open_text.setOnClickListener (this);
        button2.setOnClickListener (this);
        button_2.setOnClickListener (this);
        button3.setOnClickListener (this);
        button5.setOnClickListener (this);
        button6.setOnClickListener (this);
        button7.setOnClickListener (this);
        button8.setOnClickListener (this);
        button9.setOnClickListener (this);
        user_control_1.setOnClickListener (this);
        user_control_2.setOnClickListener (this);
        user_control_3.setOnClickListener (this);
        button_dance.setOnClickListener (this);
        button_over.setOnClickListener (this);
        //button_over2.setOnClickListener (this);
        button_over3.setOnClickListener (this);
        button_over4.setOnClickListener (this);



        spinner = findViewById (R.id.spinner);
        // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String> (this, android.R.layout.simple_spinner_item, NumberPozichion);
        // Определяем разметку для использования при выборе элемента
        adapter.setDropDownViewResource (android.R.layout.simple_spinner_dropdown_item);
        // Применяем адаптер к элементу spinner
        spinner.setAdapter (adapter);
        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener () {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // Получаем выбранный объект
                String item = (String) parent.getItemAtPosition (position);
                Algoritme.setText(item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
        spinner.setOnItemSelectedListener (itemSelectedListener);




    progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable (false);
        progressDialog.setTitle (getString(R.string.connect));
        progressDialog.setMessage (getString(R.string.plise_weit));


        switchGreenLed.setOnCheckedChangeListener (this);
        switchRedLed.setOnCheckedChangeListener (this);

        bluetoothDevices = new ArrayList<> ();

        IntentFilter filter = new IntentFilter ();
        filter.addAction (BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction (BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        filter.addAction (BluetoothDevice.ACTION_FOUND);
        registerReceiver (receiver, filter);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter ();

        if (bluetoothAdapter == null) {
            Toast.makeText (this, R.string.bluetooth_not_supported, Toast.LENGTH_SHORT).show ();
            Log.d (TAG, "onCreate: " + getString (R.string.bluetooth_not_supported));
            finish ();
        }

        if (bluetoothAdapter.isEnabled ()) {
            showFrameControls ();
            switchEnableBt.setChecked (true);
            setListAdapter (BT_BOUNDED);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy ();

        unregisterReceiver (receiver);

        if (connectThread != null) {
            connectThread.cancel ();
        }

        if (connectedThread != null) {
            connectedThread.cancel ();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {
        if (v.equals (btnEnableSearch)) {
            enableSearch ();
        } else if (v.equals (btnDisconnect)) {
            // TODO отключение от устройства
            if (connectedThread != null) {
                connectedThread.cancel ();
            }

            showFrameControls ();
        } else if (v.equals (button2)) {
            if (connectedThread != null) {
                String command = "1";
                connectedThread.write (command);
            }
        } else if (v.equals (button_2)) {
            if (connectedThread != null) {
                String command = "2";
                connectedThread.write (command);
            }
        } else if (v.equals (button3)) {
            if (connectedThread != null) {
                String command = "3";
                connectedThread.write (command);
            }
        } else if (v.equals (button5)) {
            if (connectedThread != null) {
                String command = "$333;";
                connectedThread.write (command);
            }
        } else if (v.equals (button6)) {
            if (connectedThread != null) {
                String command = "5";
                connectedThread.write (command);
            }
        } else if (v.equals (button7)) {
            if (connectedThread != null) {
                String command = "6";
                connectedThread.write (command);
            }
        } else if (v.equals (button8)) {
            if (connectedThread != null) {
                String command = "7";
                connectedThread.write (command);
            }
        } else if (v.equals (button9)) {
            if (connectedThread != null) {
                String command = "8";
                connectedThread.write (command);
            }
        } else if (v.equals (button_dance)) {
            if (connectedThread != null) {
                String command = "$444;";
                connectedThread.write (command);
            }
        } else if (v.equals (user_control_3)) {
            showFrameLedControls_2 ();
        } else if (v.equals (button_over)) {
            showFrameLedControls ();
//        } else if (v.equals (button_over2)) {
//            showFrameLedControls ();
        }
        else if (v.equals (button_over3)) {
            showFrameLedControls ();
        }
        else if (v.equals (button_over4)) {
            showFrameLedControls ();
        }else if (v.equals (user_control_1)) {
            showFrameLedControls_1 ();
        } else if (v.equals (user_control_2)) {
            showFrameLedControls_3 ();
        } else if (v.equals (btn_open_text)) {
            openText ();

        } else if (v.equals (btn_save_text)) {
            saveText (v);
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (parent.equals (listBtDevices)) {
            BluetoothDevice device = bluetoothDevices.get (position);
            if (device != null) {
                connectThread = new ConnectThread (device);
                connectThread.start ();
            }
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.equals (switchEnableBt)) {
            enableBt (isChecked);

            if (!isChecked) {
                showFrameMessage ();
            }

        } else if (buttonView.equals (switchRedLed)) {
            // TODO включение или отключение красного светодиода (11111)
            enableLed (LED_GREEN, isChecked);
        } else if (buttonView.equals (switchGreenLed)) {
            // TODO включение или отключение зеленого светодиода
            enableLed (LED_GREEN, isChecked);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQ_ENABLE_BT) {
            if (resultCode == RESULT_OK && bluetoothAdapter.isEnabled ()) {
                showFrameControls ();
                setListAdapter (BT_BOUNDED);
            } else if (resultCode == RESULT_CANCELED) {
                enableBt (true);
            }
        }
    }

    private void showFrameMessage() {
        frameMessage.setVisibility (View.VISIBLE);// показываем сообщенеие о том ,что ты лох
        frameLedControls.setVisibility (View.GONE);
        frameControls.setVisibility (View.GONE);
        switchEnableBt.setVisibility (View.VISIBLE);
    }

    private void showFrameControls() {
        frameMessage.setVisibility (View.GONE);
        frameLedControls.setVisibility (View.GONE);
        frameControls.setVisibility (View.VISIBLE);//показываем список усройств
        switchEnableBt.setVisibility (View.GONE);
    }

    private void showFrameLedControls() {
        frameusercontrol1.setVisibility (View.VISIBLE);//то ,что после подклбчения
        frameLedControls.setVisibility (View.GONE);     //денсы ,джойстики и т.д
        switchEnableBt.setVisibility (View.GONE);
        frameMessage.setVisibility (View.GONE);
        frameControls.setVisibility (View.GONE);
        frame_dance.setVisibility (View.GONE);
        programing.setVisibility (View.GONE);
    }

    private void showFrameLedControls_1() {
        frameusercontrol1.setVisibility (View.GONE);
        frameLedControls.setVisibility (View.VISIBLE);//показываем джойстик панель
        switchEnableBt.setVisibility (View.GONE);
        frameMessage.setVisibility (View.GONE);
        programing.setVisibility (View.GONE);
        frameControls.setVisibility (View.GONE);
        frame_dance.setVisibility (View.GONE);
    }

    private void showFrameLedControls_2() {
        frameusercontrol1.setVisibility (View.GONE);
        frameLedControls.setVisibility (View.GONE);
        switchEnableBt.setVisibility (View.GONE);
        frameMessage.setVisibility (View.GONE);
        frameControls.setVisibility (View.GONE);
        frame_dance.setVisibility (View.VISIBLE);
        programing.setVisibility (View.GONE);
    }

    private void showFrameLedControls_3() {
        frameusercontrol1.setVisibility (View.GONE);
        frameLedControls.setVisibility (View.GONE);
        switchEnableBt.setVisibility (View.GONE);
        frameMessage.setVisibility (View.GONE);
        programing.setVisibility (View.VISIBLE);
        frameControls.setVisibility (View.GONE);
        frame_dance.setVisibility (View.GONE);
    }

    private void enableBt(boolean flag) {
        if (flag) {
            Intent intent = new Intent (BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult (intent, REQ_ENABLE_BT);
        } else {
            bluetoothAdapter.disable ();
        }
    }

    private void setListAdapter(int type) {

        bluetoothDevices.clear ();
        int iconType = R.drawable.ic_bluetooth_bounded_device;

        switch (type) {
            case BT_BOUNDED:
                bluetoothDevices = getBoundedBtDevices ();
                iconType = R.drawable.ic_bluetooth_bounded_device;
                break;
            case BT_SEARCH:
                iconType = R.drawable.ic_bluetooth_search_device;
                break;
        }
        listAdapter = new BtListAdapter (this, bluetoothDevices, iconType);
        listBtDevices.setAdapter (listAdapter);
    }

    private ArrayList<BluetoothDevice> getBoundedBtDevices() {
        Set<BluetoothDevice> deviceSet = bluetoothAdapter.getBondedDevices ();
        ArrayList<BluetoothDevice> tmpArrayList = new ArrayList<> ();
        if (deviceSet.size () > 0) {
            for (BluetoothDevice device : deviceSet) {
                tmpArrayList.add (device);
            }
        }

        return tmpArrayList;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void enableSearch() {
        if (bluetoothAdapter.isDiscovering ()) {
            bluetoothAdapter.cancelDiscovery ();
        } else {
            accessLocationPermission ();
            bluetoothAdapter.startDiscovery ();
        }
    }

    private BroadcastReceiver receiver = new BroadcastReceiver () {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction ();

            switch (action) {
                case BluetoothAdapter.ACTION_DISCOVERY_STARTED:
                    btnEnableSearch.setText (R.string.stop_search);
                    pbProgress.setVisibility (View.VISIBLE);
                    setListAdapter (BT_SEARCH);
                    break;
                case BluetoothAdapter.ACTION_DISCOVERY_FINISHED:
                    btnEnableSearch.setText (R.string.start_search);
                    pbProgress.setVisibility (View.GONE);
                    break;
                case BluetoothDevice.ACTION_FOUND:
                    BluetoothDevice device = intent.getParcelableExtra (BluetoothDevice.EXTRA_DEVICE);
                    if (device != null) {
                        bluetoothDevices.add (device);
                        listAdapter.notifyDataSetChanged ();
                    }
                    break;
            }
        }
    };

    /**
     * Запрос на разрешение данных о местоположении (для Marshmallow 6.0)
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void accessLocationPermission() {
        int accessCoarseLocation = this.checkSelfPermission (android.Manifest.permission.ACCESS_COARSE_LOCATION);
        int accessFineLocation = this.checkSelfPermission (android.Manifest.permission.ACCESS_FINE_LOCATION);

        List<String> listRequestPermission = new ArrayList<String> ();

        if (accessCoarseLocation != PackageManager.PERMISSION_GRANTED) {
            listRequestPermission.add (android.Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (accessFineLocation != PackageManager.PERMISSION_GRANTED) {
            listRequestPermission.add (android.Manifest.permission.ACCESS_FINE_LOCATION);
        }

        if (!listRequestPermission.isEmpty ()) {
            String[] strRequestPermission = listRequestPermission.toArray (new String[listRequestPermission.size ()]);
            this.requestPermissions (strRequestPermission, REQUEST_CODE_LOC);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_LOC:

                if (grantResults.length > 0) {
                    for (int gr : grantResults) {
                        // Check if request is granted or not
                        if (gr != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                    }
                    //TODO - Add your code here to start Discovery
                }
                break;
            default:
                return;
        }
    }

    private class ConnectThread extends Thread {

        private BluetoothSocket bluetoothSocket = null;
        private boolean success = false;

        public ConnectThread(BluetoothDevice device) {
            try {
                Method method = device.getClass ().getMethod ("createRfcommSocket", new Class[]{int.class});
                bluetoothSocket = (BluetoothSocket) method.invoke (device, 1);
                progressDialog.show ();
            } catch (Exception e) {
                e.printStackTrace ();
            }
        }

        @Override
        public void run() {
            try {
                bluetoothSocket.connect ();
                success = true;
                progressDialog.dismiss ();
            } catch (IOException e) {
                e.printStackTrace ();

                runOnUiThread (new Runnable () {
                    @Override
                    public void run() {
                        progressDialog.dismiss ();
                        Toast.makeText (MainActivity.this, "Не могу соединиться!", Toast.LENGTH_SHORT).show ();
                    }
                });

                cancel ();
            }

            if (success) {
                connectedThread = new ConnectedThread (bluetoothSocket);
                connectedThread.start ();

                runOnUiThread (new Runnable () {
                    @Override
                    public void run() {
                        showFrameLedControls ();
                    }
                });
            }
        }

        public boolean isConnect() {
            return bluetoothSocket.isConnected ();
        }

        public void cancel() {
            try {
                bluetoothSocket.close ();
            } catch (IOException e) {
                e.printStackTrace ();
            }
        }
    }

    private class ConnectedThread extends Thread {

        private final InputStream inputStream;
        private final OutputStream outputStream;

        private boolean isConnected = false;

        public ConnectedThread(BluetoothSocket bluetoothSocket) {
            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                inputStream = bluetoothSocket.getInputStream ();
                outputStream = bluetoothSocket.getOutputStream ();
            } catch (IOException e) {
                e.printStackTrace ();
            }

            this.inputStream = inputStream;
            this.outputStream = outputStream;
            isConnected = true;
        }

        @Override
        public void run() {
            BufferedInputStream bis = new BufferedInputStream (inputStream);
            StringBuffer buffer = new StringBuffer ();
            final StringBuffer sbConsol = new StringBuffer ();
            final ScrollingMovementMethod movementMethod = new ScrollingMovementMethod ();

            while (isConnected){
                try {
                    int bytes = bis.read ();
                    buffer.append ((char)bytes);
                    int eof = buffer.indexOf ("\r\n");
                    if(eof > 0){
                        sbConsol.append (buffer.toString ());
                        buffer.delete (0,buffer.length ());

                        runOnUiThread (new Runnable () {
                            @Override
                            public void run() {
                                Connect_chek = String.valueOf (sbConsol);
                                Algoritme.setText (sbConsol.toString ());
                                Algoritme.setMovementMethod (new ScrollingMovementMethod ());
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace ();
                }
            }
            try {
                bis.close ();
            }catch (IOException e){
                e.printStackTrace ();
            }

        }

        public void write(String command) {
            byte[] bytes = command.getBytes ();
            if (outputStream != null) {
                try {
                    outputStream.write (bytes);
                    outputStream.flush ();
                } catch (IOException e) {
                    e.printStackTrace ();
                }
            }
        }


        public void cancel() {
            try {
                isConnected = false;
                inputStream.close ();
                outputStream.close ();
            } catch (IOException e) {
                e.printStackTrace ();
            }
        }
    }

    public void enableLed(int led, boolean state) {
        if (connectedThread != null && connectThread.isConnect ()) {
            String command = "";

            switch (led) {
                case LED_RED:
                    command = (state) ? "red on#" : "red off#";
                    break;
                case LED_GREEN:
                    command = (state) ? "green on#" : "green off#";
                    break;
            }

            connectedThread.write (command);
        }
    }

    public void saveText(View view) {



        String text_0 = servo_0.getText ().toString ();
        String text_1 = servo_1.getText ().toString ();
        String text_2 = servo_2.getText ().toString ();
        String text_3 = servo_3.getText ().toString ();
        String text_4 = servo_4.getText ().toString ();
        String text_5 = servo_5.getText ().toString ();
        String text_6 = servo_6.getText ().toString ();
        String text_7 = servo_7.getText ().toString ();
        String text_8 = servo_8.getText ().toString ();
        String text_9 = servo_9.getText ().toString ();
        String text_10 = servo_10.getText ().toString ();
        String text_11 = servo_11.getText ().toString ();
        String text_12 = servo_12.getText ().toString ();
        String text_13 = servo_13.getText ().toString ();
        String text_14 = servo_14.getText ().toString ();
        String text_15 = servo_15.getText ().toString ();

        String text_vse = "$"+"222"+"," + text_0 + "," + text_1 + "," + text_2 + "," + text_3 + "," + text_4 + "," + text_5 + "," + text_6 + "," + text_7 + "," + text_8 + "," + text_9 + "," + text_10 + "," + text_11 + "," + text_12 + "," + text_13 + "," + text_14 + "," + text_15 + ";";
        Toast.makeText (this, text_vse, Toast.LENGTH_SHORT).show ();
        connectedThread.write (text_vse);

        FileOutputStream fos = null;
        try {


            fos = openFileOutput (FILE_NAME,MODE_PRIVATE);
            fos.write (text_vse.getBytes ());
            fos.write ("\n".getBytes ());
            Toast.makeText (this, "Файл сохранен", Toast.LENGTH_SHORT).show ();
        } catch (IOException ex) {

            Toast.makeText (this, ex.getMessage (), Toast.LENGTH_SHORT).show ();
        } finally {
            try {
                if (fos != null)
                    fos.close ();
            } catch (IOException ex) {

                Toast.makeText (this, ex.getMessage (), Toast.LENGTH_SHORT).show ();
            }
        }
    }


    private void openText() {
        FileInputStream fin = null;

        try {
            fin = openFileInput (FILE_NAME);
            byte[] bytes = new byte[fin.available ()];
            fin.read (bytes);
            String text = new String (bytes);
            Algoritme.setText (text);


        } catch (IOException ex) {

            Toast.makeText (this, ex.getMessage (), Toast.LENGTH_SHORT).show ();
        } finally {

            try {
                if (fin != null)
                    fin.close ();
            } catch (IOException ex) {

                Toast.makeText (this, ex.getMessage (), Toast.LENGTH_SHORT).show ();
            }
        }
        if (connectedThread != null) {
            connectedThread.write (text_vse);

        }
    }


    }