package org.fxapps.temis.service;

import java.util.List;

import org.fxapps.temis.model.Alderman;
import org.fxapps.temis.model.Law;

public interface TemisClientService {

	/**
	 * All aldermen
	 * 
	 * @return
	 */
	public List<Alderman> aldermen();

	/**
	 * Laws by alderman
	 * 
	 * @param aldermen
	 * @param page
	 * @param numberOfResults
	 * @return
	 */
	public List<Law> laws(Alderman aldermen, int page, int numberOfResults);

	/**
	 * 
	 * All Laws
	 * 
	 * @param page
	 * @param numberOfResults
	 * @return
	 */
	public List<Law> laws(int page, int numberOfResults);
	
	
	
	/**
	 * returns a new TemisClientService instance - NOT SINGLETON
	 * 
	 * @param baseUrl
	 * @return
	 */
	public static TemisClientService get(String baseUrl) {
		return new TemisClientServiceImpl(baseUrl);
	}

}