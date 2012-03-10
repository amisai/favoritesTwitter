package org.okiju.pir.extractor;

import java.util.Set;

import org.okiju.pir.model.Entry;

public interface Extractor {
    Set<Entry> extract();
}
