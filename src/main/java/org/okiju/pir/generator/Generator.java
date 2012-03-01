package org.okiju.pir.generator;

import java.util.Set;

import org.okiju.pir.model.Entry;

public interface Generator {
    Set<Entry> generate();
}
