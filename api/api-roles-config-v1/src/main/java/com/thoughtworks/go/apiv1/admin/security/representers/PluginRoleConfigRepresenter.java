/*
 * Copyright 2018 ThoughtWorks, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.thoughtworks.go.apiv1.admin.security.representers;


import com.thoughtworks.go.api.base.OutputWriter;
import com.thoughtworks.go.api.representers.JsonReader;
import com.thoughtworks.go.config.PluginRoleConfig;
import com.thoughtworks.go.domain.config.ConfigurationProperty;

import java.util.ArrayList;
import java.util.List;

public class PluginRoleConfigRepresenter {

    public static void toJSON(OutputWriter attributeWriter, PluginRoleConfig pluginRoleConfig) {
        attributeWriter.add("auth_config_id", pluginRoleConfig.getAuthConfigId())
            .addChildList("properties", propertiesWriter -> ConfigurationPropertyRepresenter.toJSON(propertiesWriter, pluginRoleConfig));
    }

    public static PluginRoleConfig fromJSON(JsonReader jsonReader) {
        PluginRoleConfig model = new PluginRoleConfig();
        if (jsonReader == null) {
            return model;
        }
        jsonReader.readStringIfPresent("auth_config_id", model::setAuthConfigId);
        jsonReader.readArrayIfPresent("properties", properties -> {
            List<ConfigurationProperty> configurationProperties = new ArrayList<>();
            properties.forEach(property -> {
                JsonReader configPropertyReader = new JsonReader(property.getAsJsonObject());
                ConfigurationProperty configurationProperty = ConfigurationPropertyRepresenter.fromJSON(configPropertyReader);
                configurationProperties.add(configurationProperty);
            });
            model.addConfigurations(configurationProperties);
        });
        return model;
    }

}
