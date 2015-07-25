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

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ImageCollection {
	@JsonProperty("page")
	private Page page;
	@JsonProperty("results")
	private List<ImageResult> results = new ArrayList<ImageResult>();

	/**
	*
	* @return
	* The page
	*/
	@JsonProperty("page")
	public Page getPage() {
		return page;
	}

	/**
	*
	* @param page
	* The page
	*/
	@JsonProperty("page")
	public void setPage(Page page) {
		this.page = page;
	}

	/**
	*
	* @return
	* The results
	*/
	@JsonProperty("results")
	public List<ImageResult> getResults() {
		return results;
	}

	/**
	*
	* @param results
	* The results
	*/
	@JsonProperty("results")
	public void setResults(List<ImageResult> results) {
		this.results = results;
	}
}
