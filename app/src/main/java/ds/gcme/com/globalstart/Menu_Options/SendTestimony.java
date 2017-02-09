package ds.gcme.com.globalstart.Menu_Options;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import ds.gcme.com.globalstart.Global_Start;
import ds.gcme.com.globalstart.R;

/**
 * Created by bengeos on 7/1/16.
 */

public class SendTestimony extends DialogFragment {

    private EditText Title,Content;
    private Button Send;
    private static final String TAG = "Send_Testimony";
    private ProgressDialog myProgressDialog;
    private Context myContext;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        String txtTitle = getArguments().getString("Title");
        String txtMore = getArguments().getString("Detail");
        View view = inflater.inflate(R.layout.fragment_view_testimony,null);
        myProgressDialog = new ProgressDialog(getActivity());

        Title = (EditText) view.findViewById(R.id.testimony_title);
        Content = (EditText) view.findViewById(R.id.testimony_content);
        Send = (Button) view.findViewById(R.id.testimony_send);


        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myProgressDialog.show();
                myProgressDialog.setCancelable(false);
                myProgressDialog.setTitle("Sending your testimony");
                if(Title.getText().toString().length()<3 || Content.getText().toString().length()<5){
                    Toast.makeText(getActivity(),"Please Fill the Form First, Title must be at least one word and Content must contains more than 3 words", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getActivity(),"Sending Tetimony", Toast.LENGTH_SHORT).show();
//                    Send_Param = new ArrayList<Pair<String,String>>();
//                    Send_Param.add(new kotlin.Pair<String, String>("Service","Testimony"));
//                    Send_Param.add(new kotlin.Pair<String, String>("User_ID", Global_Start.Tel.getDeviceId()));
//                    Send_Param.add(new kotlin.Pair<String, String>("Title", Title.getText().toString()));
//                    Send_Param.add(new kotlin.Pair<String, String>("Content", Content.getText().toString()));
//                    Log.i(TAG, "Sent Request: \n" + Send_Param.toString());
//
//                    Fuel.post(Global_Start.API_URL, Send_Param).responseString(new Handler<String>() {
//                        @Override
//                        public void success(@NotNull Request request, @NotNull Response response, String s) {
//                            Log.i(TAG, "Request: \n" + request);
//                            Log.i(TAG, "Response: \n" + s);
//                            Toast.makeText(getActivity(),"Your Testimony has sent", Toast.LENGTH_SHORT).show();
//                            SendTestimony.this.getDialog().cancel();
//                            myProgressDialog.cancel();
//                        }
//
//                        @Override
//                        public void failure(@NotNull Request request, @NotNull Response response, @NotNull FuelError fuelError) {
//                            myProgressDialog.cancel();
//                        }
//                    });
                }
                myProgressDialog.cancel();

            }
        });

        builder.setView(view)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        SendTestimony.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}
