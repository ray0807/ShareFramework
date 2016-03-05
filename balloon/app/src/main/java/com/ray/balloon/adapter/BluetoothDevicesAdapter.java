package com.ray.balloon.adapter;

import android.bluetooth.BluetoothDevice;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ray.balloon.R;
import com.ray.balloon.callback.RecyclerViewCallback;
import com.ray.balloon.presenter.BluetoothPresenter;

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

    private int state = -1;
    private int clickPosition = -1;

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
            holder.tv.setText(devices.get(position).getName() + "\n" + devices.get(position).getAddress() + "\n" + "已绑定");
        } else {
            holder.tv.setText(devices.get(position).getName() + "\n" + devices.get(position).getAddress() + "\n" + "未连接");
        }
        if (clickPosition == position) {
            switch (state) {
                case BluetoothPresenter.STATE_CONNECTED:
                    holder.tv.setText(devices.get(position).getName() + "\n" + devices.get(position).getAddress() + "\n" + "已连接");
                    break;
                case BluetoothPresenter.STATE_CONNECTING:
                    holder.tv.setText(devices.get(position).getName() + "\n" + devices.get(position).getAddress() + "\n" + "正在连接");
                    break;
                case BluetoothPresenter.STATE_LISTEN:
                    holder.tv.setText(devices.get(position).getName() + "\n" + devices.get(position).getAddress() + "\n" + "正在监听");
                    break;
                case BluetoothPresenter.STATE_NONE:
                    holder.tv.setText(devices.get(position).getName() + "\n" + devices.get(position).getAddress() + "\n" + "未连接");
                    break;
            }
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

    public void setState(int state,int position) {
        this.clickPosition=position;
        this.state = state;
        notifyItemChanged(position);
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
