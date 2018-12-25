package io.bitjob.util.proxy;


import org.apache.http.client.HttpClient;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**

 * Date: 15-1-28
 * Time: 下午1:39
 */
public class RestResourceClientProxyFactoryBean<T> implements FactoryBean,
        InitializingBean {
    Map<Method, HttpClientInvoker> methodMap = new HashMap<Method, HttpClientInvoker>();
    private Class<T> serviceInterface;
    private URI baseUri;
    private HttpClient httpClient;
    private T client;
    private Map httpHeaderMap;

    @Override
    public Object getObject() throws Exception {
        return client;
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        analyzeClassAnnotion();
        Class<?>[] intfs = {serviceInterface};
        client = (T) Proxy.newProxyInstance(serviceInterface.getClassLoader(), intfs,
                new InvocationHandlerImpl(serviceInterface, methodMap));
    }

    private void analyzeClassAnnotion() throws Exception {
        Annotation[] classAnnotations = serviceInterface.getAnnotations();
        if (!serviceInterface.isAnnotationPresent(Path.class)) {
            throw new Exception("资源类不符合格式，必须带有注解是Path的");
        }
        String classPath = "";
        for (Annotation annotation : classAnnotations) {
            if (annotation instanceof Path) {
                Path path = serviceInterface.getAnnotation(Path.class);
                classPath = path.value();
            }
        }
        Method[] methods = serviceInterface.getMethods();
        for (Method method : methods) {
                    /*  Annotation[] annotations =  method.getAnnotations();
                  for(Annotation annotation:annotations){
                        if(annotation instanceof RequestMapping){
                            RequestMapping mapping = method.getAnnotation(RequestMapping.class);
                            String path = mapping.path();
                            break;
                        }
                    }*/

            RequestMethod requestMethod = RequestMethod.POST;
            if (method.isAnnotationPresent(POST.class)) {
                requestMethod = RequestMethod.POST;
            }
            if (method.isAnnotationPresent(GET.class)) {
                requestMethod = RequestMethod.GET;
            }
            if (method.isAnnotationPresent(PUT.class)) {
                requestMethod = RequestMethod.PUT;
            }
            if (method.isAnnotationPresent(Path.class)) {
                Path path = method.getAnnotation(Path.class);
                String subPath = path.value();

                HttpClientInvoker httpClientInvoker = new HttpClientInvoker();
                httpClientInvoker.setBaseURL(baseUri.toString());
                String requestUrl = classPath + "/" + subPath;
                requestUrl = requestUrl.replaceAll("//", "/");
                httpClientInvoker.setRequetURL(requestUrl);
                httpClientInvoker.setReturnType(method.getGenericReturnType());
                Type[] genericParameterTypes = method.getGenericParameterTypes();
                if (genericParameterTypes.length > 0) {
                    httpClientInvoker.setParameterType(method.getGenericParameterTypes()[0]);//目前支持一个参数
                }
                httpClientInvoker.setHttpHeaderMap(httpHeaderMap);
                httpClientInvoker.setHttpMethod(requestMethod.name());
                httpClientInvoker.setHttpClient(httpClient);
                methodMap.put(method, httpClientInvoker);
            }
        }
    }

    public Class<T> getServiceInterface() {
        return serviceInterface;
    }

    public void setServiceInterface(Class<T> serviceInterface) {
        this.serviceInterface = serviceInterface;
    }

    public URI getBaseUri() {
        return baseUri;
    }

    public void setBaseUri(URI baseUri) {
        this.baseUri = baseUri;
    }

    public HttpClient getHttpClient() {
        return httpClient;
    }

    public void setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public Map getHttpHeaderMap() {
        return httpHeaderMap;
    }

    public void setHttpHeaderMap(Map httpHeaderMap) {
        this.httpHeaderMap = httpHeaderMap;
    }
}

