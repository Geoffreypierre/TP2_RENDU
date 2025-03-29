package tp2.Exercice_2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tp2.R;

import java.util.List;

public class Exo2Adaptateur extends BaseAdapter {

    private Context context;
    private List<String> list;
    private LayoutInflater inflater;

    public Exo2Adaptateur(Context context, List<String> list) {
        this.context = context;
        this.list = list;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public String getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.exo2_adaptateur_layout,null);
        TextView Text1 = view.findViewById(R.id.textexo2);
        String s = getItem(i);
        Text1.setText(s);
        return view;
    }
}