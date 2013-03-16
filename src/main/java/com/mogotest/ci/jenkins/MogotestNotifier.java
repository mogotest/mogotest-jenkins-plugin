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

package com.mogotest.ci.jenkins;

import com.mogotest.ci.TestRunFactory;
import hudson.Launcher;
import hudson.Extension;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.model.BuildListener;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.BuildStepMonitor;
import hudson.tasks.Notifier;
import hudson.tasks.Publisher;
import hudson.util.Secret;
import org.kohsuke.stapler.DataBoundConstructor;

import java.io.IOException;

public class MogotestNotifier extends Notifier
{
  private final Secret apiKey;
  private final String testPlanId;

  @DataBoundConstructor
  public MogotestNotifier(final String apiKey, final String testPlanId)
  {
    this.apiKey = Secret.fromString(apiKey);
    this.testPlanId = testPlanId;
  }

  public String getApiKey()
  {
    return "".equals(Secret.toString(apiKey)) ? "" : apiKey.getEncryptedValue();
  }

  public String getTestPlanId()
  {
    return testPlanId;
  }

  public BuildStepMonitor getRequiredMonitorService()
  {
    return BuildStepMonitor.NONE;
  }

  @Override
  public boolean perform(AbstractBuild build, Launcher launcher, BuildListener listener)
  {
    TestRunFactory.create(Secret.toString(apiKey), getTestPlanId(), "jenkins");

    return true;
  }

  /**
   * Allows adding the Mogotest Notifier as a post-build action in the job configuration.
   */
  @Extension
  public static class DescriptorImpl extends BuildStepDescriptor<Publisher>
  {
    @Override
    public boolean isApplicable(Class<? extends AbstractProject> jobType)
    {
      return true;
    }

    @Override
    public String getDisplayName()
    {
      return "Start Mogotest Test Run";
    }
  }
}
