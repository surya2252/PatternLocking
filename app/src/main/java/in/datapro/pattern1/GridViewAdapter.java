package in.datapro.pattern1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class GridViewAdapter extends ArrayAdapter {
    private Context context;
    private int layoutResourceId;
    private ArrayList data = new ArrayList();

    public static int kk=0;
    public GridViewAdapter(Context context, int layoutResourceId, ArrayList data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position , View convertView, ViewGroup parent) {
        View row = convertView;
        final ViewHolder holder = new ViewHolder();

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            ImageItem item = (ImageItem)data.get(kk++);//position);

            holder.imageTitle = (TextView) row.findViewById(R.id.text);
            holder.image = (ImageView) row.findViewById(R.id.image);
            holder.image.setTag(item.getTitle());
            holder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent go = new Intent(context, ImageDetailsActivity.class);
                    String imgurl=view.getTag().toString();
                    go.putExtra("image", imgurl);
                    Log.i("images", "imgurl "+imgurl);
                    context.startActivity(go);
                }
            });

            Log.i("images", position+" "+kk);

            //holder.imageTitle.setText(item.getTitle());
            //holder.image.setImageBitmap(item.getImage());

            if(kk>=data.size()) kk=0;
            //Glide.with(context).load(holder.imageTitle.getText().toString()).into(holder.image);
            try {
                Glide.with(context).load(item.getTitle()).into(holder.image);
            }catch(Exception exp){Log.e("image", exp.toString());}
            row.setTag(holder);
        }
        else {
            //holder = (ViewHolder) row.getTag();
        }

    //Log.i("images", holder.imageTitle);
        return row;
    }

    static class ViewHolder {
        TextView imageTitle;
        ImageView image;
    }
}