import com.typesafe.config.{Config, ConfigFactory}
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import com.warrenstrange.googleauth.GoogleAuthenticator

import uk.gov.hmcts.refdata.util._

class MainSimulation extends Simulation {

  val config: Config = ConfigFactory.load()

  private val authenticator: GoogleAuthenticator = new GoogleAuthenticator()

  private val otpFeeder = Iterator.continually(Map("otp" -> authenticator.getTotpPassword(config.getString("service.pass"))))


  val httpProtocol = http
    .baseUrl(config.getString("baseUrl"))
    .proxy(Proxy("proxyout.reform.hmcts.net", 8080))


  val IdAMToken = refDataTokenGenerator.generateSIDAMUserTokenInternal()


  val  createOrgString = "{\n   \"name\": \"Praveen Thottempudi\",\n   \"sraId\": \"TPA1111111\",\n   \"sraRegulated\": true,\n   \"companyNumber\": \"TPA11111\",\n" +
    "\"companyUrl\": \"www.trA1111111.com\",\n   \"superUser\": {\n       \"firstName\": \"Praveen\",\n       \"lastName\": \"Thottempudi\",\n" +
    "\"email\": \"tpA1111111@email.co.uk\"\n   },\n   \"paymentAccount\": [\n\n          \"PBA1111111\",\"PBA1111112\"\n\n   ],\n" +
    "\"contactInformation\": [\n       {\n           \"addressLine1\": \"1-2-3 high road\",\n           \"addressLine2\": \"Praveen Complex\",\n           \"addressLine3\": \"Maharaj road\",\n" +
    "\"townCity\": \"West Kirby\",\n           \"county\": \"Wirral\",\n           \"country\": \"UK\",\n           \"postCode\": \"TEST1\",\n           \"dxAddress\": [\n" +
    "{\n                   \"dxNumber\": \"DX 1111111990\",\n                   \"dxExchange\": \"111111192099908492\"\n               }\n           ]\n       }\n   ]\n}"

  val scn = scenario("PRD_S2SToken")
    .feed(otpFeeder)
      .exec(http ("Lease service token")
        .post(config.getString("s2sUrl") + "/lease")
        .body(StringBody("""{"microservice":"""" + config.getString("service.name") + """","oneTimePassword":"${otp}"}"""))
        .asJson
        .check(bodyString.saveAs("service_token")))

    .exec(http("Reference Data - Create Organization")
      .post("/refdata/internal/v1/organisations")
      .header("ServiceAuthorization", "${service_token}")
     .body(StringBody(createOrgString))
      .header("Content-Type", "application/json")
      .check(status is 201))


  setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}


