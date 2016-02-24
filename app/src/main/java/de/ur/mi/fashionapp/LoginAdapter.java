package de.ur.mi.fashionapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import com.christianbahl.appkit.core.adapter.CBAdapterRecyclerView;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Philip on 24/02/2016.
 */
public class LoginAdapter extends CBAdapterRecyclerView<String> {

  public LoginAdapter(Context context) {
    super(context);
  }

  public void setData(String data) {
    List strings = new ArrayList<String>();
    strings.add(data);
    setItems(strings);
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position, int viewType) {
    ((TextViewHolder) viewHolder).bind(getItem(position));
  }

  @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new TextViewHolder(inflater.inflate(R.layout.simple_text, parent));
  }

  @Override public int getItemViewType(int position) {
    return super.getItemViewType(position);
  }
}
