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

class TemisClientServiceImpl implements TemisClientService {

	private TemisClientProxy temisClientProxy;

	public TemisClientServiceImpl(String baseUrl) {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(baseUrl);
		ResteasyWebTarget rtarget = (ResteasyWebTarget) target;
		temisClientProxy = rtarget.proxy(TemisClientProxy.class);
	}

	@Override
	public List<Alderman> aldermen() {
		return temisClientProxy.aldermen();
	}

	@Override
	public List<Law> laws(Alderman aldermen, int page, int numberOfResults) {
		return temisClientProxy.laws(aldermen.getName(), page, numberOfResults);
	}

	@Override
	public List<Law> laws(int page, int numberOfResults) {
		return temisClientProxy.laws(page, numberOfResults);
	}

	@Path("api")
	public static interface TemisClientProxy {

		@Path("laws")
		@GET
		public List<Law> laws(@QueryParam("page") int p, @QueryParam("size") int s);

		@Path("alderman")
		@GET
		public List<Alderman> aldermen();

		@Path("laws/alderman")
		@GET
		public List<Law> laws(@PathParam("name") String name, @QueryParam("page") int p, @QueryParam("size") int s);

	}

}
