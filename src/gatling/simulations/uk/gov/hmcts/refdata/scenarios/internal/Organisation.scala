package uk.gov.hmcts.refdata.scenarios.internal

import com.typesafe.config.{Config, ConfigFactory}
import com.warrenstrange.googleauth.GoogleAuthenticator
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import uk.gov.hmcts.refdata.util.refDataTokenGenerator
import scala.util.Random
import scala.concurrent.duration._

object Organisation {

  val config: Config = ConfigFactory.load()

  private val authenticator: GoogleAuthenticator = new GoogleAuthenticator()

  val s2sToken = refDataTokenGenerator.generateS2SToken()

  val IdAMToken = refDataTokenGenerator.generateSIDAMUserTokenInternal()

  private val rng: Random = new Random()
  private def sRAId(): String = rng.alphanumeric.take(15).mkString
  private def companyNumber(): String = rng.alphanumeric.take(8).mkString
  private def companyURL(): String = rng.alphanumeric.take(15).mkString
  private def firstName(): String = rng.alphanumeric.take(20).mkString
  private def lastName(): String = rng.alphanumeric.take(20).mkString
  private def companyEmail(): String = rng.alphanumeric.take(15).mkString
  private def paymentAccount1(): String = rng.alphanumeric.take(7).mkString
  private def paymentAccount2(): String = rng.alphanumeric.take(7).mkString
  private def addressLine1(): Int = rng.nextInt(999)

  private def internalUser_firstName(): String = rng.alphanumeric.take(20).mkString
  private def internalUser_lastName(): String = rng.alphanumeric.take(20).mkString
  private def internalUser_Email(): String = rng.alphanumeric.take(15).mkString


  val OrgIdData = csv("BAR_OrgIDs.csv").circular

  val createOrgString = "{\n   \"name\": \"Kapil${FirstName} Jain${LastName}\",\n   \"sraId\": \"TRA${SRAId}\",\n   \"sraRegulated\": true,\n   \"companyNumber\": \"${CompanyNumber}\",\n" +
    "\"companyUrl\": \"www.tr${CompanyURL}.com\",\n   \"superUser\": {\n       \"firstName\": \"${FirstName}\",\n       \"lastName\": \"${LastName}\",\n" +
    "\"email\": \"tpA${CompanyEmail}@email.co.uk\"\n,\n        \"jurisdictions\": [\n    {\n      \"id\": \"Divorce\"\n    },\n    {\n      \"id\": \"SSCS\"\n    },\n    {\n      \"id\": \"Probate\"\n    },\n    {\n      \"id\": \"Public Law\"\n    },\n    {\n      \"id\": \"Bulk Scanning\"\n    },\n    {\n      \"id\": \"Immigration & Asylum\"\n    },\n    {\n      \"id\": \"Civil Money Claims\"\n    },\n    {\n      \"id\": \"Employment\"\n    },\n    {\n      \"id\": \"Family public law and adoption\"\n    },\n    {\n      \"id\": \"Civil enforcement and possession\"\n    }\n  ]   },\n   \"paymentAccount\": [\n\n          \"PBA${PaymentAccount1}\",\"PBA${PaymentAccount2}\"\n\n   ],\n" +
    "\"contactInformation\": [\n       {\n           \"addressLine1\": \"${AddressLine1} high road\",\n           \"addressLine2\": \"${FirstName} ${LastName}\",\n           \"addressLine3\": \"Maharaj road\",\n" +
    "\"townCity\": \"West Kirby\",\n           \"county\": \"Wirral\",\n           \"country\": \"UK\",\n           \"postCode\": \"TEST1\",\n           \"dxAddress\": [\n" +
    "{\n                   \"dxNumber\": \"DX 1121111990\",\n                   \"dxExchange\": \"112111192099908492\"\n               }\n           ]\n       }\n   ]\n}"


  val updateOrgString = "{\n   \"name\": \"Kapil${FirstName} Jain${LastName}\",\n    \"status\": \"ACTIVE\",\n    \"sraId\": \"TRA${SRAId}\",\n   \"sraRegulated\": true,\n   \"companyNumber\": \"${CompanyNumber}\",\n" +
    "\"companyUrl\": \"www.tr${CompanyURL}.com\",\n   \"superUser\": {\n       \"firstName\": \"${FirstName}\",\n       \"lastName\": \"${LastName}\",\n" +
    "\"email\": \"tpA${CompanyEmail}@email.co.uk\"\n,\n        \"jurisdictions\": [\n    {\n      \"id\": \"Divorce\"\n    },\n    {\n      \"id\": \"SSCS\"\n    },\n    {\n      \"id\": \"Probate\"\n    },\n    {\n      \"id\": \"Public Law\"\n    },\n    {\n      \"id\": \"Bulk Scanning\"\n    },\n    {\n      \"id\": \"Immigration & Asylum\"\n    },\n    {\n      \"id\": \"Civil Money Claims\"\n    },\n    {\n      \"id\": \"Employment\"\n    },\n    {\n      \"id\": \"Family public law and adoption\"\n    },\n    {\n      \"id\": \"Civil enforcement and possession\"\n    }\n  ]   },\n   \"paymentAccount\": [\n\n          \"PBA${PaymentAccount1}\",\"PBA${PaymentAccount2}\"\n\n   ],\n" +
    "\"contactInformation\": [\n       {\n           \"addressLine1\": \"${AddressLine1} high road\",\n           \"addressLine2\": \"${FirstName} ${LastName}\",\n           \"addressLine3\": \"Maharaj road\",\n" +
    "\"townCity\": \"West Kirby\",\n           \"county\": \"Wirral\",\n           \"country\": \"UK\",\n           \"postCode\": \"TEST1\",\n           \"dxAddress\": [\n" +
    "{\n                   \"dxNumber\": \"DX 1121111990\",\n                   \"dxExchange\": \"112111192099908492\"\n               }\n           ]\n       }\n   ]\n}"


  val addInternalUserString = "{\n \"firstName\": \"Kapil ${InternalUser_FirstName}\",\n \"lastName\": \"Jain ${InternalUser_LastName}\",\n \"email\": \"Kapil.${InternalUser_Email}@gmail.com\",\n \"roles\": [\n   \"pui-user-manager\",\n   \"pui-organisation-manager\"\n ]\n,\n        \"jurisdictions\": [\n    {\n      \"id\": \"Divorce\"\n    },\n    {\n      \"id\": \"SSCS\"\n    },\n    {\n      \"id\": \"Probate\"\n    },\n    {\n      \"id\": \"Public Law\"\n    },\n    {\n      \"id\": \"Bulk Scanning\"\n    },\n    {\n      \"id\": \"Immigration & Asylum\"\n    },\n    {\n      \"id\": \"Civil Money Claims\"\n    },\n    {\n      \"id\": \"Employment\"\n    },\n    {\n      \"id\": \"Family public law and adoption\"\n    },\n    {\n      \"id\": \"Civil enforcement and possession\"\n    }\n  ]\n}"

  val createOrganisation = exec(_.setAll(
      ("SRAId", sRAId()),
      ("CompanyNumber", companyNumber()),
      ("CompanyURL", companyURL()),
      ("FirstName",firstName()),
      ("LastName",lastName()),
      ("CompanyEmail",companyEmail()),
      ("PaymentAccount1",paymentAccount1()),
      ("PaymentAccount2",paymentAccount2()),
      ("AddressLine1",addressLine1())
    ))

    .exec(http("RD01_Internal_CreateOrganization")
      .post("/refdata/internal/v1/organisations")
      .header("ServiceAuthorization", s2sToken)
      .body(StringBody(createOrgString))
      .header("Content-Type", "application/json")
      .check(jsonPath("$.organisationIdentifier").saveAs("NewPendingOrg_Id"))
      .check(status in (200,201)))
    .pause(15 seconds, 25 seconds)

  val updateOrganisation = exec(_.setAll(
    ("SRAId", sRAId()),
    ("CompanyNumber", companyNumber()),
    ("CompanyURL", companyURL()),
    ("FirstName",firstName()),
    ("LastName",lastName()),
    ("CompanyEmail",companyEmail()),
    ("PaymentAccount1",paymentAccount1()),
    ("PaymentAccount2",paymentAccount2()),
    ("AddressLine1",addressLine1())
  ))

    .feed(OrgIdData)

    .exec(http("RD02_Internal_ActivateOrganization")
      .put("/refdata/internal/v1/organisations/${NewPendingOrg_Id}")
      .header("ServiceAuthorization", s2sToken)
      .header("Authorization", IdAMToken)
      .body(StringBody(updateOrgString))
      .header("Content-Type", "application/json")
      .check(status in (200,201)))
    .pause(15 seconds, 25 seconds)


  val GETAllOrganisation = exec(http("RD03_Internal_GetAllOrganizations")
        .get("/refdata/internal/v1/organisations")
      .header("ServiceAuthorization", s2sToken)
      .header("Authorization", IdAMToken)
      .header("Content-Type", "application/json")
      .check(status is 200))
    .pause(15 seconds, 25 seconds)

  val GETOrganisationByID = feed(OrgIdData)

    .exec(http("RD04_Internal_GetOrganizationsByID")
    .get("/refdata/internal/v1/organisations?id=${NewPendingOrg_Id}")
    .header("ServiceAuthorization", s2sToken)
    .header("Authorization", IdAMToken)
    .header("Content-Type", "application/json")
    .check(status is 200))
    .pause(15 seconds, 25 seconds)

  val GETOrganisationsByStatusACTIVE = exec(http("RD05_Internal_GetOrganizationsByStatusACTIVE")
    .get("/refdata/internal/v1/organisations?status=ACTIVE")
    .header("ServiceAuthorization", s2sToken)
    .header("Authorization", IdAMToken)
    .header("Content-Type", "application/json")
    .check(status is 200))
    .pause(15 seconds, 25 seconds)

  val GETOrganisationsByStatusPENDING = exec(http("RD06_Internal_GetOrganizationsByStatusPENDING")
    .get("/refdata/internal/v1/organisations?status=PENDING")
    .header("ServiceAuthorization", s2sToken)
    .header("Authorization", IdAMToken)
    .header("Content-Type", "application/json")
    .check(status is 200))
    .pause(15 seconds, 25 seconds)

  val GETPbas = exec(http("RD07_Internal_RetrievesOrganisationsPaymentAccounts")
    .get("/refdata/internal/v1/organisations/pbas?email=tpanw2pyresecivwmz@email.co.uk")
      .header("ServiceAuthorization", s2sToken)
      .header("Authorization", IdAMToken)
      .header("Content-Type", "application/json")
      .check(status is 200))
    .pause(15 seconds, 25 seconds)

  val AddInternalUserToOrg = repeat(8){
    exec(_.setAll(
      ("InternalUser_FirstName",internalUser_firstName()),
      ("InternalUser_LastName",internalUser_lastName()),
      ("InternalUser_Email",internalUser_Email())
    ))

      .feed(OrgIdData)

      .exec(http("RD08_Internal_AddInternalUserToOrganisation")
        .post("/refdata/internal/v1/organisations/${NewPendingOrg_Id}/users/")
        .header("ServiceAuthorization", s2sToken)
        .header("Authorization", IdAMToken)
        .body(StringBody(addInternalUserString))
        .header("Content-Type", "application/json")
        .check(status is 201))
  }
    .pause(3 seconds, 5 seconds)

  val GETInternalUserForGivenOrganisations = feed(OrgIdData)

    .exec(http("RD09_Internal_GetInternalUserForGivenOrganisation")
    .get("/refdata/internal/v1/organisations/${NewPendingOrg_Id}/users?showdeleted=True")
    .header("ServiceAuthorization", s2sToken)
    .header("Authorization", IdAMToken)
    .header("Content-Type", "application/json")
    .check(status is 200))
    .pause(15 seconds, 25 seconds)

  val GETInternalUserForActiveOrganisationByEmail = exec(http("RD10_Internal_GetInternalUserForActiveOrganisationByEmailAddress")
      .get("/refdata/internal/v1/organisations/${NewPendingOrg_Id}/users?email=" + "test_user0108@gmail.com" + "")
      .header("ServiceAuthorization", s2sToken)
      .header("Authorization", IdAMToken)
      .header("Content-Type", "application/json")
      .check(status is 200))
    .pause(15 seconds, 25 seconds)

  val SearchPbas = exec(http("RD11_Internal_SearchPBAsByEmailAddress")
    .get("/search/pba/tpabsya3pt3o8w0qtg@email.co.uk")
    .header("ServiceAuthorization", s2sToken)
    .header("Authorization", IdAMToken)
    .header("Content-Type", "application/json")
    .check(status is 200))
    .pause(15 seconds, 25 seconds)
}
