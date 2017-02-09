package ds.gcme.com.globalstart.Sync;

import android.annotation.TargetApi;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.ContentValues;
import android.os.Build;
import android.util.Log;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ds.gcme.com.globalstart.Database.MyDatabase;
import ds.gcme.com.globalstart.Global_Start;
import ds.gcme.com.globalstart.News_Feed.NewsFeed;
import ds.gcme.com.globalstart.News_Feed.NewsFeed_Fragment;

/**
 * Created by bengeos on 6/30/16.
 */

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class SyncService extends JobService {
    private static final String TAG = "SyncService";
    public static final String[] Sync_Tasks = {"Send_Log", "Send_Disciples","Remove_Disciple","Update_Disciple","Send_Schedule","Send_Report","Send_Testimony"};
    private List<Object> Param;
    private Gson myParser;
    private FileManager myFileManager;
    private String SyncParam;

    public SyncService(){
        Param = new ArrayList<Object>();
        myParser = new Gson();
        myFileManager = new FileManager(this);
    }

    @Override
    public boolean onStartJob(JobParameters params) {
//        Log.i("JobService", "The Job scheduler started");
//        Send_Param = new ArrayList<kotlin.Pair<String,String>>();
//
//        Send_Param.add(new kotlin.Pair<String, String>("Service",getService()));
//        Send_Param.add(new kotlin.Pair<String, String>("Param",SyncParam));
//        Send_Param.add(new kotlin.Pair<String, String>("User_ID",Global_Start.Tel.getDeviceId()));
//        Log.i(TAG, "Sent Request: \n" + Send_Param.toString());
//
//        Fuel.post(Global_Start.API_URL, Send_Param).responseString(new Handler<String>() {
//            @Override
//            public void success(@NotNull Request request, @NotNull Response response, String s) {
//                Log.i(TAG, "Request: \n" + request);
//                Log.i(TAG, "Response: \n" + s);
//                Gson myGson = new Gson();
//                try {
//                    JSONObject myObject = (JSONObject) new JSONTokener(s).nextValue();
//                    Log.i(TAG, "Server Response -> \n" + myObject.toString());
//                    if (!myObject.isNull("Response")) {
//                        JSONObject json_response = myObject.getJSONObject("Response");
//                        Log.i(TAG, "Server  Response JSON OBJECT -> \n" + json_response.toString() + "---->" + !json_response.isNull("Confirmed_Logs"));
//                        if (!json_response.isNull("NewsFeeds")) {
//                            JSONArray json_NewsFeeds = json_response.getJSONArray("NewsFeeds");
//                            Log.i(TAG, "Response->NewsFeeds: \n" + json_NewsFeeds.toString());
//                            Add_New_NewsFeeds(json_NewsFeeds);
//
//                        }
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void failure(@NotNull Request request, @NotNull Response response, @NotNull FuelError fuelError) {
//
//            }
//        });

        ArrayList<NewsFeed> newsFeeds = Global_Start.myDatabase.get_All_News();
        if(newsFeeds.size()>0){
            if(newsFeeds.size()<2){
                for(int i = 0; i<newsFeeds.size();i++){
                    //String image_name = "image"+newsFeeds.get(i).getNews_ID()+".png";
                    String image_name = "news"+newsFeeds.get(i).getNews_ID()+".png";
                    Log.i(TAG, "Downloading:->" + image_name);
                    File image_file = myFileManager.createFileAt("images", image_name);
                    if(!image_file.isFile()){
                        Log.i(TAG, "Downloading From:->" + newsFeeds.get(i).getImageURL());
                        new FileDownloader(this,newsFeeds.get(i).getImageURL(),"images",image_name).execute();
                    }
                }
            } else{
                int count = 0;
                for(int i = 0; i<newsFeeds.size();i++){
                    String image_name = "News"+newsFeeds.get(i).getNews_ID()+".png";
                    Log.i(TAG, "Downloading:->" + image_name);
//                        File image_file = myFileManager.getFileAt("images",image_name);
                    File image_file = myFileManager.createFileAt("images", image_name);

                    if(!image_file.isFile()){
                        count++;
                        Log.i(TAG, "Downloading From:->" + newsFeeds.get(i).getImageURL());
                        new FileDownloader(this,newsFeeds.get(i).getImageURL(),"images",image_name).execute();
                        if(count>2){
                            break;
                        }
                    }
                }
            }
        }

        return false;
    }

    private String getService() {
        this.SyncParam = "";
        return "Update";
    }

    public static void Add_New_NewsFeeds(JSONArray NOTIS) throws JSONException {
        boolean is_added = false;
        if (NOTIS.length() > 0) {
            for (int i = 0; i < NOTIS.length(); i++) {
                JSONObject obj = NOTIS.getJSONObject(i);
                ContentValues sch_vals = new ContentValues();
                Log.i("Sync_Service", "News Feed Count: " + Global_Start.myDatabase.count(MyDatabase.Table_NewsFeed));
                sch_vals.put(MyDatabase.NewsFeed_FIELDS[0], obj.getString("id"));
                sch_vals.put(MyDatabase.NewsFeed_FIELDS[1], obj.getString(MyDatabase.NewsFeed_FIELDS[1]));
                sch_vals.put(MyDatabase.NewsFeed_FIELDS[2], obj.getString(MyDatabase.NewsFeed_FIELDS[2]));
                sch_vals.put(MyDatabase.NewsFeed_FIELDS[3], obj.getString(MyDatabase.NewsFeed_FIELDS[3]));
                sch_vals.put(MyDatabase.NewsFeed_FIELDS[4], obj.getString(MyDatabase.NewsFeed_FIELDS[4]));
                long val = Global_Start.myDatabase.insert(MyDatabase.Table_NewsFeed, sch_vals);
                Log.i("Sync_Service", "Adding new NewsFeed:-> "+val);
            }
        }
        if(is_added){
            NewsFeed_Fragment.update_view();
        }
    }
    public static void confirmLog(JSONArray NOTIS) throws JSONException {
        if (NOTIS.length() > 0) {
            for (int i = 0; i < NOTIS.length(); i++) {
                JSONObject obj = NOTIS.getJSONObject(i);
                long state = Global_Start.myDatabase.remove_news_log(obj.getString("News_ID"));
                Log.i("Sync_Service", "Adding News_Log:-> "+state);
            }
        }
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
