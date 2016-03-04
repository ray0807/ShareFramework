package com.ray.balloon.adapter;

import android.bluetooth.BluetoothDevice;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ray.balloon.R;
import com.ray.balloon.callback.RecyclerViewCallback;

import java.util.ArrayList;
import java.util.List;

import carbon.widget.RecyclerView;
import carbon.widget.TextView;

/**
 * Created by Administrator on 2016/3/4.
 */
public class BluetoothDevicesAdapter extends RecyclerView.Adapter<BluetoothDevicesAdapter.ViewHolder, BluetoothDevice> {
    private List<BluetoothDevice> devices = new ArrayList<BluetoothDevice>();
    private RecyclerViewCallback callback;


    public void addDevice(BluetoothDevice device) {
        this.devices.add(device);
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_recycler_bluetooth, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return devices.size();
    }

    @Override
    public BluetoothDevice getItem(int position) {
        return devices.get(position);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        if (devices.get(position).getBondState() == BluetoothDevice.BOND_BONDED) {
            holder.tv.setText(devices.get(position).getName() + "\n" + devices.get(position).getAddress() + "\n" + "已连接");
        } else {
            holder.tv.setText(devices.get(position).getName() + "\n" + devices.get(position).getAddress() + "\n" + "未连接");
        }

        holder.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.onItemClick(position);
                }
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tv;

        public ViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv_item_device);

        }
    }

    public void setOnItemClickListener(RecyclerViewCallback callback) {
        this.callback = callback;
    }
}
