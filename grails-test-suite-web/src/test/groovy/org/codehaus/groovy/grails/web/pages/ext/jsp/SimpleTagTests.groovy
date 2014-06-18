package org.codehaus.groovy.grails.web.pages.ext.jsp

import grails.util.GrailsWebUtil

import javax.servlet.jsp.JspException
import javax.servlet.jsp.JspWriter
import javax.servlet.jsp.tagext.SimpleTagSupport

import org.codehaus.groovy.grails.commons.DefaultGrailsApplication
import org.codehaus.groovy.grails.web.pages.GroovyPagesServlet
import org.grails.web.servlet.mvc.GrailsWebRequest
import org.springframework.mock.web.MockServletContext
import org.springframework.web.context.request.RequestContextHolder

/**
 * @author Graeme Rocher
 * @since 1.0
 */
class SimpleTagTests extends GroovyTestCase {

    GrailsWebRequest webRequest

    protected void setUp() {
        webRequest = GrailsWebUtil.bindMockWebRequest()
        webRequest.getCurrentRequest().setAttribute(GroovyPagesServlet.SERVLET_INSTANCE, new GroovyPagesServlet())
    }

    protected void tearDown() {
        RequestContextHolder.setRequestAttributes null
    }

    void testSimpleTagWithBodyUsage() {
        def resolver = new TagLibraryResolverImpl()
        resolver.servletContext = new MockServletContext()
        resolver.grailsApplication = new DefaultGrailsApplication()

        JspTag jspTag = new JspTagImpl(BodySimpleTagSupport)
        def sw = new StringWriter()
        jspTag.doTag(sw, [:]) {
            "testbody"
        }

        assertEquals "bodySimpleTagSupport:testbody", sw.toString().trim()
    }

    void testSimpleTagUsage() {

        def resolver = new TagLibraryResolverImpl()
        resolver.servletContext = new MockServletContext()
        resolver.grailsApplication = new DefaultGrailsApplication()

        JspTag jspTag = new JspTagImpl(ExtendsSimpleTagSupport)
        def sw = new StringWriter()
        jspTag.doTag(sw, [:])

        assertEquals "extendsSimpleTagSupport:output", sw.toString().trim()
    }
}

class ExtendsSimpleTagSupport extends SimpleTagSupport {
    @Override
    void doTag() throws JspException, IOException {
        getJspContext().getOut().println("extendsSimpleTagSupport:output");
    }
}

class BodySimpleTagSupport extends SimpleTagSupport {
    @Override
    void doTag() throws JspException, IOException {
        JspWriter out = getJspContext().getOut()
        out.print("bodySimpleTagSupport:");
        super.getJspBody().invoke(out)
    }
}
