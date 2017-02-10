package ds.gcme.com.globalstart.Menu_Options;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import ds.gcme.com.globalstart.R;


/**
 * Created by Roger on 3/18/2016.
 */
public class ContactUsOptions extends DialogFragment {
    Button fb, twitter, website;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_view_contact,null);

        fb = (Button) view.findViewById(R.id.menu_find_fb);
        twitter = (Button) view.findViewById(R.id.menu_find_twitter);
        website = (Button) view.findViewById(R.id.menu_find_website);

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.facebook.com/Global-Start-1814011832184355/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

            }
        });

        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Twitter", Toast.LENGTH_SHORT).show();
            }
        });

        website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Website", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setView(view)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        ContactUsOptions.this.getDialog().cancel();
                    }
                });


        return builder.create();
    }
}
