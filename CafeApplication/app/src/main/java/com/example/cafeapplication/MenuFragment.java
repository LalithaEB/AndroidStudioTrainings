package com.example.cafeapplication;

import android.content.ContentValues;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MenuFragment extends Fragment {
    View view;
    TextView n1, n2, n3;
    TextView item1, item2, item3;
    ImageView m1, m2, m3, p1, p2, p3;
    Button submit;

    public MenuFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container != null) {
            container.removeAllViews();
        }
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_menu, container, false);

        m1 = view.findViewById(R.id.minus1);
        m2 = view.findViewById(R.id.minus2);
        m3 = view.findViewById(R.id.minus3);
        p1 = view.findViewById(R.id.plus1);
        p2 = view.findViewById(R.id.plus2);
        p3 = view.findViewById(R.id.plus3);
        n1 = view.findViewById(R.id.num1);
        n2 = view.findViewById(R.id.num2);
        n3 = view.findViewById(R.id.num3);
        item1 = view.findViewById(R.id.textView1);
        item2 = view.findViewById(R.id.textView2);
        item3 = view.findViewById(R.id.textView3);
        submit = view.findViewById(R.id.submitButton);

        m1.setOnClickListener(view -> {
            int num = Integer.parseInt(n1.getText().toString());
            minus(num, n1);
        });

        p1.setOnClickListener(view -> {
            int num = Integer.parseInt(n1.getText().toString());
            plus(num, n1);
        });

        m2.setOnClickListener(view -> {
            int num = Integer.parseInt(n2.getText().toString());
            minus(num, n2);
        });

        p2.setOnClickListener(view -> {
            int num = Integer.parseInt(n2.getText().toString());
            plus(num, n2);
        });

        m3.setOnClickListener(view -> {
            int num = Integer.parseInt(n3.getText().toString());
            minus(num, n3);
        });

        p3.setOnClickListener(view -> {
            int num = Integer.parseInt(n3.getText().toString());
            plus(num, n3);
        });

        submit.setOnClickListener(view -> {
            String num1 = n1.getText().toString();
            String num2 = n2.getText().toString();
            String num3 = n3.getText().toString();

            String tv1 = item1.getText().toString();
            String tv2 = item2.getText().toString();
            String tv3 = item3.getText().toString();

            ContentValues order1 = new ContentValues();
            ContentValues order2 = new ContentValues();
            ContentValues order3 = new ContentValues();

            order1.put(MyContentProvider.orders, num1);
            order2.put(MyContentProvider.orders, num2);
            order3.put(MyContentProvider.orders, num3);

            Log.d("LALITHA", "orders- " + num1 + ", " + num2 + ", " + num3);

            getContext().getContentResolver().insert(MyContentProvider.CONTENT_URI_ORDERS, order1);
            getContext().getContentResolver().insert(MyContentProvider.CONTENT_URI_ORDERS, order2);
            getContext().getContentResolver().insert(MyContentProvider.CONTENT_URI_ORDERS, order3);
            Log.d("LALITHA", "content values- " + order1 + ", " + order2 + ", " + order3);

            Toast.makeText(getContext(), "YOUR ORDER IS- " + tv1 + "- " + num1 + ", " + tv2 + "- " + num2 + ", " + tv3 + "- " + num3, Toast.LENGTH_LONG).show();

            n1.setText(String.valueOf(0));
            n2.setText(String.valueOf(0));
            n3.setText(String.valueOf(0));
        });

        return view;
    }

    public void minus(int num, TextView n) {
        if (num > 0) {
            num -= 1;
            n.setText(String.valueOf(num));
        } else {
            Toast.makeText(getContext(), "Invalid", Toast.LENGTH_SHORT).show();
        }
    }

    public void plus(int num, TextView n) {
        if (num >= 0 && num < 5) {
            num += 1;
            n.setText(String.valueOf(num));
        } else {
            Toast.makeText(getContext(), "Reached max limit", Toast.LENGTH_SHORT).show();
        }
    }
}