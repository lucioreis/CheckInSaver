package inf221.trabalho.com.checkinsaver.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import inf221.trabalho.com.checkinsaver.R;
import inf221.trabalho.com.checkinsaver.controller.GestaoCheckinActivity;
import inf221.trabalho.com.checkinsaver.model.CheckIn;
import inf221.trabalho.com.checkinsaver.model.ControladoraFachadaSingleton;
import inf221.trabalho.com.checkinsaver.util.MyApp;

/**
 * Created by lucio on 7/9/2017.
 */

public class MyArrayAdapterGestaoDeCheckIn extends BaseAdapter {
    private List<CheckIn> checkIns;
    private Activity act;

    private class ViewHolder{
        final TextView textView;
        final Button imageView;
        public ViewHolder(View view) {
            this.textView = (TextView) view.findViewById(R.id.list_nome_do_local);
            this.imageView = (Button) view.findViewById(R.id.excluir);
        }
    }

    public MyArrayAdapterGestaoDeCheckIn(List<CheckIn> checkIns, Activity act){
        this.checkIns = checkIns;
        this.act = act;
    }
    @Override
    public int getCount() {
        if(checkIns == null) return 0;
        return checkIns.size();
    }

    @Override
    public Object getItem(int position) {
        return checkIns.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    @Override
    public boolean isEnabled(int position){
        return false;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder viewHolder;
        if(convertView == null){
            view = act.getLayoutInflater().inflate(R.layout.row, parent, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        final CheckIn checkIn = checkIns.get(position);
        viewHolder.textView.setText(checkIn.getNomeDoLocal());
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog alertDialog = new AlertDialog.Builder(act)
                        .setTitle(R.string.exclusao)
                        .setMessage(act.getString(R.string.exclusao_message) + checkIn.getNomeDoLocal())
                        .setPositiveButton(R.string.confirma, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ControladoraFachadaSingleton cfs = ControladoraFachadaSingleton.getInstance();
                                cfs.removeCheckin(checkIn);
                                act.finish();
                                act.startActivity(new Intent(act, GestaoCheckinActivity.class));
                            }
                        })
                        .setNegativeButton(R.string.rejeita, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .create();
                alertDialog.show();
            }
        });
        return view;
    }
}
