package ds.gcme.com.globalstart;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.Color;
import android.telephony.TelephonyManager;

import com.goka.blurredgridmenu.BlurredGridMenuConfig;

import ds.gcme.com.globalstart.Database.MyDatabase;
import ds.gcme.com.globalstart.Sync.FileManager;
import ds.gcme.com.globalstart.Sync.SyncService;

/**
 * Created by BENGEOS on 3/17/16.
 */
public class Global_Start extends Application {

    public static MyDatabase myDatabase;
    public static int DOWNLOAD_STATUS;
    public static FileManager myFileManager;
    public static TelephonyManager Tel;

    @Override
    public void onCreate() {
        super.onCreate();
        myDatabase = new MyDatabase(this);
        myFileManager = new FileManager(this);
    }
}
