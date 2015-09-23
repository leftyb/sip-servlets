/*
 * TeleStax, Open Source Cloud Communications
 * Copyright 2011-2015, Telestax Inc and individual contributors
 * by the @authors tag.
 *
 * This program is free software: you can redistribute it and/or modify
 * under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation; either version 3 of
 * the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package org.mobicents.servlet.sip.undertow;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.http.HttpSession;

import org.mobicents.io.undertow.server.session.ConvergedInMemorySessionManager;
import org.mobicents.servlet.sip.core.MobicentsSipFactory;
import org.mobicents.servlet.sip.core.SipContext;
import org.mobicents.servlet.sip.core.SipManager;
import org.mobicents.servlet.sip.core.session.MobicentsSipApplicationSession;
import org.mobicents.servlet.sip.core.session.MobicentsSipApplicationSessionKey;
import org.mobicents.servlet.sip.core.session.MobicentsSipSession;
import org.mobicents.servlet.sip.core.session.MobicentsSipSessionKey;
import org.mobicents.servlet.sip.core.session.SipApplicationSessionKey;
import org.mobicents.servlet.sip.core.session.SipManagerDelegate;
import org.mobicents.servlet.sip.core.session.SipSessionKey;
import org.mobicents.servlet.sip.core.session.SipStandardManagerDelegate;
import org.mobicents.servlet.sip.message.SipFactoryImpl;
import org.mobicents.servlet.sip.startup.ConvergedServletContextImpl;

/**
 *
 * This class is based on org.mobicents.servlet.sip.catalina.SipStandardManager class from sip-servlet-as7 project,
 * re-implemented for jboss as8 (wildfly) by:
 *
 * @author kakonyi.istvan@alerant.hu
 *
 */
public class UndertowSipManager extends ConvergedInMemorySessionManager implements SipManager {

    private SipManagerDelegate sipManagerDelegate;
    private SipContext container;

    public UndertowSipManager(String id) {
        super(id);
        sipManagerDelegate = new SipStandardManagerDelegate();
    }

    public UndertowSipManager(String deploymentName, int maxSessions) {
        super(deploymentName, maxSessions);
        sipManagerDelegate = new SipStandardManagerDelegate();
    }

    public SipContext getContainer() {
        return container;
    }

    public void setContainer(SipContext container) {

        // De-register from the old Container (if any)
        // kakonyii: There is no such thing in wildfly, so we skip this part
        // if ((this.container != null) && (this.container instanceof Context))
        // ((Context) this.container).removePropertyChangeListener(this);

        this.container = container;
        sipManagerDelegate.setContainer(container);

        // Register with the new Container (if any)
        // kakonyii: There is no such thing in wildfly, so we skip this part
        // if ((this.container != null) && (this.container instanceof Context)) {
        // setMaxInactiveInterval
        // ( ((Context) this.container).getSessionTimeout()*60 );
        // ((Context) this.container).addPropertyChangeListener(this);
        // }
    }

    // There is no getNewSession() in wildfly (there was in catalina's StandardManager interface),
    // so we don't implement this method:
    // protected StandardSession getNewSession() {
    // }

    @Override
    public MobicentsSipSession removeSipSession(MobicentsSipSessionKey key) {
        return sipManagerDelegate.removeSipSession(key);
    }

    @Override
    public MobicentsSipApplicationSession removeSipApplicationSession(MobicentsSipApplicationSessionKey key) {
        return sipManagerDelegate.removeSipApplicationSession(key);
    }

    @Override
    public MobicentsSipApplicationSession getSipApplicationSession(MobicentsSipApplicationSessionKey key, boolean create) {
        return sipManagerDelegate.getSipApplicationSession((SipApplicationSessionKey) key, create);
    }

    @Override
    public MobicentsSipSession getSipSession(MobicentsSipSessionKey key, boolean create, MobicentsSipFactory sipFactoryImpl,
            MobicentsSipApplicationSession mobicentsSipApplicationSession) {
        return sipManagerDelegate.getSipSession((SipSessionKey) key, create, (SipFactoryImpl) sipFactoryImpl,
                mobicentsSipApplicationSession);
    }

    @Override
    public MobicentsSipApplicationSession findSipApplicationSession(HttpSession httpSession) {
        return sipManagerDelegate.findSipApplicationSession(httpSession);
    }

    @Override
    public void removeAllSessions() {
        sipManagerDelegate.removeAllSessions();

    }

    @Override
    public void setMobicentsSipFactory(MobicentsSipFactory sipFactoryImpl) {
        sipManagerDelegate.setSipFactoryImpl((SipFactoryImpl) sipFactoryImpl);

    }

    @Override
    public MobicentsSipFactory getMobicentsSipFactory() {
        return sipManagerDelegate.getSipFactoryImpl();
    }

    @Override
    public void dumpSipSessions() {
        sipManagerDelegate.dumpSipSessions();

    }

    @Override
    public void dumpSipApplicationSessions() {
        sipManagerDelegate.dumpSipApplicationSessions();

    }

    @Override
    public Iterator<MobicentsSipSession> getAllSipSessions() {

        return sipManagerDelegate.getAllSipSessions();
    }

    @Override
    public Iterator<MobicentsSipApplicationSession> getAllSipApplicationSessions() {
        return sipManagerDelegate.getAllSipApplicationSessions();
    }

    @Override
    public int getMaxActiveSipSessions() {
        return sipManagerDelegate.getMaxActiveSipSessions();
    }

    @Override
    public void setMaxActiveSipSessions(int max) {
        sipManagerDelegate.setMaxActiveSipSessions(max);

    }

    @Override
    public int getMaxActiveSipApplicationSessions() {
        return sipManagerDelegate.getMaxActiveSipApplicationSessions();
    }

    @Override
    public void setMaxActiveSipApplicationSessions(int max) {
        sipManagerDelegate.setMaxActiveSipApplicationSessions(max);

    }

    @Override
    public int getRejectedSipSessions() {

        return sipManagerDelegate.getRejectedSipSessions();
    }

    @Override
    public void setRejectedSipSessions(int rejectedSipSessions) {
        sipManagerDelegate.setRejectedSipSessions(rejectedSipSessions);

    }

    @Override
    public int getRejectedSipApplicationSessions() {

        return sipManagerDelegate.getRejectedSipApplicationSessions();
    }

    @Override
    public void setRejectedSipApplicationSessions(int rejectedSipApplicationSessions) {
        sipManagerDelegate.setRejectedSipApplicationSessions(rejectedSipApplicationSessions);

    }

    @Override
    public void setSipSessionCounter(int sipSessionCounter) {
        sipManagerDelegate.setSipSessionCounter(sipSessionCounter);

    }

    @Override
    public int getSipSessionCounter() {

        return sipManagerDelegate.getSipSessionCounter();
    }

    @Override
    public int getActiveSipSessions() {

        return sipManagerDelegate.getActiveSipSessions();
    }

    @Override
    public int getSipSessionMaxAliveTime() {
        return sipManagerDelegate.getSipSessionMaxAliveTime();
    }

    @Override
    public void setSipSessionMaxAliveTime(int sipSessionMaxAliveTime) {
        sipManagerDelegate.setSipSessionMaxAliveTime(sipSessionMaxAliveTime);

    }

    @Override
    public int getSipSessionAverageAliveTime() {
        return sipManagerDelegate.getSipSessionAverageAliveTime();
    }

    @Override
    public void setSipSessionAverageAliveTime(int sipSessionAverageAliveTime) {
        sipManagerDelegate.setSipSessionAverageAliveTime(sipSessionAverageAliveTime);

    }

    @Override
    public void setSipApplicationSessionCounter(int sipApplicationSessionCounter) {
        sipManagerDelegate.setSipApplicationSessionCounter(sipApplicationSessionCounter);

    }

    @Override
    public int getSipApplicationSessionCounter() {
        return sipManagerDelegate.getSipApplicationSessionCounter();
    }

    @Override
    public int getActiveSipApplicationSessions() {
        return sipManagerDelegate.getActiveSipApplicationSessions();
    }

    @Override
    public int getSipApplicationSessionMaxAliveTime() {
        return sipManagerDelegate.getSipApplicationSessionMaxAliveTime();
    }

    @Override
    public void setSipApplicationSessionMaxAliveTime(int sipApplicationSessionMaxAliveTime) {
        sipManagerDelegate.setSipApplicationSessionMaxAliveTime(sipApplicationSessionMaxAliveTime);

    }

    @Override
    public int getSipApplicationSessionAverageAliveTime() {
        return sipManagerDelegate.getSipApplicationSessionAverageAliveTime();
    }

    @Override
    public void setSipApplicationSessionAverageAliveTime(int sipApplicationSessionAverageAliveTime) {
        sipManagerDelegate.setSipApplicationSessionAverageAliveTime(sipApplicationSessionAverageAliveTime);

    }

    @Override
    public int getExpiredSipSessions() {

        return sipManagerDelegate.getExpiredSipSessions();
    }

    @Override
    public void setExpiredSipSessions(int expiredSipSessions) {
        sipManagerDelegate.setExpiredSipSessions(expiredSipSessions);

    }

    @Override
    public int getExpiredSipApplicationSessions() {

        return sipManagerDelegate.getExpiredSipApplicationSessions();
    }

    @Override
    public void setExpiredSipApplicationSessions(int expiredSipApplicationSessions) {
        sipManagerDelegate.setExpiredSipApplicationSessions(expiredSipApplicationSessions);

    }

    @Override
    public double getNumberOfSipApplicationSessionCreationPerSecond() {
        return sipManagerDelegate.getNumberOfSipApplicationSessionCreationPerSecond();
    }

    @Override
    public double getNumberOfSipSessionCreationPerSecond() {
        return sipManagerDelegate.getNumberOfSipSessionCreationPerSecond();
    }

    @Override
    public void updateStats() {
        sipManagerDelegate.updateStats();

    }

    @Override
    public Object findSession(String id) throws IOException {
        ConvergedServletContextImpl servletContext = (ConvergedServletContextImpl) container.getServletContext();
        return servletContext.getSession(id);
    }

}
