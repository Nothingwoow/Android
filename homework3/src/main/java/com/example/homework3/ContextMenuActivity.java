package com.example.homework3;

import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class ContextMenuActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_context_menu);

        // 启用返回按钮
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // 初始化 ListView 和数据
        listView = findViewById(R.id.context_list_view);
        for (int i = 1; i <= 20; i++) {
            items.add("Item " + i);
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_activated_1, items);
        listView.setAdapter(adapter);

        // 设置多选模式
        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            private final List<Integer> selectedPositions = new ArrayList<>();

            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                if (checked) {
                    selectedPositions.add(position);
                } else {
                    selectedPositions.remove((Integer) position);
                }
                mode.setTitle(selectedPositions.size() + " selected");
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater inflater = mode.getMenuInflater();
                inflater.inflate(R.menu.context_menu, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                if (item.getItemId() == R.id.action_delete) {
                    deleteSelectedItems();
                    mode.finish();
                    return true;
                }
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                selectedPositions.clear();
            }

            private void deleteSelectedItems() {
                for (int i = selectedPositions.size() - 1; i >= 0; i--) {
                    items.remove((int) selectedPositions.get(i));
                }
                adapter.notifyDataSetChanged();
                Toast.makeText(ContextMenuActivity.this, "已删除选中项", Toast.LENGTH_SHORT).show();
            }
        });

        // 设置返回 MainActivity 按钮
        Button returnMainButton = findViewById(R.id.btn_return_main);
        returnMainButton.setOnClickListener(v -> {
            Intent intent = new Intent(ContextMenuActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // 可选，结束当前活动
        });
    }

    // 处理返回按钮点击事件
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // 返回到上一个活动
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
