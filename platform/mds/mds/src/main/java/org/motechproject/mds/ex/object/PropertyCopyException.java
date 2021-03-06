package org.motechproject.mds.ex.object;

import org.motechproject.mds.ex.MdsException;

/**
 * Thrown when there was a problem with property creation.
 */
public class PropertyCopyException extends MdsException {

    private static final long serialVersionUID = 212286569748750001L;

    public PropertyCopyException(String message, Throwable cause) {
        super(message, cause);
    }
}
