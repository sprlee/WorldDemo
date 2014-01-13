package com.example.mifiledemo;


import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private TextView mNavigationBarText;

	private View mDropdownNavigation;

	private ImageView mNavigationBarUpDownArrow;
	String displayPath ="/storage/sdcard0/MIUI/music/";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mDropdownNavigation = findViewById(R.id.dropdown_navigation);
		mNavigationBarText = (TextView) findViewById(R.id.current_path_view);
		mNavigationBarUpDownArrow = (ImageView) findViewById(R.id.path_pane_arrow);
		View clickable = findViewById(R.id.current_path_pane);
		clickable.setOnClickListener(buttonClick);
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	private View.OnClickListener buttonClick = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {

			case R.id.current_path_pane:
				onNavigationBarClick();
				break;
			default:
				break;
			}
		}

	};

	@SuppressLint("NewApi")
	protected void onNavigationBarClick() {
		if (mDropdownNavigation.getVisibility() == View.VISIBLE) {
			showDropdownNavigation(false);
		} else {
			LinearLayout list = (LinearLayout) mDropdownNavigation
					.findViewById(R.id.dropdown_navigation_list);
			list.removeAllViews();
			int pos = 0;
			
			boolean root = true;
			int left = 0;
			while (pos != -1 && !displayPath.equals("/")) {// 如果当前位置在根文件夹则不显示导航条
				int end = displayPath.indexOf("/", pos);
				if (end == -1)
					break;

				View listItem = LayoutInflater.from(this).inflate(
						R.layout.dropdown_item, null);

				View listContent = listItem.findViewById(R.id.list_item);
				listContent.setPadding(left, 0, 0, 0);
				left += 20;
				ImageView img = (ImageView) listItem
						.findViewById(R.id.item_icon);

				img.setImageResource(root ? R.drawable.dropdown_icon_root
						: R.drawable.dropdown_icon_folder);
				root = false;

				TextView text = (TextView) listItem
						.findViewById(R.id.path_name);
				String substring = displayPath.substring(pos, end);
				if (substring.isEmpty()){
					
					substring = "/";
				}
				text.setText(substring);
//
				listItem.setOnClickListener(navigationClick);
				listItem.setTag(displayPath
						.substring(0, end));
				pos = end + 1;
				list.addView(listItem);
			}
			if (list.getChildCount() > 0)
				showDropdownNavigation(true);

		}
		 updateNavigationPane();
	}

	private void showDropdownNavigation(boolean show) {
		mDropdownNavigation.setVisibility(show ? View.VISIBLE : View.GONE);
		mNavigationBarUpDownArrow.setImageResource(mDropdownNavigation
				.getVisibility() == View.VISIBLE ? R.drawable.arrow_up
				: R.drawable.arrow_down);
	}
	
    private OnClickListener navigationClick = new OnClickListener() {

        @Override
        public void onClick(View v) {
            String path = (String) v.getTag();
            assert (path != null);
            Toast.makeText(MainActivity.this, path, Toast.LENGTH_SHORT).show();
//            showDropdownNavigation(false);
//            if (mFileViewListener.onNavigation(path))
//                return;
//
//            if(path.isEmpty()){
//                mCurrentPath = mRoot;
//            } else{
//                mCurrentPath = path;
//            }
//            refreshFileList();
        }

    };
    private void updateNavigationPane() {
//        View upLevel = this.findViewById(R.id.path_pane_up_level);
//        upLevel.setVisibility(mRoot.equals(mCurrentPath) ? View.INVISIBLE : View.VISIBLE);
//
//        View arrow =this.findViewById(R.id.path_pane_arrow);
//        arrow.setVisibility(mRoot.equals(mCurrentPath) ? View.GONE : View.VISIBLE);

        mNavigationBarText.setText(displayPath);
    }


}
