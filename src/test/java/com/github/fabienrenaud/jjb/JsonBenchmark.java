package com.github.fabienrenaud.jjb;

import com.github.fabienrenaud.jjb.model.Users;
import com.github.fabienrenaud.jjb.support.Api;
import com.github.fabienrenaud.jjb.support.BenchSupport;
import com.github.fabienrenaud.jjb.support.Library;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

/**
 * Created by frenaud on 7/23/16.
 */
public abstract class JsonBenchmark<T> {

    public static JsonBench BENCH;
    public static BenchSupport BENCH_SUPPORT;
    public static Api BENCH_API;

    private static final int ITERATIONS = 3;

    protected void test(final Library lib, final Object o) {
        if (o == null) { // means it shouldn't be supported.
            assertFalse("Library '" + lib + "' for api '" + BENCH_API + " returned null", supports(lib));
            return;
        }

        if (o instanceof Users) {
            testPojo((T) o);
        } else if (o instanceof com.cedarsoftware.util.io.JsonObject) {
            String v = com.cedarsoftware.util.io.JsonWriter.objectToJson(o, BENCH.JSON_SOURCE.provider().jsonioStreamOptions());
            testString(v);
        } else {
            testString(o.toString());
        }
    }

    private void testString(String v) {
        try {
            testPojo(BENCH.JSON_SOURCE.provider().jackson().readValue(v, pojoType()));
        } catch (IOException ex) {
            fail(ex.getMessage());
        }
    }

    private boolean supports(final Library lib) {
        return BENCH_SUPPORT.libapis().stream()
            .anyMatch((l) -> l.lib() == lib && l.api().contains(BENCH_API));
    }

    protected abstract void testPojo(T obj);

    protected abstract Class<T> pojoType();

    @Test
    public void gson() throws Exception {
        for (int i = 0; i < ITERATIONS; i++) {
            test(Library.GSON, BENCH.gson());
        }
    }

    @Test
    public void jackson() throws Exception {
        for (int i = 0; i < ITERATIONS; i++) {
            test(Library.JACKSON, BENCH.jackson());
        }
    }

    @Test
    public void jackson_afterburner() throws Exception {
        for (int i = 0; i < ITERATIONS; i++) {
            test(Library.JACKSON_AFTERBURNER, BENCH.jackson_afterburner());
        }
    }

    @Test
    public void orgjson() throws Exception {
        for (int i = 0; i < ITERATIONS; i++) {
            test(Library.ORGJSON, BENCH.orgjson());
        }
    }

    @Test
    public void genson() throws Exception {
        for (int i = 0; i < ITERATIONS; i++) {
            test(Library.GENSON, BENCH.genson());
        }
    }

    @Test
    public void javaxjson() throws Exception {
        for (int i = 0; i < ITERATIONS; i++) {
            test(Library.JAVAXJSON, BENCH.javaxjson());
        }
    }

    @Test
    public void flexjson() throws Exception {
        for (int i = 0; i < ITERATIONS; i++) {
            test(Library.FLEXJSON, BENCH.flexjson());
        }
    }

    @Test
    public void fastjson() throws Exception {
        for (int i = 0; i < ITERATIONS; i++) {
            test(Library.FASTJSON, BENCH.fastjson());
        }
    }

    @Test
    public void jsonio() throws Exception {
        for (int i = 0; i < ITERATIONS; i++) {
            test(Library.JSONIO, BENCH.jsonio());
        }
    }

    @Test
    public void boon() throws Exception {
        for (int i = 0; i < ITERATIONS; i++) {
            test(Library.BOON, BENCH.boon());
        }
    }

    @Test
    public void johnson() throws Exception {
        for (int i = 0; i < ITERATIONS; i++) {
            test(Library.JOHNZON, BENCH.johnzon());
        }
    }

    @Test
    public void jsonsmart() throws Exception {
        for (int i = 0; i < ITERATIONS; i++) {
            test(Library.JSONSMART, BENCH.jsonsmart());
        }
    }

    @Test
    public void dsljson() throws Exception {
        for (int i = 0; i < ITERATIONS; i++) {
            test(Library.DSLJSON, BENCH.dsljson());
        }
    }

    @Test
    public void logansquare() throws Exception {
        for (int i = 0; i < ITERATIONS; i++) {
            test(Library.LOGANSQUARE, BENCH.logansquare());
        }
    }

    @Test
    public void nanojson() throws Exception {
        for (int i = 0; i < ITERATIONS; i++) {
            test(Library.NANOJSON, BENCH.nanojson());
        }
    }
}
