package com.example.notesapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    private List<ItemModel> itemModels;
    private static OnEditItemClickListener onEditItemClickListener;
    private static onDeleteItemClickListener onDeleteItemClickListener;

    public ItemAdapter(List<ItemModel> itemModels, OnEditItemClickListener onEditItemClickListener, onDeleteItemClickListener onDeleteItemClickListener) {
        this.itemModels = itemModels;
        this.onEditItemClickListener = onEditItemClickListener;
        this.onDeleteItemClickListener = onDeleteItemClickListener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View item = layoutInflater.inflate(R.layout.activity_items_list, parent, false);
        ItemViewHolder viewHolder = new ItemViewHolder(item);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.bind(itemModels.get(position));
    }

    @Override
    public int getItemCount() {
        return itemModels.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView titleTV;
        TextView descriptionTV;
        ImageButton deleteButton;
        CardView card;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTV = itemView.findViewById(R.id.titleTextView);
            descriptionTV = itemView.findViewById(R.id.descriptionTextView);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            card = itemView.findViewById(R.id.card);

            card.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    onEditItemClickListener.onEditItemClick(position);
                }
            });

            deleteButton.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    showDeleteDialog(itemView.getContext(), position);
                }
            });
        }

        private void showDeleteDialog(Context context, int position) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Are you sure you want to delete this item?")
                    .setPositiveButton("Yes", ((dialog, which) -> {
                        if (onDeleteItemClickListener != null) {
                            onDeleteItemClickListener.onDeleteItemClick(position);
                        }
                    }))
                    .setNegativeButton("No", ((dialog, which) -> {}))
                    .show();
        }

        public void bind(ItemModel itemModel) {
            titleTV.setText(itemModel.getTitle());
            descriptionTV.setText(itemModel.getDescription());
        }
    }

    public interface OnEditItemClickListener {
        void onEditItemClick(int position);
    }

    public interface onDeleteItemClickListener {
        void onDeleteItemClick(int position);
    }

}
