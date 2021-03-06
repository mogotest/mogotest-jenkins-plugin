/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * wKIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

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
    final HttpClient client = new HttpClient();
    final String url = String.format("https://mogotest.com/api/v1/test_plans/%s/tests.json", testPlanId);
    final PostMethod post = new PostMethod(url);

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
