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

package com.wms.opensource.images3android.listener;

import java.io.File;

import com.wms.opensource.images3android.intent.IntentRequestCode;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;

public class ImageButtonTakePictureOnClickListener implements OnClickListener {

	Activity activity = null;
	File imageFile = null;

	public ImageButtonTakePictureOnClickListener(Activity activity) {
		this.activity = activity;
	}

	@Override
	public void onClick(View v) {
		String BX1 =  android.os.Build.MANUFACTURER;
		if(BX1.equalsIgnoreCase("samsung")) {
			// We need a special way to take picture on Samsung devices. 
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            activity.startActivityForResult(intent, IntentRequestCode.TAKE_PICTURE_SAMSUNG);
		}
		else {
			Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
            activity.startActivityForResult(intent, IntentRequestCode.TAKE_PICTURE); 
		}
	}

	public void setImageFile(File file) {
		imageFile = file;
	}

}
