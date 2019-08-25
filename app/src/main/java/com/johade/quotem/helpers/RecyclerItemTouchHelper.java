package com.johade.quotem.helpers;

import android.graphics.Canvas;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.johade.quotem.adapters.QuestionAdapter;
import com.johade.quotem.adapters.QuizAdapter;
import com.johade.quotem.listeners.RecyclerItemTouchHelperListener;

public class RecyclerItemTouchHelper extends ItemTouchHelper.SimpleCallback {
    private RecyclerItemTouchHelperListener listener;

    public RecyclerItemTouchHelper(int dragDirs, int swipeDirs, RecyclerItemTouchHelperListener listener) {
        super(dragDirs, swipeDirs);
        this.listener = listener;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        if(listener != null){
            listener.onSwiped(viewHolder, direction, viewHolder.getAdapterPosition());
        }
    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        View foreground = null;
        if(viewHolder instanceof QuizAdapter.QuizViewHolder) {
            foreground = ((QuizAdapter.QuizViewHolder) viewHolder).foreground;
        }else if(viewHolder instanceof QuestionAdapter.QuestionViewHolder){
            foreground = ((QuestionAdapter.QuestionViewHolder) viewHolder).foreground;
        }
        getDefaultUIUtil().clearView(foreground);
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        View foreground = null;
        if(viewHolder instanceof QuizAdapter.QuizViewHolder) {
            foreground = ((QuizAdapter.QuizViewHolder) viewHolder).foreground;
        }else if(viewHolder instanceof QuestionAdapter.QuestionViewHolder){
            foreground = ((QuestionAdapter.QuestionViewHolder) viewHolder).foreground;
        }
        getDefaultUIUtil().onDraw(c, recyclerView, foreground, dX, dY, actionState, isCurrentlyActive);
    }

    @Override
    public void onChildDrawOver(@NonNull Canvas c, @NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        View foreground = null;
        if(viewHolder instanceof QuizAdapter.QuizViewHolder) {
            foreground = ((QuizAdapter.QuizViewHolder) viewHolder).foreground;
        }else if(viewHolder instanceof QuestionAdapter.QuestionViewHolder){
            foreground = ((QuestionAdapter.QuestionViewHolder) viewHolder).foreground;
        }
        getDefaultUIUtil().onDrawOver(c, recyclerView, foreground, dX, dY, actionState, isCurrentlyActive);
    }

    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
        if(viewHolder != null){
            View foreground = null;
            if(viewHolder instanceof QuizAdapter.QuizViewHolder) {
                foreground = ((QuizAdapter.QuizViewHolder) viewHolder).foreground;
            }else if(viewHolder instanceof QuestionAdapter.QuestionViewHolder){
                foreground = ((QuestionAdapter.QuestionViewHolder) viewHolder).foreground;
            }
            getDefaultUIUtil().onSelected(foreground);
        }
    }

    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }
}
