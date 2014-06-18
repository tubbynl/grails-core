/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.grails.web.servlet.mvc

import groovy.transform.CompileStatic
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.context.request.RequestAttributes
import org.springframework.web.context.request.ServletRequestAttributes
import org.springframework.web.servlet.DispatcherServlet

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Simple extension to the Spring {@link DispatcherServlet} implementation that makes sure a {@link GrailsWebRequest} is bound
 *
 * @author Graeme Rocher
 * @since 3.0
 */
@CompileStatic
class GrailsDispatcherServlet extends DispatcherServlet{

    GrailsDispatcherServlet() {
    }

    GrailsDispatcherServlet(WebApplicationContext webApplicationContext) {
        super(webApplicationContext)
    }

    @Override
    protected ServletRequestAttributes buildRequestAttributes(HttpServletRequest request, HttpServletResponse response, RequestAttributes previousAttributes) {
        if (previousAttributes == null || !(previousAttributes instanceof GrailsWebRequest)) {
            def webRequest = new GrailsWebRequest(request, response, request.getServletContext())
            webRequest.informParameterCreationListeners();
            return webRequest;
        }
        else {
            return (GrailsWebRequest) previousAttributes;
        }
    }
}
