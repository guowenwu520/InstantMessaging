/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hyphenate.easeui.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.easeui.widget.EaseContactList;
import com.hyphenate.exceptions.HyphenateException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * contact list
 * 
 */
public class EaseContactListFragment extends EaseBaseFragment {
    private static final String TAG = "EaseContactListFragment";

  //  protected ImageButton clearSearch;
   // protected EditText query;
    protected Handler handler = new Handler();
    protected TabLayout toplay;
    protected ViewPager viewPager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.ease_fragment_contact_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
    	//to avoid crash when open app after long time stay in background after user logged into another device
        if(savedInstanceState != null && savedInstanceState.getBoolean("isConflict", false))
            return;
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    protected void initView() {

        //search
        toplay = getView().findViewById(R.id.toplay);
        viewPager=getView().findViewById(R.id.viewpageXi);
      //  clearSearch = (ImageButton) getView().findViewById(R.id.search_clear);
    }

    @Override
    protected void setUpView() {


//        query.addTextChangedListener(new TextWatcher() {
//                public void onTextChanged(CharSequence s, int start, int before, int count) {
//                    contactListLayout.filter(s);
//                    if (s.length() > 0) {
//                        clearSearch.setVisibility(View.VISIBLE);
//                    } else {
//                        clearSearch.setVisibility(View.INVISIBLE);
//
//                    }
//                }
//
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            public void afterTextChanged(Editable s) {
//            }
//        });
//        clearSearch.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                query.getText().clear();
//                hideSoftKeyboard();
//            }
//        });



    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

    }

    @Override
    public void onResume() {
        super.onResume();

    }



    

    

    @Override
    public void onDestroy() {
        

        super.onDestroy();
    }
    

    /**
     * get contact list and sort, will filter out users in blacklist
     */



    
    
    



}
