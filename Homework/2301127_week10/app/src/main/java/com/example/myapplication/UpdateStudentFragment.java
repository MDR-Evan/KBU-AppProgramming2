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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class UpdateStudentFragment extends Fragment {
    private EditText etSearchName, etNewAge, etNewAddress;
    private TextView tvOldAge, tvOldAddress, tvResult;
    private LinearLayout layoutOld, layoutNew;
    private Button btnSearch, btnUpdateConfirm, btnBackMain;
    private StudentDbHelper dbHelper;
    private int targetId = -1;

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_update, container, false);

        etSearchName     = root.findViewById(R.id.et_search_name);
        tvOldAge         = root.findViewById(R.id.tv_old_age);
        tvOldAddress     = root.findViewById(R.id.tv_old_address);
        etNewAge         = root.findViewById(R.id.et_new_age);
        etNewAddress     = root.findViewById(R.id.et_new_address);
        layoutOld        = root.findViewById(R.id.layout_old);
        layoutNew        = root.findViewById(R.id.layout_new);
        btnSearch        = root.findViewById(R.id.btn_search);
        btnUpdateConfirm = root.findViewById(R.id.btn_update_confirm);
        btnBackMain      = root.findViewById(R.id.btn_back_main);
        tvResult         = root.findViewById(R.id.tv_update_result);
        dbHelper         = new StudentDbHelper(requireContext());

        btnSearch.setOnClickListener(v -> searchData());
        btnUpdateConfirm.setOnClickListener(v -> updateData());
        btnBackMain.setOnClickListener(v ->
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new QueryStudentFragment())
                        .commit()
        );

        return root;
    }

    private void searchData() {
        String name = etSearchName.getText().toString().trim();
        if (name.isEmpty()) {
            Toast.makeText(getContext(), "이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                StudentDbHelper.TABLE_STUDENT,
                null,
                "name=?",
                new String[]{name},
                null, null,
                StudentDbHelper.COLUMN_ID + " ASC"
        );

        if (cursor.moveToFirst()) {
            targetId = cursor.getInt(cursor.getColumnIndexOrThrow(StudentDbHelper.COLUMN_ID));
            int age = cursor.getInt(cursor.getColumnIndexOrThrow(StudentDbHelper.COLUMN_AGE));
            String addr = cursor.getString(cursor.getColumnIndexOrThrow(StudentDbHelper.COLUMN_ADDRESS));

            tvOldAge.setText(String.valueOf(age));
            tvOldAddress.setText(addr);
            layoutOld.setVisibility(View.VISIBLE);
            layoutNew.setVisibility(View.VISIBLE);
            tvResult.setText("");
        } else {
            Toast.makeText(getContext(), "해당 이름의 데이터가 없습니다.", Toast.LENGTH_SHORT).show();
            layoutOld.setVisibility(View.GONE);
            layoutNew.setVisibility(View.GONE);
            tvResult.setText("");
            targetId = -1;
        }
        cursor.close();
        db.close();
    }

    private void updateData() {
        if (targetId == -1) {
            Toast.makeText(getContext(), "먼저 검색을 수행해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        String newAgeStr = etNewAge.getText().toString().trim();
        String newAddr   = etNewAddress.getText().toString().trim();
        if (newAgeStr.isEmpty() || newAddr.isEmpty()) {
            Toast.makeText(getContext(), "새 데이터를 모두 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        ContentValues values = new ContentValues();
        values.put(StudentDbHelper.COLUMN_AGE, Integer.parseInt(newAgeStr));
        values.put(StudentDbHelper.COLUMN_ADDRESS, newAddr);

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int updated = db.update(
                StudentDbHelper.TABLE_STUDENT,
                values,
                StudentDbHelper.COLUMN_ID + "=?",
                new String[]{String.valueOf(targetId)}
        );
        db.close();

        String msg = updated > 0 ?
                "Update 성공 (" + updated + "건 수정됨)" :
                "Update 실패";
        tvResult.setText(msg);
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
