package it.redhat.demo.test;

import it.redhat.demo.model.Pojo;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.PomEquippedResolveStage;
import org.junit.*;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import java.io.File;
import java.net.URI;

@RunWith(Arquillian.class)
public class TestGrafted {

    private static final Logger logger = LoggerFactory.getLogger(TestGrafted.class);


    @Rule
    public TestWatcher watcher = new TestWatcher() {
        @Override
        protected void starting(Description description) {
            logger.debug("{} --> START", description.getMethodName());
        }

        @Override
        protected void finished(Description description) {
            logger.debug("{} --> END", description.getMethodName());
        }

        @Override
        protected void failed(Throwable e, Description description) {
            logger.error("Errore nel metodo " + description.getMethodName(), e);
        }

    };

    @Test
    public void testPostAndGet() {
        Pojo expected = new Pojo("aaa", 1L);

        ResteasyClient resteasyClient = new ResteasyClientBuilder().build();
        ReSTCacheInterface restThroughEmbeddedServer = resteasyClient
                .target("http://localhost:8080/jdg-mixed-server/")
                .proxy(ReSTCacheInterface.class);

        Response responseCreate = restThroughEmbeddedServer.putCacheValue("MyCoolCache", "key", expected);
        Assert.assertNotNull(responseCreate);
        Assert.assertEquals(201, responseCreate.getStatus());

        URI location = responseCreate.getLocation();
        responseCreate.close();

        ResteasyWebTarget webTarget = resteasyClient.target(location);
        Pojo response = webTarget.request().get(Pojo.class);

        Assert.assertNotNull(response);
        Assert.assertEquals(expected, response);
    }

    @Test
    public void testPostAndGetFromHotRod() {
        Pojo expected = new Pojo("aaa", 1L);

        ResteasyClient resteasyClient = new ResteasyClientBuilder().build();
        ReSTCacheInterface restThroughEmbeddedServer = resteasyClient
                .target("http://localhost:8080/jdg-mixed-server/")
                .proxy(ReSTCacheInterface.class);
        ReSTCacheInterface restThroughHotRod = resteasyClient
                .target("http://localhost:8080/jdg-mixed-client/")
                .proxy(ReSTCacheInterface.class);

        Response responseCreate = restThroughEmbeddedServer.putCacheValue("MyCoolCache", "key", expected);
        Assert.assertNotNull(responseCreate);
        Assert.assertEquals(201, responseCreate.getStatus());
        responseCreate.close();

        Pojo response = restThroughHotRod.getCacheKey("MyCoolCache", "key");

        Assert.assertNotNull(response);
        Assert.assertEquals(expected, response);
    }

    @Before
    public void setUp() throws Exception {
        logger.debug("setUp() --> client Created!");
    }

    @After
    public void tearDown() throws Exception {
        logger.debug("tearDown() --> close!");
    }

    @Deployment(testable = false, order = 0, name = "jdg-mixed-server")
    public static WebArchive createServerDeployment() {
        return getWebArchive("it.redhat.demo:jdg-mixed-server:war:1.0.0-SNAPSHOT");
    }

    @Deployment(testable = false, order = 1, name = "jdg-mixed-client")
    public static WebArchive createClientDeployment() {
        return getWebArchive("it.redhat.demo:jdg-mixed-client:war:1.0.0-SNAPSHOT");
    }

    private static WebArchive getWebArchive(String canonicalForm) {
        logger.info("Create web archive deployment");
        PomEquippedResolveStage resolveStage = Maven.resolver().loadPomFromFile("./pom.xml");
        File file = resolveStage
                .resolve(canonicalForm)
                .withoutTransitivity().asSingleFile();
        logger.info(String.format("Deploy resource [%s]", file.getName()));
        return ShrinkWrap.createFromZipFile(WebArchive.class, file);
    }

}
