/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.example.robert.myapplication.backend;

import com.example.JavaJoke;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import javax.inject.Named;


/**
 * An endpoint class we are exposing
 */
@Api(
        name = "myApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend.myapplication.robert.example.com",
                ownerName = "backend.myapplication.robert.example.com",
                packagePath = ""
        )
)
public class MyEndpoint {

    @ApiMethod(name = "tellJoke")
    public MyBean tellJoke() {
        // get a java joke.
        JavaJoke javaJoke = new JavaJoke();

        // get a bean
        MyBean response = new MyBean();

        response.setData("(Passed through Endpoint)" + javaJoke.tellJoke());

        return response;
    }



}
