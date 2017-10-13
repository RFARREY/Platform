/**
 * Copyright 2017 Smart Society Services B.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 */
package com.alliander.osgp.domain.core.valueobjects.smartmetering;

import java.io.Serializable;

import com.alliander.osgp.domain.core.valueobjects.DeviceFunction;
import com.alliander.osgp.shared.exceptionhandling.FunctionalException;

public class SetClockConfigurationRequestData implements Serializable, ActionRequest {

    private static final long serialVersionUID = -5007970690190618239L;

    protected final short timeZoneOffset;

    protected final CosemDateTime daylightSavingsBegin;

    protected final CosemDateTime daylightSavingsEnd;

    protected final boolean daylightSavingsEnabled;

    public SetClockConfigurationRequestData(final short timeZoneOffset, final CosemDateTime daylightSavingsBegin,
            final CosemDateTime daylightSavingsEnd, final boolean daylightSavingsEnabled) {
        this.timeZoneOffset = timeZoneOffset;
        this.daylightSavingsBegin = daylightSavingsBegin;
        this.daylightSavingsEnd = daylightSavingsEnd;
        this.daylightSavingsEnabled = daylightSavingsEnabled;
    }

    public short getTimeZoneOffset() {
        return this.timeZoneOffset;
    }

    public CosemDateTime getDaylightSavingsBegin() {
        return this.daylightSavingsBegin;
    }

    public CosemDateTime getDaylightSavingsEnd() {
        return this.daylightSavingsEnd;
    }

    public boolean isDaylightSavingsEnabled() {
        return this.daylightSavingsEnabled;
    }

    @Override
    public void validate() throws FunctionalException {
        // No validation needed
    }

    @Override
    public DeviceFunction getDeviceFunction() {
        return DeviceFunction.SET_CLOCK_CONFIGURATION;
    }

}
