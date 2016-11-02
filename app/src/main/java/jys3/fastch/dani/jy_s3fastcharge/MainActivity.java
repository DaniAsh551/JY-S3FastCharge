package jys3.fastch.dani.jy_s3fastcharge;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import java.io.*;
import java.lang.Process;

public class MainActivity extends AppCompatActivity {
    String sysrw = "mount -o rw,remount,rw /system";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button Initd = (Button)findViewById(R.id.btnInitd);
        TextView instat = (TextView)findViewById(R.id.tvInitd);
        java.io.File inscript = new File("/system/etc/init.d/01fastcharge");
        TextView status = (TextView) findViewById(R.id.txvStatus);
        EditText ac = (EditText)findViewById(R.id.etCha);
        EditText usb = (EditText)findViewById(R.id.etUSB);
        try{
            java.lang.Process p = Runtime.getRuntime().exec("su");
            DataOutputStream outs = new DataOutputStream(p.getOutputStream());
            outs.writeBytes(sysrw + "\n");
        }catch(Exception e){
            e.printStackTrace();
        }
        java.io.File file = new File("/sys/kernel/charge_levels/quick_charge_enable");
        if (inscript.exists()){instat.setText("init.d script found :D");}
        if (!inscript.exists()){
            instat.setText("init.d script not found, press the button below to add");
            Initd.setVisibility(View.VISIBLE);
        }
        if (file.exists()) {
            try {
                FileInputStream fstream = new FileInputStream(file);
                BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
                String strLine = br.readLine();
                if(strLine.equals("0")){
                    status.setText("Fast Charge available, but disabled");
                }
                if(strLine.equals("1")){
                    status.setText("Fast Charge available, and enabled");
                }
                Button btnToggle = (Button)findViewById(R.id.btnToggle);
                btnToggle.setVisibility(View.VISIBLE);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (!file.exists()) {
            status.setText("Fast Charge unavailable, your kernel probably doesnt support it");
            Button btnToggle = (Button)findViewById(R.id.btnToggle);
            btnToggle.setVisibility(View.INVISIBLE);
        }
        file = new File("/sys/kernel/charge_levels/charge_level_ac");
        if(file.exists()){
            try {
                FileInputStream fstream = new FileInputStream(file);
                BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
                String strLine = br.readLine();
                ac.setText(strLine);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (!file.exists()) {
            Toast.makeText(getApplicationContext(),"ERR: AC Charge Level not found",Toast.LENGTH_SHORT).show();
        }

        file = new File("/sys/kernel/charge_levels/charge_level_usb");
        if(file.exists()){
            try {
                FileInputStream fstream = new FileInputStream(file);
                BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
                String strLine = br.readLine();
                usb.setText(strLine);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (!file.exists()) {
            Toast.makeText(getApplicationContext(),"ERR: USB Charge Level not found",Toast.LENGTH_SHORT).show();
        }
    }

    public void onCheckButtonClick(View view) {
        TextView status = (TextView) findViewById(R.id.txvStatus);
        EditText ac = (EditText)findViewById(R.id.etCha);
        EditText usb = (EditText)findViewById(R.id.etUSB);
        java.io.File file = new File("/sys/kernel/charge_levels/quick_charge_enable");
        if (file.exists()) {
            try {
                FileInputStream fstream = new FileInputStream(file);
                BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
                String strLine = br.readLine();
                if(strLine.equals("0")){
                    status.setText("Fast Charge available, but disabled");
                }
                if(strLine.equals("1")){
                    status.setText("Fast Charge available, and enabled");
                }
                Button btnToggle = (Button)findViewById(R.id.btnToggle);
                btnToggle.setVisibility(View.VISIBLE);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (!file.exists()) {
            status.setText("Fast Charge unavailable, your kernel probably doesnt support it");
            Button btnToggle = (Button)findViewById(R.id.btnToggle);
            btnToggle.setVisibility(View.INVISIBLE);
        }
        file = new File("/sys/kernel/charge_levels/charge_level_ac");
        if (file.exists()){
            try {
                FileInputStream fstream = new FileInputStream(file);
                BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
                String strLine = br.readLine();
                ac.setText(strLine);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        file = new File("/sys/kernel/charge_levels/charge_level_usb");
        if (file.exists()){
            try {
                FileInputStream fstream = new FileInputStream(file);
                BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
                String strLine = br.readLine();
                usb.setText(strLine);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void onbtnToggleClick(View view){
        TextView status = (TextView) findViewById(R.id.txvStatus);
        String fcstatus = status.getText().toString();
        if(fcstatus.equals("Fast Charge available, but disabled")){
            try{
                java.lang.Process p = Runtime.getRuntime().exec("su");
                DataOutputStream outs = new DataOutputStream(p.getOutputStream());
                String cmd = "rm /sys/kernel/charge_levels/quick_charge_enable";
                outs.writeBytes(cmd + "\n");
                cmd = "echo 1 > /sys/kernel/charge_levels/quick_charge_enable";
                outs.writeBytes(cmd + "\n");
                status.setText("Fast Charge available, and enabled");
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        if(fcstatus.equals("Fast Charge available, and enabled")){
            try{
                java.lang.Process p = Runtime.getRuntime().exec("su");
                DataOutputStream outs = new DataOutputStream(p.getOutputStream());
                String cmd = "rm /sys/kernel/charge_levels/quick_charge_enable";
                outs.writeBytes(cmd + "\n");
                cmd = "echo 0 > /sys/kernel/charge_levels/quick_charge_enable";
                outs.writeBytes(cmd + "\n");
                status.setText("Fast Charge available, but disabled");
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public void onbtnSetClick (View view){
        EditText ac = (EditText)findViewById(R.id.etCha);
        EditText usb = (EditText)findViewById(R.id.etUSB);
        String accurrent = new String(ac.getText().toString());
        String usbcurrent = new String(usb.getText().toString());
        try{
            java.lang.Process p = Runtime.getRuntime().exec("su");
            DataOutputStream outs = new DataOutputStream(p.getOutputStream());
            outs.writeBytes(sysrw + "\n");
        }catch(Exception e){
            e.printStackTrace();
        }
        try{
            java.lang.Process p = Runtime.getRuntime().exec("su");
            DataOutputStream outs = new DataOutputStream(p.getOutputStream());
            String cmd = "rm /sys/kernel/charge_levels/charge_level_ac";
            outs.writeBytes(cmd + "\n");
            cmd = "echo " + accurrent + " > /sys/kernel/charge_levels/charge_level_ac";
            outs.writeBytes(cmd + "\n");
        }catch(Exception e){
            e.printStackTrace();
        }
        try{
            java.lang.Process p = Runtime.getRuntime().exec("su");
            DataOutputStream outs = new DataOutputStream(p.getOutputStream());
            String cmd = "rm /sys/kernel/charge_levels/charge_level_usb";
            outs.writeBytes(cmd + "\n");
            cmd = "echo " + usbcurrent + " > /sys/kernel/charge_levels/charge_level_usb";
            outs.writeBytes(cmd + "\n");
        }catch(Exception e){
            e.printStackTrace();
        }
        String initdscript = "echo -e " + "\"" + "#!/system/bin/sh\n#call userinit.sh if present in /data/local\necho " + accurrent + " > /sys/kernel/charge_levels/charge_level_ac\necho " + usbcurrent + " > /sys/kernel/charge_levels/charge_level_usb" + "\" >> /system/etc/init.d/01fastcharge";
        try{
            java.lang.Process p = Runtime.getRuntime().exec("su");
            DataOutputStream outs = new DataOutputStream(p.getOutputStream());
            outs.writeBytes(sysrw + "\n");
        }catch(Exception e){
            e.printStackTrace();
        }
        try{
            java.lang.Process p = Runtime.getRuntime().exec("su");
            DataOutputStream outs = new DataOutputStream(p.getOutputStream());
            outs.writeBytes("rm /system/etc/init.d/01fastcharge" + "\n");
        }catch(Exception e){
            e.printStackTrace();
        }
        try{
            java.lang.Process p = Runtime.getRuntime().exec("su");
            DataOutputStream outs = new DataOutputStream(p.getOutputStream());
            String cmd = initdscript;
            outs.writeBytes(cmd + "\n");
            cmd = "chmod 755 /system/etc/init.d/01fastcharge";
            outs.writeBytes(cmd + "\n");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void onInitdClick (View view) {
        TextView instat = (TextView)findViewById(R.id.tvInitd);
        File f = new File("/sdcard/01fastcharge");
		if (f.exists()){
			f.delete();
		}
		try {
			FileWriter f0 = new FileWriter(f);
			String ac = new String("1700");
			String usb = new String("1000");

			String newLine = System.getProperty("line.separator");
			f0.write("#!/system/bin/sh" + newLine);
			f0.write("echo 1 > /sys/kernel/charge_levels/quick_charge_enable" + newLine);
			f0.write("echo " + ac + " > /sys/kernel/charge_levels/charge_level_ac" + newLine);
			f0.write("echo " + usb + " > /sys/kernel/charge_levels/charge_level_usb");
			f0.close();
		}catch (IOException e) {
			e.printStackTrace();
		}
        try{
            java.lang.Process p = Runtime.getRuntime().exec("su");
            DataOutputStream outs = new DataOutputStream(p.getOutputStream());
            String cmd = "mv /sdcard/01fastcharge /system/etc/init.d/01fastcharge";
            outs.writeBytes(cmd + "\n");
            cmd = "chmod 755 /system/etc/init.d/01fastcharge";
            outs.writeBytes(cmd + "\n");
        }catch(Exception e){
            e.printStackTrace();
        }
        instat.setText("init.d script has been written, it is now recommended to reboot");
    }

    public void btnNoinitClick (View view)
    {
        Intent i = new Intent(this, set_on_boot_settings.class);
        startActivity(i);
    }
}
