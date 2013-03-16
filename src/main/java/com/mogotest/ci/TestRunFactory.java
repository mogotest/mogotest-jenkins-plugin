package com.mogotest.ci;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestRunFactory
{
  private static final Logger logger = Logger.getLogger(TestRunFactory.class.getName());

  public static void create(final String apiKey, final String testPlanId, final String source)
  {
    HttpClient client = new HttpClient();
    // 'https://mogotest.com/api/v1/test_plans/pg329o/tests.json' -d 'user_credentials=CREDENTIALS'

    String url = String.format("https://mogotest.com/api/v1/test_plans/%s/tests.json", testPlanId);
    PostMethod post = new PostMethod(url);

    try
    {
      post.addParameter("user_credentials", apiKey);
      post.addParameter("source", source);
      client.executeMethod(post);
    }
    catch (final Exception e)
    {
      logger.log(Level.SEVERE, "Error creating Mogotest test run.", e);
    }
    finally
    {
      post.releaseConnection();
    }
  }
}
