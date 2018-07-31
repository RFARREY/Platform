/**
 * Copyright 2015 Smart Society Services B.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 */
package com.alliander.osgp.adapter.ws.publiclighting.infra.jms;

import java.io.Serializable;

import org.joda.time.DateTime;

import com.alliander.osgp.shared.infra.jms.DeviceMessageMetadata;
import com.alliander.osgp.shared.infra.jms.RequestMessage;

public class PublicLightingRequestMessage extends RequestMessage {

    /**
     * Generated Serial Version UID.
     */
    private static final long serialVersionUID = 7881466312299271185L;
    private final PublicLightingRequestMessageType messageType;
    private final DateTime scheduleTime;
    private final Integer messagePriority;

    private PublicLightingRequestMessage(final DeviceMessageMetadata deviceMessageMetadata, final String ipAddress,
            final Serializable request) {
        super(deviceMessageMetadata.getCorrelationUid(), deviceMessageMetadata.getOrganisationIdentification(),
                deviceMessageMetadata.getDeviceIdentification(), ipAddress, request);
        this.messageType = PublicLightingRequestMessageType.valueOf(deviceMessageMetadata.getMessageType());
        this.messagePriority = deviceMessageMetadata.getMessagePriority();
        if (deviceMessageMetadata.getScheduleTime() == null) {
            this.scheduleTime = null;
        } else {
            this.scheduleTime = new DateTime(deviceMessageMetadata.getScheduleTime());
        }
    }

    public PublicLightingRequestMessageType getMessageType() {
        return this.messageType;
    }

    public DateTime getScheduleTime() {
        return this.scheduleTime;
    }

    public Integer getMessagePriority() {
        return this.messagePriority;
    }

    public static class Builder {
        private DeviceMessageMetadata deviceMessageMetadata;
        private String ipAddress;
        private Serializable request;

        public Builder() {
            // empty constructor
        }

        public Builder deviceMessageMetadata(final DeviceMessageMetadata deviceMessageMetadata) {
            this.deviceMessageMetadata = deviceMessageMetadata;
            return this;
        }

        public Builder ipAddress(final String ipAddress) {
            this.ipAddress = ipAddress;
            return this;
        }

        public Builder request(final Serializable request) {
            this.request = request;
            return this;
        }

        public PublicLightingRequestMessage build() {
            return new PublicLightingRequestMessage(this.deviceMessageMetadata, this.ipAddress, this.request);
        }
    }
}
