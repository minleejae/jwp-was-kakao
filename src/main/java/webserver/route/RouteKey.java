package webserver.route;

import http.HttpMethod;

import java.util.Objects;

public class RouteKey {

    private final HttpMethod httpMethod;
    private final String endPoint;

    public RouteKey(HttpMethod httpMethod, String endPoint) {
        this.httpMethod = httpMethod;
        this.endPoint = endPoint;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RouteKey routeKey = (RouteKey) o;
        return httpMethod == routeKey.httpMethod &&
                Objects.equals(endPoint, routeKey.endPoint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(httpMethod, endPoint);
    }
}
