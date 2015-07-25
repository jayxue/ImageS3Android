/*
 * Copyright 2015 Waterloo Mobile Studio
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wms.opensource.images3android.adapter;

import com.wms.opensource.images3android.activity.ImageListFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class ImagesFragmentAdapter extends FragmentStatePagerAdapter {

	private static String[] contents = null;
	
	private int pageCount = 0;
	
	public ImagesFragmentAdapter(FragmentManager fm, int pageCount) {
		super(fm);
		this.pageCount = pageCount;
		contents = new String[pageCount];
		for(int i = 0; i < contents.length; i++)
			contents[i] = "";
	}

	public void setPageCount(int count) {
		contents = new String[count];
	}

	public void setPageContent(int position, String content) {
		contents[position] = content;
	}	
	
	@Override
	public Fragment getItem(int position) {
		return ImageListFragment.newInstance(contents[position % contents.length], position + 1);
	}

	@Override
	public int getCount() {
		return pageCount;
	}

}
