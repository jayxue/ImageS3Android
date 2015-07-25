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

import java.util.ArrayList;
import java.util.List;

import com.wms.opensource.images3android.R;
import com.wms.opensource.images3android.adapter.ImageArrayAdapter;
import com.wms.opensource.images3android.handler.HandlerMessage;
import com.wms.opensource.images3android.images3.ImageS3Service;
import com.wms.opensource.images3android.images3.entity.Image;
import com.wms.opensource.images3android.task.LoadCachedImagesTask;
import com.wms.opensource.images3android.task.LoadImagesTask;
import com.wms.opensource.images3android.type.NetworkStatus;
import com.wms.opensource.images3android.util.FileUtil;
import com.wms.opensource.images3android.util.PersistFileNameUtil;
import com.wms.opensource.images3android.util.StorageUtil;
import com.wms.opensource.images3android.util.NetworkUtil;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

public class ImageListFragment extends Fragment {

	private static final String KEY_CONTENT = "ImageListFragment";
	
	private String mContent = "";
	private int page = 1;

	private RelativeLayout layout = null;
	private ListView listView = null;
	private ProgressBar progressBar = null;
	
	private LoadImagesTask loadImagesTask = null;
	private LoadCachedImagesTask loadCachedImagesTask = null;
	
	private LoadImagesHandler loadImagesHandler = new LoadImagesHandler();
	
	private List<Image> images = new ArrayList<Image>();
	
    public static ImageListFragment newInstance(String content, int page) {
    	ImageListFragment fragment = new ImageListFragment();
        fragment.page = page;
        return fragment;        
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if ((savedInstanceState != null) && savedInstanceState.containsKey(KEY_CONTENT)) {
            mContent = savedInstanceState.getString(KEY_CONTENT);
        }        
    }

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	if(layout != null) {	
    		ViewGroup parent = (ViewGroup) layout.getParent();    		
    		parent.removeView(layout);
    		return layout;
    	}
    	
    	if(listView == null) {
            layout = new RelativeLayout(getActivity());                       
	        listView = new ListView(getActivity());	        
	        layout.addView(listView);

	        progressBar = new ProgressBar(getActivity(), null, android.R.attr.progressBarStyleLarge);

            // Center a view in relative layout
            RelativeLayout.LayoutParams progressBarParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            progressBarParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            progressBar.setLayoutParams(progressBarParams);
            progressBar.setVisibility(View.INVISIBLE);
            layout.addView(progressBar);

            // Before reloading videos, display videos if they have been saved before
	        String imagesFilePath = "";
       		imagesFilePath = StorageUtil.getTempDirectory(this.getActivity()) + "/" + PersistFileNameUtil.getImagesPersistFileName(getString(R.string.imagePlantId), page);
        	
	    	boolean imagesFileExists = FileUtil.fileExist(imagesFilePath);            
	    	if(imagesFileExists) {
    			loadCachedImagesTask = new LoadCachedImagesTask(getActivity(), loadImagesHandler, progressBar, getActivity().getString(R.string.ImageS3ServiceURL), getActivity().getString(R.string.imagePlantId), page);
    			loadCachedImagesTask.execute();
	    	}
	    	else {
		        NetworkStatus networkStatus = NetworkUtil.getNetworkStatus(getActivity());        
		        if (networkStatus.equals(NetworkStatus.WIFI_CONNECTED) || networkStatus.equals(NetworkStatus.MOBILE_CONNECTED)) {
		        	String pageToken = page == 1 ? "" : ImageListFragmentActivity.pageTokens.get(page - 1);
	        		loadImagesTask = new LoadImagesTask(getActivity(), loadImagesHandler, getActivity().getString(R.string.ImageS3ServiceURL), getActivity().getString(R.string.imagePlantId),
	        				pageToken, page);
	        		loadImagesTask.execute();
	        	}
		    	else {
		    		Toast.makeText(getActivity(), getString(R.string.noNetworkAvailable), Toast.LENGTH_LONG).show();
		    	}	    		
	    	}        
    	}    	
        return layout;
    }
	
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_CONTENT, mContent);
    }
    
 	private class LoadImagesHandler extends Handler { 	
     	
     	public void handleMessage(Message msg) {
     		super.handleMessage(msg);
     		if (msg.what == HandlerMessage.IMAGES_LOADED) {
   				images = loadImagesTask.getImages();
     		}
     		else if(msg.what == HandlerMessage.IMAGES_LOADED_FROM_CACHE) {
   				images = loadCachedImagesTask.getImages();
     		}
     		progressBar.setVisibility(View.INVISIBLE);
     		setImageList();

 			int startImage = 0;
 			if(page == 1) {
 				startImage = 1;
 			}
 			else {
 				startImage = (page - 1) * ImageS3Service.NUMBER_OF_IMAGES_PER_PAGE;
 			}
 			int endImage = ImageS3Service.NUMBER_OF_IMAGES_PER_PAGE;
			endImage = (page - 1) * ImageS3Service.NUMBER_OF_IMAGES_PER_PAGE + images.size();
 			if(endImage == 0) {
 				endImage = ImageS3Service.NUMBER_OF_IMAGES_PER_PAGE;
 			}
 			if(page == 1) {
 				if(getActivity() != null) {
 					((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.imageListActivityTitle) + " (" + startImage + " to " + endImage + ")");
 				}
 			}
     	}
    }

	private void setImageList() {
    	if(getActivity() != null)
    	{	
    		ArrayAdapter<Image> videoArrayAdapter = new ImageArrayAdapter(getActivity(), R.layout.image_list_item, images);
			listView.setAdapter(videoArrayAdapter);
    	}
    }
   
}
