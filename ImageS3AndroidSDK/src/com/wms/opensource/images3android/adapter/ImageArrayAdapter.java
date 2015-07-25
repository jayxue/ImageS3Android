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

import java.util.List;

import com.androidquery.AQuery;
import com.androidquery.callback.ImageOptions;
import com.wms.opensource.images3android.R;
import com.wms.opensource.images3android.images3.entity.Image;
import com.wms.opensource.images3android.manager.MenuManager;
import com.wms.opensource.images3android.util.DeviceUtil;
import com.wms.opensource.images3android.util.ViewUtil;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageArrayAdapter extends ArrayAdapter<Image> {
	
	private AQuery listAq = null;
	private AQuery aq = null;
	
	public ImageArrayAdapter(Context context, int resource, List<Image> images) {
		super(context, resource, images);
		listAq = new AQuery(context);
		aq = new AQuery(context);
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		if(convertView == null){
			convertView = ((Activity)getContext()).getLayoutInflater().inflate(R.layout.image_list_item, null);
		}

		convertView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
           		Image image = ImageArrayAdapter.this.getItem(position);
    			MenuManager.displayTemplatesMenu(getContext(), image);
            }
            
        });
		
		final Image image = this.getItem(position);
		
		int screenWidth = DeviceUtil.getDeviceWidth(getContext());
		
		String thumbnailUrl = image.getImageFileUrl();
		
		aq = listAq.recycle(convertView);
		ImageOptions options = new ImageOptions();
		options.round = 1;
		options.ratio = AQuery.RATIO_PRESERVE;
		options.memCache = true;
		options.fileCache = true;
		options.targetWidth = 200;
		aq.id(R.id.imageViewThumbnail).progress(R.id.imageLoadingProgressBar).image(thumbnailUrl, options);
	
		ImageView imageViewThumbnail = (ImageView) convertView.findViewById(R.id.imageViewThumbnail);
		
		// Adjust sizes of the thumbnail image according to screen width			
		if(screenWidth >= 1200) {
			ViewUtil.setImageViewSize(imageViewThumbnail, 480, 360);
		}
		else if(screenWidth >= 640) {
			ViewUtil.setImageViewSize(imageViewThumbnail, 320, 180);
		}			

		TextView textViewInfo = (TextView) convertView.findViewById(R.id.textViewID);
		textViewInfo.setText(image.getId());

		TextView textViewPublishTime = (TextView) convertView.findViewById(R.id.textViewPublishTime);
		textViewPublishTime.setText(image.getUploadTime());

		TextView textViewDimension = (TextView) convertView.findViewById(R.id.textViewDimension);
		textViewDimension.setText(image.getWidth() + " x " + image.getHeight() + " (px)");

		TextView textViewSize = (TextView) convertView.findViewById(R.id.textViewSize);
		textViewSize.setText(image.getSize() + " Bytes");

		TextView textViewFormat = (TextView) convertView.findViewById(R.id.textViewFormat);
		textViewFormat.setText(image.getFormat());

		TextView textViewTemplate = (TextView) convertView.findViewById(R.id.textViewTemplate);
		textViewTemplate.setText(image.getTemplate());
		
		TextView textViewOriginalImageId = (TextView) convertView.findViewById(R.id.textViewOriginalImageId);
		textViewOriginalImageId.setText(image.getOriginalImageId());
		
		return convertView;
	}
	
}
