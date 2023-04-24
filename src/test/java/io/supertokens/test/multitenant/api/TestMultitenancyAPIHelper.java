/*
 *    Copyright (c) 2023, VRAI Labs and/or its affiliates. All rights reserved.
 *
 *    This software is licensed under the Apache License, Version 2.0 (the
 *    "License") as published by the Apache Software Foundation.
 *
 *    You may not use this file except in compliance with the License. You may
 *    obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *    WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *    License for the specific language governing permissions and limitations
 *    under the License.
 */

package io.supertokens.test.multitenant.api;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.supertokens.Main;
import io.supertokens.pluginInterface.multitenancy.TenantIdentifier;
import io.supertokens.pluginInterface.multitenancy.ThirdPartyConfig;
import io.supertokens.test.Utils;
import io.supertokens.test.httpRequest.HttpRequestForTesting;
import io.supertokens.test.httpRequest.HttpResponseException;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class TestMultitenancyAPIHelper {
    public static void createConnectionUriDomain(Main main, TenantIdentifier sourceTenant, String connectionUriDomain, boolean emailPasswordEnabled,
                                             boolean thirdPartyEnabled, boolean passwordlessEnabled,
                                             JsonObject coreConfig) throws HttpResponseException, IOException {
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("connectionUriDomain", connectionUriDomain);
        requestBody.addProperty("emailPasswordEnabled", emailPasswordEnabled);
        requestBody.addProperty("thirdPartyEnabled", thirdPartyEnabled);
        requestBody.addProperty("passwordlessEnabled", passwordlessEnabled);
        requestBody.add("coreConfig", coreConfig);

        JsonObject response = HttpRequestForTesting.sendJsonPUTRequest(main, "",
                HttpRequestForTesting.getMultitenantUrl(sourceTenant, "/recipe/multitenancy/connectionuridomain"),
                requestBody, 1000, 1000, null,
                Utils.getCdiVersionStringLatestForTests(), "multitenancy");

        assertEquals("OK", response.getAsJsonPrimitive("status").getAsString());
    }

    public static JsonObject listConnectionUriDomains(TenantIdentifier sourceTenant, Main main)
            throws HttpResponseException, IOException {
        JsonObject response = HttpRequestForTesting.sendGETRequest(main, "",
                HttpRequestForTesting.getMultitenantUrl(sourceTenant, "/recipe/multitenancy/connectionuridomain/list"),
                null, 1000, 1000, null,
                Utils.getCdiVersionStringLatestForTests(), "multitenancy");

        assertEquals("OK", response.getAsJsonPrimitive("status").getAsString());
        return response;
    }

    public static JsonObject deleteConnectionUriDomain(TenantIdentifier sourceTenant, String connectionUriDomain,
                                                       Main main)
            throws HttpResponseException, IOException {
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("connectionUriDomain", connectionUriDomain);

        JsonObject response = HttpRequestForTesting.sendJsonPOSTRequest(main, "",
                HttpRequestForTesting.getMultitenantUrl(sourceTenant, "/recipe/multitenancy/connectionuridomain/remove"),
                requestBody, 1000, 1000, null,
                Utils.getCdiVersionStringLatestForTests(), "multitenancy");

        assertEquals("OK", response.getAsJsonPrimitive("status").getAsString());
        return response;
    }

    public static void createApp(Main main, TenantIdentifier sourceTenant, String appId, boolean emailPasswordEnabled,
                             boolean thirdPartyEnabled, boolean passwordlessEnabled,
                             JsonObject coreConfig) throws HttpResponseException, IOException {
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("appId", appId);
        requestBody.addProperty("emailPasswordEnabled", emailPasswordEnabled);
        requestBody.addProperty("thirdPartyEnabled", thirdPartyEnabled);
        requestBody.addProperty("passwordlessEnabled", passwordlessEnabled);
        requestBody.add("coreConfig", coreConfig);

        JsonObject response = HttpRequestForTesting.sendJsonPUTRequest(main, "",
                HttpRequestForTesting.getMultitenantUrl(sourceTenant, "/recipe/multitenancy/app"),
                requestBody, 1000, 1000, null,
                Utils.getCdiVersionStringLatestForTests(), "multitenancy");

        assertEquals("OK", response.getAsJsonPrimitive("status").getAsString());
    }

    public static JsonObject listApps(TenantIdentifier sourceTenant, Main main)
            throws HttpResponseException, IOException {
        JsonObject response = HttpRequestForTesting.sendGETRequest(main, "",
                HttpRequestForTesting.getMultitenantUrl(sourceTenant, "/recipe/multitenancy/app/list"),
                null, 1000, 1000, null,
                Utils.getCdiVersionStringLatestForTests(), "multitenancy");

        assertEquals("OK", response.getAsJsonPrimitive("status").getAsString());
        return response;
    }

    public static JsonObject deleteApp(TenantIdentifier sourceTenant, String appId, Main main)
            throws HttpResponseException, IOException {
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("appId", appId);

        JsonObject response = HttpRequestForTesting.sendJsonPOSTRequest(main, "",
                HttpRequestForTesting.getMultitenantUrl(sourceTenant, "/recipe/multitenancy/app/remove"),
                requestBody, 1000, 1000, null,
                Utils.getCdiVersionStringLatestForTests(), "multitenancy");

        assertEquals("OK", response.getAsJsonPrimitive("status").getAsString());
        return response;
    }

    public static void createTenant(Main main, TenantIdentifier sourceTenant, String tenantId, boolean emailPasswordEnabled,
                             boolean thirdPartyEnabled, boolean passwordlessEnabled,
                             JsonObject coreConfig) throws HttpResponseException, IOException {
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("tenantId", tenantId);
        requestBody.addProperty("emailPasswordEnabled", emailPasswordEnabled);
        requestBody.addProperty("thirdPartyEnabled", thirdPartyEnabled);
        requestBody.addProperty("passwordlessEnabled", passwordlessEnabled);
        requestBody.add("coreConfig", coreConfig);

        JsonObject response = HttpRequestForTesting.sendJsonPUTRequest(main, "",
                HttpRequestForTesting.getMultitenantUrl(sourceTenant, "/recipe/multitenancy/tenant"),
                requestBody, 1000, 1000, null,
                Utils.getCdiVersionStringLatestForTests(), "multitenancy");

        assertEquals("OK", response.getAsJsonPrimitive("status").getAsString());
    }

    public static JsonObject listTenants(TenantIdentifier sourceTenant, Main main)
            throws HttpResponseException, IOException {
        JsonObject response = HttpRequestForTesting.sendGETRequest(main, "",
                HttpRequestForTesting.getMultitenantUrl(sourceTenant, "/recipe/multitenancy/tenant/list"),
                null, 1000, 1000, null,
                Utils.getCdiVersionStringLatestForTests(), "multitenancy");

        assertEquals("OK", response.getAsJsonPrimitive("status").getAsString());
        return response;
    }

    public static JsonObject deleteTenant(TenantIdentifier sourceTenant, String tenantId, Main main)
            throws HttpResponseException, IOException {
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("tenantId", tenantId);

        JsonObject response = HttpRequestForTesting.sendJsonPOSTRequest(main, "",
                HttpRequestForTesting.getMultitenantUrl(sourceTenant, "/recipe/multitenancy/tenant/remove"),
                requestBody, 1000, 1000, null,
                Utils.getCdiVersionStringLatestForTests(), "multitenancy");

        assertEquals("OK", response.getAsJsonPrimitive("status").getAsString());
        return response;
    }

    public static JsonObject getTenant(TenantIdentifier tenantIdentifier, Main main)
            throws HttpResponseException, IOException {

        JsonObject response = HttpRequestForTesting.sendGETRequest(main, "",
                HttpRequestForTesting.getMultitenantUrl(tenantIdentifier, "/recipe/multitenancy/tenant"),
                null, 1000, 1000, null,
                Utils.getCdiVersionStringLatestForTests(), "multitenancy");

        assertEquals("OK", response.getAsJsonPrimitive("status").getAsString());
        return response;
    }

    public static JsonObject associateUserToTenant(TenantIdentifier tenantIdentifier, String userId, Main main)
            throws HttpResponseException, IOException {
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("userId", userId);

        JsonObject response = HttpRequestForTesting.sendJsonPOSTRequest(main, "",
                HttpRequestForTesting.getMultitenantUrl(tenantIdentifier, "/recipe/multitenancy/tenant/user"),
                requestBody, 1000, 1000, null,
                Utils.getCdiVersionStringLatestForTests(), "multitenancy");

        return response;
    }

    public static JsonObject disassociateUserFromTenant(TenantIdentifier tenantIdentifier, String userId, Main main)
            throws HttpResponseException, IOException {
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("userId", userId);

        JsonObject response = HttpRequestForTesting.sendJsonPOSTRequest(main, "",
                HttpRequestForTesting.getMultitenantUrl(tenantIdentifier, "/recipe/multitenancy/tenant/user/remove"),
                requestBody, 1000, 1000, null,
                Utils.getCdiVersionStringLatestForTests(), "multitenancy");

        assertEquals("OK", response.getAsJsonPrimitive("status").getAsString());
        return response;
    }

    public static JsonObject addOrUpdateThirdPartyProviderConfig(TenantIdentifier tenantIdentifier, ThirdPartyConfig.Provider provider, Main main)
            throws HttpResponseException, IOException {
        Gson gson = new Gson();
        JsonObject requestBody = gson.toJsonTree(provider).getAsJsonObject();

        JsonObject response = HttpRequestForTesting.sendJsonPUTRequest(main, "",
                HttpRequestForTesting.getMultitenantUrl(tenantIdentifier, "/recipe/multitenancy/config/thirdparty"),
                requestBody, 1000, 1000, null,
                Utils.getCdiVersionStringLatestForTests(), "multitenancy");

        assertEquals("OK", response.getAsJsonPrimitive("status").getAsString());
        return response;
    }

    public static JsonObject deleteThirdPartyProvider(TenantIdentifier tenantIdentifier, String thirdPartyId, Main main)
            throws HttpResponseException, IOException {
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("thirdPartyId", thirdPartyId);

        JsonObject response = HttpRequestForTesting.sendJsonPOSTRequest(main, "",
                HttpRequestForTesting.getMultitenantUrl(tenantIdentifier, "/recipe/multitenancy/config/thirdparty/remove"),
                requestBody, 1000, 1000, null,
                Utils.getCdiVersionStringLatestForTests(), "multitenancy");

        assertEquals("OK", response.getAsJsonPrimitive("status").getAsString());
        return response;
    }

    public static JsonObject epSignUp(TenantIdentifier tenantIdentifier, String email, String password, Main main)
            throws HttpResponseException, IOException {
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("email", email);
        requestBody.addProperty("password", password);
        JsonObject signUpResponse = HttpRequestForTesting.sendJsonPOSTRequest(main, "",
                HttpRequestForTesting.getMultitenantUrl(tenantIdentifier, "/recipe/signup"),
                requestBody, 1000, 1000, null,
                Utils.getCdiVersionStringLatestForTests(), "emailpassword");
        assertEquals("OK", signUpResponse.getAsJsonPrimitive("status").getAsString());
        return signUpResponse.getAsJsonObject("user");
    }
}
