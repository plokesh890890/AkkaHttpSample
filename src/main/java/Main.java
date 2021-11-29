import akka.actor.typed.ActorSystem;
import akka.actor.typed.javadsl.Behaviors;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.model.ContentTypes;
import akka.http.javadsl.model.HttpEntities;
import akka.http.javadsl.server.PathMatchers;
import akka.http.javadsl.server.Route;
import org.geo.model.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

import static akka.http.javadsl.server.Directives.*;

public class Main {
    public static void main(String[] args) throws IOException {
        ActorSystem system = ActorSystem.create(Behaviors.empty(), "system");
        final Http http = Http.get(system);
        final CompletionStage<ServerBinding> server = http.newServerAt("localhost", 8081).bind(createRoute1());
        System.out.println("Server online at http://localhost:8081/\nPress RETURN to stop...");
        System.in.read();
        server.thenCompose(ServerBinding::unbind).thenAccept(unbound -> system.terminate());
    }

    private static Route createRoute() {
        return concat(
                path("hello", () ->
                        get(() -> complete(HttpEntities.create(ContentTypes.TEXT_HTML_UTF8, "<h1>Say hello to akka-http</h1>"))
                        )),
                pathPrefix("user", () -> path("a", () -> get(() -> complete("user a")))),
                pathPrefix( "num", () -> path( PathMatchers.integerSegment(), i -> complete(String.format("Hello number %d", i))))
        );
    }

    private static Route createRoute1() {
        return concat(
                parameterMap(params -> concat(
                        path("area", () -> complete(handleArea(params).toString())),
                        path("perimeter", () -> complete(handlePerimeter(params).toString())),
                        path("volume", () -> complete(handleVolume(params).toString())),
                        path("surfacearea", () -> complete(handleSurfaceArea(params).toString()))
                ))
        );
    }

    private static Object handleSurfaceArea(Map<String, String> params) {
        Shape shape = AvailableShapes.getShapeFactory(params.get("shape")).apply(params);
        return Optional.of(shape).filter(Shape::isThreeDimensional).map(s -> ((ThreeDimensionalShape)s).getSurfaceArea()).orElseThrow(IllegalArgumentException::new).get();
    }

    private static Object handleVolume(Map<String, String> params) {
        Shape shape = AvailableShapes.getShapeFactory(params.get("shape")).apply(params);
        return Optional.of(shape).filter(Shape::isThreeDimensional).map(s -> ((ThreeDimensionalShape)s).getVolume()).orElseThrow(IllegalArgumentException::new).get();
    }

    private static Object handlePerimeter(Map<String, String> params) {
        Shape shape = AvailableShapes.getShapeFactory(params.get("shape")).apply(params);
        return Optional.of(shape).filter(Shape::isTwoDimensional).map(s -> ((TwoDimensionalShape)s).getArea()).orElseThrow(IllegalArgumentException::new).get();
    }

    private static Double handleArea(Map<String, String> params) {
        Shape shape = AvailableShapes.getShapeFactory(params.get("shape")).apply(params);
        return Optional.of(shape).filter(Shape::isTwoDimensional).map(s -> ((TwoDimensionalShape)s).getPerimeter()).orElseThrow(IllegalArgumentException::new).get();
    }
}
