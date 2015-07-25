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

package com.wms.opensource.images3android.images3.entity;

public class Image {

	private String baseUrl = "";
	private String imagePlantId = "";
	private String id = "";
	private String uploadTime = "";
	private int width = 0;
	private int height = 0;
	private String format = "";
	private int size = 0;
	private String template = "";
	private String originalImageId = "";
	
	public Image( String baseUrl, ImageResult result) {
		this.baseUrl = baseUrl;
		this.imagePlantId = result.getId().getImagePlantId();
		this.id = result.getId().getImageId();
		this.uploadTime = result.getDateTime();
		this.width = result.getMetadata().getDimension().getWidth();
		this.height= result.getMetadata().getDimension().getHeight();
		this.format = result.getMetadata().getFormat();
		this.size = result.getMetadata().getSize();
		this.template = result.getVersion().getTemplateName();
		this.originalImageId = result.getVersion().getOriginalImageId();
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public String getImagePlantId() {
		return imagePlantId;
	}

	public void setImagePlantId(String imagePlantId) {
		this.imagePlantId = imagePlantId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(String uploadTime) {
		this.uploadTime = uploadTime;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getOriginalImageId() {
		return originalImageId;
	}

	public void setOriginalImageId(String originalImageId) {
		this.originalImageId = originalImageId;
	}

	public String getImageFileUrl() {
		return baseUrl + imagePlantId + "/imagefiles/" + id;
	}
		
	public String getImageWithTemplateFileUrl(String templateName) {
		if(template.equals("Master")) {
			return baseUrl + imagePlantId + "/imagefiles/" + id + "?template=" + templateName;
		}
		else {
			return baseUrl + imagePlantId + "/imagefiles/" + originalImageId + "?template=" + templateName;
		}
	}
}
