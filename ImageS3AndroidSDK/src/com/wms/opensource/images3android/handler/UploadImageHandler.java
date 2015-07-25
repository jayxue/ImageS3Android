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

package com.wms.opensource.images3android.handler;

import com.wms.opensource.images3android.activity.UploadImageActivity;

import android.os.Handler;
import android.os.Message;

public class UploadImageHandler extends Handler {

	private UploadImageActivity activity = null;
	
	public UploadImageHandler(UploadImageActivity activity) {
		this.activity = activity;
	}
	
	public void handleMessage(Message msg) {
		super.handleMessage(msg);
		
		if (msg.what == HandlerMessage.IMAGE_UPLOADED) {
			activity.reset();
		}
	}
}
