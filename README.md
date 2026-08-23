# THE DARK AGENT

Hacker-style Android utility app with Matrix rain UI.

**Owner:** @DARK_AGENT_OWNER (Telegram)

## Features

- Matrix digital rain animated background
- Glitch neon "THE DARK AGENT" branding
- Device Info (model, brand, hardware, Android version)
- System Stats (RAM, storage, battery, CPU)
- Network Scan (WiFi state, local IP)
- 100% offline - sirf basic Android permissions

## APK Kaise Banaye (No Android Studio Needed)

1. Ye repo GitHub pe push hai
2. **Actions** tab kholo
3. **Build Dark Agent APK** workflow run hoga automatically
4. Run complete hone ke baad artifact section mein **THE-DARK-AGENT-debug-apk** download karo
5. Phone pe install karo

## Project Structure

```
app/src/main/
├── AndroidManifest.xml
├── java/com/darkagent/app/
│   ├── SplashActivity.java   → glitch intro
│   ├── MainActivity.java     → dashboard cards
│   └── MatrixView.java       → matrix rain engine
└── res/
    ├── layout/               → UI layouts
    ├── drawable/             → card backgrounds, icon
    └── values/               → colors, themes
```

## Disclaimer

Ye ek aesthetic utility app hai - device info dikhata hai. Koi hacking tool, game cheat, ya unauthorized access feature NAHI hai.
