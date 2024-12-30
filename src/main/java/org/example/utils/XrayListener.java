package org.example.utils;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.io.IOException;
import java.util.Base64;

import static io.restassured.RestAssured.given;

public class XrayListener extends TestListenerAdapter {

    @Override
    public void onTestSuccess(ITestResult result) {
        String testKey = getXrayTestKey(result);
        try {
            updateTestExecutionStatus(testKey, "PASSED");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String testKey = getXrayTestKey(result);
        String failureMessage = result.getThrowable().getMessage();
        try {
            updateTestExecutionStatus(testKey, "FAILED");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            createJiraBugTicket(testKey, failureMessage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getXrayTestKey(ITestResult result) {
        return result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(XrayTest.class).key();
    }

    private String getAuthToken() throws IOException {
        RestAssured.baseURI = PropertiesLoader.loadXrayProperty("xray.baseUrl");
        String clientId = PropertiesLoader.loadXrayProperty("xray.clientId");
        String clientSecret = PropertiesLoader.loadXrayProperty("xray.clientSecret");

        String response = given()
                .contentType("application/json")
                .body("{\"client_id\": \"" + clientId + "\", \"client_secret\": \"" + clientSecret + "\"}")
                .when()
                .post("/authenticate")
                .then()
                .statusCode(200)
                .extract()
                .asString();

        return response.replace("\"", ""); // Remove quotes
    }

    private void updateTestExecutionStatus(String testKey, String status) throws IOException {
        String authToken = getAuthToken();

        String body = "{"
                + "\"testExecutionKey\": \"QA-20\","
                + "\"tests\": [{"
                + "    \"testKey\": \"" + testKey + "\","
                + "    \"status\": \"" + status + "\""
                + "}]"
                + "}";

        RestAssured.baseURI = PropertiesLoader.loadXrayProperty("xray.baseUrl");
        given()
                .header("Authorization", "Bearer " + authToken)
                .contentType("application/json")
                .body(body)
                .when()
                .post("/import/execution")
                .then()
                .statusCode(200); // Ensure successful import
    }

    private void createJiraBugTicket(String testKey, String failureMessage) throws IOException {
        String body = "{"
                + "\"fields\": {"
                + "\"project\": {"
                + "    \"key\": \"QA\" }, "
                + "\"summary\": \"Bug: Test failed for " + testKey + "\","
                + "\"description\": {"
                + "    \"type\": \"doc\","
                + "    \"version\": 1,"
                + "    \"content\": ["
                + "        {"
                + "            \"type\": \"paragraph\","
                + "            \"content\": ["
                + "                {"
                + "                    \"text\": \"Test failed. Failure details: " + failureMessage + "\","
                + "                    \"type\": \"text\""
                + "                }"
                + "            ]"
                + "        }"
                + "    ]"
                + "},"
                + "\"issuetype\": {"
                + "    \"name\": \"Bug\" }"
                + "}"
                + "}";

        // URL de base de Jira Cloud
        RestAssured.baseURI = PropertiesLoader.loadJiraProperty("jiraIssue.baseURI");
        String response = given()
                .header("Authorization", getAuthTokenJira())
                .contentType("application/json")
                .body(body)
                .when()
                .post()
                .then()
                .statusCode(201)
                .extract().asString();

        // Récupérer l'ID du ticket créé
        String issueId = new JsonPath(response).getString("id");

        // Créer un lien entre le bug et le test Xray
        linkBugToTest(issueId, testKey);
    }


    private void linkBugToTest(String bugId, String testKey) throws IOException {

        String body = "{"
                + "\"type\": {"
                + "    \"name\": \"Blocks\" }, " // Lien de type "Blocks" (vous pouvez choisir un autre type si nécessaire)
                + "\"inwardIssue\": {"
                + "    \"key\": \"" + testKey + "\" },"
                + "\"outwardIssue\": {"
                + "    \"key\": \"" + bugId + "\" }"
                + "}";

        // Lier le ticket de bug au test Xray
        RestAssured.baseURI = PropertiesLoader.loadJiraProperty("jiraIssueLink.baseURI");
        given()
                .header("Authorization", getAuthTokenJira())
                .contentType("application/json")
                .body(body)
                .when()
                .post()
                .then()
                .statusCode(201); // Code de succès pour la création du lien
    }

    public static String getAuthTokenJira() throws IOException {
        // Combine email and API token with a colon (:) separator
        String email=PropertiesLoader.loadJiraProperty("jira.email");
        String apiToken=PropertiesLoader.loadJiraProperty("jira.apiToken");
        String credentials = email + ":" + apiToken;

        // Encode the credentials in Base64
        return "Basic " + Base64.getEncoder().encodeToString(credentials.getBytes());
    }

}
