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

// [START gae_java11_task_handler]
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaskHandlerController {

  @RequestMapping(
      value = "/tasks/create",
      method = RequestMethod.POST,
      consumes = "application/octet-stream")
  @ResponseStatus(HttpStatus.OK)
  public String taskHandler(@RequestBody String body) {
    String output;
    output = String.format("Received task with payload %s", body);
    System.out.println(output);

    return output;
  }


  @RequestMapping(
          value = "/trigger",
          method = RequestMethod.GET,
          consumes = "application/octet-stream")
  @ResponseStatus(HttpStatus.OK)
  public String taskTrigger() {
    String output;
    output = String.format("Received task with payload");
    System.out.println(output);

    CreateTask tradeTaskScheduler = new CreateTask();
    try {
      tradeTaskScheduler.scheduleTask();
    } catch (Exception e ) {
      System.out.println("errrrrrrrrrrrrr");
      e.printStackTrace();
    }
    System.out.println("point here");
    return output;
  }
}
// [END gae_java11_task_handler]
