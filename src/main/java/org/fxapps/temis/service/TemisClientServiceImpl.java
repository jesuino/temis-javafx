package org.fxapps.temis.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.fxapps.temis.model.Alderman;
import org.fxapps.temis.model.Law;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

class TemisClientServiceImpl implements TemisClientService {

	private String baseUrl;

	public TemisClientServiceImpl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	@Override
	public List<Alderman> aldermen() {
		return getClient().aldermen();
	}

	@Override
	public List<Law> laws(Alderman aldermen, int page, int numberOfResults) {
		return getClient().laws(aldermen.getName(), page, numberOfResults).getEmbedded().getLaws();
	}

	@Override
	public List<Law> laws(int page, int numberOfResults) {
		return getClient().laws(page, numberOfResults).getEmbedded().getLaws();
	}

	private TemisClientProxy getClient() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(baseUrl);
		ResteasyWebTarget rtarget = (ResteasyWebTarget) target;
		return rtarget.proxy(TemisClientProxy.class);
	}
	
	@Path("api")
	public static interface TemisClientProxy {

		@GET
		@Path("laws")
		public LawList laws(@QueryParam("page") int p, @QueryParam("size") int s);

		@GET
		@Path("alderman")
		public List<Alderman> aldermen();

		@GET
		@Path("laws/alderman/{name}")
		public LawList laws(@PathParam("name") String name, @QueryParam("page") int p, @QueryParam("size") int s);

	}
	
	
	
	/**
	 * Wrapping class for Laws
	 * 
	 * @author wsiqueir
	 *
	 */
	@JsonIgnoreProperties("_links")
	public static class LawList {
		
		@JsonProperty("_embedded")
		private Embedded embedded;
		
		public Embedded getEmbedded() {
			return embedded;
		}
		public void setEmbedded(Embedded embedded) {
			this.embedded = embedded;
		}
	}

	/**
	 * Wrapping class for laws list
	 * 
	 * @author wsiqueir
	 *
	 */
	public static class Embedded {

		@JsonProperty("lawList")
		private List<Law> laws;

		public List<Law> getLaws() {
			return laws;
		}

		public void setLaws(List<Law> laws) {
			this.laws = laws;
		}
	}

}
