package com.hewaiming.slidebottomview.demo;

import java.util.ArrayList;
import java.util.List;

import com.hewaiming.slidebottompanel.SlideBottomPanel;

import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewPagerActivity extends FragmentActivity {
    private ViewPager mPager;//椤靛崱鍐呭
    private ArrayList<Fragment> listViews; //Tab椤甸潰鍒楄�??
    private ImageView cursor;//鍔ㄧ敾鍥剧墖
    private TextView t1, t2, t3;//椤靛崱澶存爣
    private int offset = 0;//鍔ㄧ敾鍥剧墖鍋忕Щ閲�
    private int currIndex = 0;//褰撳墠椤靛崱缂栧�?
    private int bmpW;//鍔ㄧ敾鍥剧墖瀹藉�?
    OneFragment oneFragment=new OneFragment();
    TWOFragment twoFragment=new TWOFragment();
    THREEFragment thrFragment=new THREEFragment();

    private SlideBottomPanel sbv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        sbv = (SlideBottomPanel) findViewById(R.id.sbv);

        InitTextView();
        InitViewPager() ;
        InitImageView();
    }

    @Override
    public void onBackPressed() {
        if (sbv.isPanelShowing()) {
            sbv.hide();
            return;
        }
        super.onBackPressed();
    }

    private void InitTextView() {
        t1 = (TextView) findViewById(R.id.text1);
        t2 = (TextView) findViewById(R.id.text2);
        t3 = (TextView) findViewById(R.id.text3);
        t1.setOnClickListener(new MyOnClickListener(0));
        t2.setOnClickListener(new MyOnClickListener(1));
        t3.setOnClickListener(new MyOnClickListener(2));
    }
    /**
     * 澶存爣鐐瑰嚮鐩戝�?
     */
    public class MyOnClickListener implements View.OnClickListener {
        private int index = 0;
        public MyOnClickListener(int i) {
            index = i;
        }
        @Override
        public void onClick(View v) {
            mPager.setCurrentItem(index);
        }
    };
    /**
     * 鍒濆鍖朧iewPager
     */
    private void InitViewPager() {
        mPager = (ViewPager) findViewById(R.id.vPager);
        listViews = new ArrayList<Fragment>();
/*	LayoutInflater mInflater = getLayoutInflater();
	listViews.add(mInflater.inflate(R.layout.one, null));
	listViews.add(mInflater.inflate(R.layout.two, null));
	listViews.add(mInflater.inflate(R.layout.three, null));*/
        listViews.add(oneFragment);
        listViews.add(twoFragment);
        listViews.add(thrFragment);
        //鐢╯upport鍖咃紝鍙兘鐢╣etSupportFragmentManager();
        FragmentManager fragmentManager =this.getSupportFragmentManager();
        //閫氳繃fragment閫傞厤鍣ㄦ妸fragment娣诲姞鍏iewpager涓�
        mPager.setAdapter(new MainFragmentPagerAdapter(fragmentManager,listViews));
        mPager.setCurrentItem(0);
        mPager.setOnPageChangeListener(new MyOnPageChangeListener());
    }
    /**
     * ViewPager閫傞厤鍣�?
     */
    public class MyPagerAdapter extends PagerAdapter {
        public List<View> mListViews;
        public MyPagerAdapter(List<View> mListViews) {
            this.mListViews = mListViews;
        }
        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView(mListViews.get(arg1));
        }
        @Override
        public void finishUpdate(View arg0) {
        }
        @Override
        public int getCount() {
            return mListViews.size();
        }
        @Override
        public Object instantiateItem(View arg0, int arg1) {
            ((ViewPager) arg0).addView(mListViews.get(arg1), 0);
            return mListViews.get(arg1);
        }
        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == (arg1);
        }
        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
        }
        @Override
        public Parcelable saveState() {
            return null;
        }
        @Override
        public void startUpdate(View arg0) {
        }
    }
    /**
     * 鍒濆鍖栧姩鐢�
     */
    private void InitImageView() {
        cursor = (ImageView) findViewById(R.id.cursor);
        bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.a).getWidth();//鑾峰彇鍥剧墖瀹藉�?
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;//鑾峰彇鍒嗚鲸鐜囧搴�?
        offset = (screenW/3-bmpW)/2;//璁＄畻鍋忕Щ閲�?
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        cursor.setImageMatrix(matrix);//璁剧疆鍔ㄧ敾鍒濆浣嶇疆
    }
    /**
     * 椤靛崱鍒囨崲鐩戝�?,�?瑰彉鍔ㄧ敾浣嶇疆
     */
    public class MyOnPageChangeListener implements OnPageChangeListener {
        int one,two;
        @Override
        public void onPageSelected(int arg0) {
            one = offset * 2 + bmpW;//椤靛�?1 -> 椤靛�?2 鍋忕Щ閲�
            two = one * 2;//椤靛�?1 -> 椤靛�?3 鍋忕Щ閲�
            Animation animation = null;
            switch(arg0) {
                case 0:
                    if(currIndex == 1) {
                        animation = new TranslateAnimation(one, 0, 0, 0);
                    } else if(currIndex == 2) {
                        animation = new TranslateAnimation(two, 0, 0, 0);
                    }
                    break;
                case 1:
                    if(currIndex == 0) {
                        animation = new TranslateAnimation(offset, one, 0, 0);
                    } else if(currIndex == 2) {
                        animation = new TranslateAnimation(two, one, 0, 0);
                    }
                    break;
                case 2:
                    if(currIndex == 0) {
                        animation = new TranslateAnimation(offset, two, 0, 0);
                    } else if(currIndex == 1) {
                        animation = new TranslateAnimation(one, two, 0, 0);
                    }
                    break;
            }
            currIndex = arg0;
            animation.setFillAfter(true);//True:鍥剧墖鍋滃湪鍔ㄧ敾缁撴潫浣嶇�?
            animation.setDuration(300);
            cursor.startAnimation(animation);
        }
        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }
        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }
}
