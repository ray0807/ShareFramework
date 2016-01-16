package com.ryan.corelibstest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.corelibs.utils.ViewHolder;
import com.ryan.corelibstest.R;
import com.ryan.corelibstest.model.bean.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Ryan on 2016/1/6.
 */
public class HomeAdapter extends BaseAdapter {

    private Context context;
    private List<Product> products;

    public HomeAdapter(Context context) {
        this.context = context;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return products == null ? 0 : products.size();
    }

    @Override
    public Object getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return products.get(position).id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(context).inflate(R.layout.item_home, parent, false);

        Product p = products.get(position);

        ImageView iv_prodImage = ViewHolder.get(convertView, R.id.iv_prodImage);
        TextView tv_prod_name = ViewHolder.get(convertView, R.id.tv_prod_name);
        TextView tv_prod_price = ViewHolder.get(convertView, R.id.tv_prod_price);

        Picasso.with(context).load(p.logo).into(iv_prodImage);
        tv_prod_name.setText(p.title);
        tv_prod_price.setText(String.format(context.getResources()
                .getString(R.string.price), p.price));

        return convertView;
    }
}
