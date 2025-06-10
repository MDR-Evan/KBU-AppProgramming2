package com.example.myapplication;

import android.content.ContentValues;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AddStudentFragment extends Fragment {
    private EditText etName;
    private EditText etAge;
    private EditText etAddress;
    private Button btnAddConfirm;
    private Button btnBackMain;
    private TextView tvResult;
    private StudentDbHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add, container, false);

        etName        = root.findViewById(R.id.et_name);
        etAge         = root.findViewById(R.id.et_age);
        etAddress     = root.findViewById(R.id.et_address);
        btnAddConfirm = root.findViewById(R.id.btn_add_confirm);
        btnBackMain   = root.findViewById(R.id.btn_back_main);
        tvResult      = root.findViewById(R.id.tv_result);
        dbHelper      = new StudentDbHelper(requireContext());

        btnAddConfirm.setOnClickListener(v -> addStudent());

        btnBackMain.setOnClickListener(v ->
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new QueryStudentFragment())
                        .commit()
        );

        return root;
    }

    private void addStudent() {
        String name    = etName.getText().toString().trim();
        String ageStr  = etAge.getText().toString().trim();
        String address = etAddress.getText().toString().trim();

        if (name.isEmpty() || ageStr.isEmpty() || address.isEmpty()) {
            tvResult.setText("모든 필드를 입력해주세요.");
            return;
        }

        int age;
        try {
            age = Integer.parseInt(ageStr);
        } catch (NumberFormatException e) {
            tvResult.setText("나이는 숫자로 입력해주세요.");
            return;
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor cursor = db.query(
                StudentDbHelper.TABLE_STUDENT,
                new String[]{StudentDbHelper.COLUMN_ID},
                StudentDbHelper.COLUMN_NAME + "=? AND " +
                        StudentDbHelper.COLUMN_AGE  + "=? AND " +
                        StudentDbHelper.COLUMN_ADDRESS + "=?",
                new String[]{name, String.valueOf(age), address},
                null, null, null
        );
        boolean exists = cursor.moveToFirst();
        cursor.close();

        if (exists) {
            String msg = "중복된 데이터입니다.";
            tvResult.setText(msg);
            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
        } else {
            ContentValues values = new ContentValues();
            values.put(StudentDbHelper.COLUMN_NAME, name);
            values.put(StudentDbHelper.COLUMN_AGE, age);
            values.put(StudentDbHelper.COLUMN_ADDRESS, address);

            long rowId = db.insert(StudentDbHelper.TABLE_STUDENT, null, values);
            if (rowId != -1) {
                String msg = rowId + "번째 row insert 성공";
                tvResult.setText(msg);
                Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                clearInputs();
            } else {
                String msg = "insert 실패";
                tvResult.setText(msg);
                Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
            }
        }

        db.close();
    }

    private void clearInputs() {
        etName.setText("");
        etAge.setText("");
        etAddress.setText("");
    }
}
