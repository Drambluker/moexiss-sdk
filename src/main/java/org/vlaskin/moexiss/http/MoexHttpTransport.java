package org.vlaskin.moexiss.http;

import java.io.IOException;

@FunctionalInterface
public interface MoexHttpTransport
{
    String get(String url) throws IOException;
}
