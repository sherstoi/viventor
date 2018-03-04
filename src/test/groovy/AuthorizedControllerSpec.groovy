import com.viventor.service.UserService
import groovyx.net.http.HttpResponseDecorator
import groovyx.net.http.RESTClient
import groovy.json.JsonSlurper
import com.viventor.Application

import javax.annotation.Resource

import static groovyx.net.http.ContentType.*
import spock.lang.Specification
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(
        classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
class AuthorizedControllerSpec extends Specification {

    RESTClient restClient = new RESTClient("http://localhost:8080/")

    @Resource
    UserService userService

    def "Check application user creation"() {

        when: "register new application user"
            def jsonAppUser = new JsonSlurper().parseText('{ "userName":' + '"' + Constants.TEST_USER + '", "password":' + '"' + Constants.TEST_PASS + '"}')
            def response = restClient.post(path: 'auth/createAppUser',
                                           body: jsonAppUser,
                                           requestContentType : JSON )

        then: "Status is 200"
            response.status == 200

        and: "Body response contains proper value"
            HttpResponseDecorator httpResponseDecorator = (HttpResponseDecorator) response
            assert httpResponseDecorator.getData()["userName"] == Constants.TEST_USER
            assert httpResponseDecorator.getData()["password"]
    }
}