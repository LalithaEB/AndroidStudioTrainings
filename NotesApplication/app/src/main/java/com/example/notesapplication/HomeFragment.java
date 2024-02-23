package com.example.notesapplication;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements ItemAdapter.OnEditItemClickListener, ItemAdapter.onDeleteItemClickListener {
    View view;
    ImageView addItem;
    RecyclerView recyclerView;
    ItemAdapter adapter;
    private List<ItemModel> itemModels;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        itemModels = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recyclerView);

        if (recyclerView != null) {
            adapter = new ItemAdapter(itemModels, this, this);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(adapter);
            loadData();
        }

        addItem = view.findViewById(R.id.addItemImageView);
        addItem.setOnClickListener(view -> {
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.mainFragment, new NewNotesFragment());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });
        return view;
    }

    private void loadData() {
        try (Cursor cursor = getActivity().getContentResolver().query(MyContentProvider.CONTENT_URI, null, null, null, null)) {

            if (cursor != null && cursor.moveToFirst()) {
                try {
                    itemModels.clear();

                    do {
                        int idIndex = cursor.getColumnIndex("id");
                        int titleIndex = cursor.getColumnIndex("title");
                        int descriptionIndex = cursor.getColumnIndex("description");

                        if (titleIndex != -1 && descriptionIndex != -1) {
                            int id = cursor.getInt(idIndex);
                            String title = cursor.getString(titleIndex);
                            String description = cursor.getString(descriptionIndex);

                            Log.d("TAG", "Load Data- " + title + description);
                            itemModels.add(new ItemModel(id, title, description));
                        } else {
                            Log.d("TAG", "Load Data Invalid");
                        }
                    } while (cursor.moveToNext());
                } finally {
                    cursor.close();
                }
            } else {
                itemModels.clear();
            }
        }
        if (recyclerView.getAdapter() != null) {
            recyclerView.getAdapter().notifyDataSetChanged();
        }
    }

    public void onEditItemClick(int position) {
        ItemModel clickedItem = itemModels.get(position);

        EditFragment editFragment = new EditFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", clickedItem.getId());
        bundle.putString("title", clickedItem.getTitle());
        bundle.putString("description", clickedItem.getDescription());
        editFragment.setArguments(bundle);

        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainFragment, editFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onDeleteItemClick(int position) {
        ItemModel deletedItem = itemModels.get(position);
        int itemId = deletedItem.getId();
        getContext().getContentResolver().delete(MyContentProvider.CONTENT_URI, "id =" + itemId, null);
        itemModels.remove(position);
        adapter.notifyItemRemoved(position);
    }
}