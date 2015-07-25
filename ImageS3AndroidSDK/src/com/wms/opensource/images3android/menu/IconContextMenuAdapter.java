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

package com.wms.opensource.images3android.menu;

import android.content.Context;
import android.view.*;
import android.widget.*;

public class IconContextMenuAdapter extends BaseAdapter {
	private Context context;
	private Menu menu;
   
    public IconContextMenuAdapter(Context context, Menu menu) {
    	this.context = context;
    	this.menu = menu;
    }

    @Override
    public int getCount() {
    	return menu.size();
    }
   
    @Override
    public MenuItem getItem(int position) {
    	return menu.getItem(position);
    }

    @Override
    public long getItemId(int position) {
    	return getItem(position).getItemId();
    }
   
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MenuItem item = getItem(position);
       
        TextView res = (TextView) convertView;
        if (res == null) {
        	res = (TextView) LayoutInflater.from(context).inflate(android.R.layout.select_dialog_item, null);
        }

        res.setTag(item);
        res.setText(item.getTitle());
        res.setCompoundDrawablesWithIntrinsicBounds(item.getIcon(), null, null, null);

        return res;
    }
}
