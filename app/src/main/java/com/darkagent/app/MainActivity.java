package com.darkagent.app;

import android.app.ActivityManager;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.text.format.Formatter;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.File;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_main);

        TextView title = findViewById(R.id.mainTitle);
        TextView tg = findViewById(R.id.mainTg);

        // glitch flicker
        android.view.animation.Animation gl = new android.view.animation.AlphaAnimation(1f, 0.55f);
        gl.setDuration(120); gl.setRepeatMode(android.view.animation.Animation.REVERSE);
        gl.setRepeatCount(3);
        title.startAnimation(gl);

        LinearLayout cardDevice = findViewById(R.id.cardDevice);
        LinearLayout cardSystem = findViewById(R.id.cardSystem);
        LinearLayout cardNetwork = findViewById(R.id.cardNetwork);

        fillDevice(cardDevice);
        fillSystem(cardSystem);
        fillNetwork(cardNetwork);
    }

    private TextView row(String k, String v) {
        TextView t = new TextView(this);
        t.setText(k + "  ▸  " + v);
        t.setTextColor(0xFF9FE8A8);
        t.setTextSize(13.5f);
        t.setPadding(4, 8, 4, 8);
        return t;
    }

    private void addRows(LinearLayout card, String header, TextView... rows) {
        TextView h = new TextView(this);
        h.setText(header);
        h.setTextColor(0xFF00FF66);
        h.setTextSize(16f);
        h.setTypeface(null, android.graphics.Typeface.BOLD);
        card.addView(h);
        for (TextView r : rows) card.addView(r);
    }

    private void fillDevice(LinearLayout card) {
        addRows(card, "[ DEVICE_INFO ]",
            row("MODEL", Build.MODEL),
            row("BRAND", Build.BRAND.toUpperCase(Locale.ROOT)),
            row("BOARD", Build.BOARD),
            row("HARDWARE", Build.HARDWARE),
            row("ANDROID", "API " + Build.VERSION.SDK_INT + " / " + Build.VERSION.RELEASE),
            row("FINGERPRINT", Build.FINGERPRINT.length() > 42
                ? Build.FINGERPRINT.substring(0, 42) + "..." : Build.FINGERPRINT)
        );
    }

    private void fillSystem(LinearLayout card) {
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        long ramUsed = (mi.totalMem - mi.availMem) / (1024 * 1024);
        long ramTot = mi.totalMem / (1024 * 1024);

        StatFs st = new StatFs(Environment.getDataDirectory().getPath());
        long intUsed = (st.getTotalBytes() - st.getAvailableBytes()) / (1024 * 1024 * 1024);
        long intTot = st.getTotalBytes() / (1024 * 1024 * 1024);

        BatteryManager bm = (BatteryManager) getSystemService(Context.BATTERY_SERVICE);
        int bat = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);

        Runtime rt = Runtime.getRuntime();
        addRows(card, "[ SYSTEM_STATS ]",
            row("RAM", ramUsed + " MB / " + ramTot + " MB"),
            row("STORAGE", intUsed + " GB / " + intTot + " GB"),
            row("BATTERY", bat + "%"),
            row("CPU_CORES", String.valueOf(rt.availableProcessors())),
            row("JAVA_HEAP", Formatter.formatFileSize(this, rt.maxMemory()))
        );
    }

    private void fillNetwork(LinearLayout card) {
        WifiManager wm = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        String ip = "offline";
        boolean wifi = false;
        try {
            if (wm != null && wm.isWifiEnabled()) {
                wifi = true;
                ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
            }
        } catch (Exception ignored) { }

        addRows(card, "[ NETWORK_SCAN ]",
            row("WIFI_STATE", wifi ? "CONNECTED" : "DISCONNECTED"),
            row("LOCAL_IP", ip),
            row("NET_IFACE", wifi ? "wlan0" : "none"),
            row("STATUS", wifi ? "● ONLINE" : "○ OFFLINE")
        );
    }
}
