/**
 * 
 */
package ch.zhaw.mdp.lhb.citr.gcm;

import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;

import ch.zhaw.mdp.lhb.citr.Logging.LoggingFactory;
import ch.zhaw.mdp.lhb.citr.Logging.LoggingStrategy;

/**
 * @author Daniel Brun
 * 
 *         Helper class for Google Cloud Messaging requests.
 */
public class GcmRequestHelper {

    private static final String GCM_URL = "https://android.googleapis.com/gcm/send";
    private static final String API_KEY = "AIzaSyCGeuPmENKcz9pmQPqiweyoL4xMrbCweBk";

    private static final LoggingStrategy LOG = LoggingFactory.get();

    /**
     * Sends a http request with the given recipients to the GCM-Server.
     * 
     * @param someRecipients The recipents
     * @throws IOException thrown if an io error occurs.
     */
    public static void sendHTTPRequest(List<String> someRecipients)
	    throws IOException {

	// Create POst-Request
	HttpClient httpClient = new DefaultHttpClient();
	HttpPost httpPost = new HttpPost(GCM_URL);

	// Set Header
	httpPost.addHeader("Authorization", "key= " + API_KEY);
	httpPost.addHeader("Content-Type", "application/json");

	// Create json body
	StringWriter jsonWriter = new StringWriter();
	JsonFactory factory = new JsonFactory();
	JsonGenerator generator = factory.createJsonGenerator(jsonWriter);

	generator.writeStartObject();
	generator.writeFieldName("registration_ids");

	generator.writeStartArray();

	for (String recp : someRecipients) {
	    generator.writeString(recp);
	}

	generator.writeEndArray();
	generator.writeEndObject();

	generator.close();

	LOG.info("GCM-Request: " + jsonWriter.toString());

	// Set Request parameters and other properties.
	try {
	    httpPost.setEntity(new StringEntity(jsonWriter.toString()));
	} catch (UnsupportedEncodingException e) {
	    LOG.error(e.getMessage());
	}

	// Fire request
	try {
	    HttpResponse response = httpClient.execute(httpPost);
	    HttpEntity respEntity = response.getEntity();

	    if (respEntity != null) {
		// EntityUtils to get the reponse content
		String respString = EntityUtils.toString(respEntity);
		LOG.info("GCM-Response: " + respString);
	    }
	} catch (ClientProtocolException e) {
	    LOG.error(e.getMessage());
	} catch (IOException e) {
	    LOG.error(e.getMessage());
	}
    }
}
