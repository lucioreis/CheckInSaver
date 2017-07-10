package inf221.trabalho.com.checkinsaver.view;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Comparator;
import java.util.List;

import inf221.trabalho.com.checkinsaver.R;
import inf221.trabalho.com.checkinsaver.model.CheckIn;

/**
 * Created by lucio on 7/9/2017.
 */

public class MyArrayAdapterLocaisMaisVisitados extends BaseAdapter {
    private List<CheckIn> checkIns;
    private Activity activity;

    private class ViewHolder {
        final TextView nomeDoLugar;
        final TextView numeroDeVisitas;

        public ViewHolder(View view){
            this.nomeDoLugar = (TextView) view.findViewById(R.id.list_nome_do_lugar_mais_visitado);
            this.numeroDeVisitas = (TextView) view.findViewById(R.id.list_numero_de_visitas_ao_mais_visitado);
        }
    }

    public MyArrayAdapterLocaisMaisVisitados(List<CheckIn> checkIns, Activity activity){
        this.activity = activity;
        this.checkIns = checkIns;
    }
    @Override
    public int getCount() {
        if(checkIns == null) return 0;
        return checkIns.size();
    }

    @Override
    public Object getItem(int position) {
        if(checkIns.isEmpty()) return null;
        return checkIns.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder viewHolder;
        if(convertView == null){
            view = activity.getLayoutInflater().inflate(R.layout.row_lugares_mais_visitados, parent, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            view = convertView;
        }
        viewHolder.numeroDeVisitas.setText(checkIns.get(position).getQtdVisitas()+"");
        viewHolder.nomeDoLugar.setText(checkIns.get(position).getNomeDoLocal());
        return view;
    }

    @Override
    public boolean isEnabled(int pos){
        return false;
    }
}
