import akka.actor.typed.ActorSystem;
import akka.actor.typed.javadsl.Behaviors;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.model.ContentTypes;
import akka.http.javadsl.model.HttpEntities;
import akka.http.javadsl.model.StatusCodes;
import akka.http.javadsl.server.PathMatchers;
import akka.http.javadsl.server.Route;
import org.geo.model.AvailableShapes;
import org.geo.model.Shape;
import org.geo.model.ThreeDimensionalShape;
import org.geo.model.TwoDimensionalShape;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.function.Supplier;

import static akka.http.javadsl.server.Directives.*;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        ActorSystem system = ActorSystem.create(Behaviors.empty(), "system");
        final Http http = Http.get(system);
        final CompletionStage<ServerBinding> server = http.newServerAt("localhost", 8081).bind(createRoute1());
        logger.info("Server online at http://localhost:8081/\nPress RETURN to stop...");
        System.in.read();
        server.thenCompose(ServerBinding::unbind).thenAccept(unbound -> system.terminate());
    }

    private static Route createRoute() {
        return concat(
                path("hello", () ->
                        get(() -> complete(HttpEntities.create(ContentTypes.TEXT_HTML_UTF8, "<h1>Say hello to akka-http</h1>"))
                        )),
                pathPrefix("user", () -> path("a", () -> get(() -> complete("user a")))),
                pathPrefix("num", () -> path(PathMatchers.integerSegment(), i -> complete(String.format("Hello number %d", i))))
        );
    }

    private static Route createRoute1() {
        return concat(
                parameterMap(params -> concat(
                        path("area", ccc(params, Main::handleArea)),
                        path("perimeter", ccc(params, Main::handlePerimeter)),
                        path("volume", ccc(params, Main::handleVolume)),
                        path("surfacearea", ccc(params, Main::handleSurfaceArea))
                ))
        );
    }

    private static Supplier<Route> ccc(Map<String, String> params, Function<Map<String, String>, CompletableFuture<Double>> fn) {
        logger.info("params: {}", params);
        return () -> {
            try {
                return fn.apply(params).thenApply(v -> complete(v.toString())).exceptionally(e -> { e.printStackTrace(); return complete(e.getMessage());}).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            return complete(StatusCodes.INTERNAL_SERVER_ERROR, "Internal server error");
        };
    }

    private static CompletableFuture<Double> handleSurfaceArea(Map<String, String> params) {
        return CompletableFuture.supplyAsync(() -> Optional.of(AvailableShapes.getShapeFactory(params.get("shape")).apply(params)).filter(Shape::isThreeDimensional).map(s -> ((ThreeDimensionalShape) s).getSurfaceArea()).orElseThrow(IllegalArgumentException::new).get());
    }

    private static CompletableFuture<Double> handleVolume(Map<String, String> params) {
        return CompletableFuture.supplyAsync(() -> Optional.of(AvailableShapes.getShapeFactory(params.get("shape")).apply(params)).filter(Shape::isThreeDimensional).map(s -> ((ThreeDimensionalShape) s).getVolume()).orElseThrow(IllegalArgumentException::new).get());
    }

    private static CompletableFuture<Double> handlePerimeter(Map<String, String> params) {
        return CompletableFuture.supplyAsync(() -> Optional.of(AvailableShapes.getShapeFactory(params.get("shape")).apply(params)).filter(Shape::isTwoDimensional).map(s -> ((TwoDimensionalShape) s).getPerimeter()).orElseThrow(IllegalArgumentException::new).get());
    }

    private static CompletableFuture<Double> handleArea(Map<String, String> params) {
        return CompletableFuture.supplyAsync(() -> Optional.of(AvailableShapes.getShapeFactory(params.get("shape")).apply(params)).filter(Shape::isTwoDimensional).map(s -> ((TwoDimensionalShape) s).getArea()).orElseThrow(IllegalArgumentException::new).get());
    }
}
