/**
 * Copyright (C) 2009-2015 the original author or authors.
 * See the notice.md file distributed with this work for additional
 * information regarding copyright ownership.
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

package org.fusesource.restygwt.client.codec;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.google.gwt.core.client.GWT;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.junit.client.GWTTestCase;

import org.fusesource.restygwt.client.JsonEncoderDecoder;

public class JsonIncludeEncoderTestGwt extends GWTTestCase {

    @JsonTypeInfo(use = Id.NAME, include = As.PROPERTY, property = "@type")
    @JsonSubTypes({ @Type(Thing.class) })
    @JsonTypeName("thing")
    public static class Thing {
        private String name;
        private String baz;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @JsonInclude(Include.NON_NULL)
        public String getBaz() {
            return baz;
        }

        public void setBaz(String baz) {
            this.baz = baz;
        }
    }

    public interface ThingCodec extends JsonEncoderDecoder<Thing> {
    }

    @Override
    public String getModuleName() {
        return "org.fusesource.restygwt.JsonIncludeEncoderTestGwt";
    }

    public void testEncodeJsonIncludeNonNull() {
        ThingCodec codec = GWT.create(ThingCodec.class);

        Thing thing = new Thing();
        thing.setBaz("quux");

        JSONValue thingJson = codec.encode(thing);
        System.out.println(thingJson);
        assertTrue(thingJson.isObject().containsKey("baz"));

        Thing thing2 = new Thing();

        JSONValue thingJson2 = codec.encode(thing2);
        System.out.println(thingJson2);
        assertFalse(thingJson2.isObject().containsKey("baz"));
    }
}
