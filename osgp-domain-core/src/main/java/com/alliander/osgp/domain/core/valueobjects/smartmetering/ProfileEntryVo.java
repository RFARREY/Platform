/**
 * Copyright 2017 Smart Society Services B.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 */
package com.alliander.osgp.domain.core.valueobjects.smartmetering;

import java.io.Serializable;
import java.util.List;

public class ProfileEntryVo implements Serializable {

    private static final long serialVersionUID = 991045734132231709L;

    private List<ProfileEntryValueVo> profileEntryValues;

    public ProfileEntryVo(List<ProfileEntryValueVo> profileEntryValues) {
        super();
        this.profileEntryValues = profileEntryValues;
    }

    public List<ProfileEntryValueVo> getProfileEntryValues() {
        return this.profileEntryValues;
    }
}
