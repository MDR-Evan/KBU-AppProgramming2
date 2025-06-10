package com.example.myapplication;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DeleteStudentFragment extends Fragment {
    private EditText etName;
    private Button btnDeleteConfirm, btnBackMain;
    private TextView tvResult;
    private StudentDbHelper dbHelper;

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_delete, container, false);

        etName           = root.findViewById(R.id.et_del_name);
        btnDeleteConfirm = root.findViewById(R.id.btn_delete_confirm);
        btnBackMain      = root.findViewById(R.id.btn_back_main);
        tvResult         = root.findViewById(R.id.tv_delete_result);
        dbHelper         = new StudentDbHelper(requireContext());

        refreshList();

        btnDeleteConfirm.setOnClickListener(v -> deleteData());
        btnBackMain.setOnClickListener(v ->
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new QueryStudentFragment())
                        .commit()
        );

        return root;
    }

    private void refreshList() {
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
        tvResult.setText(builder.toString());
    }

    private void deleteData() {
        String name = etName.getText().toString().trim();
        if (name.isEmpty()) {
            Toast.makeText(getContext(), "이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                StudentDbHelper.TABLE_STUDENT,
                new String[]{StudentDbHelper.COLUMN_ID},
                "name=?",
                new String[]{name},
                null, null, null
        );
        int count = cursor.getCount();
        cursor.close();

        if (count == 0) {
            String msg = "delete 실패 - 항목이 없습니다.";
            tvResult.setText(msg);
            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
        } else {
            int deleted = db.delete(
                    StudentDbHelper.TABLE_STUDENT,
                    "name=?",
                    new String[]{name}
            );
            String msg = "delete 성공 - " + deleted + "항목 삭제.";
            tvResult.setText(msg);
            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
        }
        db.close();
        refreshList();
    }
}
