/*
 *    Copyright (c) 2020, VRAI Labs and/or its affiliates. All rights reserved.
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

package io.supertokens.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.supertokens.Main;
import io.supertokens.cliOptions.CLIOptions;
import io.supertokens.exceptions.QuitProgramException;
import io.supertokens.utils.Utils;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CoreConfig {

    @JsonProperty
    private int core_config_version = -1;

    @JsonProperty
    private int access_token_validity = 3600; // in seconds

    @JsonProperty
    private boolean access_token_blacklisting = false;

    @JsonProperty
    private String access_token_path = "/";

    @JsonProperty
    private boolean enable_anti_csrf = false;

    @JsonProperty
    private double refresh_token_validity = 60 * 2400; // in mins

    @JsonProperty
    private String refresh_api_path = "/session/refresh";

    private final String logDefault = "asdkfahbdfk3kjHS";
    @JsonProperty
    private String info_log_path = logDefault;

    @JsonProperty
    private String error_log_path = logDefault;

    @JsonProperty
    private String cookie_domain = null; // default is null meaning that it gets set to whatever their API layer is.

    @JsonProperty
    private Boolean cookie_secure = false;

    @JsonProperty
    private String cookie_same_site = "lax";

    @JsonProperty
    private int port = 3567;

    @JsonProperty
    private String host = "localhost";

    @JsonProperty
    private int max_server_pool_size = 10;

    @JsonProperty
    private int session_expired_status_code = 401;

    //	TODO: add https in later version
//	# (OPTIONAL) boolean value (true or false). Set to true if you want to enable https requests to SuperTokens.
//	# If you are not running SuperTokens within a closed network along with your API process, for 
//	# example if you are using multiple cloud vendors, then it is recommended to set this to true.
//	# webserver_https_enabled:
    @JsonProperty
    private boolean webserver_https_enabled = false;

    public int getConfigVersion() {
        return core_config_version;
    }

    public long getAccessTokenValidity() {
        return access_token_validity * 1000;
    }

    public boolean getAccessTokenBlacklisting() {
        return access_token_blacklisting;
    }

    public String getAccessTokenPath() {
        return access_token_path;
    }

    public boolean getEnableAntiCSRF() {
        return enable_anti_csrf;
    }

    public long getRefreshTokenValidity() {
        return (long) (refresh_token_validity * 60 * 1000);
    }

    public String getRefreshAPIPath() {
        return refresh_api_path;
    }

    public String getCookieSameSite() {
        return cookie_same_site.toLowerCase();
    }

    public String getInfoLogPath(Main main) {
        if (info_log_path == null || info_log_path.equalsIgnoreCase("null")) {
            return "null";
        }
        if (info_log_path.equals(logDefault)) {
            // this works for windows as well
            return CLIOptions.get(main).getInstallationPath() + "logs/info.log";
        }
        return info_log_path;
    }

    public String getErrorLogPath(Main main) {
        if (error_log_path == null || error_log_path.equalsIgnoreCase("null")) {
            return "null";
        }
        if (error_log_path.equals(logDefault)) {
            // this works for windows as well
            return CLIOptions.get(main).getInstallationPath() + "logs/error.log";
        }
        return error_log_path;
    }

    public String getCookieDomain(@Nullable String currCDIVersion) {
        if (cookie_domain == null && Utils.CDIVersionNeedsToHaveCookieDomain(currCDIVersion)) {
            return "localhost";
        }
        return cookie_domain;
    }

    public boolean getCookieSecure(Main main) {
        return cookie_secure;
    }

    public int getSessionExpiredStatusCode() {
        return session_expired_status_code;
    }

    public int getPort(Main main) {
        Integer cliPort = CLIOptions.get(main).getPort();
        if (cliPort != null) {
            return cliPort;
        }
        return port;
    }

    public String getHost(Main main) {
        String cliHost = CLIOptions.get(main).getHost();
        if (cliHost != null) {
            return cliHost;
        }
        return host;
    }

    public int getMaxThreadPoolSize() {
        return max_server_pool_size;
    }

    public boolean getHttpsEnabled() {
        return webserver_https_enabled;
    }

    private String getConfigFileLocation(Main main) {
        return new File(CLIOptions.get(main).getConfigFilePath() == null
                ? CLIOptions.get(main).getInstallationPath() + "config.yaml"
                : CLIOptions.get(main).getConfigFilePath()).getAbsolutePath();
    }

    void validateAndInitialise(Main main) throws IOException {
        if (getConfigVersion() == -1) {
            throw new QuitProgramException(
                    "'core_config_version' is not set in the config.yaml file. Please redownload and install " +
                            "SuperTokens");
        }
        if (access_token_validity < 1 || access_token_validity > 86400000) {
            throw new QuitProgramException(
                    "'access_token_validity' must be between 1 and 86400000 seconds inclusive. The config file can be" +
                            " found here: " + getConfigFileLocation(main));
        }
        Boolean validityTesting = CoreConfigTestContent.getInstance(main)
                .getValue(CoreConfigTestContent.VALIDITY_TESTING);
        validityTesting = validityTesting == null ? false : validityTesting;
        if ((refresh_token_validity * 60) <= access_token_validity) {
            if (!Main.isTesting || validityTesting) {
                throw new QuitProgramException(
                        "'refresh_token_validity' must be strictly greater than 'access_token_validity'. The config " +
                                "file can be found here: " + getConfigFileLocation(main));
            }
        }

        if (max_server_pool_size <= 0) {
            throw new QuitProgramException("'max_server_pool_size' must be >= 1. The config file can be found here: " +
                    getConfigFileLocation(main));
        }

        if (!cookie_same_site.toLowerCase().equals("none") && !cookie_same_site.toLowerCase().equals("lax") &&
                !cookie_same_site.toLowerCase().equals("strict")) {
            throw new QuitProgramException("'cookie_same_site' must be either \"none\", \"strict\" or \"lax\"");
        }

        if (session_expired_status_code < 400 || session_expired_status_code >= 600) {
            throw new QuitProgramException(
                    "'session_expired_status_code' must be a value between 400 and 599, inclusive");
        }

        if (cookie_same_site.toLowerCase().equals("none") && !enable_anti_csrf) {
            throw new QuitProgramException(
                    "please set 'enable_anti_csrf' to true if 'cookie_same_site' is set to 'none'");
        }

        if (!getInfoLogPath(main).equals("null")) {
            File infoLog = new File(getInfoLogPath(main));
            if (!infoLog.exists()) {
                File parent = infoLog.getParentFile();
                if (parent != null) {
                    parent.mkdirs();
                }
                infoLog.createNewFile();
            }
        }

        if (!getErrorLogPath(main).equals("null")) {
            File errorLog = new File(getErrorLogPath(main));
            if (!errorLog.exists()) {
                File parent = errorLog.getParentFile();
                if (parent != null) {
                    parent.mkdirs();
                }
                errorLog.createNewFile();
            }
        }
    }

}