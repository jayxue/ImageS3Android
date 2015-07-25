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

package com.wms.opensource.images3android.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.viewpagerindicator.LinePageIndicator;
import com.viewpagerindicator.PageIndicator;
import com.wms.opensource.images3android.R;
import com.wms.opensource.images3android.adapter.ImagesFragmentAdapter;
import com.wms.opensource.images3android.dialog.ConfirmReloadImagesDialogBuilder;
import com.wms.opensource.images3android.handler.HandlerMessage;
import com.wms.opensource.images3android.images3.ImageS3Service;
import com.wms.opensource.images3android.images3.entity.ImageCollection;
import com.wms.opensource.images3android.images3.entity.ImagePlant;
import com.wms.opensource.images3android.task.LoadImagePlantTask;
import com.wms.opensource.images3android.type.NetworkStatus;
import com.wms.opensource.images3android.util.FileUtil;
import com.wms.opensource.images3android.util.JsonUtil;
import com.wms.opensource.images3android.util.MessageUtil;
import com.wms.opensource.images3android.util.NetworkUtil;
import com.wms.opensource.images3android.util.PersistFileNameUtil;
import com.wms.opensource.images3android.util.StorageUtil;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.WindowManager;
import android.widget.Toast;

public class ImageListFragmentActivity extends AppCompatActivity {

	private int pageCount = 1;
	
    private ImagesFragmentAdapter mAdapter;
    private ViewPager mPager;
    private PageIndicator mIndicator;
    
    private LoadImagePlantTask loadImagePlantTask = null;

    private LoadImagePlantHandler loadImagePlantHandler = null;
	
	// Shall we automatically load overall info?
	boolean shouldAutoRunOverallInfoLoadingTask = false;
	
    private int savedImagesCount = 0;
    
    private int maxPageCount = 30;
    
    public static int currentPage = 1;
    
    // Maintain tokens of all pages
    public static List<String> pageTokens = new ArrayList<String>();
    
    private String imagePlantId = "";
    private ImagePlant imagePlant = null;
    
    List<Fragment> fragments = new ArrayList<Fragment>();
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_pages);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_launcher);
        getSupportActionBar().setTitle(getString(R.string.imageListActivityTitle));

        maxPageCount = getResources().getInteger(R.integer.MAX_PAGE_COUNT);
        imagePlantId = getString(R.string.imagePlantId);
        
        loadImagePlantHandler = new LoadImagePlantHandler();
        
    	String imagePlantFilePath = "";
   		imagePlantFilePath = StorageUtil.getTempDirectoryPath(this) + "/" + PersistFileNameUtil.getImagePlantPersistFileName(imagePlantId);
    	
    	boolean imagePlantFileExists = FileUtil.fileExist(imagePlantFilePath);
    	
    	if(imagePlantFileExists) {
       		shouldAutoRunOverallInfoLoadingTask = true;   		
    		
    		// The app has loaded image plant before, so we directly get images count
    		String imagePlantStr = "";
    		imagePlantStr = FileUtil.getStringFromFileInCache(StorageUtil.getTempDirectory(this), PersistFileNameUtil.getImagePlantPersistFileName(imagePlantId), 
    							getString(R.string.charSetName));
    		
			try {
				imagePlant = (ImagePlant) JsonUtil.deserialize(imagePlantStr, "", ImagePlant.class);
	    		savedImagesCount = imagePlant.getNumberOfImages();	    		
				pageCount = (int) Math.ceil(savedImagesCount * 1.0 / ImageS3Service.NUMBER_OF_IMAGES_PER_PAGE);
	   			MessageUtil.sendHandlerMessage(loadImagePlantHandler, HandlerMessage.IMAGES_COUNT_LOADED);
			}
			catch (JsonParseException e) {

			}
			catch (JsonMappingException e) {

			}
			catch (IOException e) {

			}
    	}
    	else {
    		// The app never loaded image plant before, so we should load it
    		loadImagePlant(false, savedImagesCount);
    	}

        // Do not show the soft keyboard
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
	public boolean onCreateOptionsMenu(Menu menu) {		
		return true;
	}

    @SuppressLint("HandlerLeak")
	private class LoadImagePlantHandler extends Handler { 	
    	
    	public void handleMessage(Message msg) {
    		super.handleMessage(msg);
    		if (msg.what == HandlerMessage.IMAGES_COUNT_LOADED_ASK_FOR_RELOADING) {
    			ConfirmReloadImagesDialogBuilder builder = new ConfirmReloadImagesDialogBuilder(ImageListFragmentActivity.this, loadImagePlantHandler,imagePlantId);
    			builder.create().show();
    		}
    		else if (msg.what == HandlerMessage.IMAGES_COUNT_LOADED) {
   				if(loadImagePlantTask != null) {
   					pageCount = (int) Math.ceil(loadImagePlantTask.getImagesCount() * 1.0 / ImageS3Service.NUMBER_OF_IMAGES_PER_PAGE);
   				}
   				else {
   					pageCount = (int) Math.ceil(savedImagesCount * 1.0 / ImageS3Service.NUMBER_OF_IMAGES_PER_PAGE);
   				}
   				
    	        if(pageCount == 0) {
    	        	// At least one page
    	        	pageCount = 1;
    	        }
    	        else if(pageCount > maxPageCount) {
    	        	pageCount = maxPageCount;
    	        }
    	        
    	        pageTokens.clear();
    	        for(int i = 0; i < pageCount; i++) {
    	        	pageTokens.add("");
    	        }
    	        if(pageTokens.size() > 1) {
    	        	if(loadImagePlantTask != null) {    	        
    	        		pageTokens.set(1, loadImagePlantTask.getSecondPageToken());
    	        	}
    	        	else {
    	        		String firstPageImageCollectionFileName = StorageUtil.getTempDirectory(ImageListFragmentActivity.this) + "/" + PersistFileNameUtil.getImagesPersistFileName(imagePlantId, 1);
    	        		if(FileUtil.fileExist(firstPageImageCollectionFileName)) {
    	        			// The first page has been cached. Load it
    	        			String jsonString = FileUtil.getStringFromFileInCache(StorageUtil.getTempDirectory(ImageListFragmentActivity.this), PersistFileNameUtil.getImagesPersistFileName(imagePlantId, 1), getString(R.string.charSetName));
    	        			try {
								ImageCollection imageCollection = (ImageCollection) JsonUtil.deserialize(jsonString, "", ImageCollection.class);
								pageTokens.set(1, imageCollection.getPage().getNext());
							}
    	        			catch (JsonParseException e) {
							
    	        			}
    	        			catch (JsonMappingException e) {
							
    	        			}
    	        			catch (IOException e) {
							
    	        			}
    	        		}
    	        	}
    	        }

   				mAdapter = new ImagesFragmentAdapter(getSupportFragmentManager(), pageCount);

    	        mPager = (ViewPager)findViewById(R.id.pager);
    	        mPager.setAdapter(mAdapter);

    	        mIndicator = (LinePageIndicator)findViewById(R.id.indicator);
	        	// Set width of the line page indicator
    	        if(pageCount > 20) {
    	        	((LinePageIndicator)mIndicator).setLineWidth(15);
    	        }
    	        if(pageCount > 10) {
    	        	((LinePageIndicator)mIndicator).setLineWidth(25);
    	        }
    	        mIndicator.setViewPager(mPager);

    	        mIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
    	            @Override
    	            public void onPageSelected(int position) {
    	            	currentPage = position + 1;
    	    			int startImage = (currentPage - 1) * ImageS3Service.NUMBER_OF_IMAGES_PER_PAGE + 1;
    	    			int endImage = currentPage * ImageS3Service.NUMBER_OF_IMAGES_PER_PAGE;
    	    			getSupportActionBar().setTitle(getString(R.string.imageListActivityTitle) + " (" + startImage + " to " + endImage + ")");
    	            }

    	            @Override
    	            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    	            	
    	            }

    	            @Override
    	            public void onPageScrollStateChanged(int state) {
    	            	
    	            }
    	        });
    	         
    		}
    	}
    	
    }
    
    @Override
    public void onAttachFragment (Fragment fragment) {
    	// Collect all fragments of the activity
        fragments.add(fragment);
        
        if(shouldAutoRunOverallInfoLoadingTask && (pageCount == 1 && fragments.size() == 1 || pageCount >= 2 && fragments.size() == 2)) {
        	// After at least one fragment is attached, start reloading image plant info.
        	loadImagePlant(true, savedImagesCount);
        }
    }

    private void loadImagePlant(boolean shouldReloadSilently, int savedImagesCount) {
    	NetworkStatus networkStatus = NetworkUtil.getNetworkStatus(this);
	    if (networkStatus.equals(NetworkStatus.WIFI_CONNECTED) || networkStatus.equals(NetworkStatus.MOBILE_CONNECTED)) {
    		loadImagePlantTask = new LoadImagePlantTask(this, loadImagePlantHandler, shouldReloadSilently, savedImagesCount);
	    	loadImagePlantTask.execute(getString(R.string.ImageS3ServiceURL), getString(R.string.imagePlantId));   			
   		}
       	else {
       		Toast.makeText(this, getString(R.string.noNetworkAvailable), Toast.LENGTH_LONG).show();
       	}    	
    }
}
