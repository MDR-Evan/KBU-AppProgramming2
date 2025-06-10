package com.example.myapplication;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class QueryStudentFragment extends Fragment {
    private TextView tvQueryResult;
    private StudentDbHelper dbHelper;

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_query, container, false);

        tvQueryResult = root.findViewById(R.id.tv_query_result);
        dbHelper      = new StudentDbHelper(requireContext());

        Button btnAdd    = root.findViewById(R.id.btn_add);
        Button btnUpdate = root.findViewById(R.id.btn_update);
        Button btnDelete = root.findViewById(R.id.btn_delete);
        Button btnQuery  = root.findViewById(R.id.btn_query);

        btnAdd.setOnClickListener(v ->
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new AddStudentFragment())
                        .commit()
        );
        btnUpdate.setOnClickListener(v ->
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new UpdateStudentFragment())
                        .commit()
        );
        btnDelete.setOnClickListener(v ->
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new DeleteStudentFragment())
                        .commit()
        );
        btnQuery.setOnClickListener(v -> {
            refreshData();
        });

        refreshData();
        return root;
    }

    public void refreshData() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                StudentDbHelper.TABLE_STUDENT,
                null, null, null, null, null,
                StudentDbHelper.COLUMN_ID + " ASC"
        );

        StringBuilder builder = new StringBuilder();
        int displayId = 0;
        while (cursor.moveToNext()) {
            displayId++;
            String name = cursor.getString(
                    cursor.getColumnIndexOrThrow(StudentDbHelper.COLUMN_NAME));
            int age = cursor.getInt(
                    cursor.getColumnIndexOrThrow(StudentDbHelper.COLUMN_AGE));
            String addr = cursor.getString(
                    cursor.getColumnIndexOrThrow(StudentDbHelper.COLUMN_ADDRESS));

            builder
                    .append("id : ").append(displayId).append(", ")
                    .append("이름=").append(name).append(", ")
                    .append("나이=").append(age).append(", ")
                    .append("주소=").append(addr)
                    .append("\n");
        }
        cursor.close();
        db.close();

        builder.append(displayId).append("개");
        tvQueryResult.setText(builder.toString());
    }

}
