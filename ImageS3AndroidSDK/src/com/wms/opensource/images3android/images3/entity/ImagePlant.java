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

import com.fasterxml.jackson.annotation.JsonProperty;

public class ImagePlant {

	@JsonProperty("id")
	private String id;
	@JsonProperty("name")
	private String name;
	@JsonProperty("creationTime")
	private String creationTime;
	@JsonProperty("bucket")
	private Bucket bucket;
	@JsonProperty("masterTemplate")
	private MasterTemplate masterTemplate;
	@JsonProperty("numberOfTemplates")
	private Integer numberOfTemplates;
	@JsonProperty("numberOfImages")
	private Integer numberOfImages;

	/**
	*
	* @return
	* The id
	*/
	@JsonProperty("id")
	public String getId() {
		return id;
	}

	/**
	*
	* @param id
	* The id
	*/
	@JsonProperty("id")
	public void setId(String id) {
		this.id = id;
	}

	/**
	*
	* @return
	* The name
	*/
	@JsonProperty("name")
	public String getName() {
		return name;
	}

	/**
	*
	* @param name
	* The name
	*/
	@JsonProperty("name")
	public void setName(String name) {
		this.name = name;
	}

	/**
	*
	* @return
	* The creationTime
	*/
	@JsonProperty("creationTime")
	public String getCreationTime() {
		return creationTime;
	}

	/**
	*
	* @param creationTime
	* The creationTime
	*/
	@JsonProperty("creationTime")
	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
	}

	/**
	*
	* @return
	* The bucket
	*/
	@JsonProperty("bucket")
	public Bucket getBucket() {
		return bucket;
	}

	/**
	*
	* @param bucket
	* The bucket
	*/
	@JsonProperty("bucket")
	public void setBucket(Bucket bucket) {
		this.bucket = bucket;
	}

	/**
	*
	* @return
	* The masterTemplate
	*/
	@JsonProperty("masterTemplate")
	public MasterTemplate getMasterTemplate() {
		return masterTemplate;
	}

	/**
	*
	* @param masterTemplate
	* The masterTemplate
	*/
	@JsonProperty("masterTemplate")
	public void setMasterTemplate(MasterTemplate masterTemplate) {
		this.masterTemplate = masterTemplate;
	}

	/**
	*
	* @return
	* The numberOfTemplates
	*/
	@JsonProperty("numberOfTemplates")
	public Integer getNumberOfTemplates() {
		return numberOfTemplates;
	}

	/**
	*
	* @param numberOfTemplates
	* The numberOfTemplates
	*/
	@JsonProperty("numberOfTemplates")
	public void setNumberOfTemplates(Integer numberOfTemplates) {
		this.numberOfTemplates = numberOfTemplates;
	}

	/**
	*
	* @return
	* The numberOfImages
	*/
	@JsonProperty("numberOfImages")
	public Integer getNumberOfImages() {
		return numberOfImages;
	}

	/**
	*
	* @param numberOfImages
	* The numberOfImages
	*/
	@JsonProperty("numberOfImages")
	public void setNumberOfImages(Integer numberOfImages) {
		this.numberOfImages = numberOfImages;
	}

}
