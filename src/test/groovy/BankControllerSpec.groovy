import groovyx.net.http.HttpResponseDecorator
import groovyx.net.http.HttpResponseException
import groovyx.net.http.RESTClient
import groovy.json.JsonSlurper
import com.viventor.Application
import static groovyx.net.http.ContentType.*
import spock.lang.Specification
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(
        classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
class BankControllerSpec extends Specification {

    RESTClient restClient = new RESTClient("http://localhost:8080/")

    def setup() {
        restClient.auth.basic(Constants.TEST_USER, Constants.TEST_PASS)
    }

    def "Check client of bank creation"() {
        when: "register new client"
            def jsonAppUser = new JsonSlurper().parseText('{ "clientName": "Dave"}')
            def response = restClient.post(path: 'bank/createClient',
                    body: jsonAppUser,
                    requestContentType : JSON )

        then: "Status is 200"
            response.status == 200

        and: "Body response contains proper value"
            HttpResponseDecorator httpResponseDecorator = (HttpResponseDecorator) response
            assert httpResponseDecorator.getData()["clientName"] == 'Dave'
            assert httpResponseDecorator.getData()["clientId"]
    }

    def "Check account creation"() {
        when: "register new account"
            def jsonAppUser = new JsonSlurper().parseText('{ "accountNum": "12345", "balance": "10000"}')
            def response = restClient.post(path: 'bank/createAccount',
                    body: jsonAppUser,
                    requestContentType : JSON )

        then: "Status is 200"
            response.status == 200

        and: "Body response contains proper value"
            HttpResponseDecorator httpResponseDecorator = (HttpResponseDecorator) response
            assert httpResponseDecorator.getData()["balance"] == 10000
            assert httpResponseDecorator.getData()["accountNum"] == '12345'
            assert httpResponseDecorator.getData()["accountId"]
    }

    def "Update account deposit"() {
        when: "add money to account"
            def jsonAppUser = new JsonSlurper().parseText('{"accountNum":"12345", "balance": "5"}')
            def response = restClient.post(path: 'bank/deposit',
                    body: jsonAppUser,
                    requestContentType : JSON )

        then: "Status is 200"
            response.status == 200

        and: "Body response contains proper value"
            HttpResponseDecorator httpResponseDecorator = (HttpResponseDecorator) response
            assert httpResponseDecorator.getData()["balance"] == 10005
    }

    def "Check that user with wrong login/password can't make any actions"() {
        when: "user without login/password try to call rest service"
            restClient.auth.basic("anonymous", "")
            def jsonAppUser = new JsonSlurper().parseText('{"accountNum":"12345", "balance": "5"}')
            def response = restClient.post(path: 'bank/deposit',
                    body: jsonAppUser,
                    requestContentType : JSON )

        then: "Status is 401"
            thrown(HttpResponseException)
    }
}