package uk.gov.hmcts.refdata.simulation

import com.typesafe.config.{Config, ConfigFactory}
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._
import uk.gov.hmcts.refdata.scenarios.internal._
import uk.gov.hmcts.refdata.scenarios.external._

class RefDataPT extends Simulation{

  val config: Config = ConfigFactory.load()

  val httpProtocol = http
    .baseUrl(config.getString("baseUrl"))
    .proxy(Proxy("proxyout.reform.hmcts.net", 8080))

  val scn = scenario("Professional Reference Data")
      .exec(
        Organisation.createOrganisation,
      // ExtOrganisation.createOrganisation,
        Organisation.updateOrganisation,
        Organisation.GETAllOrganisation,
        Organisation.GETOrganisationByID,
        Organisation.GETOrganisationsByStatusACTIVE,
        Organisation.GETOrganisationsByStatusPENDING,
        Organisation.GETPbas,
        Organisation.AddInternalUserToOrg,
        Organisation.GETInternalUserForGivenOrganisations,
        Organisation.GETInternalUserForActiveOrganisationByEmail,
        Organisation.SearchPbas,
        ExtOrganisation.GETOrganisationByID,
        ExtOrganisation.GETPbas,
        ExtOrganisation.AddInternalUserToOrg,
        ExtOrganisation.GETInternalUserForGivenOrganisations,
        ExtOrganisation.GETInternalUserForActiveOrganisationByEmail,
        ExtOrganisation.SearchPbas

    )

  setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}
