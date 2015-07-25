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

package com.wms.opensource.images3android.manager;

import com.androidquery.AQuery;
import com.wms.opensource.images3android.R;
import com.wms.opensource.images3android.images3.entity.Image;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.Toast;

public class ReviewImageManager {

	private Context context = null;	
	
	private View popUpView = null;
	private PopupWindow popupWindow = null;
	
	public ReviewImageManager(Context context) {
		this.context = context;
	}

	@SuppressWarnings("deprecation")
	public void showImagePopupWindow(final Image image, String templateName)  {	
		DisplayMetrics metrics = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
		int parentWidth = metrics.widthPixels;
		int parentHeight = metrics.heightPixels;

		popUpView = ((Activity) context).getLayoutInflater().inflate(R.layout.display_image, null);
		popupWindow = new PopupWindow(popUpView, (int) (parentWidth * 1.0), (int) (parentHeight * 1.0), true);

		final ImageView imageViewImage = (ImageView) popUpView.findViewById(R.id.imageViewImage);

		imageViewImage.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				popupWindow.dismiss();
			}

		});
		
		// Long press to save picture or set as wall paper
		imageViewImage.setOnLongClickListener(new ImageView.OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				MenuManager.displayImageProcessOptionsMenu(context, image, ((BitmapDrawable)imageViewImage.getDrawable()).getBitmap(), ReviewImageManager.this);
				return false;
			}

		});
		
		AQuery aq = new AQuery(context);
		boolean memCache = false;
		boolean fileCache = true;
		ProgressBar progressBar = (ProgressBar) popUpView.findViewById(R.id.progressBar);
		String imageWithTemplateFileUrl = image.getImageWithTemplateFileUrl(templateName);
		aq.id(imageViewImage).progress(progressBar).image(imageWithTemplateFileUrl, memCache, fileCache, 0, 0, null, AQuery.FADE_IN);

		popupWindow.setBackgroundDrawable(new BitmapDrawable());

		popupWindow.setAnimationStyle(R.style.AnimationPopup);
		try {
			popupWindow.showAtLocation(popUpView, Gravity.CENTER, 0, 0);
		}
		catch(Exception e) {
			
		}

		// Do not show the soft keyboard
		((Activity) context).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		Toast.makeText(context, context.getString(R.string.longPressImage), Toast.LENGTH_LONG).show();;
	}
	
	public void dismiss() {
		popupWindow.dismiss();
	}

}
