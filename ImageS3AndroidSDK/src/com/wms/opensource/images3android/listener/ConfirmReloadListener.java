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

import com.wms.opensource.images3android.cache.ImageCache;
import com.wms.opensource.images3android.handler.HandlerMessage;
import com.wms.opensource.images3android.util.FileUtil;
import com.wms.opensource.images3android.util.MessageUtil;
import com.wms.opensource.images3android.util.PersistFileNameUtil;
import com.wms.opensource.images3android.util.StorageUtil;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Handler;

public class ConfirmReloadListener implements OnClickListener {

	private Context context = null;
	private Handler handler = null;
	private String imagePlantId = null;
	

	public ConfirmReloadListener(Context context, Handler handler, String imagePlantId) {
		this.context = context;
		this.handler = handler;
		this.imagePlantId = imagePlantId;
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		ImageCache.clearCache();
		FileUtil.deleteFilesInDir(StorageUtil.getTempDirectory(context), PersistFileNameUtil.getImagesPersistFileNamePattern(imagePlantId));
		MessageUtil.sendHandlerMessage(handler, HandlerMessage.IMAGES_COUNT_LOADED);
	}
	
}
