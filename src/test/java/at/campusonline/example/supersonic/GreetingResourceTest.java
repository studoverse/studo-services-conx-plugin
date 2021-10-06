package at.campusonline.example.supersonic;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.number.OrderingComparison.greaterThanOrEqualTo;

/**
 * TODO: Enable test again, when @TestSecurity works
 */
@QuarkusTest @Disabled
public class GreetingResourceTest {

  private final static String BASE_PATH = Api.PATH + GreetingRestService.PATH;

  @Test
  @TestSecurity(authorizationEnabled = false)
  public void testGreetingsContainsAtLeastThreeItems() {

    given().basePath(BASE_PATH)
            .when().get()
            .then()
            .statusCode(Response.Status.OK.getStatusCode())
            .body("items.size()", greaterThanOrEqualTo(3));
  }

  @Test
  @TestSecurity(authorizationEnabled = false)
  public void testGreeting1IsHelloWorld() {

    given().basePath(BASE_PATH)
            .when().get("1")
            .then()
            .statusCode(Response.Status.OK.getStatusCode())
            .body("text", equalTo("Hello World!"));
  }

  @Test
  @TestSecurity(authorizationEnabled = false)
  public void testGreetingIsNotFound() {

    given().basePath(BASE_PATH)
            .when().get("-1")
            .then()
            .statusCode(Response.Status.NOT_FOUND.getStatusCode());
  }

}
