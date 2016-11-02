package com.github.fabienrenaud.jjb.stream;

import com.fasterxml.jackson.core.JsonGenerator;
import com.github.fabienrenaud.jjb.JsonBench;
import com.github.fabienrenaud.jjb.JsonUtils;
import com.grack.nanojson.JsonAppendableWriter;
import com.grack.nanojson.JsonWriter;
import com.owlike.genson.stream.ObjectWriter;
import org.openjdk.jmh.annotations.Benchmark;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * @author Fabien Renaud
 */
public class Serialization extends JsonBench {

    @Benchmark
    @Override
    public Object orgjson() {
        return JSON_SOURCE.nextJsonAsOrgJsonObject().toString();
    }

    @Benchmark
    @Override
    public Object javaxjson() throws Exception {
        ByteArrayOutputStream baos = JsonUtils.byteArrayOutputStream();
        javax.json.JsonWriter jw = javax.json.Json.createWriter(baos);
        jw.writeObject(JSON_SOURCE.nextJsonAsJavaxJsonObject());
        jw.close();
        return baos;
    }

    @Benchmark
    @Override
    public Object jackson() throws Exception {
        ByteArrayOutputStream baos = JsonUtils.byteArrayOutputStream();
        try (JsonGenerator jGenerator = JSON_SOURCE.provider().jacksonFactory().createGenerator(baos)) {
            JSON_SOURCE.streamSerializer().jackson(jGenerator, JSON_SOURCE.nextPojo());
        }
        return baos;
    }

    @Benchmark
    @Override
    public Object gson() throws Exception {
        ByteArrayOutputStream baos = JsonUtils.byteArrayOutputStream();
        Writer w = new OutputStreamWriter(baos);
        try (com.google.gson.stream.JsonWriter jw = new com.google.gson.stream.JsonWriter(w)) {
            JSON_SOURCE.streamSerializer().gson(jw, JSON_SOURCE.nextPojo());
        }
        w.close();
        return baos;
    }

    @Benchmark
    @Override
    public Object genson() throws Exception {
        ByteArrayOutputStream os = JsonUtils.byteArrayOutputStream();
        ObjectWriter ow = JSON_SOURCE.provider().genson().createWriter(os);
        JSON_SOURCE.streamSerializer().genson(ow, JSON_SOURCE.nextPojo());
        ow.close();
        return os;
    }

    @Benchmark
    @Override
    public Object jsonio() throws Exception {
        return com.cedarsoftware.util.io.JsonWriter.objectToJson(JSON_SOURCE.nextPojo(), JSON_SOURCE.provider().jsonioStreamOptions());
    }

    @Benchmark
    @Override
    public Object nanojson() throws Exception {
        ByteArrayOutputStream os = JsonUtils.byteArrayOutputStream();
        JsonAppendableWriter ow = JsonWriter.on(os);
        JSON_SOURCE.streamSerializer().nanojson(ow, JSON_SOURCE.nextPojo());
        ow.done();
        return os;
    }
}
