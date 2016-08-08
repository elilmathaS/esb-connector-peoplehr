/*
* Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
* WSO2 Inc. licenses this file to you under the Apache License,
* Version 2.0 (the "License"); you may not use this file except
* in compliance with the License.
* You may obtain a copy of the License at
*
*http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied. See the License for the
* specific language governing permissions and limitations
* under the License.
*/

package org.wso2.carbon.connector.integration.test.peoplehr;

import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.wso2.connector.integration.test.base.ConnectorIntegrationTestBase;
import org.wso2.connector.integration.test.base.RestResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PeoplehrConnectorIntegrationTest extends ConnectorIntegrationTestBase {

	private Map<String, String> esbRequestHeadersMap = new HashMap<String, String>();

	private Map<String, String> apiRequestHeadersMap = new HashMap<String, String>();

	/**
	 * Set up the environment.
	 */
	@BeforeClass(alwaysRun = true)
	public void setEnvironment() throws Exception {

		init("peoplehr-connector-1.0.3-SNAPSHOT");

		esbRequestHeadersMap.put("Accept-Charset", "UTF-8");
		esbRequestHeadersMap.put("Content-Type", "application/json");
		apiRequestHeadersMap.putAll(esbRequestHeadersMap);
	}

	/**
	 * Positive test case for createEmployee method with mandatory parameters.
	 */
	@Test(priority = 1, groups = { "wso2.esb" }, description =
			"peoplehr {createEmployee} integration test with mandatory" + " parameters.")
	public void testCreateEmployeeWithMandatoryParameters() throws IOException, JSONException {

		esbRequestHeadersMap.put("Action", "urn:createEmployee");

		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
				                    "esb_createEmployee_mandatory.json");

		String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/Employee";

		RestResponse<JSONObject> apiRestResponse =
				sendJsonRestRequest(apiEndPoint, "POST", apiRequestHeadersMap,
				                    "api_createEmployee_mandatory.json");

		Assert.assertEquals(esbRestResponse.getBody().getString("isError"), "false");
		Assert.assertEquals(apiRestResponse.getBody().getString("isError"), "false");
		Assert.assertEquals(connectorProperties.getProperty("firstName"),
		                    apiRestResponse.getBody().getJSONObject("Result")
		                                   .getJSONObject("FirstName").getString("DisplayValue"));
		Assert.assertEquals(connectorProperties.getProperty("lastName"),
		                    apiRestResponse.getBody().getJSONObject("Result")
		                                   .getJSONObject("LastName").getString("DisplayValue"));
	}

	/**
	 * Positive test case for createEmployee method with optional parameters.
	 */
	@Test(groups = { "wso2.esb" }, dependsOnMethods = {
			"testCreateEmployeeWithMandatoryParameters" }, description =
			"peoplehr {createEmployee} integration test with " + "optional parameters.")
	public void testCreateEmployeeWithOptionalParameters() throws IOException, JSONException {

		esbRequestHeadersMap.put("Action", "urn:createEmployee");

		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
				                    "esb_createEmployee_optional.json");

		String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/Employee";

		RestResponse<JSONObject> apiRestResponse =
				sendJsonRestRequest(apiEndPoint, "POST", apiRequestHeadersMap,
				                    "api_createEmployee_optional.json");

		Assert.assertEquals(esbRestResponse.getBody().getString("isError"), "false");
		Assert.assertEquals(apiRestResponse.getBody().getString("isError"), "false");
		Assert.assertEquals(connectorProperties.getProperty("empEmailOptional"),
		                    apiRestResponse.getBody().getJSONObject("Result")
		                                   .getJSONObject("EmailId").getString("DisplayValue"));
		Assert.assertEquals(connectorProperties.getProperty("empDateOfBirth"),
		                    apiRestResponse.getBody().getJSONObject("Result")
		                                   .getJSONObject("DateOfBirth").getString("DisplayValue"));
	}

	/**
	 * Negative test case for createEmployee method.
	 */
	@Test(groups = { "wso2.esb" }, description = "peoplehr {createEmployee} integration test with" +
	                                             " negative case.")
	public void testCreateEmployeeWithNegativeCase() throws IOException, JSONException {

		esbRequestHeadersMap.put("Action", "urn:createEmployee");

		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
				                    "esb_createEmployee_negative.json");

		String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/Employee";

		RestResponse<JSONObject> apiRestResponse =
				sendJsonRestRequest(apiEndPoint, "POST", apiRequestHeadersMap,
				                    "api_createEmployee_negative.json");

		Assert.assertEquals(esbRestResponse.getBody().getString("isError"),
		                    apiRestResponse.getBody().getString("isError"));
		Assert.assertEquals(esbRestResponse.getBody().getString("Status"),
		                    apiRestResponse.getBody().getString("Status"));
		Assert.assertEquals(esbRestResponse.getBody().getString("Message"),
		                    apiRestResponse.getBody().getString("Message"));

	}

	/**
	 * Positive test case for getEmployeeDetailById method with mandatory parameters.
	 */
	@Test(groups = { "wso2.esb" }, dependsOnMethods = {
			"testCreateEmployeeWithMandatoryParameters" }, description =
			"peoplehr {getEmployeeById} integration test with mandatory parameters.")
	public void testGetEmployeeDetailByIdWithMandatoryParameters()
			throws IOException, JSONException {

		esbRequestHeadersMap.put("Action", "urn:getEmployeeDetailById");

		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
				                    "esb_getEmployeeDetailById_mandatory.json");

		String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/Employee";

		RestResponse<JSONObject> apiRestResponse =
				sendJsonRestRequest(apiEndPoint, "POST", apiRequestHeadersMap,
				                    "api_getEmployeeDetailById_mandatory.json");

		Assert.assertEquals(esbRestResponse.getBody().getString("isError"), "false");
		Assert.assertEquals(apiRestResponse.getBody().getString("isError"), "false");
		Assert.assertEquals(
				esbRestResponse.getBody().getJSONObject("Result").getJSONObject("FirstName")
				               .getString("DisplayValue"),
				apiRestResponse.getBody().getJSONObject("Result").getJSONObject("FirstName")
				               .getString("DisplayValue"));
		Assert.assertEquals(
				esbRestResponse.getBody().getJSONObject("Result").getJSONObject("LastName")
				               .getString("DisplayValue"),
				apiRestResponse.getBody().getJSONObject("Result").getJSONObject("LastName")
				               .getString("DisplayValue"));
	}

	/**
	 * Negative test case for getEmployeeDetailById method.
	 */
	@Test(groups = {
			"wso2.esb" }, description = "peoplehr {getEmployeeById} integration test with negative " +
	                                    "case.")
	public void testGetEmployeeDetailByIdWithNegativeCase() throws IOException, JSONException {

		esbRequestHeadersMap.put("Action", "urn:getEmployeeDetailById");

		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
				                    "esb_getEmployeeDetailById_negative.json");

		String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/Employee";

		RestResponse<JSONObject> apiRestResponse =
				sendJsonRestRequest(apiEndPoint, "POST", apiRequestHeadersMap,
				                    "api_getEmployeeDetailById_negative.json");

		Assert.assertEquals(esbRestResponse.getBody().getString("isError"),
		                    apiRestResponse.getBody().getString("isError"));
		Assert.assertEquals(esbRestResponse.getBody().getString("Status"),
		                    apiRestResponse.getBody().getString("Status"));
		Assert.assertEquals(esbRestResponse.getBody().getString("Message"),
		                    apiRestResponse.getBody().getString("Message"));
	}

	/**
	 * Positive test case for getAllEmployeeDetail method with mandatory parameters.
	 */
	@Test(groups = { "wso2.esb" }, dependsOnMethods = {
			"testCreateEmployeeWithMandatoryParameters" },
			description = "peoplehr {getAllEmployeeDetail} integration test with " +
			              "mandatory parameters.")
	public void testGetAllEmployeeDetailWithMandatoryParameters()
			throws IOException, JSONException {

		esbRequestHeadersMap.put("Action", "urn:getAllEmployeeDetail");

		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
				                    "esb_getAllEmployeeDetail_mandatory.json");

		String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/Employee";

		RestResponse<JSONObject> apiRestResponse =
				sendJsonRestRequest(apiEndPoint, "POST", apiRequestHeadersMap,
				                    "api_getAllEmployeeDetail_mandatory.json");

		Assert.assertEquals(esbRestResponse.getBody().getString("isError"), "false");
		Assert.assertEquals(apiRestResponse.getBody().getString("isError"), "false");
	}

	/**
	 * Negative test case for getAllEmployeeDetail method.
	 */
	@Test(groups = { "wso2.esb" }, description =
			"peoplehr {getAllEmployeeDetail} integration test" + " with negative case.")
	public void testGetAllEmployeeDetailWithNegativeCase() throws IOException, JSONException {

		esbRequestHeadersMap.put("Action", "urn:getAllEmployeeDetail");

		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
				                    "esb_getAllEmployeeDetail_negative.json");

		String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/Employee";

		RestResponse<JSONObject> apiRestResponse =
				sendJsonRestRequest(apiEndPoint, "POST", apiRequestHeadersMap,
				                    "api_getAllEmployeeDetail_negative.json");

		Assert.assertEquals(esbRestResponse.getBody().getString("isError"),
		                    apiRestResponse.getBody().getString("isError"));
		Assert.assertEquals(esbRestResponse.getBody().getString("Status"),
		                    apiRestResponse.getBody().getString("Status"));
		Assert.assertEquals(esbRestResponse.getBody().getString("Message"),
		                    apiRestResponse.getBody().getString("Message"));
	}

	/**
	 * Positive test case for getAllEmployeeDetail method with optional parameters.
	 */
	@Test(groups = { "wso2.esb" }, dependsOnMethods = {
			"testCreateEmployeeWithMandatoryParameters" },
			description = "peoplehr {getAllEmployeeDetail} integration test with " +
			              "optional parameters.")
	public void testGetAllEmployeeDetailWithOptionalParameters() throws IOException, JSONException {

		esbRequestHeadersMap.put("Action", "urn:getAllEmployeeDetail");

		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
				                    "esb_getAllEmployeeDetail_optional.json");

		String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/Employee";

		RestResponse<JSONObject> apiRestResponse =
				sendJsonRestRequest(apiEndPoint, "POST", apiRequestHeadersMap,
				                    "api_getAllEmployeeDetail_optional.json");

		Assert.assertEquals(esbRestResponse.getBody().getString("isError"), "false");
		Assert.assertEquals(apiRestResponse.getBody().getString("isError"), "false");
	}

	/**
	 * Positive test case for updateEmployee method with optional parameters.
	 */
	@Test(priority = 4, groups = { "wso2.esb" }, dependsOnMethods = {
			"testCreateEmployeeWithMandatoryParameters" }, description =
			"peoplehr {updateEmployee} integration " + "test with optional parameters.")
	public void testUpdateEmployeeWithOptionalParameters() throws IOException, JSONException {

		esbRequestHeadersMap.put("Action", "urn:updateEmployee");

		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
				                    "esb_updateEmployee_optional.json");

		String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/Employee";

		RestResponse<JSONObject> apiRestResponse =
				sendJsonRestRequest(apiEndPoint, "POST", apiRequestHeadersMap,
				                    "api_updateEmployee_optional.json");

		Assert.assertFalse(Boolean.parseBoolean(esbRestResponse.getBody().getString("isError")));
		Assert.assertEquals(connectorProperties.getProperty("firstNameUpdated"),
		                    apiRestResponse.getBody().getJSONObject("Result")
		                                   .getJSONObject("FirstName").getString("DisplayValue"));
		Assert.assertEquals(connectorProperties.getProperty("empEmailUpdated"),
		                    apiRestResponse.getBody().getJSONObject("Result")
		                                   .getJSONObject("EmailId").getString("DisplayValue"));
	}

	/**
	 * Negative test case for updateEmployee method.
	 */
	@Test(groups = {
			"wso2.esb" }, description = "peoplehr {updateEmployee} integration test with negative " +
	                                    "case.")
	public void testUpdateEmployeeNegativeCase() throws IOException, JSONException {

		esbRequestHeadersMap.put("Action", "urn:updateEmployee");

		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
				                    "esb_updateEmployee_negative.json");

		String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/Employee";

		RestResponse<JSONObject> apiRestResponse =
				sendJsonRestRequest(apiEndPoint, "POST", apiRequestHeadersMap,
				                    "api_updateEmployee_negative.json");

		Assert.assertTrue(Boolean.parseBoolean(esbRestResponse.getBody().getString("isError")));
		Assert.assertTrue(Boolean.parseBoolean(apiRestResponse.getBody().getString("isError")));
		Assert.assertEquals(apiRestResponse.getBody().getString("Message"),
		                    esbRestResponse.getBody().getString("Message"));
	}

	/**
	 * Positive test case for updateEmployeeId method with optional parameters.
	 */
	@Test(priority = 5, groups = { "wso2.esb" }, dependsOnMethods = {
			"testCreateEmployeeWithMandatoryParameters" },
			description = "peoplehr {updateEmployeeId} integration test with optional parameters.")
	public void testUpdateEmployeeIdWithMandatoryParameters() throws IOException, JSONException {

		esbRequestHeadersMap.put("Action", "urn:updateEmployeeId");

		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
				                    "esb_updateEmployeeId_mandatory.json");

		Assert.assertFalse(Boolean.parseBoolean(esbRestResponse.getBody().getString("isError")));
	}

	/**
	 * Negative test case for updateEmployeeId method.
	 */
	@Test(groups = {
			"wso2.esb" }, description = "peoplehr {updateEmployeeId} integration test with negative " +
	                                    "case.")
	public void testUpdateEmployeeIdNegativeCase() throws IOException, JSONException {

		esbRequestHeadersMap.put("Action", "urn:updateEmployeeId");

		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
				                    "esb_updateEmployeeId_negative.json");

		Assert.assertTrue(Boolean.parseBoolean(esbRestResponse.getBody().getString("isError")));
	}

	/**
	 * Positive test case for markAsLeaverById method with mandatory parameters.
	 */
	@Test(priority = 6, groups = { "wso2.esb" },
			description = "peoplehr {markAsLeaverById} integration test with mandatory parameters.")
	public void testMarkAsLeaverByIdWithMandatoryParameters() throws IOException, JSONException {

		esbRequestHeadersMap.put("Action", "urn:markAsLeaverById");

		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
				                    "esb_markAsLeaverById_mandatory.json");

		Assert.assertFalse(Boolean.parseBoolean(esbRestResponse.getBody().getString("isError")));
	}

	/**
	 * Negative test case for markAsLeaverById method.
	 */
	@Test(groups = { "wso2.esb" }, description =
			"peoplehr {markAsLeaverById} integration test with " + "negative case.")
	public void testMarkAsLeaverByIdNegativeCase() throws IOException, JSONException {

		esbRequestHeadersMap.put("Action", "urn:markAsLeaverById");

		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
				                    "esb_markAsLeaverById_negative.json");

		Assert.assertTrue(Boolean.parseBoolean(esbRestResponse.getBody().getString("isError")));
	}

	/**
	 * Positive test case for createTimesheet method with mandatory parameters.
	 */
	@Test(groups = { "wso2.esb" }, dependsOnMethods = {
			"testCreateEmployeeWithMandatoryParameters" },
			description = "peoplehr {createTimesheet} integration test with " +
			              "mandatory parameters.")
	public void testCreateTimesheetWithMandatoryParameters() throws IOException, JSONException {

		esbRequestHeadersMap.put("Action", "urn:createTimesheet");

		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
				                    "esb_createTimesheet_mandatory.json");

		String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/Timesheet";

		RestResponse<JSONObject> apiRestResponse =
				sendJsonRestRequest(apiEndPoint, "POST", apiRequestHeadersMap,
				                    "api_createTimesheet_mandatory.json");

		Assert.assertEquals(esbRestResponse.getBody().getString("isError"), "false");
		Assert.assertEquals(connectorProperties.getProperty("timesheetDate"),
		                    apiRestResponse.getBody().getJSONArray("Result").getJSONObject(0)
		                                   .getString("TimesheetDate"));
		Assert.assertEquals(connectorProperties.getProperty("timeIn1").toString() + ":00",
		                    apiRestResponse.getBody().getJSONArray("Result").getJSONObject(0)
		                                   .getString("TimeIn1"));
	}

	/**
	 * Positive test case for createTimesheet method with optional parameters.
	 */
	@Test(groups = { "wso2.esb" }, dependsOnMethods = {
			"testCreateEmployeeWithOptionalParameters" },
			description = "peoplehr {createTimesheet} integration test with optional parameters.")
	public void testCreateTimesheetWithOptionalParameters() throws IOException, JSONException {

		esbRequestHeadersMap.put("Action", "urn:createTimesheet");

		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
				                    "esb_createTimesheet_optional.json");

		String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/Timesheet";

		RestResponse<JSONObject> apiRestResponse =
				sendJsonRestRequest(apiEndPoint, "POST", apiRequestHeadersMap,
				                    "api_createTimesheet_optional.json");

		Assert.assertEquals(esbRestResponse.getBody().getString("isError"), "false");
		Assert.assertEquals(connectorProperties.getProperty("timesheetDateOpt"),
		                    apiRestResponse.getBody().getJSONArray("Result").getJSONObject(0)
		                                   .getString("TimesheetDate"));
		Assert.assertEquals(connectorProperties.getProperty("timeIn1").toString() + ":00",
		                    apiRestResponse.getBody().getJSONArray("Result").getJSONObject(0)
		                                   .getString("TimeIn1"));
	}

	/**
	 * Negative test case for createTimesheet method.
	 */
	@Test(groups = {
			"wso2.esb" }, description = "peoplehr {createTimesheet} integration test with negative " +
	                                    "case.")
	public void testCreateTimesheetWithNegativeParameters() throws IOException, JSONException {

		esbRequestHeadersMap.put("Action", "urn:createTimesheet");

		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
				                    "esb_createTimesheet_negative.json");

		String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/Timesheet";

		RestResponse<JSONObject> apiRestResponse =
				sendJsonRestRequest(apiEndPoint, "POST", apiRequestHeadersMap,
				                    "api_createTimesheet_negative.json");

		Assert.assertEquals(esbRestResponse.getBody().getString("isError"), "true");
		Assert.assertEquals(esbRestResponse.getBody().getString("Message"),
		                    apiRestResponse.getBody().getString("Message"));
	}

	/**
	 * Positive test case for getTimesheet method with mandatory parameters.
	 */
	@Test(groups = { "wso2.esb" }, dependsOnMethods = {
			"testCreateTimesheetWithMandatoryParameters" },
			description = "peoplehr {getTimesheet} integration test with mandatory parameters.")
	public void testGetTimesheetWithMandatoryParameters() throws IOException, JSONException {

		esbRequestHeadersMap.put("Action", "urn:getTimesheet");

		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
				                    "esb_getTimesheet_mandatory.json");

		String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/Timesheet";

		RestResponse<JSONObject> apiRestResponse =
				sendJsonRestRequest(apiEndPoint, "POST", apiRequestHeadersMap,
				                    "api_getTimesheet_mandatory.json");

		Assert.assertEquals(esbRestResponse.getBody().getString("isError"), "false");
		Assert.assertEquals(esbRestResponse.getBody().getJSONArray("Result").length(),
		                    apiRestResponse.getBody().getJSONArray("Result").length());
		Assert.assertEquals(esbRestResponse.getBody().getJSONArray("Result").getJSONObject(0)
		                                   .getString("TimesheetDate"),
		                    apiRestResponse.getBody().getJSONArray("Result").getJSONObject(0)
		                                   .getString("TimesheetDate"));
	}

	/**
	 * Positive test case for getTimesheet method with optional parameters.
	 */
	@Test(groups = { "wso2.esb" }, dependsOnMethods = {
			"testCreateTimesheetWithOptionalParameters" },
			description = "peoplehr {getTimesheet} integration test with optional parameters.")
	public void testGetTimesheetWithOptionalParameters() throws IOException, JSONException {

		esbRequestHeadersMap.put("Action", "urn:getTimesheet");

		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
				                    "esb_getTimesheet_optional.json");

		String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/Timesheet";

		RestResponse<JSONObject> apiRestResponse =
				sendJsonRestRequest(apiEndPoint, "POST", apiRequestHeadersMap,
				                    "api_getTimesheet_optional.json");

		Assert.assertEquals(esbRestResponse.getBody().getString("isError"), "false");
		Assert.assertEquals(esbRestResponse.getBody().getJSONArray("Result").length(),
		                    apiRestResponse.getBody().getJSONArray("Result").length());
		Assert.assertEquals(esbRestResponse.getBody().getJSONArray("Result").getJSONObject(0)
		                                   .getString("TimesheetDate"),
		                    apiRestResponse.getBody().getJSONArray("Result").getJSONObject(0)
		                                   .getString("TimesheetDate"));
	}

	/**
	 * Negative test case for getTimesheet method.
	 */
	@Test(groups = {
			"wso2.esb" }, description = "peoplehr {getTimesheet} integration negative case.")
	public void testGetTimesheetNegativeCase() throws IOException, JSONException {

		esbRequestHeadersMap.put("Action", "urn:getTimesheet");

		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
				                    "esb_getTimesheet_negative.json");

		String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/Timesheet";

		RestResponse<JSONObject> apiRestResponse =
				sendJsonRestRequest(apiEndPoint, "POST", apiRequestHeadersMap,
				                    "api_getTimesheet_negative.json");

		Assert.assertEquals(esbRestResponse.getBody().getString("isError"), "true");
		Assert.assertEquals(apiRestResponse.getBody().getString("isError"), "true");
		Assert.assertEquals(esbRestResponse.getBody().getString("Message"),
		                    apiRestResponse.getBody().getString("Message"));
	}

	/**
	 * Positive test case for updateTimesheet method with mandatory parameters.
	 */
	@Test(priority = 3, groups = { "wso2.esb" }, dependsOnMethods = {
			"testCreateTimesheetWithMandatoryParameters" }, description =
			"peoplehr {updateTimesheet} " + "integration test with mandatory parameters.")
	public void testUpdateTimesheetWithMandatoryParameters() throws IOException, JSONException {

		esbRequestHeadersMap.put("Action", "urn:updateTimesheet");

		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
				                    "esb_updateTimesheet_mandatory.json");

		String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/Timesheet";

		RestResponse<JSONObject> apiRestResponse =
				sendJsonRestRequest(apiEndPoint, "POST", apiRequestHeadersMap,
				                    "api_updateTimesheet_mandatory.json");

		Assert.assertEquals(esbRestResponse.getBody().getString("isError"), "false");
		Assert.assertEquals(connectorProperties.getProperty("timeIn1update").toString() + ":00",
		                    apiRestResponse.getBody().getJSONArray("Result").getJSONObject(0)
		                                   .getString("TimeIn1"));
		Assert.assertEquals(connectorProperties.getProperty("timesheetDate"),
		                    apiRestResponse.getBody().getJSONArray("Result").getJSONObject(0)
		                                   .getString("TimesheetDate"));
	}

	/**
	 * Positive test case for updateTimesheet method with optional parameters.
	 */
	@Test(priority = 3, groups = { "wso2.esb" }, dependsOnMethods = {
			"testCreateTimesheetWithOptionalParameters" }, description =
			"peoplehr {updateTimesheet} " + "integration test with optional parameters.")
	public void testUpdateTimesheetWithOptionalParameters() throws IOException, JSONException {

		esbRequestHeadersMap.put("Action", "urn:updateTimesheet");

		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
				                    "esb_updateTimesheet_optional.json");

		String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/Timesheet";

		RestResponse<JSONObject> apiRestResponse =
				sendJsonRestRequest(apiEndPoint, "POST", apiRequestHeadersMap,
				                    "api_updateTimesheet_optional.json");

		Assert.assertEquals(esbRestResponse.getBody().getString("isError"), "false");
		Assert.assertEquals(connectorProperties.getProperty("timeIn1update").toString() + ":00",
		                    apiRestResponse.getBody().getJSONArray("Result").getJSONObject(0)
		                                   .getString("TimeIn1"));
		Assert.assertEquals(connectorProperties.getProperty("timesheetDate"),
		                    apiRestResponse.getBody().getJSONArray("Result").getJSONObject(0)
		                                   .getString("TimesheetDate"));
		Assert.assertEquals(connectorProperties.getProperty("testComment"),
		                    apiRestResponse.getBody().getJSONArray("Result").getJSONObject(0)
		                                   .getString("Comments"));
	}

	/**
	 * Negative test case for updateTimesheet method.
	 */
	@Test(groups = {
			"wso2.esb" }, description = "peoplehr {updateTimesheet} integration negative case.")
	public void testUpdateTimesheetNegativeCase() throws IOException, JSONException {

		esbRequestHeadersMap.put("Action", "urn:updateTimesheet");

		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
				                    "esb_updateTimesheet_negative.json");

		String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/Timesheet";

		RestResponse<JSONObject> apiRestResponse =
				sendJsonRestRequest(apiEndPoint, "POST", apiRequestHeadersMap,
				                    "api_updateTimesheet_negative.json");

		Assert.assertEquals(esbRestResponse.getBody().getString("isError"), "true");
		Assert.assertEquals(apiRestResponse.getBody().getString("isError"), "true");
	}

	/**
	 * Positive test case for deleteTimesheet method.
	 */
	@Test(priority = 5, groups = { "wso2.esb" }, description = "peoplehr {deleteTimesheet}" +
	                                                           " integration positive case.")
	public void testDeleteTimesheetWithMandatoryParameters() throws IOException, JSONException {

		esbRequestHeadersMap.put("Action", "urn:deleteTimesheet");

		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
				                    "esb_deleteTimesheet_mandatory.json");
		Assert.assertEquals(esbRestResponse.getBody().getString("isError"), "false");
	}

	/**
	 * Positive test case for createProjectTimesheet method with mandatory parameters.
	 */
	@Test(groups = { "wso2.esb" }, dependsOnMethods = {
			"testCreateEmployeeWithMandatoryParameters" }, description =
			"peoplehr {createProjectTimesheet}" + " integration test with mandatory parameters.")
	public void testCreateProjectTimesheetWithMandatoryParameters()
			throws IOException, JSONException {

		esbRequestHeadersMap.put("Action", "urn:createProjectTimesheet");

		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
				                    "esb_createProjectTimesheet_mandatory.json");

		String transactionIdMandatory = esbRestResponse.getBody().getString("Result").split("=")[1];

		connectorProperties.put("transactionIdMandatory", transactionIdMandatory);

		String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/ProjectTimesheet";

		RestResponse<JSONObject> apiRestResponse =
				sendJsonRestRequest(apiEndPoint, "POST", apiRequestHeadersMap,
				                    "api_createProjectTimesheet_mandatory.json");

		Assert.assertEquals(esbRestResponse.getBody().getString("isError"), "false");
		Assert.assertEquals(apiRestResponse.getBody().getString("isError"), "false");

		Assert.assertEquals(connectorProperties.getProperty("timesheetDate"),
		                    apiRestResponse.getBody().getJSONObject("Result")
		                                   .getString("ProjectTimesheetDate"));
		Assert.assertEquals(connectorProperties.getProperty("timesheetProject"),
		                    apiRestResponse.getBody().getJSONObject("Result")
		                                   .getString("TimesheetProject"));
	}

	/**
	 * Positive test case for createProjectTimesheet method with optional parameters.
	 */
	@Test(groups = { "wso2.esb" }, dependsOnMethods = {
			"testCreateProjectTimesheetWithMandatoryParameters" },
			description = "peoplehr {createProjectTimesheet} integration test with optional" +
			              " parameters.")
	public void testCreateProjectTimesheetWithOptionalParameters()
			throws IOException, JSONException {

		esbRequestHeadersMap.put("Action", "urn:createProjectTimesheet");

		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
				                    "esb_createProjectTimesheet_optional.json");

		String transactionIdOptional = esbRestResponse.getBody().getString("Result").split("=")[1];
		connectorProperties.put("transactionIdOptional", transactionIdOptional);

		String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/ProjectTimesheet";

		RestResponse<JSONObject> apiRestResponse =
				sendJsonRestRequest(apiEndPoint, "POST", apiRequestHeadersMap,
				                    "api_createProjectTimesheet_optional.json");

		Assert.assertEquals(esbRestResponse.getBody().getString("isError"), "false");
		Assert.assertEquals(apiRestResponse.getBody().getString("isError"), "false");

		Assert.assertEquals(connectorProperties.getProperty("projectTimeSheetquantity"),
		                    apiRestResponse.getBody().getJSONObject("Result")
		                                   .getString("Quantity"));
		Assert.assertEquals(connectorProperties.getProperty("projectTimeSheetTask"),
		                    apiRestResponse.getBody().getJSONObject("Result")
		                                   .getString("TimesheetTask"));
		Assert.assertEquals(connectorProperties.getProperty("testComment"),
		                    apiRestResponse.getBody().getJSONObject("Result").getString("Notes"));
	}

	/**
	 * Negative test case for createProjectTimesheet method.
	 */
	@Test(groups = {
			"wso2.esb" }, description = "peoplehr {createProjectTimesheet} integration test with " +
	                                    "negative case.")
	public void testCreateProjectTimesheetWithNegativeCase() throws IOException, JSONException {

		esbRequestHeadersMap.put("Action", "urn:createProjectTimesheet");

		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
				                    "esb_createProjectTimesheet_negative.json");

		String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/ProjectTimesheet";

		RestResponse<JSONObject> apiRestResponse =
				sendJsonRestRequest(apiEndPoint, "POST", apiRequestHeadersMap,
				                    "api_createProjectTimesheet_negative.json");

		Assert.assertEquals(esbRestResponse.getBody().getString("isError"),
		                    apiRestResponse.getBody().getString("isError"));
	}

	/**
	 * Positive test case for getProjectTimesheet method with mandatory parameters.
	 */
	@Test(groups = { "wso2.esb" }, dependsOnMethods = {
			"testCreateProjectTimesheetWithMandatoryParameters" },
			description = "peoplehr {getProjectTimesheet} integration test with mandatory " +
			              "parameters.")
	public void testGetProjectTimesheetWithMandatoryParameters() throws IOException, JSONException {

		esbRequestHeadersMap.put("Action", "urn:getProjectTimesheet");

		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
				                    "esb_getProjectTimesheet_mandatory.json");

		String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/ProjectTimesheet";

		RestResponse<JSONObject> apiRestResponse =
				sendJsonRestRequest(apiEndPoint, "POST", apiRequestHeadersMap,
				                    "api_getProjectTimesheet_mandatory.json");

		Assert.assertFalse(Boolean.parseBoolean(esbRestResponse.getBody().getString("isError")));
		Assert.assertEquals(esbRestResponse.getBody().get("Message"),
		                    apiRestResponse.getBody().get("Message"));
		Assert.assertEquals(esbRestResponse.getBody().get("Status"),
		                    apiRestResponse.getBody().get("Status"));
	}

	/**
	 * Positive test case for getProjectTimesheet method with optional parameters.
	 */
	@Test(groups = { "wso2.esb" }, dependsOnMethods = {
			"testCreateProjectTimesheetWithOptionalParameters" },
			description = "peoplehr {getProjectTimesheet} integration test with optional" +
			              " parameters.")
	public void testGetProjectTimesheetWithOptionalParameters() throws IOException, JSONException {

		esbRequestHeadersMap.put("Action", "urn:getProjectTimesheet");

		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
				                    "esb_getProjectTimesheet_optional.json");

		String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/ProjectTimesheet";

		RestResponse<JSONObject> apiRestResponse =
				sendJsonRestRequest(apiEndPoint, "POST", apiRequestHeadersMap,
				                    "api_getProjectTimesheet_optional.json");

		Assert.assertFalse(Boolean.parseBoolean(esbRestResponse.getBody().getString("isError")));
		Assert.assertEquals(esbRestResponse.getBody().get("Message"),
		                    apiRestResponse.getBody().get("Message"));
		Assert.assertEquals(esbRestResponse.getBody().get("Status"),
		                    apiRestResponse.getBody().get("Status"));
		Assert.assertEquals(esbRestResponse.getBody().getJSONArray("Result").getJSONObject(0)
		                                   .get("ProjectTimesheetDate"),
		                    connectorProperties.get("timesheetDate"));
	}

	/**
	 * Negative test case for getProjectTimesheet method.
	 */
	@Test(groups = {
			"wso2.esb" }, description = "peoplehr {getProjectTimesheet} integration " +
	                                    "test with negative case.")
	public void testGetProjectTimesheetWithNegativeCase() throws IOException, JSONException {

		esbRequestHeadersMap.put("Action", "urn:getProjectTimesheet");

		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
				                    "esb_getProjectTimesheet_negative.json");

		String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/ProjectTimesheet";

		RestResponse<JSONObject> apiRestResponse =
				sendJsonRestRequest(apiEndPoint, "POST", apiRequestHeadersMap,
				                    "api_getProjectTimesheet_negative.json");

		Assert.assertTrue(Boolean.parseBoolean(esbRestResponse.getBody().getString("isError")));
	}

	/**
	 * Positive test case for updateProjectTimesheet method with mandatory parameters.
	 */
	@Test(priority = 4, groups = { "wso2.esb" }, dependsOnMethods = {
			"testCreateProjectTimesheetWithMandatoryParameters" }, description =
			"peoplehr {updateProjectTimesheet} " + "integration test with mandatory parameters.")
	public void testUpdateProjectTimesheetWithMandatoryParameters()
			throws IOException, JSONException {

		esbRequestHeadersMap.put("Action", "urn:updateProjectTimesheet");

		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
				                    "esb_updateProjectTimesheet_mandatory.json");

		String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/ProjectTimesheet";

		RestResponse<JSONObject> apiRestResponse =
				sendJsonRestRequest(apiEndPoint, "POST", apiRequestHeadersMap,
				                    "api_updateProjectTimesheet_mandatory.json");

		Assert.assertEquals(esbRestResponse.getBody().getString("isError"), "false");
		Assert.assertEquals(apiRestResponse.getBody().getString("isError"), "false");

		Assert.assertEquals(connectorProperties.getProperty("updatedTimesheetProject"),
		                    apiRestResponse.getBody().getJSONObject("Result")
		                                   .getString("TimesheetProject"));
	}

	/**
	 * Positive test case for updateProjectTimesheet method with optional parameters.
	 */
	@Test(priority = 4, groups = { "wso2.esb" }, dependsOnMethods = {
			"testCreateProjectTimesheetWithOptionalParameters" }, description =
			"peoplehr {updateProjectTimesheet}" + " integration test with optional parameters.")
	public void testUpdateProjectTimesheetWithOptionalParameters()
			throws IOException, JSONException {

		esbRequestHeadersMap.put("Action", "urn:updateProjectTimesheet");

		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
				                    "esb_updateProjectTimesheet_optional.json");

		String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/ProjectTimesheet";

		RestResponse<JSONObject> apiRestResponse =
				sendJsonRestRequest(apiEndPoint, "POST", apiRequestHeadersMap,
				                    "api_updateProjectTimesheet_optional.json");

		Assert.assertEquals(esbRestResponse.getBody().getString("isError"), "false");
		Assert.assertEquals(apiRestResponse.getBody().getString("isError"), "false");

		Assert.assertEquals(connectorProperties.getProperty("updatedProjectTimesheetQuantity"),
		                    apiRestResponse.getBody().getJSONObject("Result")
		                                   .getString("Quantity"));
		Assert.assertEquals(connectorProperties.getProperty("updatedProjectTimesheetTask"),
		                    apiRestResponse.getBody().getJSONObject("Result")
		                                   .getString("TimesheetTask"));
		Assert.assertEquals(connectorProperties.getProperty("testComment"),
		                    apiRestResponse.getBody().getJSONObject("Result").getString("Notes"));
	}

	/**
	 * Negative test case for updateProjectTimesheet method.
	 */
	@Test(groups = {
			"wso2.esb" }, description = "peoplehr {updateProjectTimesheet} integration test with" +
	                                    " negative case.")
	public void testUpdateProjectTimesheetWithNegativeCase() throws IOException, JSONException {

		esbRequestHeadersMap.put("Action", "urn:updateProjectTimesheet");

		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
				                    "esb_updateProjectTimesheet_negative.json");

		String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/ProjectTimesheet";

		RestResponse<JSONObject> apiRestResponse =
				sendJsonRestRequest(apiEndPoint, "POST", apiRequestHeadersMap,
				                    "api_updateProjectTimesheet_negative.json");

		Assert.assertEquals(esbRestResponse.getBody().getString("isError"),
		                    apiRestResponse.getBody().getString("isError"));
	}

	/**
	 * Positive test case for deleteProjectTimesheet method.
	 */
	@Test(priority = 5, groups = { "wso2.esb" }, dependsOnMethods = {
			"testUpdateProjectTimesheetWithOptionalParameters",
			"testCreateProjectTimesheetWithOptionalParameters" }, description =
			"peoplehr {deleteProjectTimesheet}" + " integration positive case.")
	public void testDeleteProjectTimesheetWithMandatoryParameters()
			throws IOException, JSONException {

		esbRequestHeadersMap.put("Action", "urn:deleteProjectTimesheet");

		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
				                    "esb_deleteProjectTimesheet_mandatory.json");
		Assert.assertEquals(esbRestResponse.getBody().getString("isError"), "false");
	}

	/**
	 * Positive test case for createSalary method with mandatory parameters.
	 */
	@Test(groups = { "wso2.esb" }, dependsOnMethods = {
			"testCreateEmployeeWithMandatoryParameters" }, description =
			"peoplehr {createSalary} " + "integration test with mandatory parameters.")
	public void testCreateSalaryWithMandatoryParameters() throws IOException, JSONException {

		esbRequestHeadersMap.put("Action", "urn:createSalary");

		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
				                    "esb_createSalary_mandatory.json");

		String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/Salary";

		RestResponse<JSONObject> apiRestResponse =
				sendJsonRestRequest(apiEndPoint, "POST", apiRequestHeadersMap,
				                    "api_createSalary_mandatory.json");

		Assert.assertEquals(esbRestResponse.getBody().getString("isError"), "false");
		Assert.assertEquals(apiRestResponse.getBody().getString("isError"), "false");

		Assert.assertEquals(connectorProperties.getProperty("effectiveFromDate"),
		                    apiRestResponse.getBody().getJSONArray("Result").getJSONObject(0)
		                                   .getString("EffectiveFrom"));
		Assert.assertEquals(connectorProperties.getProperty("salaryType"),
		                    apiRestResponse.getBody().getJSONArray("Result").getJSONObject(0)
		                                   .getString("SalaryType"));
		Assert.assertEquals(connectorProperties.getProperty("salaryAmount"),
		                    apiRestResponse.getBody().getJSONArray("Result").getJSONObject(0)
		                                   .getString("SalaryAmount"));
	}

	/**
	 * Positive test case for createSalary method with optional parameters.
	 */
	@Test(groups = { "wso2.esb" }, dependsOnMethods = {
			"testCreateSalaryWithMandatoryParameters" },
			description = "peoplehr {createSalary} integration test with optional parameters.")
	public void testCreateSalaryWithOptionalParameters() throws IOException, JSONException {

		esbRequestHeadersMap.put("Action", "urn:createSalary");

		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
				                    "esb_createSalary_optional.json");

		String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/Salary";

		RestResponse<JSONObject> apiRestResponse =
				sendJsonRestRequest(apiEndPoint, "POST", apiRequestHeadersMap,
				                    "api_createSalary_optional.json");

		Assert.assertEquals(esbRestResponse.getBody().getString("isError"), "false");
		Assert.assertEquals(apiRestResponse.getBody().getString("isError"), "false");

		Assert.assertEquals(connectorProperties.getProperty("effectiveFromDate"),
		                    apiRestResponse.getBody().getJSONArray("Result").getJSONObject(0)
		                                   .getString("EffectiveFrom"));
		Assert.assertEquals(connectorProperties.getProperty("salaryType"),
		                    apiRestResponse.getBody().getJSONArray("Result").getJSONObject(0)
		                                   .getString("SalaryType"));
		Assert.assertEquals(connectorProperties.getProperty("salaryAmount"),
		                    apiRestResponse.getBody().getJSONArray("Result").getJSONObject(0)
		                                   .getString("SalaryAmount"));
		Assert.assertEquals(connectorProperties.getProperty("testComment"),
		                    apiRestResponse.getBody().getJSONArray("Result").getJSONObject(0)
		                                   .getString("Comments"));
	}

	/**
	 * Negative test case for createSalary method.
	 */
	@Test(groups = { "wso2.esb" }, dependsOnMethods = {
			"testCreateSalaryWithOptionalParameters" },
			description = "peoplehr {createSalary} integration test with negative case.")
	public void testCreateSalaryWithNegativeCase() throws IOException, JSONException {

		esbRequestHeadersMap.put("Action", "urn:createSalary");

		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
				                    "esb_createSalary_negative.json");

		String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/Salary";

		RestResponse<JSONObject> apiRestResponse =
				sendJsonRestRequest(apiEndPoint, "POST", apiRequestHeadersMap,
				                    "api_createSalary_negative.json");

		Assert.assertEquals(esbRestResponse.getBody().getString("isError"),
		                    apiRestResponse.getBody().getString("isError"));
	}

	/**
	 * Positive test case for getSalary method with mandatory parameters.
	 */
	@Test(groups = { "wso2.esb" }, dependsOnMethods = {
			"testCreateSalaryWithMandatoryParameters" },
			description = "peoplehr {getSalary} integration test with mandatory parameters.")
	public void testGetSalaryWithMandatoryParameters() throws IOException, JSONException {

		esbRequestHeadersMap.put("Action", "urn:getSalary");

		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
				                    "esb_getSalary_mandatory.json");

		String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/Salary";

		RestResponse<JSONObject> apiRestResponse =
				sendJsonRestRequest(apiEndPoint, "POST", apiRequestHeadersMap,
				                    "api_getSalary_mandatory.json");

		Assert.assertFalse(Boolean.parseBoolean(esbRestResponse.getBody().getString("isError")));
		Assert.assertFalse(Boolean.parseBoolean(apiRestResponse.getBody().getString("isError")));
		Assert.assertEquals(esbRestResponse.getBody().getJSONArray("Result").getJSONObject(0)
		                                   .getString("SalaryType"),
		                    apiRestResponse.getBody().getJSONArray("Result").getJSONObject(0)
		                                   .getString("SalaryType"));
		Assert.assertEquals(esbRestResponse.getBody().getJSONArray("Result").getJSONObject(0)
		                                   .getString("EffectiveFrom"),
		                    apiRestResponse.getBody().getJSONArray("Result").getJSONObject(0)
		                                   .getString("EffectiveFrom"));
	}

	/**
	 * Negative test case for getSalary method.
	 */
	@Test(groups = {
			"wso2.esb" }, description = "peoplehr {getSalary} integration test with negative case.")
	public void testGetSalaryNegativeCase() throws IOException, JSONException {

		esbRequestHeadersMap.put("Action", "urn:getSalary");

		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
				                    "esb_getSalary_negative.json");

		String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/Salary";

		RestResponse<JSONObject> apiRestResponse =
				sendJsonRestRequest(apiEndPoint, "POST", apiRequestHeadersMap,
				                    "api_getSalary_negative.json");

		Assert.assertTrue(Boolean.parseBoolean(esbRestResponse.getBody().getString("isError")));
		Assert.assertTrue(Boolean.parseBoolean(apiRestResponse.getBody().getString("isError")));
	}

	/**
	 * Positive test case for deleteSalary method.
	 */
	@Test(priority = 5, groups = { "wso2.esb" }, dependsOnMethods = {
			"testCreateSalaryWithOptionalParameters" }, description = "peoplehr {deleteSalary}" +
	                                                                  " integration positive case.")
	public void testDeleteSalaryWithMandatoryParameters() throws IOException, JSONException {

		esbRequestHeadersMap.put("Action", "urn:deleteSalary");

		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
				                    "esb_deleteSalary_mandatory.json");
		Assert.assertEquals(esbRestResponse.getBody().getString("isError"), "false");
	}

	/**
	 * Positive test case for createAbsenceRecord method with mandatory parameters.
	 */
	@Test(groups = { "wso2.esb" }, dependsOnMethods = {
			"testCreateEmployeeWithMandatoryParameters" }, description =
			"peoplehr {createAbsenceRecord} integration test with mandatory parameters.")
	public void testCreateAbsenceRecordWithMandatoryParameters() throws IOException, JSONException {

		esbRequestHeadersMap.put("Action", "urn:createAbsenceRecord");

		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
				                    "esb_createAbsenceRecord_mandatory.json");

		String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/Absence";

		RestResponse<JSONObject> apiRestResponse =
				sendJsonRestRequest(apiEndPoint, "POST", apiRequestHeadersMap,
				                    "api_createAbsenceRecord_mandatory.json");

		Assert.assertFalse(Boolean.parseBoolean(esbRestResponse.getBody().getString("isError")));
		Assert.assertEquals(connectorProperties.getProperty("leaveDate"),
		                    apiRestResponse.getBody().getJSONArray("Result").getJSONObject(0)
		                                   .getString("StartDate"));
		Assert.assertEquals(connectorProperties.getProperty("leaveDate"),
		                    apiRestResponse.getBody().getJSONArray("Result").getJSONObject(0)
		                                   .getString("EndDate"));
	}

	/**
	 * Positive test case for createAbsenceRecord method with optional parameters.
	 */
	@Test(groups = { "wso2.esb" }, dependsOnMethods = {
			"testCreateEmployeeWithOptionalParameters" }, description =
			"peoplehr {createAbsenceRecord} integration test with optional parameters.")
	public void testCreateAbsenceRecordWithOptionalParameters() throws IOException, JSONException {

		esbRequestHeadersMap.put("Action", "urn:createAbsenceRecord");

		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
				                    "esb_createAbsenceRecord_optional.json");

		String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/Absence";

		RestResponse<JSONObject> apiRestResponse =
				sendJsonRestRequest(apiEndPoint, "POST", apiRequestHeadersMap,
				                    "api_createAbsenceRecord_optional.json");

		Assert.assertFalse(Boolean.parseBoolean(esbRestResponse.getBody().getString("isError")));
		Assert.assertEquals(connectorProperties.getProperty("leavePaidStatus"),
		                    apiRestResponse.getBody().getJSONArray("Result").getJSONObject(0)
		                                   .getString("AbsencePaidStatus"));
		Assert.assertEquals(connectorProperties.getProperty("testComment"),
		                    apiRestResponse.getBody().getJSONArray("Result").getJSONObject(0)
		                                   .getJSONArray("Comments").getJSONObject(0)
		                                   .getString("Comments"));
	}

	/**
	 * Negative test case for createAbsenceRecord method.
	 */
	@Test(groups = { "wso2.esb" }, description =
			"peoplehr {createAbsenceRecord} integration test with " + "negative case.")
	public void testCreateAbsenceRecordNegativeCase() throws IOException, JSONException {

		esbRequestHeadersMap.put("Action", "urn:createAbsenceRecord");

		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
				                    "esb_createAbsenceRecord_negative.json");

		String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/Absence";

		RestResponse<JSONObject> apiRestResponse =
				sendJsonRestRequest(apiEndPoint, "POST", apiRequestHeadersMap,
				                    "api_createAbsenceRecord_negative.json");

		Assert.assertTrue(Boolean.parseBoolean(esbRestResponse.getBody().getString("isError")));
		Assert.assertTrue(Boolean.parseBoolean(apiRestResponse.getBody().getString("isError")));
	}

	/**
	 * Positive test case for getAbsenceRecord method with mandatory parameters.
	 */
	@Test(groups = { "wso2.esb" }, dependsOnMethods = {
			"testCreateAbsenceRecordWithMandatoryParameters" },
			description = "peoplehr {getAbsenceRecord} integration test with mandatory parameters.")
	public void testGetAbsenceRecordWithMandatoryParameters() throws IOException, JSONException {

		esbRequestHeadersMap.put("Action", "urn:getAbsenceRecord");

		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
				                    "esb_getAbsenceRecord_mandatory.json");

		String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/Absence";

		RestResponse<JSONObject> apiRestResponse =
				sendJsonRestRequest(apiEndPoint, "POST", apiRequestHeadersMap,
				                    "api_getAbsenceRecord_mandatory.json");

		Assert.assertFalse(Boolean.parseBoolean(esbRestResponse.getBody().getString("isError")));
		Assert.assertEquals(esbRestResponse.getBody().get("Message"),
		                    apiRestResponse.getBody().get("Message"));
		Assert.assertEquals(esbRestResponse.getBody().get("Status"),
		                    apiRestResponse.getBody().get("Status"));
	}

	/**
	 * Positive test case for getAbsenceRecord method with optional parameters.
	 */
	@Test(groups = { "wso2.esb" }, dependsOnMethods = {
			"testCreateAbsenceRecordWithOptionalParameters" },
			description = "peoplehr {getAbsenceRecord} integration test with optional parameters.")
	public void testGetAbsenceRecordWithOptionalParameters() throws IOException, JSONException {

		esbRequestHeadersMap.put("Action", "urn:getAbsenceRecord");

		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
				                    "esb_getAbsenceRecord_optional.json");

		String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/Absence";

		RestResponse<JSONObject> apiRestResponse =
				sendJsonRestRequest(apiEndPoint, "POST", apiRequestHeadersMap,
				                    "api_getAbsenceRecord_optional.json");

		Assert.assertFalse(Boolean.parseBoolean(esbRestResponse.getBody().getString("isError")));
		Assert.assertEquals(esbRestResponse.getBody().get("Message"),
		                    apiRestResponse.getBody().get("Message"));
		Assert.assertEquals(esbRestResponse.getBody().get("Status"),
		                    apiRestResponse.getBody().get("Status"));
		Assert.assertEquals(
				esbRestResponse.getBody().getJSONArray("Result").getJSONObject(0).get("StartDate"),
				connectorProperties.get("leaveDate"));
	}

	/**
	 * Negative test case for getAbsenceRecord method.
	 */
	@Test(groups = { "wso2.esb" }, description =
			"peoplehr {getAbsenceRecord} integration test with negative" + " case.")
	public void testGetAbsenceRecordWithNegativeCase() throws IOException, JSONException {

		esbRequestHeadersMap.put("Action", "urn:getAbsenceRecord");

		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
				                    "esb_getAbsenceRecord_negative.json");

		String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/Absence";

		RestResponse<JSONObject> apiRestResponse =
				sendJsonRestRequest(apiEndPoint, "POST", apiRequestHeadersMap,
				                    "api_getAbsenceRecord_negative.json");

		Assert.assertTrue(Boolean.parseBoolean(esbRestResponse.getBody().getString("isError")));
	}

	/**
	 * Positive test case for updateAbsenceRecord method with mandatory parameters.
	 */
	@Test(priority = 4, groups = { "wso2.esb" }, dependsOnMethods = {
			"testCreateAbsenceRecordWithMandatoryParameters" },
			description = "peoplehr {updateAbsenceRecord} integration test with mandatory parameters.")
	public void testUpdateAbsenceRecordWithMandatoryParameters() throws IOException, JSONException {

		esbRequestHeadersMap.put("Action", "urn:updateAbsenceRecord");

		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
				                    "esb_updateAbsenceRecord_mandatory.json");

		String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/Absence";

		RestResponse<JSONObject> apiRestResponse =
				sendJsonRestRequest(apiEndPoint, "POST", apiRequestHeadersMap,
				                    "api_updateAbsenceRecord_mandatory.json");

		Assert.assertFalse(Boolean.parseBoolean(esbRestResponse.getBody().getString("isError")));
		Assert.assertEquals(esbRestResponse.getBody().get("Status"), 0);
		Assert.assertEquals(connectorProperties.get("leaveDate"),
		                    apiRestResponse.getBody().getJSONArray("Result").getJSONObject(0)
		                                   .get("StartDate"));
	}

	/**
	 * Positive test case for updateAbsenceRecord method with optional parameters.
	 */
	@Test(priority = 4, groups = { "wso2.esb" }, dependsOnMethods = {
			"testCreateAbsenceRecordWithOptionalParameters" },
			description = "peoplehr {updateAbsenceRecord} integration test with optional parameters.")
	public void testUpdateAbsenceRecordWithOptionalParameters() throws IOException, JSONException {

		esbRequestHeadersMap.put("Action", "urn:updateAbsenceRecord");

		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
				                    "esb_updateAbsenceRecord_optional.json");

		String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/Absence";

		RestResponse<JSONObject> apiRestResponse =
				sendJsonRestRequest(apiEndPoint, "POST", apiRequestHeadersMap,
				                    "api_updateAbsenceRecord_optional.json");

		Assert.assertFalse(Boolean.parseBoolean(esbRestResponse.getBody().getString("isError")));
		Assert.assertEquals(esbRestResponse.getBody().get("Status"), 0);
		Assert.assertEquals(connectorProperties.get("leaveDate"),
		                    apiRestResponse.getBody().getJSONArray("Result").getJSONObject(0)
		                                   .get("StartDate"));
		Assert.assertEquals(connectorProperties.get("testComment"),
		                    apiRestResponse.getBody().getJSONArray("Result").getJSONObject(0)
		                                   .getJSONArray("Comments").getJSONObject(0)
		                                   .get("Comments"));
	}

	/**
	 * Negative test case for updateAbsenceRecord method.
	 */
	@Test(groups = { "wso2.esb" }, description =
			"peoplehr {updateAbsenceRecord} integration test with negative " + "case.")
	public void testUpdateAbsenceRecordWithNegativeCase() throws IOException, JSONException {

		esbRequestHeadersMap.put("Action", "urn:updateAbsenceRecord");

		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
				                    "esb_updateAbsenceRecord_negative.json");

		String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/Absence";

		RestResponse<JSONObject> apiRestResponse =
				sendJsonRestRequest(apiEndPoint, "POST", apiRequestHeadersMap,
				                    "api_updateAbsenceRecord_negative.json");

		Assert.assertTrue(Boolean.parseBoolean(esbRestResponse.getBody().getString("isError")));
	}

	/**
	 * Positive test case for deleteAbsence method.
	 */
	@Test(priority = 5, groups = { "wso2.esb" }, dependsOnMethods = {
			"testUpdateAbsenceRecordWithOptionalParameters" }, description =
			"peoplehr {deleteAbsence}" + " integration positive case.")
	public void testDeleteAbsenceWithMandatoryParameters() throws IOException, JSONException {

		esbRequestHeadersMap.put("Action", "urn:deleteAbsence");

		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
				                    "esb_deleteAbsence_mandatory.json");
		Assert.assertEquals(esbRestResponse.getBody().getString("isError"), "false");
	}

	/**
	 * Positive test case for query method with mandatory parameters.
	 */
	@Test(groups = {
			"wso2.esb" }, description = "peoplehr {query} integration test with mandatory" +
	                                    " parameters.")
	public void testQueryWithMandatoryParameters() throws IOException, JSONException {

		esbRequestHeadersMap.put("Action", "urn:query");

		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
				                    "esbQueryWithMandatory.json");
		String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/Query";
		RestResponse<JSONObject> apiRestResponse =
				sendJsonRestRequest(apiEndPoint, "POST", apiRequestHeadersMap,
				                    "apiQueryWithMandatory.json");
		Assert.assertFalse(Boolean.parseBoolean(esbRestResponse.getBody().getString("isError")));
		Assert.assertEquals(esbRestResponse.getBody().get("Status"), 0);
		Assert.assertEquals(esbRestResponse.getBody().get("Message"),
		                    apiRestResponse.getBody().get("Message"));
	}

	/**
	 * Negative test case for query method.
	 */
	@Test(groups = {
			"wso2.esb" }, description = "peoplehr {query} integration test with negative case.")
	public void testQueryWithNegativeCase() throws IOException, JSONException {

		esbRequestHeadersMap.put("Action", "urn:query");

		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
				                    "esbQueryWithNegative.json");
		String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/Query";
		RestResponse<JSONObject> apiRestResponse =
				sendJsonRestRequest(apiEndPoint, "POST", apiRequestHeadersMap,
				                    "apiQueryWithNegative.json");
		Assert.assertTrue(Boolean.parseBoolean(esbRestResponse.getBody().getString("isError")));
	}

	/**
	 * Positive test case for getHistoryByEmployeeIdAndFieldName method with mandatory parameters.
	 */
	@Test(priority = 2, groups = { "wso2.esb" }, description =
			"peoplehr {getHistoryByEmployeeIdAndFieldName} " +
			"integration test with mandatory parameters.")
	public void testGetHistoryByEmployeeIdAndFieldNameWithMandatoryParameters()
			throws IOException, JSONException {

		esbRequestHeadersMap.put("Action", "urn:getHistoryByEmployeeIdAndFieldName");

		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
				                    "esb_getHistoryByEmployeeIdAndFieldName_mandatory.json");
		String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/History";
		RestResponse<JSONObject> apiRestResponse =
				sendJsonRestRequest(apiEndPoint, "POST", apiRequestHeadersMap,
				                    "api_getHistoryByEmployeeIdAndFieldName_mandatory.json");
		Assert.assertFalse(Boolean.parseBoolean(esbRestResponse.getBody().getString("isError")));
		Assert.assertEquals(esbRestResponse.getBody().get("Status"), 0);
		Assert.assertEquals(esbRestResponse.getBody().get("Message"),
		                    apiRestResponse.getBody().get("Message"));
	}

	/**
	 * Negative test case for getHistoryByEmployeeIdAndFieldName method.
	 */
	@Test(groups = { "wso2.esb" }, description = "peoplehr {getHistoryByEmployeeIdAndFieldName} " +
	                                             "integration test with negative case.")
	public void testGetHistoryByEmployeeIdAndFieldNameWithNegativeCase()
			throws IOException, JSONException {

		esbRequestHeadersMap.put("Action", "urn:getHistoryByEmployeeIdAndFieldName");

		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
				                    "esb_getHistoryByEmployeeIdAndFieldName_negative.json");
		String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/History";
		RestResponse<JSONObject> apiRestResponse =
				sendJsonRestRequest(apiEndPoint, "POST", apiRequestHeadersMap,
				                    "api_getHistoryByEmployeeIdAndFieldName_negative.json");
		Assert.assertTrue(Boolean.parseBoolean(esbRestResponse.getBody().getString("isError")));
	}

	/**
	 * Positive test case for createHoliday method with mandatory parameters.
	 */
	@Test(groups = { "wso2.esb" }, dependsOnMethods = {
			"testCreateEmployeeWithMandatoryParameters" }, description =
			"peoplehr {createHoliday} " + "integration test with mandatory parameters.")
	public void testCreateHolidayWithMandatoryParameters() throws IOException, JSONException {

		esbRequestHeadersMap.put("Action", "urn:createHoliday");

		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
				                    "esb_createHoliday_mandatory.json");
		String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/Holiday";
		RestResponse<JSONObject> apiRestResponse =
				sendJsonRestRequest(apiEndPoint, "POST", apiRequestHeadersMap,
				                    "api_getHolidayDetail_mandatory.json");
		Assert.assertFalse(Boolean.parseBoolean(esbRestResponse.getBody().getString("isError")));

		Assert.assertFalse(Boolean.parseBoolean(esbRestResponse.getBody().getString("isError")));
		Assert.assertEquals(esbRestResponse.getBody().get("Status"), 0);
		Assert.assertEquals(connectorProperties.get("startHolidayDate"),
		                    apiRestResponse.getBody().getJSONArray("Result").getJSONObject(0)
		                                   .get("StartDate"));
		Assert.assertEquals(connectorProperties.get("endHolidayDate"),
		                    apiRestResponse.getBody().getJSONArray("Result").getJSONObject(0)
		                                   .get("EndDate"));
	}

	/**
	 * Positive test case for createHoliday method with optional parameters.
	 */
	@Test(groups = { "wso2.esb" }, dependsOnMethods = {
			"testCreateEmployeeWithMandatoryParameters" }, description =
			"peoplehr {createHoliday} " + "integration test with optional parameters.")
	public void testCreateHolidayWithOptionalParameters() throws IOException, JSONException {

		esbRequestHeadersMap.put("Action", "urn:createHoliday");

		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
				                    "esb_createHoliday_optional.json");
		String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/Holiday";
		RestResponse<JSONObject> apiRestResponse =
				sendJsonRestRequest(apiEndPoint, "POST", apiRequestHeadersMap,
				                    "api_getHolidayDetail_optional.json");
		Assert.assertFalse(Boolean.parseBoolean(esbRestResponse.getBody().getString("isError")));

		Assert.assertFalse(Boolean.parseBoolean(esbRestResponse.getBody().getString("isError")));
		Assert.assertEquals(esbRestResponse.getBody().get("Status"), 0);
		Assert.assertEquals(connectorProperties.get("leaveDateOpt"),
		                    apiRestResponse.getBody().getJSONArray("Result").getJSONObject(0)
		                                   .get("StartDate"));
		Assert.assertEquals(connectorProperties.get("leaveDateOpt"),
		                    apiRestResponse.getBody().getJSONArray("Result").getJSONObject(0)
		                                   .get("EndDate"));
	}

	/**
	 * Negative test case for createHoliday method.
	 */
	@Test(groups = { "wso2.esb" }, description = "peoplehr {createHoliday} " +
	                                             "integration test with negative case.")
	public void testCreateHolidayWithNegativeCase() throws IOException, JSONException {

		esbRequestHeadersMap.put("Action", "urn:getHistoryByEmployeeIdAndFieldName");

		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
				                    "esb_createHoliday_negative.json");

		Assert.assertTrue(Boolean.parseBoolean(esbRestResponse.getBody().getString("isError")));
	}

	/**
	 * Positive test case for getHolidayDetail method with mandatory parameters.
	 */
	@Test(priority = 2, groups = { "wso2.esb" }, dependsOnMethods = {
			"testCreateHolidayWithMandatoryParameters" }, description =
			"peoplehr {getHolidayDetail} " +
			"integration test with mandatory " +
			"parameters.")
	public void testGetHolidayDetailWithMandatoryParameters() throws IOException, JSONException {

		esbRequestHeadersMap.put("Action", "urn:getHolidayDetail");

		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
				                    "esb_getHolidayDetail_mandatory.json");

		String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/Holiday";

		RestResponse<JSONObject> apiRestResponse =
				sendJsonRestRequest(apiEndPoint, "POST", apiRequestHeadersMap,
				                    "api_getHolidayDetail_mandatory.json");

		Assert.assertFalse(Boolean.parseBoolean(esbRestResponse.getBody().getString("isError")));
		Assert.assertEquals(esbRestResponse.getBody().get("Message"),
		                    apiRestResponse.getBody().get("Message"));
		Assert.assertEquals(esbRestResponse.getBody().get("Status"),
		                    apiRestResponse.getBody().get("Status"));
	}

	/**
	 * Negative test case for getHolidayDetail method.
	 */
	@Test(groups = { "wso2.esb" }, description = "peoplehr {getHolidayDetail} " +
	                                             "integration test with negative case.")
	public void testGetHolidayDetailWithNegativeCase() throws IOException, JSONException {

		esbRequestHeadersMap.put("Action", "urn:getHistoryByEmployeeIdAndFieldName");

		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
				                    "esb_getHolidayDetail_negative.json");

		Assert.assertTrue(Boolean.parseBoolean(esbRestResponse.getBody().getString("isError")));
	}

	/**
	 * Positive test case for updateHoliday method with mandatory parameters.
	 */
	@Test(priority = 4, groups = { "wso2.esb" }, dependsOnMethods = {
			"testCreateHolidayWithMandatoryParameters" }, description =
			"peoplehr {updateHoliday} " + "integration test with mandatory parameters.")
	public void testUpdateHolidayWithMandatoryParameters() throws IOException, JSONException {

		esbRequestHeadersMap.put("Action", "urn:updateHoliday");

		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
				                    "esb_updateHoliday_mandatory.json");

		String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/Holiday";

		RestResponse<JSONObject> apiRestResponse =
				sendJsonRestRequest(apiEndPoint, "POST", apiRequestHeadersMap,
				                    "api_updateHoliday_mandatory.json");

		Assert.assertFalse(Boolean.parseBoolean(esbRestResponse.getBody().getString("isError")));
		Assert.assertEquals(esbRestResponse.getBody().get("Status"), 0);
		Assert.assertEquals(connectorProperties.get("updatedStartHolidayDate"),
		                    apiRestResponse.getBody().getJSONArray("Result").getJSONObject(0)
		                                   .get("StartDate"));
	}

	/**
	 * Positive test case for updateHoliday method with optional parameters.
	 */
	@Test(priority = 4, groups = { "wso2.esb" }, dependsOnMethods = {
			"testCreateHolidayWithOptionalParameters" },
			description = "peoplehr {updateHoliday} integration test with optional parameters.")
	public void testUpdateHolidayWithOptionalParameters() throws IOException, JSONException {

		esbRequestHeadersMap.put("Action", "urn:updateHoliday");

		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
				                    "esb_updateHoliday_optional.json");

		String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/Holiday";

		RestResponse<JSONObject> apiRestResponse =
				sendJsonRestRequest(apiEndPoint, "POST", apiRequestHeadersMap,
				                    "api_updateHoliday_optional.json");

		Assert.assertFalse(Boolean.parseBoolean(esbRestResponse.getBody().getString("isError")));
		Assert.assertEquals(esbRestResponse.getBody().get("Status"), 0);
		Assert.assertEquals(connectorProperties.get("updatedDate"),
		                    apiRestResponse.getBody().getJSONArray("Result").getJSONObject(0)
		                                   .get("StartDate"));
		Assert.assertEquals(
				apiRestResponse.getBody().getJSONArray("Result").getJSONObject(0).get("PartOfDay"),
				"AM");
	}

	/**
	 * Negative test case for updateHoliday method.
	 */
	@Test(groups = { "wso2.esb" }, description = "peoplehr {updateHoliday} integration test with " +
	                                             "negative case.")
	public void testUpdateHolidayWithNegativeCase() throws IOException, JSONException {

		esbRequestHeadersMap.put("Action", "urn:updateHoliday");

		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
				                    "esb_updateHoliday_negative.json");

		Assert.assertTrue(Boolean.parseBoolean(esbRestResponse.getBody().getString("isError")));
	}

	/**
	 * Positive test case for deleteHoliday method.
	 */
	@Test(priority = 5, groups = { "wso2.esb" }, dependsOnMethods = {
			"testUpdateHolidayWithOptionalParameters" },
			description = "peoplehr {deleteHoliday} integration positive case.")
	public void testDeleteHolidayWithMandatoryParameters() throws IOException, JSONException {

		esbRequestHeadersMap.put("Action", "urn:deleteHoliday");

		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
				                    "esb_deleteHoliday_mandatory.json");
		Assert.assertEquals(esbRestResponse.getBody().getString("isError"), "false");
	}

	/**
	 * Positive test case for getHolidayEntitlement method with mandatory parameters.
	 */
	@Test(priority = 3, groups = {
			"wso2.esb" }, description = "peoplehr {getHolidayEntitlement} integration test with " +
	                                    "mandatory parameters.")
	public void testGetHolidayEntitlementWithMandatoryParameters()
			throws IOException, JSONException {

		esbRequestHeadersMap.put("Action", "urn:getHolidayEntitlement");

		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
				                    "esb_getHolidayEntitlement_mandatory.json");

		String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/HolidayEntitlements";

		RestResponse<JSONObject> apiRestResponse =
				sendJsonRestRequest(apiEndPoint, "POST", apiRequestHeadersMap,
				                    "api_getHolidayEntitlement_mandatory.json");

		Assert.assertFalse(Boolean.parseBoolean(esbRestResponse.getBody().getString("isError")));
		Assert.assertEquals(esbRestResponse.getBody().get("Message"),
		                    apiRestResponse.getBody().get("Message"));
		Assert.assertEquals(esbRestResponse.getBody().get("Status"),
		                    apiRestResponse.getBody().get("Status"));
	}

	/**
	 * Negative test case for getHolidayEntitlement method.
	 */
	@Test(groups = { "wso2.esb" }, description = "peoplehr {getHolidayEntitlement} " +
	                                             "integration test with negative case.")
	public void testGetHolidayEntitlementWithNegativeCase() throws IOException, JSONException {

		esbRequestHeadersMap.put("Action", "urn:getHolidayEntitlement");

		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
				                    "esb_getHolidayEntitlement_negative.json");

		Assert.assertTrue(Boolean.parseBoolean(esbRestResponse.getBody().getString("isError")));
	}

	/**
	 * Positive test case for getNextYearHolidayEntitlement method with mandatory parameters.
	 */
	@Test(priority = 3, groups = { "wso2.esb" }, description =
			"peoplehr {getNextYearHolidayEntitlement} integration test " +
			"with mandatory parameters.")
	public void testGetNextYearHolidayEntitlementWithMandatoryParameters()
			throws IOException, JSONException {

		esbRequestHeadersMap.put("Action", "urn:getNextYearHolidayEntitlement");

		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
				                    "esb_getNextYearHolidayEntitlement_mandatory.json");

		String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/HolidayEntitlements";

		RestResponse<JSONObject> apiRestResponse =
				sendJsonRestRequest(apiEndPoint, "POST", apiRequestHeadersMap,
				                    "api_getNextYearHolidayEntitlement_mandatory.json");

		Assert.assertFalse(Boolean.parseBoolean(esbRestResponse.getBody().getString("isError")));
		Assert.assertEquals(esbRestResponse.getBody().get("Message"),
		                    apiRestResponse.getBody().get("Message"));
		Assert.assertEquals(esbRestResponse.getBody().get("Status"),
		                    apiRestResponse.getBody().get("Status"));
	}

	/**
	 * Negative test case for getNextYearHolidayEntitlement method.
	 */
	@Test(groups = { "wso2.esb" }, description = "peoplehr {getNextYearHolidayEntitlement} " +
	                                             "integration test with negative case.")
	public void testGetNextYearHolidayEntitlementWithNegativeCase()
			throws IOException, JSONException {

		esbRequestHeadersMap.put("Action", "urn:getNextYearHolidayEntitlement");

		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
				                    "esb_getNextYearHolidayEntitlement_negative.json");

		Assert.assertTrue(Boolean.parseBoolean(esbRestResponse.getBody().getString("isError")));
	}

	/**
	 * Positive test case for updateHolidayEntitlement method with mandatory parameters.
	 */
	@Test(priority = 5, groups = { "wso2.esb" }, description =
			"peoplehr {updateHolidayEntitlement} " + "integration test with mandatory parameters.")
	public void testUpdateHolidayEntitlementWithMandatoryParameters()
			throws IOException, JSONException {

		esbRequestHeadersMap.put("Action", "urn:updateHolidayEntitlement");

		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
				                    "esb_updateHolidayEntitlement_mandatory.json");

		String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/HolidayEntitlements";

		RestResponse<JSONObject> apiRestResponse =
				sendJsonRestRequest(apiEndPoint, "POST", apiRequestHeadersMap,
				                    "api_getHolidayEntitlement_mandatory.json");

		Assert.assertFalse(Boolean.parseBoolean(esbRestResponse.getBody().getString("isError")));
		Assert.assertEquals(esbRestResponse.getBody().get("Status"), 0);
		Assert.assertEquals(connectorProperties.get("entitlementThisYear"),
		                    apiRestResponse.getBody().getJSONArray("Result").getJSONObject(0)
		                                   .get("Entitlement"));
	}

	/**
	 * Negative test case for updateHolidayEntitlement method.
	 */
	@Test(groups = { "wso2.esb" }, description = "peoplehr {updateHolidayEntitlement} " +
	                                             "integration test with negative case.")
	public void testUpdateHolidayEntitlementWithNegativeCase() throws IOException, JSONException {

		esbRequestHeadersMap.put("Action", "urn:updateHolidayEntitlement");

		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
				                    "esb_updateHolidayEntitlement_negative.json");

		Assert.assertTrue(Boolean.parseBoolean(esbRestResponse.getBody().getString("isError")));
	}

	/**
	 * Positive test case for updateNextYearHolidayEntitlement method with mandatory parameters.
	 */
	@Test(priority = 5, groups = { "wso2.esb" }, description =
			"peoplehr {updateNextYearHolidayEntitlement} " +
			"integration test with mandatory parameters.")
	public void testUpdateNextYearHolidayEntitlementWithMandatoryParameters()
			throws IOException, JSONException {

		esbRequestHeadersMap.put("Action", "urn:updateNextYearHolidayEntitlement");

		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
				                    "esb_updateNextYearHolidayEntitlement_mandatory.json");

		String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/HolidayEntitlements";

		RestResponse<JSONObject> apiRestResponse =
				sendJsonRestRequest(apiEndPoint, "POST", apiRequestHeadersMap,
				                    "api_getNextYearHolidayEntitlement_mandatory.json");

		Assert.assertFalse(Boolean.parseBoolean(esbRestResponse.getBody().getString("isError")));
		Assert.assertEquals(esbRestResponse.getBody().get("Status"), 0);
	}

	/**
	 * Negative test case for updateNextYearHolidayEntitlement method.
	 */
	@Test(groups = { "wso2.esb" }, description = "peoplehr {updateNextYearHolidayEntitlement} " +
	                                             "integration test with negative case.")
	public void testUpdateNextYearHolidayEntitlementWithNegativeCase()
			throws IOException, JSONException {

		esbRequestHeadersMap.put("Action", "urn:updateNextYearHolidayEntitlement");

		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
				                    "esb_updateNextYearHolidayEntitlement.json");

		Assert.assertTrue(Boolean.parseBoolean(esbRestResponse.getBody().getString("isError")));
	}

	/**
	 * Positive test case for getAllDocument method with mandatory parameters.
	 */
	@Test(priority = 3, groups = { "wso2.esb" }, dependsOnMethods = {
			"testUploadEmployeeDocumentWithMandatoryParameters" },
			description = "peoplehr {getAllDocument} integration test with mandatory parameters.")
	public void testGetAllDocumentWithMandatoryParameters() throws IOException, JSONException {

		esbRequestHeadersMap.put("Action", "urn:getAllDocument");

		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
				                    "esb_getAllDocument_mandatory.json");

		String documentId = esbRestResponse.getBody().getJSONArray("Result").getJSONObject(0)
		                                   .getString("DocumentId");

		connectorProperties.put("documentId", documentId);

		String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/EmployeeDocument";

		RestResponse<JSONObject> apiRestResponse =
				sendJsonRestRequest(apiEndPoint, "POST", apiRequestHeadersMap,
				                    "api_getAllDocument_mandatory.json");

		Assert.assertFalse(Boolean.parseBoolean(esbRestResponse.getBody().getString("isError")));
		Assert.assertEquals(esbRestResponse.getBody().get("Message"),
		                    apiRestResponse.getBody().get("Message"));
		Assert.assertEquals(esbRestResponse.getBody().get("Status"),
		                    apiRestResponse.getBody().get("Status"));
	}

	/**
	 * Negative test case for getAllDocument method.
	 */
	@Test(groups = { "wso2.esb" }, description = "peoplehr {getAllDocument} " +
	                                             "integration test with negative case.")
	public void testGetAllDocumentWithNegativeCase() throws IOException, JSONException {

		esbRequestHeadersMap.put("Action", "urn:getAllDocument");

		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
				                    "esb_getAllDocument_negative.json");

		Assert.assertTrue(Boolean.parseBoolean(esbRestResponse.getBody().getString("isError")));
	}

	/**
	 * Positive test case for getDocumentById method with mandatory parameters.
	 */
	@Test(priority = 3, groups = { "wso2.esb" }, dependsOnMethods = {
			"testGetAllDocumentWithMandatoryParameters" }, description =
			"peoplehr {getDocumentById}integration test with mandatory parameters.")
	public void testGetDocumentByIdWithMandatoryParameters() throws IOException, JSONException {

		esbRequestHeadersMap.put("Action", "urn:getDocumentById");

		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
				                    "esb_getDocumentById_mandatory.json");

		String apiEndPoint = connectorProperties.getProperty("apiUrl") + "/EmployeeDocument";

		RestResponse<JSONObject> apiRestResponse =
				sendJsonRestRequest(apiEndPoint, "POST", apiRequestHeadersMap,
				                    "api_getDocumentById_mandatory.json");

		Assert.assertFalse(Boolean.parseBoolean(esbRestResponse.getBody().getString("isError")));
		Assert.assertEquals(esbRestResponse.getBody().get("Message"),
		                    apiRestResponse.getBody().get("Message"));
		Assert.assertEquals(esbRestResponse.getBody().get("Status"),
		                    apiRestResponse.getBody().get("Status"));
	}

	/**
	 * Negative test case for getDocumentById method.
	 */
	@Test(groups = { "wso2.esb" }, description = "peoplehr {getDocumentById} " +
	                                             "integration test with negative case.")
	public void testGetDocumentByIdWithNegativeCase() throws IOException, JSONException {

		esbRequestHeadersMap.put("Action", "urn:getDocumentById");

		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
				                    "esb_getDocumentById_negative.json");

		Assert.assertTrue(Boolean.parseBoolean(esbRestResponse.getBody().getString("isError")));
	}

	/**
	 * Positive test case for uploadEmployeeDocument method with mandatory parameters.
	 */
	@Test(priority = 2, groups = { "wso2.esb" }, description =
			"peoplehr {uploadEmployeeDocument} " + "integration test with mandatory parameters.")
	public void testUploadEmployeeDocumentWithMandatoryParameters()
			throws IOException, JSONException {

		esbRequestHeadersMap.put("Action", "urn:uploadEmployeeDocument");

		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
				                    "esb_uploadEmployeeDocument_mandatory.json");

		Assert.assertFalse(Boolean.parseBoolean(esbRestResponse.getBody().getString("isError")));
		Assert.assertEquals(esbRestResponse.getBody().get("Status"), 0);
	}

	/**
	 * Positive test case for uploadEmployeeDocument method with optional parameters.
	 */
	@Test(priority = 2, groups = { "wso2.esb" }, description =
			"peoplehr {uploadEmployeeDocument} " + "integration test with optional parameters.")
	public void testUploadEmployeeDocumentWithOptionalParameters()
			throws IOException, JSONException {

		esbRequestHeadersMap.put("Action", "urn:uploadEmployeeDocument");

		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
				                    "esb_uploadEmployeeDocument_optional.json");

		Assert.assertFalse(Boolean.parseBoolean(esbRestResponse.getBody().getString("isError")));
		Assert.assertEquals(esbRestResponse.getBody().get("Status"), 0);
	}

	/**
	 * Negative test case for uploadEmployeeDocument method.
	 */
	@Test(groups = { "wso2.esb" }, description = "peoplehr {uploadEmployeeDocument} " +
	                                             "integration test with negative case.")
	public void testUploadEmployeeDocumentWithNegativeCase() throws IOException, JSONException {

		esbRequestHeadersMap.put("Action", "urn:uploadEmployeeDocument");
		RestResponse<JSONObject> esbRestResponse =
				sendJsonRestRequest(proxyUrl, "POST", esbRequestHeadersMap,
				                    "esb_uploadEmployeeDocument_negative.json");

		Assert.assertTrue(Boolean.parseBoolean(esbRestResponse.getBody().getString("isError")));
	}
}
