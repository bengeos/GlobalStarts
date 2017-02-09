package ds.gcme.com.globalstart;

import android.app.Application;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import ds.gcme.com.globalstart.Database.MyDatabase;
import ds.gcme.com.globalstart.News_Feed.*;
import ds.gcme.com.globalstart.News_Feed.NewsFeed;
import ds.gcme.com.globalstart.Sync.FileManager;

/**
 * Created by BENGEOS on 3/17/16.
 */
public class Global_Start extends Application {

    private static String TAG = "SyncService";

    public static MyDatabase myDatabase;
    public static int DOWNLOAD_STATUS;
    public static FileManager myFileManager;
    public static TelephonyManager Tel;
    public static DatabaseReference myNewsRef;
    public static List<ds.gcme.com.globalstart.News_Feed.NewsFeed> myNewsFeeds;


    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myNewsRef = database.getReference("News");
        myNewsFeeds = new ArrayList<NewsFeed>();

        myNewsRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                try{
                    Log.d(TAG, "Adding News:-> " + dataSnapshot.getKey());
                    ds.gcme.com.globalstart.News_Feed.NewsFeed news = dataSnapshot.getValue(ds.gcme.com.globalstart.News_Feed.NewsFeed.class);
                    news.setUUID(dataSnapshot.getKey());
                    for(int i=0;i<myNewsFeeds.size();i++){
                        if(myNewsFeeds.get(i).getUUID().equals(news.getUUID())){
                            myNewsFeeds.remove(i);
                        }
                    }
                    myNewsFeeds.add(news);
                    try{
                        NewsFeed_Fragment.update_view();
                    }catch (Exception e){

                    }
                    try{
                        NewsFeedDetail.updateView();
                    }catch (Exception e){

                    }
                    Log.d(TAG, "News Added: " + dataSnapshot.getChildren());
                }catch (Exception e){

                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                try{
                    Log.d(TAG, "Updating News:-> " + dataSnapshot.getKey());
                    ds.gcme.com.globalstart.News_Feed.NewsFeed news = dataSnapshot.getValue(ds.gcme.com.globalstart.News_Feed.NewsFeed.class);
                    news.setUUID(dataSnapshot.getKey());
                    for(int i=0;i<myNewsFeeds.size();i++){
                        if(myNewsFeeds.get(i).getUUID().equals(news.getUUID())){
                            myNewsFeeds.get(i).setTitle(news.getTitle());
                            myNewsFeeds.get(i).setDetail(news.getDetail());
                            myNewsFeeds.get(i).setImageURL(news.getImageURL());
                            myNewsFeeds.get(i).setPubDate(news.getPubDate());
                        }
                    }
                    try{
                        NewsFeed_Fragment.update_view();
                    }catch (Exception e){

                    }
                    try{
                        NewsFeedDetail.updateView();
                    }catch (Exception e){

                    }
                    Log.d(TAG, "News Updated: " + dataSnapshot.getChildren());
                }catch (Exception e){

                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                try{
                    Log.d(TAG, "Updating News:-> " + dataSnapshot.getKey());
                    ds.gcme.com.globalstart.News_Feed.NewsFeed news = dataSnapshot.getValue(ds.gcme.com.globalstart.News_Feed.NewsFeed.class);
                    news.setUUID(dataSnapshot.getKey());
                    for(int i=0;i<myNewsFeeds.size();i++){
                        if(myNewsFeeds.get(i).getUUID().equals(news.getUUID())){
                            myNewsFeeds.remove(i);
                        }
                    }
                    try{
                        NewsFeed_Fragment.update_view();
                    }catch (Exception e){

                    }
                    try{
                        NewsFeedDetail.updateView();
                    }catch (Exception e){

                    }
                    Log.d(TAG, "News Updated: " + dataSnapshot.getChildren());
                }catch (Exception e){

                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
