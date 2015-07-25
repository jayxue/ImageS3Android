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

import com.wms.opensource.images3android.R;
import com.wms.opensource.images3android.task.UploadImageTask;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Handler;

public class ConfirmUploadImageDialogBuilderOnClickListener implements OnClickListener {

	private Context context = null;
	private String filePath = "";
	private Handler handler = null;

	public ConfirmUploadImageDialogBuilderOnClickListener(Context context, String filePath, Handler handler) {
		this.context = context;
		this.filePath = filePath;
		this.handler = handler;
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		UploadImageTask task = new UploadImageTask(context, true, false, R.string.uploadingImage, handler);
		task.execute(context.getString(R.string.ImageS3ServiceURL), context.getString(R.string.imagePlantId), filePath);		
	}
	
}
