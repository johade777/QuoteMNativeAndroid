package com.johade.quotem.adapters;

import androidx.recyclerview.widget.RecyclerView;

public interface RecyclerItemTouchHelperListener  {
    void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position);
}
