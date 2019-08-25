package com.johade.quotem.listeners;

import androidx.recyclerview.widget.RecyclerView;

public interface RecyclerItemTouchHelperListener  {
    void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position);
}
