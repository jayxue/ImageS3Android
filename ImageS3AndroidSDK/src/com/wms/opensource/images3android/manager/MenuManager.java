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

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.wms.opensource.images3android.R;
import com.wms.opensource.images3android.images3.entity.Image;
import com.wms.opensource.images3android.images3.entity.TemplateCollection;
import com.wms.opensource.images3android.images3.entity.TemplateResult;
import com.wms.opensource.images3android.menu.IconContextMenu;
import com.wms.opensource.images3android.menu.IconContextMenu.IconContextItemSelectedListener;
import com.wms.opensource.images3android.task.DeleteImageTask;
import com.wms.opensource.images3android.util.FileUtil;
import com.wms.opensource.images3android.util.JsonUtil;
import com.wms.opensource.images3android.util.PersistFileNameUtil;
import com.wms.opensource.images3android.util.StorageUtil;

import android.content.Context;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.widget.Toast;

public class MenuManager {

	private static TemplateCollection templateCollection = null;
	
	public static void displayTemplatesMenu(final Context context, final Image image) {
		IconContextMenu cm = new IconContextMenu(context, R.menu.templates_menu);
		if(templateCollection == null) {
			String templateCollectionString = "";
			templateCollectionString = FileUtil.getStringFromFileInCache(StorageUtil.getTempDirectory(context),
					PersistFileNameUtil.getTemplateCollectionFileName(image.getImagePlantId()),	context.getString(R.string.charSetName));
			try {
				templateCollection = (TemplateCollection) JsonUtil.deserialize(templateCollectionString, "", TemplateCollection.class);
			}
			catch (JsonParseException e) {
				
			}
			catch (JsonMappingException e) {
				
			}
			catch (IOException e) {
				
			}			
		}
		for(int i = 0; i < templateCollection.getResults().size(); i++) {
			TemplateResult result = templateCollection.getResults().get(i);
			cm.getMenu().add(0, i + 1, i, result.getId().getTemplateName());
		}

		cm.setOnIconContextItemSelectedListener(new IconContextItemSelectedListener() {
			@Override
			public void onIconContextItemSelected(MenuItem item, Object info) {
				if(item.getItemId() == R.id.action_hint) {
					// Position 0 is the indication for selecting a template, so we do nothing
				}
				else {
					String templateName = item.getTitle().toString();
					ReviewImageManager reviewImageManager = new ReviewImageManager(context);
					reviewImageManager.showImagePopupWindow(image, templateName);
				}
			}
		});
		
		cm.show();
	}
	
	public static void displayImageProcessOptionsMenu(final Context context, final Image image, final Bitmap bitmap, final ReviewImageManager manager) {
		IconContextMenu cm = new IconContextMenu(context, R.menu.image_process_options_menu);
		cm.setOnIconContextItemSelectedListener(new IconContextItemSelectedListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void onIconContextItemSelected(MenuItem item, Object info) {
				if(item.getItemId() == R.id.action_hint) {
					// Position 0 is the indication for selecting a template, so we do nothing
				}
				else if(item.getItemId() == R.id.action_save_to_gallery) {
					MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, image.getId(), "");
					Toast.makeText(context, context.getString(R.string.saveToGallaryCompleted),	Toast.LENGTH_LONG).show();
				}
				else if(item.getItemId() == R.id.action_set_as_wall_paper) {
					try {
						context.setWallpaper(bitmap);
						Toast.makeText(context, context.getString(R.string.setWallPaperCompleted), Toast.LENGTH_LONG).show();
					}
					catch (IOException e) {

					}
				}
				else if(item.getItemId() == R.id.action_delete) {
					manager.dismiss();
					// Delete image
					DeleteImageTask task = new DeleteImageTask(context, image.getBaseUrl(), image.getImagePlantId(), image.getId());
					task.execute();
				}
			}
		});
		
		cm.show();
	}
}
