/**
 * Copyright 2015 Smart Society Services B.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package com.alliander.osgp.adapter.ws.smartmetering.infra.jms.messageprocessor;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alliander.osgp.adapter.ws.schema.smartmetering.notification.NotificationType;
import com.alliander.osgp.adapter.ws.smartmetering.application.services.MeterResponseDataService;
import com.alliander.osgp.adapter.ws.smartmetering.application.services.NotificationService;
import com.alliander.osgp.adapter.ws.smartmetering.domain.entities.MeterResponseData;
import com.alliander.osgp.domain.core.valueobjects.DeviceFunction;
import com.alliander.osgp.shared.infra.jms.Constants;

@Component("domainSmartMeteringSetActivityCalendarResponseMessageProcessor")
public class SetActivityCalendarResponseMessageProcessor extends DomainResponseMessageProcessor {

    /**
     * Logger for this class
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SetActivityCalendarResponseMessageProcessor.class);

    @Autowired
    private MeterResponseDataService meterResponseDataService;

    @Autowired
    private NotificationService notificationService;

    protected SetActivityCalendarResponseMessageProcessor() {
        super(DeviceFunction.SET_ACTIVITY_CALENDAR);
    }

    @Override
    public void processMessage(final ObjectMessage message) throws JMSException {
        LOGGER.debug("Processing smart metering set Activity Calendar response message");

        String correlationUid = null;
        String messageType = null;
        String organisationIdentification = null;
        String deviceIdentification = null;

        String result = null;
        String notificationMessage = null;
        NotificationType notificationType = null;

        try {
            correlationUid = message.getJMSCorrelationID();
            messageType = message.getJMSType();
            organisationIdentification = message.getStringProperty(Constants.ORGANISATION_IDENTIFICATION);
            deviceIdentification = message.getStringProperty(Constants.DEVICE_IDENTIFICATION);

            result = message.getStringProperty(Constants.RESULT);
            notificationMessage = message.getStringProperty(Constants.DESCRIPTION);
            notificationType = NotificationType.valueOf(messageType);
        } catch (final JMSException e) {
            LOGGER.error("UNRECOVERABLE ERROR, unable to read ObjectMessage instance, giving up.", e);
            LOGGER.info("correlationUid: {}", correlationUid);
            LOGGER.info("messageType: {}", messageType);
            LOGGER.info("organisationIdentification: {}", organisationIdentification);
            LOGGER.info("deviceIdentification: {}", deviceIdentification);
            return;
        }

        try {
            LOGGER.info("Calling application service function to handle response: {}", messageType);

            final String resultString = (String) message.getObject();

            // Convert the events to entity and save the Set Activity Calendar
            // result
            final MeterResponseData meterResponseData = new MeterResponseData(organisationIdentification, messageType,
                    deviceIdentification, correlationUid, resultString);
            this.meterResponseDataService.enqueue(meterResponseData);

            // Notifying
            this.notificationService.sendNotification(organisationIdentification, deviceIdentification, result,
                    correlationUid, notificationMessage, notificationType);

        } catch (final Exception e) {
            this.handleError(e, correlationUid, organisationIdentification, deviceIdentification, notificationType);
        }
    }
}
