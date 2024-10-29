package com.example.homework3;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private String[] animalNames = {"Lion", "Tiger", "Monkey", "Dog", "Cat", "Elephant"};
    private int[] animalImages = {R.drawable.lion, R.drawable.tiger, R.drawable.monkey,
            R.drawable.dog, R.drawable.cat, R.drawable.elephant};
    private TextView testTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button showListButton = findViewById(R.id.show_list_button);
        Button showDialogButton = findViewById(R.id.show_dialog_button);
        Button showMenuButton = findViewById(R.id.show_menu_button);
        Button showContextMenuButton = findViewById(R.id.show_context_menu_button); // 新增的按钮
        ListView listView = findViewById(R.id.listView);
        testTextView = findViewById(R.id.test_text_view);

        // 设置“显示列表”按钮点击事件
        showListButton.setOnClickListener(v -> {
            if (listView.getVisibility() == View.VISIBLE) {
                // 如果 ListView 可见，隐藏它
                listView.setVisibility(View.GONE);
            } else {
                // 如果 ListView 隐藏，显示并设置数据
                listView.setVisibility(View.VISIBLE);
                setupListView(listView);
            }
        });

        // 设置“显示登录对话框”按钮点击事件
        showDialogButton.setOnClickListener(v -> showLoginDialog());

        // 设置“显示菜单”按钮点击事件
        showMenuButton.setOnClickListener(v -> showPopupMenu(v));
        // 设置“显示上下文菜单”按钮点击事件
        showContextMenuButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ContextMenuActivity.class);
            startActivity(intent); // 启动新活动
        });
    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_test, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.font_size_small) {
                testTextView.setTextSize(10);
            } else if (item.getItemId() == R.id.font_size_medium) {
                testTextView.setTextSize(16);
            } else if (item.getItemId() == R.id.font_size_large) {
                testTextView.setTextSize(20);
            } else if (item.getItemId() == R.id.color_red) {
                testTextView.setTextColor(Color.RED);
            } else if (item.getItemId() == R.id.color_black) {
                testTextView.setTextColor(Color.BLACK);
            } else if (item.getItemId() == R.id.regular_option) {
                Toast.makeText(MainActivity.this, "普通菜单项", Toast.LENGTH_SHORT).show();
            }
            return true;
        });

        popupMenu.show();
    }

    private void setupListView(ListView listView) {
        // 准备数据
        List<Map<String, Object>> data = new ArrayList<>();
        for (int i = 0; i < animalNames.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", animalNames[i]);
            map.put("image", animalImages[i]);
            data.add(map);
        }

        // 使用 SimpleAdapter 将数据绑定到 ListView
        String[] from = {"name", "image"};
        int[] to = {R.id.item_text, R.id.item_image};
        SimpleAdapter adapter = new SimpleAdapter(this, data, R.layout.list_item, from, to);
        listView.setAdapter(adapter);

        // 设置点击事件
        listView.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) ->
                Toast.makeText(MainActivity.this, animalNames[position], Toast.LENGTH_SHORT).show()
        );
    }

    private void showLoginDialog() {
        // 使用自定义的 dialog_login.xml 布局创建对话框
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.dialog_custom, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView)
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .setPositiveButton("Sign in", (dialog, which) -> {
                    // 获取输入框内容
                    Toast.makeText(this, "Login button clicked", Toast.LENGTH_SHORT).show();
                });
        builder.create().show();
    }
}
