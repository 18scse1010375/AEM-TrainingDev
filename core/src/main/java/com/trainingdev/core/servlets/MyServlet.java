package com.trainingdev.core.servlets;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.trainingdev.core.beans.ArticleListDataBean;
import com.trainingdev.core.models.ArticleListModel;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;

@Component(service = { Servlet.class})
@SlingServletResourceTypes(
        resourceTypes="sling/servlet/default",
        selectors="getArticleList",
        extensions="json",
        methods= HttpConstants.METHOD_POST)

public class MyServlet extends SlingAllMethodsServlet {

    public static final Logger LOGGER = LoggerFactory.getLogger(MyServlet.class);
    private static final String RESOURCE_PATH = "/content/trainingdev/language-masters/en/jcr:content/root/container/container/articlelist";

    @Override
    protected void doPost(final SlingHttpServletRequest request,
                          final SlingHttpServletResponse response) throws ServletException, IOException {

        LOGGER.debug("Servlet Code Started!!");
        ResourceResolver resourceResolver = request.getResourceResolver();
        Resource resource=resourceResolver.getResource(RESOURCE_PATH);

        ArticleListModel articleListModel=resource.adaptTo(ArticleListModel.class);

        List<ArticleListDataBean>articleListDataBeanList= articleListModel.getArticleListDataBeanList();

        LOGGER.debug("List of all the Child Components are==>{}" ,  articleListDataBeanList);

        ObjectWriter ow=new ObjectMapper().writer().withDefaultPrettyPrinter();

        String json=ow.writeValueAsString(articleListDataBeanList);

        response.setContentType("application/json");

        response.getWriter().write(json);

    }
}