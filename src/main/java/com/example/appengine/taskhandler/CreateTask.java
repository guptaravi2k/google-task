/*
 * Copyright 2019 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.appengine.taskhandler;

// [START gae_java11_create_task]

import com.google.cloud.tasks.v2.*;
import com.google.protobuf.ByteString;
import com.google.protobuf.Timestamp;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.time.Clock;
import java.time.Instant;

@Component
public class CreateTask {

  public void scheduleTask() throws Exception {
    // Instantiates a client.
    try (CloudTasksClient client = CloudTasksClient.create()) {
      // Variables provided by system variables.
      String projectId = System.getenv("GOOGLE_CLOUD_PROJECT");
      String queueName = "trade-q";//System.getenv("QUEUE_ID");
      String location = "australia-southeast1";//System.getenv("LOCATION_ID");
      // Optional variables.
      String payload = "hello";
      int seconds = 0; // Scheduled delay for the task in seconds

      // Construct the fully qualified queue name.
      System.out.println("project id = " + projectId);
      System.out.println("location = " + location);
      System.out.println("queueName = " + queueName);
      String queuePath = QueueName.of(projectId, location, queueName).toString();

      // Construct the task body.
      Task.Builder taskBuilder =
          Task.newBuilder()
              .setAppEngineHttpRequest(
                  AppEngineHttpRequest.newBuilder()
                      .setBody(ByteString.copyFrom(payload, Charset.defaultCharset()))
                      .setRelativeUri("/tasks/create")
                      .setHttpMethod(HttpMethod.POST)
                      .build());

      // Add the scheduled time to the request.
      taskBuilder.setScheduleTime(
          Timestamp.newBuilder()
              .setSeconds(Instant.now(Clock.systemUTC()).plusSeconds(seconds).getEpochSecond()));

      // Send create task request.
      Task task = client.createTask(queuePath, taskBuilder.build());
      System.out.println("Task created: " + task.getName());
    }
  }
}
// [END gae_java11_create_task]
